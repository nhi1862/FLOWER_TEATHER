package com.java.mju_come;


import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.java.mju_come.lost.ReservationFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new SearchFragment()).commit();

        //bottomNavigation메뉴를 눌렀을시 각각의 화면으로 이동
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new SearchFragment()).commit();
                        break;
                    case R.id.action_reservation:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new ReservationFragment()).commit();
                        break;
                    case R.id.action_mypage:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new MypageFragment()).commit();
                        break;
                    case R.id.action_setting:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new SettingFragment()).commit();
                        break;
                }


                return true;
            }
        });


    }

    public void changePage(Fragment fr, int p) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fr).commit();
        switch (p){
            case 1:{
                bottomNavigationView.setSelectedItemId(bottomNavigationView.getMenu().findItem(R.id.action_reservation).getItemId());
                break;
            }
            case 2:{
                bottomNavigationView.setSelectedItemId(bottomNavigationView.getMenu().findItem(R.id.action_mypage).getItemId());
                break;
            }
            case 3:{
                bottomNavigationView.setSelectedItemId(bottomNavigationView.getMenu().findItem(R.id.action_setting).getItemId());
                break;
            }

        }

    }
}

