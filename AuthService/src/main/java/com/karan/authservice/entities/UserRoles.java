package com.karan.authservice.entities;
import jakarta.persistence.*;
import lombok.*;


@Setter
@Getter
@Entity
@ToString
@Table(name = "user_roles")
@AllArgsConstructor
@NoArgsConstructor
public class UserRoles extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_name" , nullable = false)
    private String roleName;

}
