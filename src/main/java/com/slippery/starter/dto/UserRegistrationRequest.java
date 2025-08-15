package com.slippery.starter.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.slippery.starter.models.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRegistrationRequest {
    @NotBlank(message = "Username should not be blank")
    private String username;
    @Email
    private String email;
    @NotBlank(message = "Passwords should not be blank")
    @Size(min = 6,max = 20,message = "passwords should not be less than 6 characters or more than 20 ")
    private String password;
    private List<Role> roles =new ArrayList<>();
//    add other user values
}
