package org.demoiselle.jee.gateway.dao;

import java.util.List;
import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.demoiselle.jee.gateway.entity.Recurso;
import org.demoiselle.jee.crud.AbstractDAO;

/**
 *
 * @author gladson
 */
public class RecursoDAO extends AbstractDAO<Recurso, Long> {

    private static final Logger LOG = getLogger(RecursoDAO.class.getName());

    @PersistenceContext(unitName = "gatewayPU")
    protected EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List getRecursosLength() {
        return em.createQuery("Select r.name as nome, SUM(r.tamanho) as soma, AVG(r.tamanho) as media, MAX(r.tamanho) as max, MIN(r.tamanho) as min from Recurso r GROUP BY r.name").getResultList();
    }

    public List getRecursosCount() {
        return em.createQuery("Select r.name, COUNT(r.name) from Recurso r  GROUP BY r.name").getResultList();
    }

    public List getOrigensLength() {
        return em.createQuery("Select r.origem, SUM(r.tamanho) as soma, AVG(r.tamanho) as media, MAX(r.tamanho) as max, MIN(r.tamanho) as min from Recurso r GROUP BY r.origem").getResultList();
    }

    public List getOrigensCount() {
        return em.createQuery("Select r.origem, COUNT(r.origem) from Recurso r  GROUP BY r.origem").getResultList();
    }
}
