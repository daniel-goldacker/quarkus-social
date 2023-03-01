package io.github.danielgoldacker.quarkussocial.rest;

import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.github.danielgoldacker.quarkussocial.domain.model.Post;
import io.github.danielgoldacker.quarkussocial.domain.model.User;
import io.github.danielgoldacker.quarkussocial.domain.repository.FollowerRepository;
import io.github.danielgoldacker.quarkussocial.domain.repository.PostRepository;
import io.github.danielgoldacker.quarkussocial.domain.repository.UserRepository;
import io.github.danielgoldacker.quarkussocial.rest.dto.CreatePostRequest;
import io.github.danielgoldacker.quarkussocial.rest.dto.PostResponse;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;

@Path("/users/{userId}/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {

  private PostRepository repository;
  private UserRepository userRepository;
  private FollowerRepository followerRepository;

  @Inject
  public PostResource(PostRepository repository, UserRepository userRepository, FollowerRepository followerRepository ) {
    this.repository = repository;
    this.userRepository = userRepository;
    this.followerRepository = followerRepository;
  }

    
    @POST
    @Transactional
    public Response savePost(@PathParam("userId") Long userId, CreatePostRequest request){
      User user = userRepository.findById(userId);

      if (user != null) {
        Post post = new Post();
        post.setText(request.getText());
        post.setUser(user);

        repository.persist(post);
        return Response.status(Response.Status.CREATED).build(); 
      } else {
          return Response.status(Response.Status.NOT_FOUND).build();
      }
    }

    @GET
    public Response listPost(@PathParam("userId") Long userId, @HeaderParam("followerId") Long followeId){

      User user = userRepository.findById(userId);
      if (user == null) {
        return Response.status(Response.Status.NOT_FOUND).build();
      } 

      if (followeId  == null) {
        return Response.status(Response.Status.BAD_REQUEST).entity("you forgot the header followerId.").build();
      } 

      User follower = userRepository.findById(followeId);
      if (follower  == null) {
        return Response.status(Response.Status.BAD_REQUEST).entity("inexistent followerId.").build();
      } 

      boolean follows = followerRepository.follows(follower, user);
      if (!follows){
        return Response.status(Response.Status.FORBIDDEN).entity("you can't see these posts").build();  
      }

      PanacheQuery<Post> query = repository.find("user", Sort.by("dateTime", Sort.Direction.Descending),user);
      var list = query.list();
      var postResponseList = list.stream().map(pos ->PostResponse.fromEntity(pos)).collect(Collectors.toList());

      return Response.ok(postResponseList).build();
    }
}


