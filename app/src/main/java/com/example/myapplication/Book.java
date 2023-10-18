package com.example.myapplication;
public class Book {
    private int coverResourceId;
    private int price;
    private String title;

    public Book(int coverResourceId,int price, String title) {
        this.coverResourceId = coverResourceId;
        this.price = price;
        this.title = title;
    }

    public int getCoverResourceId() {
        return coverResourceId;
    }
    public int getPrice() {
        return price;
    }
    public String getTitle() {
        return title;
    }
}