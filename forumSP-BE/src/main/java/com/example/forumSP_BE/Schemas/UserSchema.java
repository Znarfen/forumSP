package com.example.forumSP_BE.Schemas;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "users")
public class UserSchema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(unique = true)
    private String name;

    @NotBlank(message = "Password is required")
    private String password;

    /*  Might implement later

    @Pattern(regexp = "^(USER|ADMIN)$", message = "Role must be either 'USER', 'ADMIN'.")
    private String role = "USER"; // Default role is USER
    */

    public UserSchema() {}

    public Long getId() {return id;}
    public String getName() {return name;}
    public String getPassword() {return password;}

    public void setName(String name) {this.name = name;}
    public void setPassword(String password) {this.password = password;}
}