package br.com.controleestoque.controller;

import br.com.controleestoque.model.Produto;
import br.com.controleestoque.model.UnidadeMedida;
import br.com.controleestoque.model.Utils;
import br.com.controleestoque.model.persistencia.ProdutoDAOJPA;
import br.com.controleestoque.model.persistencia.UnidadeMedidaDAOJPA;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@ViewScoped
public class ProdutoMB {

    private Produto produto;
    private String mensagem;
    private int idUnidadeMedida;
    
    public ProdutoMB() {
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public int getIdProduto() {
        return idUnidadeMedida;
    }

    public void setIdProduto(int idProduto) {
        this.idUnidadeMedida = idProduto;
    }
    
    private EntityManager getManager() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        return (EntityManager) request.getAttribute("EntityManager");
    }
    
    @PostConstruct
    public void novo(){
        produto = new Produto();
        mensagem = Utils.STRING_EMPTY;
    }
    
    public void salvar() {
        EntityManager manager = this.getManager();
        
        UnidadeMedidaDAOJPA unidadeMedidaDAOJPA = new UnidadeMedidaDAOJPA(manager);        
        ProdutoDAOJPA produtoDAOJPA = new ProdutoDAOJPA(manager);
        
        UnidadeMedida unidadeMedidaProduto = unidadeMedidaDAOJPA.buscarPorId(UnidadeMedida.class, idUnidadeMedida);
        
        produto.setUnidadeMedida(unidadeMedidaProduto);
        
        produtoDAOJPA.salvar(produto);
        novo();
    }
    
    public void remove(){
        EntityManager manager = this.getManager();
        ProdutoDAOJPA produtoDAOJPA = new ProdutoDAOJPA(manager);
        produtoDAOJPA.remover(Produto.class,produto.getId());
    }
    
    public Produto buscarPorId(){
        EntityManager manager = this.getManager();
        ProdutoDAOJPA produtoDAOJPA = new ProdutoDAOJPA(manager);
        return produtoDAOJPA.buscarPorId(Produto.class, produto.getId());
    }
    
    public List<Produto> getAll(){
        EntityManager manager = this.getManager();
        ProdutoDAOJPA produtoDAOJPA = new ProdutoDAOJPA(manager);        
        return produtoDAOJPA.buscarTodos(Produto.class);        
    }
}
