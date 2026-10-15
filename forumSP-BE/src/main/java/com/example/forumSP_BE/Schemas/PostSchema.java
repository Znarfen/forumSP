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
    private Long id;

    @NotBlank (message = "Title is required")
    private String title;

    @NotBlank (message = "Content is required")
    private String content;

    @NotBlank (message = "Author is required")
    private String author;

    @NotBlank (message = "Post must belong to a space")
    private String space;

    public PostSchema() {}

    public PostSchema(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Long getId() {return id;}
    public String getTitle() {return title;}
    public String getContent() {return content;}
    public String getAuthor() {return author;}
    public String getBelonging() {return space;}

    public void setTitle(String title) {this.title = title;}
    public void setContent(String content) {this.content = content;}
    public void setAuthor(String author) {this.author = author;}
    public void setBelonging(String space) {this.space = space;}
}
