package com.java.mju_come.Result;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

/**
 * Created by Nahyein on 2018-05-31.
 */

public class FirebaseMypage {

    Context c;
    String DB_URL;
    ListView listView;
    Firebase firebase;
    ArrayList<MypagePost> mposts = new ArrayList<>();
    MypageAdapter mypageAdapter;


    public  FirebaseMypage(Context c, String DB_URL, ListView listView)
    {
        this.c= c;
        this.DB_URL= DB_URL;
        this.listView= listView;
        firebase.setAndroidContext(c);
        firebase=new Firebase(DB_URL);
    }



    public  void refreshdata()
    {
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getupdates(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getupdates(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void getupdates(DataSnapshot dataSnapshot){
        // 변수 추출
        DataSnapshot result_name2 = dataSnapshot.child("result_name2");
        String mresult_name2 = (String) result_name2.getValue();
        DataSnapshot result_flowerlanguage = dataSnapshot.child("result_flowerlanguage");
        String mresult_flowerlanguage = (String) result_flowerlanguage.getValue();
        DataSnapshot result_environment = dataSnapshot.child("result_environment");
        String mresult_environment = (String) result_environment.getValue();
        DataSnapshot result_img = dataSnapshot.child("url");
        String mresult_img = (String) result_img.getValue();
        DataSnapshot result_percentage = dataSnapshot.child("result_percentage");
        String mresult_percentage = (String) result_percentage.getValue();

        String id = dataSnapshot.getKey();

        // 포스트 제작
        MypagePost p= new MypagePost();
        p.setFlowername(mresult_name2);
        p.setFlowerlanguage(mresult_flowerlanguage);
        p.setEnvironment(mresult_environment);
        p.setImg(mresult_img);
        p.setId(id);
        p.setPercentage(mresult_percentage);



        mposts.add(0,p);

        if(mposts.size()>0)
        {
            mypageAdapter=new MypageAdapter(c, mposts);
            listView.setAdapter(mypageAdapter);
        }else
        {
        }
    }
}