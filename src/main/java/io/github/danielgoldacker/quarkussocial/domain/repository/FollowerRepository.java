package io.github.danielgoldacker.quarkussocial.domain.repository;

import javax.enterprise.context.ApplicationScoped;

import io.github.danielgoldacker.quarkussocial.domain.model.Follower;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class FollowerRepository implements PanacheRepository<Follower> {
    
}
