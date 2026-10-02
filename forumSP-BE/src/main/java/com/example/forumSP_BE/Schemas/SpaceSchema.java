package com.example.forumSP_BE.Schemas;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "spaces")
public class SpaceSchema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    @NotBlank(message = "Owner is mandatory")
    private String owner;

    private String[] members;

    private String[] moderators;

    @Transient
    private PostSchema[] posts;

    public SpaceSchema() {}

    public Long getId() {return id;}
    public String getName() {return name;}
    public String getDescription() {return description;}
    public String getOwner() {return owner;}
    public String[] getMembers() {return members;}
    public String[] getModerators() {return moderators;}
    public PostSchema[] getPosts() {return posts;}
    

    public void setName(String name) {this.name = name;}
    public void setDescription(String description) {this.description = description;}
    public void setPosts(PostSchema[] posts) {this.posts = posts;}
    public void setOwner(String owner) {this.owner = owner;}
    public void setMembers(String[] members) {this.members = members;}
    public void setModerators(String[] moderators) {this.moderators = moderators;}
}
