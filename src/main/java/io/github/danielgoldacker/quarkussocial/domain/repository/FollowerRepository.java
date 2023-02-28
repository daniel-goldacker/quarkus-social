package io.github.danielgoldacker.quarkussocial.domain.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import io.github.danielgoldacker.quarkussocial.domain.model.Follower;
import io.github.danielgoldacker.quarkussocial.domain.model.User;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class FollowerRepository implements PanacheRepository<Follower> {
    public boolean follows(User follower, User user) {

        Map<String, Object> param = new HashMap<>();
        param.put("follower", follower);
        param.put("user", user);

        PanacheQuery<Follower> query = find("follower  = :follower and user = :user", param);
        Optional<Follower> result = query.firstResultOptional();
        
        return result.isPresent();
    }

    public List<Follower> findByUser(Long userId){
        PanacheQuery<Follower> query = find("user.id", userId);
        
        return query.list();
    }
}
