package com.example.mindspark;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ArticleAdapter extends ArrayAdapter {
    Context context;
    int resource;
    List<Article> list;
    ImageView articlePreview;
    User u;
    String imageName;
    public ArticleAdapter(@NonNull Context context, int resource, @NonNull List<Article> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View adapterLayout = layoutInflater.inflate(resource, null);
        articlePreview = adapterLayout.findViewById(R.id.articlePreview);
        TextView header = adapterLayout.findViewById(R.id.articleListViewHeader);
        header.setText(list.get(position).getTitle());
        imageName = list.get(position).getImageURL();
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/mind-spark-international.appspot.com/o/uploads%2F"+imageName+"?alt=media").resize(300, 300).into(articlePreview);
        notifyDataSetChanged();
        return adapterLayout;

    }
}
