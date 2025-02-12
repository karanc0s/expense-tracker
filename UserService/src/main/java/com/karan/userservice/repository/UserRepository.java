package com.karan.userservice.repository;

import com.karan.userservice.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<UserInfo , String> {

    Optional<UserInfo> findByUserId(String userId);
}
