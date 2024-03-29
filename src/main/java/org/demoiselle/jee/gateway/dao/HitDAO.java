package org.demoiselle.jee.gateway.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import static javax.persistence.TemporalType.DATE;
import org.demoiselle.jee.crud.AbstractDAO;
import org.demoiselle.jee.gateway.entity.Hit;
import org.demoiselle.jee.gateway.entity.Resume;

/**
 *
 * @author gladson
 */
public class HitDAO extends AbstractDAO<Hit, Long> {

    private static final Logger LOG = getLogger(HitDAO.class.getName());


    @PersistenceContext(unitName = "gatewayPU")
    protected EntityManager em;


    @Override
    protected EntityManager getEntityManager() {
        return em;
    }


    public Resume getCount(UUID id) {
        List<Resume> lista = em.createQuery("Select * from Recurso r GROUP BY r.name", Resume.class).setParameter("hoje", new Date(), DATE).getResultList();

        if (lista.isEmpty()) {
            return null;
        } else {
            return lista.get(0);
        }
    }
}
