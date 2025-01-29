package com.karan.authservice.entities;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "user_role")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRole extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_name" , nullable = false)
    private String roleName;

}
