package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class BookViewFragment extends Fragment {
    ActivityResultLauncher<Intent> launcher;
    RecycleViewBookAdapter adapter;
    BookManager bookManager;
    public class RecycleViewBookAdapter extends RecyclerView.Adapter{
        private List<Book> bookList;
        private int position;
        public void setBookList(List<Book> bookList) { this.bookList = bookList; }
        public int getContextMenuPosition() { return position; }
        public void setContextMenuPosition(int position) { this.position = position; }
        public void writeBookList(){
            try {
                FileOutputStream fileOut = getContext().openFileOutput("books.txt", Context.MODE_PRIVATE);
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                objectOut.writeObject(bookList);
                objectOut.close();
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
                adapter.setContextMenuPosition(position);
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
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                Intent intent1 = new Intent(getActivity(), AddBookActivity.class);
                intent1.putExtra("requestCode", 1);
                launcher.launch(intent1);
                return true;
            case 2:
                Intent intent2 = new Intent(getActivity(), BookDetailsActivity.class);
                try {
                    FileInputStream fileIn = getContext().openFileInput("books.txt");
                    ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                    List<Book> savedBookList = (List<Book>) objectIn.readObject();
                    objectIn.close();
                    fileIn.close();

                    intent2.putExtra("Title",savedBookList.get(adapter.getContextMenuPosition()).getTitle());
                    intent2.putExtra("requestCode", 2);
                    launcher.launch(intent2);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                return true;
            case 3:
                bookManager.removeBook(adapter.getContextMenuPosition());
                adapter.setBookList(bookManager.getBookList());
                adapter.writeBookList();
                int position = adapter.getContextMenuPosition();
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, adapter.getItemCount());
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookview, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycle_view_books);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RecycleViewBookAdapter(BookListMainActivity.getListBooks());
        adapter.writeBookList();
        recyclerView.setAdapter(adapter);

        bookManager = new BookManager(BookListMainActivity.getListBooks());
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            if(data.getIntExtra("requestCode", 0) == 1) {
                                int price = (int) data.getSerializableExtra("Price");
                                String title = (String) data.getSerializableExtra("Title");
                                int position = adapter.getContextMenuPosition();
                                bookManager.addBook(position,new Book(R.drawable.book4, price, title));
                                adapter.setBookList(bookManager.getBookList());
                                adapter.writeBookList();
                                adapter.notifyItemInserted(position);
                                adapter.notifyItemRangeChanged(position, adapter.getItemCount());
                            }
                            else{
                                String title = (String) data.getSerializableExtra("EditedTitle");
                                bookManager.getBookList().get(adapter.getContextMenuPosition()).setTitle(title);
                                adapter.setBookList(bookManager.getBookList());
                                adapter.writeBookList();
                                adapter.notifyItemChanged(adapter.getContextMenuPosition());
                            }
                        }
                    }
                });
        return view;
    }
}
