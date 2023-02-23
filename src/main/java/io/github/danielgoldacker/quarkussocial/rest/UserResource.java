package io.github.danielgoldacker.quarkussocial.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.github.danielgoldacker.quarkussocial.rest.dto.CreateUserRequest;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @POST
    public Response createUsers(CreateUserRequest userRequest){
        return Response.ok(userRequest).build();

    }

    @GET
    public Response listaAllUsers(){
        return Response.ok().build();
    }

}
