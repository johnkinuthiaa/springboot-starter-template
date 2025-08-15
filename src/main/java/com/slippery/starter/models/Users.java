package com.slippery.starter.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

//we'll use mysql
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
// @Document if using mongodb
public class Users {
    @Id
//    comment this code id you're using mongo
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String username;
    private String email;
    private String password;
//    a user can be an admin and have another role based on the application needs
    private List<Role> roles =new ArrayList<>();
//    other values for the user

}
