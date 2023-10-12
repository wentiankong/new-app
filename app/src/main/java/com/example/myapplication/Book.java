package com.example.myapplication;
public class Book {
    private int coverResourceId;
    private String title;

    public Book(int coverResourceId, String title) {
        this.coverResourceId = coverResourceId;
        this.title = title;
    }

    public int getCoverResourceId() {
        return coverResourceId;
    }

    public String getTitle() {
        return title;
    }
}