package com.karan.authservice.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.karan.authservice.entities.UserCreds;

@Repository
public interface UserCredRepository extends CrudRepository<UserCreds , Long> {

    Optional<UserCreds> findByUserId(String userId);

    Optional<UserCreds> findByUsername(String username);

}
