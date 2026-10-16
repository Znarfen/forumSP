package com.example.forumSP_BE.Schemas;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table (name = "posts")
public class PostSchema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank (message = "Title is required")
    private String title;

    @NotBlank (message = "Content is required")
    private String content;

    @NotBlank (message = "Author is required")
    private String author;

    private String space;

    private int uppVotes = 0;

    private int downVotes = 0;

    public PostSchema() {}

    public PostSchema(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public int getId() {return id;}
    public String getTitle() {return title;}
    public String getContent() {return content;}
    public String getAuthor() {return author;}
    public String getBelonging() {return space;}
    public int getPosVotes() {return uppVotes;}
    public int getNegVotes() {return downVotes;}
    /** Return the difrence of all upp- and down- votes */
    public int controversy() {return uppVotes-downVotes;}
    /** Return the sum of all upp- and down- votes */
    public int popularity() {return uppVotes+downVotes;}

    public void setTitle(String title) {this.title = title;}
    public void setContent(String content) {this.content = content;}
    public void setAuthor(String author) {this.author = author;}
    public void setBelonging(String space) {this.space = space;}
    /** add a upp-vote */
    public void like() {this.uppVotes++;}
    /** add a down-vote */
    public void dislike() {this.downVotes++;}
}
