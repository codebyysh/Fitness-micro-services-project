package com.fitness.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Email is Required")
    @Email(message = "Invalid Email Format")
    private String email ;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must have atleast 6 letters")
    private String password ;
    private String first_name ;
    private String last_name ;

}
