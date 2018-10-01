package br.com.controleestoque.model.persistencia;

import br.com.controleestoque.model.UnidadeMedida;
import br.com.controleestoque.model.persistencia.dao.UnidadeMedidaDAO;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class UnidadeMedidaDAOJPA extends DAOJPA<UnidadeMedida,Integer> implements UnidadeMedidaDAO{
    
    EntityManager manager;
    
    public UnidadeMedidaDAOJPA(EntityManager entityManager) {
        super(entityManager);
        this.manager = entityManager;
    }
    
    public boolean temUnidadeMedidaMesmoNome(String descricao) {
        Query q = this.manager.createQuery("select u from UnidadeMedida u where upper(u.descricao) = :descricao",UnidadeMedida.class);
        q.setParameter("descricao", descricao.toUpperCase());
        
        return q.getResultList().size() > 0;
    }
}
