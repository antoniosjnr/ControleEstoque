package br.com.controleestoque.model.persistencia;

import br.com.controleestoque.model.Produto;
import br.com.controleestoque.model.persistencia.dao.ProdutoDAO;
import javax.persistence.EntityManager;

public class ProdutoDAOJPA extends DAOJPA<Produto,Integer> implements ProdutoDAO {
    
    EntityManager manager;
    
    public ProdutoDAOJPA(EntityManager entityManager) {
        super(entityManager);
        this.manager = entityManager;
    }    
}
