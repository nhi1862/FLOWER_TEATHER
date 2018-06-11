package com.java.mju_come.Result;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.java.mju_come.R;
import com.java.mju_come.Result.Result;
import com.java.mju_come.lost.enroll.Find;
import com.java.mju_come.lost.enroll.Lost;
import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;
import com.kakao.util.KakaoParameterException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ResultActivity extends AppCompatActivity {



    private TextView result_name1;
    private TextView result_name2;
    private TextView result_flowerlanguage;
    private TextView result_environment;
    private ImageView result_img;
    private TextView result_percentage;


    //서버로 보낼 정보 초기화
    private String infor1; // 꽃 이름
    private String infor2; //꽃말
    private String infor3; // 자라는 환경
    private String infor4; // 확률

    //firebase
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private DatabaseReference infor_databaseReference; // 꽃 정보에 대한 DB

    public String FB_Storage_Path = "result_img/";
    public String FB_Database_Path = "result_img";
    private static final int REQUEST_CODE = 1;

    //검색에서 인텐트로 받아온 값
    Uri urivalue; // 꽃 사진

    //카카오톡으로 전달할 text값
    String kakaoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //초기화
        result_name1 = (TextView) findViewById(R.id.result_name1);
        result_name2 = (TextView) findViewById(R.id.result_name2);
        result_flowerlanguage = (TextView) findViewById(R.id.result_flowerlanguage);
        result_environment = (TextView) findViewById(R.id.result_environment);
        result_img = (ImageView) findViewById(R.id.result_image);
        result_percentage = (TextView) findViewById(R.id.result_percentage);

        // 서버 부분 초기화
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("result_img");
        infor_databaseReference = FirebaseDatabase.getInstance().getReference("flower_info");

        //SearchFragment에서 인텐트 받아오기
        Intent intent = getIntent();
        urivalue = intent.getParcelableExtra("filepath");
        infor1 = intent.getStringExtra("label");
        infor4 = intent.getStringExtra("percent");

        //flower info 검색
        infor_databaseReference.orderByChild("꽃이름").equalTo(infor1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DataSnapshot language=dataSnapshot.child("꽃말");
                infor2=language.getValue().toString();
                DataSnapshot environment=dataSnapshot.child("자라는환경");
                infor3=environment.getValue().toString();
                result_flowerlanguage.setText(infor2);
                result_environment.setText(infor3);
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
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //꽃 정보 해당 내용 출력
        result_name1.setText(infor1);
        result_name2.setText(infor1);
        result_percentage.setText(infor4);
        Glide.with(getApplicationContext()).load(urivalue).into(result_img);
    }

    //상태바 아이콘 표시
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.result_menu, menu);
        return true;
    }

    //상태바 아이콘 눌렀을 때 호출되는 함수
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        //하트 모양 아이콘 눌렀을 때
        if (id == R.id.menu_like) {
            loveSavePress();
        }
        // 공유 모양 아이콘 눌렀을 때
        else if (id == R.id.menu_share) {
            shareKakao();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    //카카오톡 공유 호출
    public void shareKakao(){
        try{
            final KakaoLink kakaoLink=KakaoLink.getKakaoLink(this);
            final KakaoTalkLinkMessageBuilder kakaoBuilder=kakaoLink.createKakaoTalkLinkMessageBuilder();

            /*메세지 추가*/
//            kakaoBuilder.addText("[꽃 선생]");
            kakaoText= "이 꽃은 "+infor1+"일 확률이 "+infor4+"%입니다.";
            kakaoBuilder.addText(kakaoText);

            /*보낼 정보 추가하기*/

            String url_a=urivalue.getPath();
            Log.i("hoho",url_a);
            kakaoBuilder.addImage(url_a,160,160);
            /*앱 실행 버튼 추가*/
            kakaoBuilder.addAppButton("앱 실행 혹은 다운로드");

            /*메시지 발송*/
            kakaoLink.sendMessage(kakaoBuilder,this);

        } catch (KakaoParameterException e) {
            e.printStackTrace();
        }
    }


    //파일 업로드시 파일 이름이 겹치지 않게 하기 위해..
    public String getImageExt(Uri uri) {
        ContentResolver contentResolver = this.getContentResolver();
        MimeTypeMap mimeTypemap = MimeTypeMap.getSingleton();
        return mimeTypemap.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    // 파일 업로드 버튼 누르면 호출
    public void loveSavePress() {

        if (urivalue != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            //파일 명
            StorageReference riversRef = storageReference.child(FB_Storage_Path + System.currentTimeMillis() + "." + getImageExt(urivalue));

            riversRef.putFile(urivalue)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        //성공시
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            if (infor1.equals("") || infor2.equals("") || infor3.equals("")  || infor4.equals("") ) {
                                progressDialog.dismiss();

                            }

                            else {
                                progressDialog.dismiss();
                                Result resultUpload = new Result(taskSnapshot.getDownloadUrl().toString(),infor1, infor2,infor3,infor4);
                                String uploadurl = databaseReference.push().getKey();
                                databaseReference.child(uploadurl).setValue(resultUpload);
                                finish();
                            }
                        }
                    })
                    //실패시
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //                        double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage("Uploading...");

                        }
                    });
        } else {
        }
    }
}


