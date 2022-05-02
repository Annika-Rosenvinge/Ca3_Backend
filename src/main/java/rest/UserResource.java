package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.UserFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.awt.*;

@Path("info")
public class UserResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final UserFacade FACADE =  UserFacade.getUserFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    @Context
    SecurityContext securityContext;
    @Context
    private UriInfo context;


    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String helloResource(){
        return "{\"msg\":\"Hello World\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user")
    @RolesAllowed("basic")
    public String getFromUser(){
        String thisUser = securityContext.getUserPrincipal().getName();
        return "{msg:" + "Hello user:" + thisUser + "}";

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/admin")
    @RolesAllowed("admin")
    public String getFromAdmin(){
        String thisAdmin = securityContext.getUserPrincipal().getName();
        return "{msg:" + "Hello admin:" + thisAdmin + "}";

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/count")
    public String count(){
        //String count =
        return "hej med dig";
    }

}
