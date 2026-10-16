package com.example.forumSP_BE.Schemas;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "spaces")
public class SpaceSchema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Name is mandatory")
    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    @NotBlank(message = "Owner is mandatory")
    private String owner;

    private String[] members;

    private String[] moderators;

    private int[] postsId = {};

    public SpaceSchema() {}

    public int getId() {return id;}
    public String getName() {return name;}
    public String getDescription() {return description;}
    public String getOwner() {return owner;}
    public String[] getMembers() {return members;}
    public String[] getModerators() {return moderators;}
    public int[] getPosts() {return postsId;}
    

    public void setName(String name) {this.name = name;}
    public void setDescription(String description) {this.description = description;}
    public void setOwner(String owner) {this.owner = owner;}
    public void setMembers(String[] members) {this.members = members;}
    public void setModerators(String[] moderators) {this.moderators = moderators;}
    public void addPost(int id) {
        int[] newPosts = new int[this.postsId.length + 1];
        newPosts[0] = id;
        for (int i = 0; i < this.postsId.length; i++) newPosts[i + 1] = this.postsId[i];
        this.postsId = newPosts;
    }
}
