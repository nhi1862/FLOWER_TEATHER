package com.java.mju_come;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.java.mju_come.lost.enroll.FindEnrollActivity;
import com.java.mju_come.lost.enroll.LostEnrollActivity;

public class EnrollActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);

        Intent intent=getIntent();

        ImageButton lost_btn=(ImageButton)findViewById(R.id.lost_btn);
        ImageButton found_btn=(ImageButton)findViewById(R.id.found_btn);

        lost_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent1 = new Intent(EnrollActivity.this,LostEnrollActivity.class);
                startActivity(intent1);
            }
        });

        found_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent1 = new Intent(EnrollActivity.this, FindEnrollActivity.class);
                startActivity(intent1);
            }
        });
    }
}
