package com.java.mju_come.Result;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.java.mju_come.R;

/**
 * Created by Nahyein on 2018-05-20.
 */

public class MypageHolder {
    ImageView img;
    TextView name;
    TextView flowerlanguage;
//    TextView environment;

    public MypageHolder(View itemview){
        img=(ImageView)itemview.findViewById(R.id.mlist_data1);
        name = (TextView)itemview.findViewById(R.id.mlist_data2);
        flowerlanguage = (TextView)itemview.findViewById(R.id.mlist_data3);
//        environment = (TextView)itemview.findViewById(R.id.result_environment);
    }
}
