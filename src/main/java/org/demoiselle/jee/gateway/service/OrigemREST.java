package org.demoiselle.jee.gateway.service;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.Response;
import org.demoiselle.jee.gateway.dao.RecursoDAO;

/**
 *
 * @author SERPRO
 */
@Path("origens")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class OrigemREST {

    @Inject
    private RecursoDAO dao;
    
    @GET
    @Path("length")
    public Response getOrigensLength(){
        return Response.ok(dao.getOrigensLength()).build();
    }
    
    @GET
    @Path("count")
    public Response getOrigensCount(){
        return Response.ok(dao.getOrigensCount()).build();
    }

}
