package com.myproject.expensetrackerapi.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserModel {

    @NotBlank(message = "please enter name ")
    private String name;

    @NotNull(message = "please enter email")
    @Email(message = "please enter valid email")
    private String email;

    @NotNull(message = "please enter password")
    @Size(min = 5,message = "password should be atleast 5 characters")
    private String password;

    private Long age =0L;
}
