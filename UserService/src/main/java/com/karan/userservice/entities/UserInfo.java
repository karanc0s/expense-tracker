package com.karan.userservice.entities;

//@Table(name = "users")
public class UserInfo extends BaseEntity {

    private long id;

    private String userId;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String profilePic;
}
