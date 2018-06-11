package com.java.mju_come;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


public class ImageListAdapter extends ArrayAdapter<ImageUpload>{
    private Activity context;
    private int resource;
    private List<ImageUpload> listimage;

    public ImageListAdapter(@NonNull Activity context, int resource, List<ImageUpload> objects){
        super(context, resource, objects);
        this.context =context;
        this.resource =resource;
        listimage =objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View converView, @NonNull ViewGroup parent){
        LayoutInflater layoutInflater =context.getLayoutInflater();
        View v =layoutInflater.inflate(resource, null);
        TextView tvName =(TextView)v.findViewById(R.id.tvImageName);
        ImageView img =(ImageView)v.findViewById(R.id.imgView);

        tvName.setText(listimage.get(position).getName());
        Glide.with(context).load(listimage.get(position).getUrl()).into(img);

        return v;
    }
}
