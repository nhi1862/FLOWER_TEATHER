package com.java.mju_come.lost.lost_show;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.java.mju_come.R;


public class MyHolder {

    //TextView nameTxt;
    ImageView img;
    TextView titleTxt;
    TextView categoryTxt;
    TextView locationTxt;

    public MyHolder(View itemview){
        //nameTxt = (TextView)itemview.findViewById(R.id.nametext);
        img = (ImageView) itemview.findViewById(R.id.imageView1);
        titleTxt = (TextView)itemview.findViewById(R.id.titletext);
        categoryTxt = (TextView)itemview.findViewById(R.id.categorytext);
        locationTxt = (TextView)itemview.findViewById(R.id.locationtext);
    }
}
