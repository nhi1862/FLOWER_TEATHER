package com.java.mju_come;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ShopAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<ShopItem> data;
    private int layout;



    public ShopAdapter(Context context, int listshop_item, ArrayList<ShopItem> data) {
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data=data;
        this.layout = listshop_item;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i).getShop_name();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view ==null){
            view = inflater.inflate(layout,viewGroup,false);
        }
        ShopItem shopItem = data.get(i);
        TextView name = (TextView)view.findViewById(R.id.shop_name);
        name.setText(shopItem.getShop_name());
        TextView location = (TextView)view.findViewById(R.id.shop_location);
        location.setText(shopItem.getShop_location());
        TextView number = (TextView)view.findViewById(R.id.shop_number);
        number.setText(shopItem.getShop_number());

        return view;
    }
}
