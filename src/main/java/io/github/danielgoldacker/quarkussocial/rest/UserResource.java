package io.github.danielgoldacker.quarkussocial.rest;

import java.util.Set;

import javax.inject.Inject;
import javax.persistence.Entity;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import io.github.danielgoldacker.quarkussocial.domain.model.User;
import io.github.danielgoldacker.quarkussocial.domain.repository.UserRepository;
import io.github.danielgoldacker.quarkussocial.rest.dto.CreateUserRequest;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

   private UserRepository repository;
   private Validator validator;

    @Inject
    public UserResource(UserRepository repository, Validator validator){
        this.repository = repository;
        this.validator = validator;
    }
    
    @POST
    @Transactional
    public Response createUsers(CreateUserRequest userRequest){
        
        Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(userRequest);
        if (!violations.isEmpty()){
            ConstraintViolation<CreateUserRequest> erro = violations.stream().findAny().get();
            String erroMessage = erro.getMessage();
            return Response.status(400).entity(erroMessage).build();
        }

        User user = new User();
        user.setName(userRequest.getName());
        user.setAge(userRequest.getAge());

        repository.persist(user);

        return Response.ok(user).build();
    }

    @GET
    public Response listaAllUsers(){
        PanacheQuery<User> query = repository.findAll();

        return Response.ok(query.list()).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteUser(@PathParam("id") Long id) {
        User user = repository.findById(id);

        if (user != null) {
            repository.delete(user);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
 
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response updateUser(@PathParam("id") Long id,  CreateUserRequest userRequest) {
        User user = repository.findById(id);

        if (user != null) {
            user.setName(userRequest.getName());
            user.setAge(userRequest.getAge());
            return Response.ok(userRequest).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
