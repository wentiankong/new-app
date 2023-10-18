package com.example.myapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityResultLauncher<Intent> launcher;
    RecycleViewBookAdapter adapter;
    public class RecycleViewBookAdapter extends RecyclerView.Adapter{
        private List<Book> bookList;
        private int position;
        public int getContextMenuPosition() { return position; }
        public void setContextMenuPosition(int position) { this.position = position; }
        public List<Book> getBookList() {
            return bookList;
        }
        public void addBookList(Book book) {
            bookList.add(book);
        }
        public void removeBookList() {
            bookList.remove(position);
        }
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
            TextView textView1 = holder.itemView.findViewById(R.id.text_view_book_title);
            TextView textView2 = holder.itemView.findViewById(R.id.text_view_book_price);

            imageView.setImageResource(book.getCoverResourceId());
            textView1.setText(book.getTitle());
            textView2.setText(Integer.toString(book.getPrice()));

            holder.itemView.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
                setContextMenuPosition(holder.getLayoutPosition());
                menu.add(0, 1, 0, "添加");
                menu.add(0, 2, 0, "修改");
                menu.add(0, 3, 0, "删除");
            });
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
        adapter = new RecycleViewBookAdapter(BookListMainActivity.getListBooks());
        recyclerView.setAdapter(adapter);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            int price = (int) data.getSerializableExtra("Price");
                            String title = (String) data.getSerializableExtra("Title");
                            adapter.addBookList(new Book(R.drawable.book4, price, title));
                            adapter.notifyItemInserted(adapter.getItemCount());
                        }
                    }
                });
    }
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
                launcher.launch(intent);
                return true;
            case 2:
                return true;
            case 3:
                adapter.removeBookList();
                adapter.notifyItemRemoved(adapter.getContextMenuPosition());
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}