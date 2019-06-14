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
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import org.demoiselle.jee.core.exception.DemoiselleException;
import org.demoiselle.jee.gateway.dao.HitDAO;
import org.demoiselle.jee.gateway.entity.Hit;

/**
 *
 * @author SERPRO
 */
@Provider
@PreMatching
@Priority(AUTHORIZATION)
public class GatewayFilter implements ContainerRequestFilter {

    private static final Logger logger = Logger.getLogger(GatewayFilter.class.getName());

    @Inject
    private HitDAO dao;

    @Override
    @Transactional
    public void filter(ContainerRequestContext req) throws IOException {
        try {
            if (req.getHeaders().containsKey("Gateway")) {

                String chave = req.getHeaders().get("Gateway").toString().replace("[", "").replace("]", "");

                if (chave.isEmpty()) {
                    req.abortWith(Response.noContent().build());
                }

                Hit hit = new Hit();
                hit.setCaminho(req.getUriInfo().getPath());
                hit.setDia(new Date());
                hit.setUsuario(UUID.fromString("966fc202-f2ef-423f-b64b-314274ca68b6"));

                dao.persist(hit);

                logger.info(chave);

            } else {
                req.abortWith(Response.ok("{ \"mensagem\":\"Utilize o parametro Gateway com sua chave de acesso, no cabecalho da requisicao\"}").build());
            }
        } catch (Exception e) {
            logger.severe(e.getMessage());
            throw new DemoiselleException("Falha no Gateway, informe ao administrador do sistema");
        }
    }
}
