/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.demoiselle.jee.gateway.filter;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.transaction.Transactional;
import static javax.ws.rs.Priorities.AUTHORIZATION;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import org.demoiselle.jee.core.exception.DemoiselleException;
import org.demoiselle.jee.gateway.dao.ClientDAO;
import org.demoiselle.jee.gateway.dao.HitDAO;
import org.demoiselle.jee.gateway.dao.ResumeDAO;
import org.demoiselle.jee.gateway.entity.Client;
import org.demoiselle.jee.gateway.entity.Hit;
import org.demoiselle.jee.gateway.entity.Resume;

/**
 *
 * @author SERPRO
 */
@Provider
@PreMatching
@Priority(AUTHORIZATION)
public class GatewayFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final Logger logger = Logger.getLogger(GatewayFilter.class.getName());

    @Inject
    private HitDAO hitDAO;

    @Inject
    private ResumeDAO resumeDAO;

    private Client client;

    @Inject
    private ClientDAO clientDAO;

    @Override
    @Transactional
    public void filter(ContainerRequestContext req) throws IOException {
        try {
            if (req.getHeaders().containsKey("Gateway")) {

                String chave = req.getHeaders().get("Gateway").toString().replace("[", "").replace("]", "");

                if (chave.isEmpty()) {
                    req.abortWith(Response.noContent().build());
                }

                UUID key = UUID.fromString(chave);

                client = clientDAO.find(key);

                if (client == null) {
                    req.abortWith(Response.ok("{ \"mensagem\":\"Chave não cadastrada\"}").build());
                }

                Resume resume = resumeDAO.find(key);

                if (resume.getQtde() >= client.getQtde()) {
                    req.abortWith(Response.ok("{ \"mensagem\":\"Limite atingido para esse dia\"}").build());
                }

                client.setTotal(resume.getQtde());

                Hit hit = new Hit();
                hit.setCaminho(req.getUriInfo().getPath());
                hit.setDia(new Date());
                hit.setUsuario(UUID.fromString(chave));

                hitDAO.persist(hit);

            } else {
                req.abortWith(Response.ok("{ \"mensagem\":\"Utilize o parametro Gateway com sua chave de acesso, no cabecalho da requisicao\"}").build());
            }
        } catch (Exception e) {
            logger.severe(e.getMessage());
            throw new DemoiselleException("Falha no Gateway, informe ao administrador do sistema");
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        responseContext.getHeaders().putSingle("Demoiselle-gateway", "Enable");

        if (client != null) {
            responseContext.getHeaders().putSingle("gateway-count", "" + client.getTotal() + "/" + client.getQtde());
        }

    }
}
