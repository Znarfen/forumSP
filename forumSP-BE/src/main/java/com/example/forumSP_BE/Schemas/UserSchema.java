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

    private int[] likedPosts = {};
    
    private int[] dislikedPosts = {};

    /*  Might implement later

    @Pattern(regexp = "^(USER|ADMIN)$", message = "Role must be either 'USER', 'ADMIN'.")
    private String role = "USER"; // Default role is USER
    */

    public UserSchema() {}

    public Long getId() {return id;}
    public String getName() {return name;}
    public String getPassword() {return password;}
    public int[] getLikedPosts() {return likedPosts;}
    public int[] getDislikedPosts() {return dislikedPosts;}

    public void setName(String name) {this.name = name;}
    public void setPassword(String password) {this.password = password;}
    
    /** If like is true, will result in a like. If like is false, will result in a dislake.  */
    public void addVote(int postId, boolean like) {
        int[] curentPosts;
        if (like) curentPosts = this.likedPosts;
        else curentPosts = this.dislikedPosts;

        int[] newPosts = new int[curentPosts.length + 1];
        newPosts[0] = postId;
        for (int i = 0; i < curentPosts.length; i++) newPosts[i + 1] = curentPosts[i];
        
        if (like) this.likedPosts = newPosts;
        else this.dislikedPosts = newPosts;
    }
    /** Add a post ass a liked posts. */
    public void addVote(int postId) {addVote(postId, true);}

    /**  */
}