package com.java.mju_come;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Shop_Holder {
    public TextView shop_name;
    public TextView location;
    public TextView shop_number;

    public Shop_Holder(View itemview){
        //nameTxt = (TextView)itemview.findViewById(R.id.nametext);
        shop_name = (TextView) itemview.findViewById(R.id.shop_name);
        location = (TextView)itemview.findViewById(R.id.shop_location);
        shop_number = (TextView)itemview.findViewById(R.id.shop_number);
    }
}
