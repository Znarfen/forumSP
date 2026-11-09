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
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    @NotBlank(message = "Owner is mandatory")
    private String admin;

    private String[] members;

    private String[] moderators;

    private Long[] postsId = {};

    // Normal tags for filtering
    private String[] tags;

    // Super tag for filtering
    //private String sTag;

    public SpaceSchema() {}

    public Long getId() {return this.id;}
    public String getName() {return this.name;}
    public String getDescription() {return this.description;}
    public String getAdmin() {return this.admin;}
    public String[] getMembers() {return this.members;}
    public String[] getModerators() {return this.moderators;}
    public Long[] getPosts() {return this.postsId;}
    public String[] getTags() {return this.tags;}
    //public String getSuperTag() {return this.sTag;}

    /**
     * @param p (postion)
     * @return The post that is on that position.
     */

    public Long getPost(int p) {return this.postsId[p];}
    
    public void setName(String name) {this.name = name;}
    public void setDescription(String description) {this.description = description;}
    public void setAdmin(String admin) {this.admin = admin;}
    public void setMembers(String[] members) {this.members = members;}
    public void setModerators(String[] moderators) {this.moderators = moderators;}
    public void setTags(String[] tags) {this.tags = tags;}
    //public void setSuperTag(String sTag) {this.sTag = sTag;}

    public void addPost(Long id) {
        Long[] newPosts = new Long[this.postsId.length + 1];
        newPosts[0] = id;
        for (int i = 0; i < this.postsId.length; i++) newPosts[i + 1] = this.postsId[i];
        this.postsId = newPosts;
    }
}
