package com.example.myapplication;

import java.util.List;

public class BookManager {
    private List<Book> bookList;
    public BookManager(List<Book> bookList) {
        this.bookList = bookList;
    }
    public List<Book> getBookList() {
        return bookList;
    }
    public void addBook(int position,Book book) {
        this.bookList.add(position,book);
    }
    public void removeBook(int position) {
        bookList.remove(position);
    }
}
