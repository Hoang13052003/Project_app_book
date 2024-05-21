package com.example.project_app_book.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_app_book.R;
import com.example.project_app_book.model.Author;

import java.util.ArrayList;

public class AuthorRecyclerAdapter extends RecyclerView.Adapter<AuthorRecyclerAdapter.ViewHolder> {

    private ArrayList<Author> authorList;
    private Context context;
    private int layoutResource;

    public AuthorRecyclerAdapter(Context context, ArrayList<Author> authorList, int layoutResource) {
        this.context = context;
        this.authorList = authorList;
        this.layoutResource = layoutResource;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgAvatarAuthor;

        public ViewHolder(View itemView) {
            super(itemView);
            imgAvatarAuthor = itemView.findViewById(R.id.imgAvatarAuthor);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutResource, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Author author = authorList.get(position);
        String imageName = author.getImgAuthor();
        int resourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        if (resourceId != 0) {
            holder.imgAvatarAuthor.setImageResource(resourceId);
        } else {
            Toast.makeText(context, "Image not found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return authorList.size();
    }
}
