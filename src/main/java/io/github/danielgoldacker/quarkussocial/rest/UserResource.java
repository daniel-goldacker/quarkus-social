package io.github.danielgoldacker.quarkussocial.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import io.github.danielgoldacker.quarkussocial.rest.dto.CreateUserRequest;

@Path("/users")
public class UserResource {
    @POST
    public Response createUsers(CreateUserRequest userRequest){
        return Response.ok().build();

    }
}
