package com.java.mju_come;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

//리스트로 이미지 보여주기
public class ImageListActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private List<ImageUpload> imglist;
    private ListView lv;
    private ImageListAdapter adapter;
    private ProgressDialog progressDialog;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);

        imglist =new ArrayList<>();
        lv =(ListView)findViewById(R.id.listViewImage);

        progressDialog =new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        //데이터베이스에 있는 이미지 가져오기
        databaseReference = FirebaseDatabase.getInstance().getReference("image");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();

                for(DataSnapshot Snapshot :dataSnapshot.getChildren()){
                    ImageUpload img =Snapshot.getValue(ImageUpload.class);
                    imglist.add(0,img);
                }

                adapter =new ImageListAdapter(ImageListActivity.this, R.layout.image_item, imglist);
                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
    }
}
