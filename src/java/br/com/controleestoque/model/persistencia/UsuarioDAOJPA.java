package br.com.controleestoque.model.persistencia;

import br.com.controleestoque.model.Usuario;
import br.com.controleestoque.model.persistencia.dao.UsuarioDAO;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class UsuarioDAOJPA extends DAOJPA<Usuario, Integer> implements UsuarioDAO {

    EntityManager manager;

    public UsuarioDAOJPA(EntityManager entityManager) {
        super(entityManager);
        this.manager = entityManager;
    }

    public boolean temUsuarioPorLogin(String login) {
        Query q = this.manager.createQuery("select u from Usuario u where u.login = :login",Usuario.class);
        q.setParameter("login", login);
        
        return q.getResultList().size() > 0;
    }

    public boolean temUsuarioPorLoginESenha(String login, String senha) {
        Query q = this.manager.createQuery("select u from Usuario u where u.login = :login and u.senha = :senha", Usuario.class);
        q.setParameter("login", login);
        q.setParameter("senha", senha);

        return q.getResultList().size() > 0;
    }
}
