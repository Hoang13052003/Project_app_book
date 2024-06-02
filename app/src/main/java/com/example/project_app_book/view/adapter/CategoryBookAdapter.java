package com.example.project_app_book.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.project_app_book.R;
import com.example.project_app_book.model.CategoryBook;

import java.util.ArrayList;

public class CategoryBookAdapter extends ArrayAdapter<CategoryBook> {
    private Context context;
    private ArrayList<CategoryBook> arrayList;
    private int layoutResource;

    public CategoryBookAdapter(@NonNull Context context,@NonNull ArrayList<CategoryBook> objects, int layoutResource) {
        super(context, layoutResource, objects);
        this.context = context;
        this.arrayList = objects;
        this.layoutResource = layoutResource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CategoryBook categoryBook = arrayList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layoutResource, parent, false);
        }
        ImageView img = convertView.findViewById(R.id.imgCategoryBook);
//        int resourceId = context.getResources().getIdentifier(categoryBook.getImg(), "drawable", context.getPackageName());
//        img.setImageResource(resourceId);

        TextView txtName = convertView.findViewById(R.id.tvNameCategoryBook);
        txtName.setText(categoryBook.getName());

        return convertView;
    }
}
