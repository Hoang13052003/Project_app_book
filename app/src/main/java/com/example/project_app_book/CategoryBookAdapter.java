package com.example.project_app_book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CategoryBookAdapter extends ArrayAdapter {
    Context context;
    ArrayList<CategoryBook> arrayList;
    int layout;
    public CategoryBookAdapter(@NonNull Context context, int resource, @NonNull
    ArrayList<CategoryBook> objects) {
        super(context, resource, objects);
        this.context = context;
        this.arrayList = objects;
        this.layout = resource;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull
    ViewGroup parent) {
        CategoryBook categoryBook = arrayList.get(position);
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout,null);
        }
        ImageView img = convertView.findViewById(R.id.imgCategoryBook);
        int resourceId = context.getResources().getIdentifier(categoryBook.getImg(), "drawable", context.getPackageName());
        img.setImageResource(resourceId);

        TextView txtName = convertView.findViewById(R.id.tvNameCategoryBook);
        txtName.setText(categoryBook.getName());

        return convertView;
    }
}