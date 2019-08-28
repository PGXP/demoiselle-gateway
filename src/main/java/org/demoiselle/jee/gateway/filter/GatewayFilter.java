package org.demoiselle.jee.gateway.filter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;
import static java.util.UUID.fromString;
import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import static javax.ws.rs.Priorities.AUTHORIZATION;
import static javax.ws.rs.Priorities.HEADER_DECORATOR;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.noContent;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.noContent;
import javax.ws.rs.ext.Provider;
import org.demoiselle.jee.gateway.annotation.Gateway;
import org.demoiselle.jee.gateway.dao.ClientDAO;
import org.demoiselle.jee.gateway.dao.HitDAO;
import org.demoiselle.jee.gateway.dao.ResumeDAO;
import org.demoiselle.jee.gateway.entity.Client;
import org.demoiselle.jee.gateway.entity.Hit;
import org.demoiselle.jee.gateway.entity.Resume;
import org.demoiselle.jee.rest.exception.DemoiselleRestException;

/**
 *
 * @author SERPRO
 */
@Provider
@Priority(AUTHORIZATION)
public class GatewayFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final Logger logger = getLogger(GatewayFilter.class.getName());

    @Inject
    private HitDAO hitDAO;

    @Inject
    private ResumeDAO resumeDAO;

    private Client client;

    @Inject
    private ClientDAO clientDAO;

    @Context
    private ResourceInfo info;

    @Context
    private HttpServletRequest httpServletRequest;

    @Override
    @Transactional
    public void filter(ContainerRequestContext req) throws IOException {
        Method method = info.getResourceMethod();
        Class<?> classe = info.getResourceClass();

        if (method != null && classe != null && (method.getAnnotation(Gateway.class) != null || classe.getAnnotation(Gateway.class) != null)) {

            if (req.getHeaders().containsKey("Gateway")) {

                String chave = req.getHeaders().get("Gateway").toString().replace("[", "").replace("]", "");

                if (chave.isEmpty()) {
                    req.abortWith(noContent().build());
                }

                UUID key = fromString(chave);

                client = clientDAO.find(key);

                if (client == null) {
                    req.abortWith(Response.status(Response.Status.BAD_REQUEST).entity("{ \"mensagem\":\"Chave não cadastrada\"}").build());
                } else {

                    Resume resume = resumeDAO.getCount(key, new Date());

                    if (resume != null && resume.getQtde() != null && resume.getQtde() >= client.getQtde()) {
                        client.setTotal(resume.getQtde());
                        req.abortWith(Response.status(Response.Status.BAD_GATEWAY).entity("{ \"mensagem\":\"Limite atingido para esse dia\"}").build());
                    } else {

                        client.setTotal(resume == null ? 0 : (resume.getQtde() == null ? 0 : resume.getQtde()));

                        Hit hit = new Hit();
                        hit.setOrigem(getIp());
                        hit.setCaminho(req.getUriInfo().getPath());
                        hit.setUsuario(fromString(chave));

                        hitDAO.persist(hit);
                    }
                    req.getHeaders().putSingle("gateway-count", "" + client.getTotal() + "/" + client.getQtde());
                }
            } else {
                req.abortWith(Response.status(Response.Status.BAD_GATEWAY).entity("{ \"mensagem\":\"Utilize o parametro Gateway com sua chave de acesso, no cabecalho da requisicao\"}").build());
            }

        }

    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        responseContext.getHeaders().putSingle("Demoiselle-gateway", "Enable");

        if (client != null) {
            responseContext.getHeaders().putSingle("gateway-count", "" + client.getTotal() + "/" + client.getQtde());
        }

    }

    private String getIp() {
        String client_ip = httpServletRequest.getHeader("x-real-ip");
        if (client_ip == null || client_ip.isEmpty()) { // extract from forward ips
            String ipForwarded = httpServletRequest.getHeader("x-forwarded-for");
            String[] ips = ipForwarded == null ? null : ipForwarded.split(",");
            client_ip = (ips == null || ips.length == 0) ? null : ips[0];

            // extract from remote addr
            client_ip = (client_ip == null || client_ip.isEmpty()) ? httpServletRequest.getRemoteAddr() : client_ip;
        }
        return client_ip;
    }
}
