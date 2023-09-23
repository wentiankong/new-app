package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public void buttonClick(View view) {

        TextView textView1 = findViewById(R.id.textView2);
        TextView textView2 = findViewById(R.id.textView3);
        String text1 = textView1.getText().toString();
        String text2 = textView2.getText().toString();
        textView1.setText(text2);
        textView2.setText(text1);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("交换结果");
        builder.setMessage("交换成功");
        builder.setPositiveButton("OK", (dialog, which) -> Toast.makeText(MainActivity.this, "OK button clicked", Toast.LENGTH_SHORT).show());
        builder.setNegativeButton("Cancel", (dialog, which) -> Toast.makeText(MainActivity.this, "Cancel button clicked", Toast.LENGTH_SHORT).show());
        builder.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.textView);
        textView.setText(R.string.hello_android);

//        Button myButton = findViewById(R.id.button);
//
//        myButton.setOnClickListener(view -> {
//            Toast.makeText(MainActivity.this, "交换成功", Toast.LENGTH_SHORT).show();
//            TextView textView1 = findViewById(R.id.textView2);
//            TextView textView2 = findViewById(R.id.textView3);
//            String text1 = textView1.getText().toString();
//            String text2 = textView2.getText().toString();
//            textView1.setText(text2);
//            textView2.setText(text1);
//        });
    }
}