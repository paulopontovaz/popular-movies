package com.example.popularmovies.model;

public class Review {
    private String id;
    private String content;
    private String author;

    public Review(String id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }
}
