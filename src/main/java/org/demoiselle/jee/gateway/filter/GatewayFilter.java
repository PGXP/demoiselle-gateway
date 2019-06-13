/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.demoiselle.jee.gateway.filter;

import java.io.IOException;
import java.util.logging.Logger;

import javax.annotation.Priority;
import javax.inject.Inject;
import static javax.ws.rs.Priorities.AUTHORIZATION;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.noContent;
import javax.ws.rs.ext.Provider;
import org.demoiselle.jee.core.api.security.Token;
import org.demoiselle.jee.core.api.security.TokenType;

/**
 *
 * @author SERPRO
 */
@Provider
@PreMatching
@Priority(AUTHORIZATION)
public class GatewayFilter implements ContainerRequestFilter {

    private static final Logger logger = Logger.getLogger(GatewayFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext req) throws IOException {
        Response.ResponseBuilder responseBuilder = noContent();
//        if (req.getMethod().equals("OPTIONS")) {
//            Response.ResponseBuilder responseBuilder = ok();
//            if (config.isCorsEnabled()) {
//                config.getParamsHeaderSecuriry().entrySet().parallelStream().forEach((entry) -> {
//                    responseBuilder.header(entry.getKey(), entry.getValue());
//                });
//            }
//            
//        }

        try {
            if (req.getHeaders().containsKey("Gateway")) {
                String chave = req.getHeaders().get("Gateway").toString().replace("[", "").replace("]", "");
                if (chave.isEmpty()) {
                    req.abortWith(responseBuilder.build());
                } else {
                    
                }
            }
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }
}