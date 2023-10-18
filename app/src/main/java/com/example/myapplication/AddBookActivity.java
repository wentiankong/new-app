package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        EditText editBookPrice = findViewById(R.id.edit_book_price);
        EditText editBookTitle = findViewById(R.id.edit_book_title);
        Button buttonAddBook = findViewById(R.id.button_add_book);

        buttonAddBook.setOnClickListener(v -> {
            int bookPrice = Integer.parseInt(editBookPrice.getText().toString());
            String bookTitle = editBookTitle.getText().toString();

            AlertDialog.Builder builder = new AlertDialog.Builder(AddBookActivity.this);
            builder.setMessage("确认添加此书籍吗？");
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent();
                    intent.putExtra("Price", bookPrice);
                    intent.putExtra("Title", bookTitle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            builder.setNegativeButton("取消", null);
            builder.show();
        });
    }
}