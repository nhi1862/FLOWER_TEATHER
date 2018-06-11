package com.java.mju_come.lost.lost_show;


import android.content.Context;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;


public class FirebaseClient {


    Context c;
    String DB_URL;
    ListView listView;
    Firebase firebase;
    ArrayList<Post> posts = new ArrayList<>();
    CustomAdapter customAdapter;


    public  FirebaseClient(Context c, String DB_URL, ListView listView)
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
        DataSnapshot category = dataSnapshot.child("category");
        String mCatagory = (String) category.getValue();
        DataSnapshot label = dataSnapshot.child("label");
        String mLabel = (String) label.getValue();
        DataSnapshot loaction = dataSnapshot.child("location");
        String mLocation = (String) loaction.getValue();
        DataSnapshot texbox = dataSnapshot.child("textbox");
        String mTextbox = (String) texbox.getValue();
        DataSnapshot url = dataSnapshot.child("url");
        String mUrl = (String) url.getValue();

        String id = dataSnapshot.getKey();

        // 포스트 제작
        Post p= new Post();
        p.setCategory(mCatagory);
        p.setLabel(mLabel);
        p.setLocation(mLocation);
        p.setTextbox(mTextbox);
        p.setUrl(mUrl);
        p.setId(id);

        posts.add(0,p);

        if(posts.size()>0)
        {
            customAdapter=new CustomAdapter(c, posts);
            listView.setAdapter(customAdapter);
        }else
        {
        }
    }
}
