package io.github.danielgoldacker.quarkussocial.rest;

import io.github.danielgoldacker.quarkussocial.domain.model.Follower;
import io.github.danielgoldacker.quarkussocial.domain.model.User;
import io.github.danielgoldacker.quarkussocial.domain.repository.FollowerRepository;
import io.github.danielgoldacker.quarkussocial.domain.repository.UserRepository;
import io.github.danielgoldacker.quarkussocial.rest.dto.FollowerPerUserResponse;
import io.github.danielgoldacker.quarkussocial.rest.dto.FollowerRequest;
import io.github.danielgoldacker.quarkussocial.rest.dto.FollowerResponse;

import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users/{userId}/followers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FollowerResource {

    private UserRepository userRepository;
    private FollowerRepository repository;

    @Inject
    public FollowerResource(FollowerRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }
    
    @PUT
    @Transactional
    public Response followUser(@PathParam("userId") Long userId, FollowerRequest request){
     
      if (userId.equals(request.getFollowerId())){
        return Response.status(Response.Status.CONFLICT).entity("you can't follow yourself").build();
      }

      User user = userRepository.findById(userId);
      if (user == null) {
          return Response.status(Response.Status.NOT_FOUND).build();
      }

      User follower = userRepository.findById(request.getFollowerId());      
      if (follower == null) {
        return Response.status(Response.Status.NOT_FOUND).build();
      }

      boolean follows = repository.follows(follower, user);
      if (!follows){
        Follower entity = new Follower(); 
        entity.setUser(user);
        entity.setFollower(follower);
  
        repository.persist(entity);
      }

      return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    public Response listFollowers(@PathParam("userId") Long userId){
      User user = userRepository.findById(userId);
      if (user == null) {
          return Response.status(Response.Status.NOT_FOUND).build();
      }

      var list = repository.findByUser(userId);
      FollowerPerUserResponse responseObject = new FollowerPerUserResponse();
      responseObject.setFollowersCount(list.size());

      var followersList = list.stream().map(fol -> new FollowerResponse(fol)).collect(Collectors.toList());
      responseObject.setFollower(followersList);
      return Response.ok(responseObject).build();
    }

    @DELETE
    @Transactional
    public Response unfollowUser(@PathParam("userId") Long userId, @QueryParam("followerId") Long followerId){

      User user = userRepository.findById(userId);
      if (user == null) {
          return Response.status(Response.Status.NOT_FOUND).build();
      }

      repository.deleteByFollowerAndUser(followerId, userId);

      return Response.status(Response.Status.NO_CONTENT).build();
    }
}
