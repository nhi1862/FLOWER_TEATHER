package com.java.mju_come.lost.lost_show;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.bumptech.glide.Glide;
import com.java.mju_come.R;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter implements Filterable {
    Context c;
    ArrayList<Post> posts = new ArrayList<Post>();
    ArrayList<Post> filteredposts;
    LayoutInflater inflater;
    Filter listFilter;

    public  CustomAdapter(){
    }

    public CustomAdapter(Context c, ArrayList<Post> posts) {
        this.c = c;
        this.posts = posts;
        filteredposts = posts;
    }

    @Override
    public int getCount() {
        return filteredposts.size();
    }

    @Override
    public Object getItem(int i) {
        return filteredposts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(inflater == null){
            inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }if(view==null){
            view=inflater.inflate(R.layout.listview_layout,viewGroup,false);
        }

        MyHolder holder = new MyHolder(view);
        Post posts = filteredposts.get(i);

        //holder.nameTxt.setText(posts.get(i).getLocation());
        Glide.with(c).load(posts.getUrl()).into( holder.img);
        holder.locationTxt.setText(posts.getLocation());
        holder.titleTxt.setText(posts.getLabel());
        holder.categoryTxt.setText(posts.getCategory());
        holder.titleTxt.setTag(posts.getId());
        //   holder.dateTxt.setText(posts.get(i).getDate());
        return view;
    }


    @Override
    public Filter getFilter() {
        if(listFilter == null){
            listFilter = new ListFilter();
        }
        return listFilter;
    }

    private class ListFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();

            if(charSequence == null || charSequence.length() == 0){
                results.values = posts;
                results.count = posts.size();
            }else{
                ArrayList<Post> postlist = new ArrayList<Post>();

                for(Post p : posts){
                    if(p.getCategory().toUpperCase().contains(charSequence.toString().toUpperCase())){
                        postlist.add(p);
                    }
                }
                results.values = postlist;
                results.count = postlist.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            filteredposts = (ArrayList<Post>) filterResults.values;

            if(filterResults.count > 0){
                notifyDataSetChanged();
            }
            else {
                notifyDataSetInvalidated();
            }
        }
    }
}
