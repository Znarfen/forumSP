package com.example.forumSP_BE.Schemas;

import java.util.function.BiFunction;

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

    public Long getId() {return this.id;}
    public String getName() {return this.name;}
    public String getPassword() {return this.password;}
    public int[] getLikedPosts() {return this.likedPosts;}
    public int[] getDislikedPosts() {return this.dislikedPosts;}

    public void setName(String name) {this.name = name;}
    public void setPassword(String password) {this.password = password;}
    

    
    /** If like is set to true, will result in a like. If like is false, will result in a dislake.  */
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

    /** Remove vote. Returns a boo, true indicades succes. */
    public boolean removeVote(int postId) {

        boolean liked = checkVote(postId, true);
        if (liked == false && checkVote(postId, false) == false) return false;

        int[] votes =  liked ? this.likedPosts : this.dislikedPosts;
        int[] newVotes = new int[votes.length - 1];
        boolean found = false;

        for (int pos = 0; pos < votes.length; pos++) {
            if (votes[pos] == postId) found = true;
            newVotes[pos] = votes[found ? pos+1 : pos];
        }
        if (!found) return false;

        if (liked) this.likedPosts = newVotes;
        else this.dislikedPosts = newVotes;

        return true;
    }

    /** Check if vote exists. If like is set to true then it will go on liked posts otherwise it will go on dislikes. */
    private boolean checkVote(int postId, boolean like) {
        for (int post : like ? this.likedPosts : this.dislikedPosts) if (post == postId) return true;
        return false;
    }
}