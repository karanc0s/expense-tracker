package com.karan.userservice.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true, nullable = false)
    private String userId;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private Long phoneNumber;

    private String profilePic;



}
