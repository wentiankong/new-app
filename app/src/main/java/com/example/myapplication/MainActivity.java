package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public class RecycleViewBookAdapter extends RecyclerView.Adapter{
        private List<Book> bookList;
        public RecycleViewBookAdapter(List<Book> bookList) {
            this.bookList = bookList;
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
            return new RecyclerView.ViewHolder(view){};
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Book book = bookList.get(position);

            ImageView imageView = holder.itemView.findViewById(R.id.image_view_book_cover);
            TextView textView = holder.itemView.findViewById(R.id.text_view_book_title);

            imageView.setImageResource(book.getCoverResourceId());
            textView.setText(book.getTitle());
        }
        @Override
        public int getItemCount() {
            return bookList.size();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycle_view_books);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecycleViewBookAdapter adapter = new RecycleViewBookAdapter(BookListMainActivity.getListBooks());
        recyclerView.setAdapter(adapter);
    }
}