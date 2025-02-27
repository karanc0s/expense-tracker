package com.karan.authservice.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.karan.authservice.entities.RefreshToken;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken , Integer> {

    Optional<RefreshToken> findByuserID(String userId);

    Optional<RefreshToken> findByRefreshToken(String refreshToken);

}
