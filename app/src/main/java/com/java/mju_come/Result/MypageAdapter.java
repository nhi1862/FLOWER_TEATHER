package com.java.mju_come.Result;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.bumptech.glide.Glide;
import com.java.mju_come.R;
import com.java.mju_come.lost.lost_show.CustomAdapter;
import com.java.mju_come.lost.lost_show.Post;


import java.util.ArrayList;

/**
 * Created by Nahyein on 2018-05-20.
 */

public class MypageAdapter extends BaseAdapter implements Filterable {
    Context c;
    ArrayList<MypagePost> mposts = new ArrayList<MypagePost>();
    ArrayList<MypagePost> filteredposts;
    LayoutInflater inflater;
    Filter mypagelistFilter;



    public MypageAdapter(){
    }

    public MypageAdapter(Context c, ArrayList<MypagePost> mpost) {
        this.c = c;
        this.mposts=mpost;
        filteredposts = mpost;
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
            view=inflater.inflate(R.layout.mypagelist_item,viewGroup,false);
        }

        MypageHolder holders = new MypageHolder(view);
        MypagePost posts=filteredposts.get(i);

        Glide.with(c).load(posts.getImg()).into(holders.img); //다시 확인
        holders.name.setText(posts.getFlowername());
        holders.flowerlanguage.setText(posts.getFlowerlanguage());
        holders.name.setTag(posts.getId());
        return view;
    }

    @Override
    public Filter getFilter() {
        if(mypagelistFilter == null){
            mypagelistFilter = new ListFilter();
        }
        return mypagelistFilter;
    }

    private class ListFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();

            if(charSequence == null || charSequence.length() == 0){
                results.values = mposts;
                results.count = mposts.size();
            }else{
                ArrayList<MypagePost> postlist = new ArrayList<MypagePost>();

                for(MypagePost p : mposts){
                    if(p.getFlowername().toUpperCase().contains(charSequence.toString().toUpperCase())){
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
            filteredposts = (ArrayList<MypagePost>) filterResults.values;

            if(filterResults.count > 0){
                notifyDataSetChanged();
            }
            else {
                notifyDataSetInvalidated();
            }
        }
    }
}