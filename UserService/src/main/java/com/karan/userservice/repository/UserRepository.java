package com.karan.userservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.karan.userservice.entities.UserInfo;

@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<UserInfo , Long> {

    Optional<UserInfo> findByUserId(String userId);
}
