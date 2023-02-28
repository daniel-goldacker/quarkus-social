package io.github.danielgoldacker.quarkussocial.rest;

import javax.ws.rs.core.MediaType;

import io.github.danielgoldacker.quarkussocial.domain.model.Follower;
import io.github.danielgoldacker.quarkussocial.domain.repository.UserRepository;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/users/{userId}/followers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FollowerResource {

    private UserRepository userRepository;
    private Follower repository;

    @Inject
    public FollowerResource(Follower repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }
    

}
