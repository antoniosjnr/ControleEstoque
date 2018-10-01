package br.com.controleestoque.controller;

import br.com.controleestoque.model.Usuario;
import br.com.controleestoque.model.Utils;
import br.com.controleestoque.model.persistencia.UsuarioDAOJPA;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@ViewScoped
public class UsuarioMB {

    private Usuario usuario;    
    private String mensagem;

    public UsuarioMB() {        
    }
    
    @PostConstruct
    public void novo(){
        usuario = new Usuario();
        mensagem = Utils.STRING_EMPTY;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
    public void salvar(){
        EntityManager manager = this.getManager();
        UsuarioDAOJPA usuarioDAOJPA = new UsuarioDAOJPA(manager);
        usuarioDAOJPA.salvar(usuario);
    }

    public String isUsuarioPermitido() {
        EntityManager manager = this.getManager();
        UsuarioDAOJPA usuarioDAOJPA = new UsuarioDAOJPA(manager);

        if (!usuarioDAOJPA.temUsuarioPorLogin(usuario.getLogin())) {
            mensagem = "Usuário não cadastrado!";
            return "login";
        }

        if (!usuarioDAOJPA.temUsuarioPorLoginESenha(usuario.getLogin(),usuario.getSenha())) {
            mensagem = "Senha incorreta!";
            return "login";
        }
        
        return "listagem_produtos";
    }

    private EntityManager getManager() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        return (EntityManager) request.getAttribute("EntityManager");
    }
}
