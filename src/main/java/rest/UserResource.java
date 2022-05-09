package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.UserDTO;
import facades.UserFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
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

    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed("admin")
    public Response delete(@PathParam("id") int id) throws NotFoundException, errorhandling.NotFoundException {
        UserDTO userDTO = new UserDTO(FACADE.delete(id));
        return Response.ok().entity(GSON.toJson(userDTO)).build();
    }

}
