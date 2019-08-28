package org.demoiselle.jee.gateway.dao;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.demoiselle.jee.crud.AbstractDAO;
import org.demoiselle.jee.gateway.entity.Client;

/**
 *
 * @author gladson
 */
public class ClientDAO extends AbstractDAO<Client, Long> {

    private static final Logger LOG = getLogger(ClientDAO.class.getName());


    @PersistenceContext(unitName = "gatewayPU")
    protected EntityManager em;


    @Override
    protected EntityManager getEntityManager() {
        return em;
    }


    public Client find(UUID codigo) {
        List<Client> lista = em.createQuery("Select r from Client r WHERE r.usuario = :usuario ", Client.class).setParameter("usuario", codigo).getResultList();

        if (lista.isEmpty()) {
            return null;
        } else {
            return lista.get(0);
        }
    }
}
