package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BookDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        EditText editText = findViewById(R.id.edit_book_details);

        Intent intent = new Intent();
        String title = getIntent().getStringExtra("Title");
        int requestCode = getIntent().getIntExtra("requestCode", 0);

        editText.setText(title);

        Button buttonEditBook = findViewById(R.id.button_edit_book);
        buttonEditBook.setOnClickListener(v -> {
            if(editText.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "请输入", Toast.LENGTH_SHORT).show();
                return;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(BookDetailsActivity.this);
            builder.setMessage("确认修改此书籍名称吗？");
            builder.setPositiveButton("确认", (dialog, which) -> {
                String editedTitle = editText.getText().toString();
                intent.putExtra("EditedTitle", editedTitle);
                intent.putExtra("requestCode", requestCode);
                setResult(RESULT_OK, intent);
                finish();
            });
            builder.setNegativeButton("取消", null);
            builder.show();
        });
    }
}