package org.demoiselle.jee.gateway.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import org.demoiselle.jee.crud.AbstractDAO;
import org.demoiselle.jee.gateway.entity.Resume;

/**
 *
 * @author gladson
 */
public class ResumeDAO extends AbstractDAO<Resume, UUID> {

    private static final Logger LOG = getLogger(ResumeDAO.class.getName());

    @PersistenceContext(unitName = "gatewayPU")
    protected EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Resume getCount(UUID id) {
        List<Resume> lista = em.createQuery("Select * from Recurso r GROUP BY r.name", Resume.class).setParameter("hoje", new Date(), TemporalType.DATE).getResultList();

        if (lista.isEmpty()) {
            return null;
        } else {
            return lista.get(0);
        }
    }
}
