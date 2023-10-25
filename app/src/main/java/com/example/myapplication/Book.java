package com.example.myapplication;

import java.io.Serializable;

public class Book implements Serializable {
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
    public void setTitle(String title) {
        this.title = title;
    }
}