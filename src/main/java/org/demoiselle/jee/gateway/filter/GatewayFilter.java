/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.demoiselle.jee.gateway.filter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.transaction.Transactional;
import static javax.ws.rs.Priorities.HEADER_DECORATOR;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import javax.ws.rs.ext.Provider;
import org.demoiselle.jee.gateway.annotation.Gateway;
import org.demoiselle.jee.gateway.dao.ClientDAO;
import org.demoiselle.jee.gateway.dao.HitDAO;
import org.demoiselle.jee.gateway.dao.ResumeDAO;
import org.demoiselle.jee.gateway.entity.Client;
import org.demoiselle.jee.gateway.entity.Hit;
import org.demoiselle.jee.rest.exception.DemoiselleRestException;

/**
 *
 * @author SERPRO
 */
@Provider
@Priority(HEADER_DECORATOR)
public class GatewayFilter implements ContainerResponseFilter {

    private static final Logger logger = Logger.getLogger(GatewayFilter.class.getName());

    @Inject
    private HitDAO hitDAO;

    @Inject
    private ResumeDAO resumeDAO;

    @Inject
    private ClientDAO clientDAO;

    @Context
    private ResourceInfo info;

    @Override
    @Transactional
    public void filter(ContainerRequestContext req, ContainerResponseContext res) throws IOException {

        res.getHeaders().putSingle("Demoiselle-gateway", "Enable");

        Method method = info.getResourceMethod();
        Class<?> classe = info.getResourceClass();

        if (method != null && classe != null && (method.getAnnotation(Gateway.class) != null || classe.getAnnotation(Gateway.class) != null)) {

            if (req.getHeaders().containsKey("Gateway")) {

                String chave = req.getHeaders().get("Gateway").toString().replace("[", "").replace("]", "");

                if (chave.isEmpty()) {
                    req.abortWith(Response.noContent().build());
                }

                UUID key = UUID.fromString(chave);

                Client client = clientDAO.find(key);

                if (client == null) {
                    throw new DemoiselleRestException("Chave não cadastrada - " + chave, BAD_REQUEST.getStatusCode());
                }

                Integer resume = resumeDAO.getCount(key);

                if (resume == null) {
                    resume = 1;
                }

                if (resume >= client.getQtde()) {
                    throw new DemoiselleRestException("Limite atingido para esse dia - " + resume + "/" + client.getQtde(), BAD_REQUEST.getStatusCode());
                }

                Hit hit = new Hit();
                hit.setCaminho(req.getUriInfo().getPath());
                hit.setDia(new Date());
                hit.setUsuario(UUID.fromString(chave));

                hitDAO.persist(hit);

                res.getHeaders().putSingle("gateway-count", "" + resume + "/" + client.getQtde());

            } else {
                throw new DemoiselleRestException("Utilize o parametro Gateway com sua chave de acesso, no cabecalho da requisicao", BAD_REQUEST.getStatusCode());
            }

        }

    }
}
