package com.slippery.starter.repository;

import com.slippery.starter.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

// using mysql --comment this if using mongodb
public interface UserRepository extends JpaRepository<Users,String> {

    Optional<Users> findByUsername(String username);
}

/* using mongodb

public interface UserRepository extends MongoRepository<Users,String>{
    Optional<Users> findByUsername(String username);
}

 */
