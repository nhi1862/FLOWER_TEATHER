package com.java.mju_come.Result;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.vision.text.Text;
import com.java.mju_come.R;

public class MypageCheck extends AppCompatActivity {

    Firebase firebase;
    String DB_URL = "https://mjucome-21a2b.firebaseio.com/result_img";

    //레이아웃 id 선언
    private TextView check_name;
    private TextView check_name2;
    private TextView check_flowerlanguage;
    private TextView check_environment;
    private ImageView check_img;
    private TextView check_percentage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_check);

        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");

        check_name=(TextView)findViewById(R.id.check_mainname);
        check_name2=(TextView)findViewById(R.id.check_name);
        check_flowerlanguage=(TextView)findViewById(R.id.check_flowerlanguage);
        check_percentage=(TextView)findViewById(R.id.check_percentage);
        check_environment=(TextView)findViewById(R.id.check_environment);
        check_img=(ImageView)findViewById(R.id.check_img);

        firebase.setAndroidContext(this);
        firebase = new Firebase(DB_URL);
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(id.equals(dataSnapshot.getKey())){
                    DataSnapshot name = dataSnapshot.child("name");
                    String cName = (String) name.getValue();
                    DataSnapshot flowerlanguage = dataSnapshot.child("language");
                    String cFlowerlanguage = (String) flowerlanguage.getValue();
                    DataSnapshot percentage = dataSnapshot.child("percentage");
                    String cPercentage = (String) percentage.getValue();
                    DataSnapshot environment = dataSnapshot.child("environment");
                    String cEnvironment = (String) environment.getValue();
                    DataSnapshot img = dataSnapshot.child("url");
                    String cImg = (String) img.getValue();

                    check_name.setText(cName.toString());
                    check_name2.setText(cName.toString());
                    check_flowerlanguage.setText(cFlowerlanguage.toString());
                    check_percentage.setText(cPercentage.toString());
                    check_environment.setText(cEnvironment.toString());
                    Glide.with(getApplicationContext()).load(cImg).into(check_img);

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
}
