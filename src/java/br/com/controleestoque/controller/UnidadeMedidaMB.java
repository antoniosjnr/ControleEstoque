package br.com.controleestoque.controller;

import br.com.controleestoque.model.UnidadeMedida;
import br.com.controleestoque.model.Utils;
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
public class UnidadeMedidaMB {

    private UnidadeMedida unidadeMedida;
    private String mensagem;

    public UnidadeMedidaMB() {
    }

    public UnidadeMedida getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    private EntityManager getManager() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        return (EntityManager) request.getAttribute("EntityManager");
    }

    @PostConstruct
    public void novo() {
        this.unidadeMedida = new UnidadeMedida();
        mensagem = Utils.STRING_EMPTY;
    }

    public void salvar() {
        EntityManager manager = this.getManager();

        if (unidadeMedida.getDescricao().equals(Utils.STRING_EMPTY)) {
            mensagem = "Informe uma descrição!";
        }

        if (temUnidadeMedidaMesmoNome(unidadeMedida.getDescricao())) {
            mensagem = "Já existe uma unidade de medida cadastrada com a descrição informada!";
        }

        if (!unidadeMedida.getDescricao().equals(Utils.STRING_EMPTY) && temUnidadeMedidaMesmoNome(unidadeMedida.getDescricao())) {
            mensagem = Utils.STRING_EMPTY;
            UnidadeMedidaDAOJPA unidadeMedidaDAOJPA = new UnidadeMedidaDAOJPA(manager);
            unidadeMedidaDAOJPA.salvar(unidadeMedida);
        }
    }

    public List<UnidadeMedida> getAll() {
        EntityManager manager = this.getManager();
        UnidadeMedidaDAOJPA unidadeMedidaDAOJPA = new UnidadeMedidaDAOJPA(manager);
        return unidadeMedidaDAOJPA.buscarTodos(UnidadeMedida.class);
    }

    public void buscarPorId() {
        EntityManager manager = this.getManager();
        UnidadeMedidaDAOJPA unidadeMedidaDAOJPA = new UnidadeMedidaDAOJPA(manager);
        this.unidadeMedida = unidadeMedidaDAOJPA.buscarPorId(UnidadeMedida.class, unidadeMedida.getId());
    }

    public boolean temUnidadeMedidaMesmoNome(String descricao) {
        EntityManager manager = this.getManager();
        UnidadeMedidaDAOJPA unidadeMedidaDAOJPA = new UnidadeMedidaDAOJPA(manager);
        return unidadeMedidaDAOJPA.temUnidadeMedidaMesmoNome(descricao);
    }

    public void remove() {
        EntityManager manager = this.getManager();
        UnidadeMedidaDAOJPA unidadeMedidaDAOJPA = new UnidadeMedidaDAOJPA(manager);
        unidadeMedidaDAOJPA.remover(UnidadeMedida.class, unidadeMedida.getId());
    }
}
