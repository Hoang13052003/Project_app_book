package com.example.project_app_book;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class BookRecyclerAdapter extends RecyclerView.Adapter<BookRecyclerAdapter.ViewHolder> {

    private ArrayList<Book> bookList;
    private Context context;
    private int layoutResource;

    // Constructor
    public BookRecyclerAdapter(Context context, ArrayList<Book> bookList, int layoutResource) {
        this.context = context;
        this.bookList = bookList;
        this.layoutResource = layoutResource;
    }

    // ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgAvatarBook;

        public ViewHolder(View itemView) {
            super(itemView);
            imgAvatarBook = itemView.findViewById(R.id.imgAvatarBook);
        }
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutResource, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = bookList.get(position);

        @SuppressLint("DiscouragedApi") int resourceId = context.getResources().getIdentifier(book.getImage(), "drawable", context.getPackageName());
        holder.imgAvatarBook.setImageResource(resourceId);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return bookList.size();
    }
}
