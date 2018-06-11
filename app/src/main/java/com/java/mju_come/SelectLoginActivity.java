package com.java.mju_come;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class SelectLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_login);

        Intent intent=getIntent();

        ImageButton nonMember_btn=(ImageButton)findViewById(R.id.nonMember_btn);
        ImageButton member_btn=(ImageButton)findViewById(R.id.member_btn);

        nonMember_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent1 = new Intent(SelectLoginActivity.this,NonMemberRegisterActivity.class);
                startActivity(intent1);
            }
        });

        member_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent1 = new Intent(SelectLoginActivity.this, RegisterActivity.class);
                startActivity(intent1);
            }
        });
    }
}
