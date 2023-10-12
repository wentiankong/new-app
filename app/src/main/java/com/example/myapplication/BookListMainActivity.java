package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class BookListMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list_main);
    }
    public static List<Book> getListBooks() {
        List<Book> bookList = new ArrayList<>();

        // Add book objects to the list
        bookList.add(new Book(R.drawable.book1, "Android 移动应用基础教程 第二版"));
        bookList.add(new Book(R.drawable.book2, "数据结构教程 第五版"));
        bookList.add(new Book(R.drawable.book3, "计算机操作系统 第四版"));

        return bookList;
    }
}