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

    public Resume getCount(UUID id, Date data) {
        List<Resume> lista = em.createQuery("Select r from Resume r where r.id = :id and r.dia = :hoje ", Resume.class)
                .setParameter("id", id)
                .setParameter("hoje", data, DATE)
                .getResultList();

        if (lista.isEmpty()) {
            return null;
        } else {
            return lista.get(0);
        }
    }

    public Resume getCount(UUID id) {
        List<Resume> lista = em.createQuery("Select r from Resume r where r.id = :id ", Resume.class)
                .setParameter("id", id)
                .getResultList();

        if (lista.isEmpty()) {
            return null;
        } else {
            return lista.get(0);
        }
    }
}
