package com.java.mju_come.lost.Check;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.java.mju_come.CheckMapActivity;
import com.java.mju_come.R;

public class LostCheckActivity extends AppCompatActivity {
    Firebase firebase;
    String DB_URL = "https://mjucome-21a2b.firebaseio.com/lost_img";

    //레이아웃 id 선언
    TextView ctitle;
    TextView ccategory;
    TextView clocation;
    ImageView cimageView;
    TextView ctextView;

    private String mlatitude;
    private String mlongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_check);

        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");

        ctitle=(TextView)findViewById(R.id.lostCheck_title);
        ccategory=(TextView)findViewById(R.id.lostCheck_category);
        clocation=(TextView)findViewById(R.id.lostCheck_location);
        cimageView=(ImageView)findViewById(R.id.lostCheck_imageView);
        ctextView=(TextView)findViewById(R.id.lostCheck_textView);

        firebase.setAndroidContext(this);
        firebase = new Firebase(DB_URL);
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(id.equals(dataSnapshot.getKey())){
                    DataSnapshot category = dataSnapshot.child("category");
                    String mCatagory = (String) category.getValue();
                    DataSnapshot label = dataSnapshot.child("label");
                    String mLabel = (String) label.getValue();
                    DataSnapshot loaction = dataSnapshot.child("location");
                    String mLocation = (String) loaction.getValue();
                    DataSnapshot latitude = dataSnapshot.child("latitude");
                    mlatitude = (String) latitude.getValue();
                    DataSnapshot longitude = dataSnapshot.child("longitude");
                    mlongitude = (String) longitude.getValue();
                    DataSnapshot texbox = dataSnapshot.child("textbox");
                    String mTextbox = (String) texbox.getValue();
                    DataSnapshot url = dataSnapshot.child("url");
                    String mUrl = (String) url.getValue();

                    ctitle.setText(mLabel.toString());
                    ccategory.setText(mCatagory.toString());
                    clocation.setText(mLocation.toString());
                    Glide.with(getApplicationContext()).load(mUrl).into(cimageView);
                    ctextView.setText(mTextbox.toString());

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
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
    public void toMap(View view) {
        Intent intent = new Intent(getApplicationContext(), CheckMapActivity.class);
        intent.putExtra("latitude", mlatitude);
        intent.putExtra("longitude", mlongitude);
        startActivity(intent);
    }
}
