package com.java.mju_come;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageButton;

import com.java.mju_come.Result.ResultActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import static android.app.Activity.RESULT_OK;


public class SearchFragment extends Fragment{
    ViewGroup rootView;

    ImageButton lay1; // 카메라 버튼
    ImageButton lay2; // 갤러리 버튼
    private static final int REQUEST_CODE = 1;
    private Uri filePath;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_search, container, false);

        lay1=(ImageButton) rootView.findViewById(R.id.img1);
        lay2=(ImageButton) rootView.findViewById(R.id.img2);

        return rootView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 카메라 버튼 눌렀을 때 이벤트
        lay1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //사진가져오기
                Intent intent_camera = new Intent(getActivity(),CameraActivity.class);
                startActivity(intent_camera);

            }

        });
        // 갤러리 버튼 눌렀을 때 이벤트
        lay2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //사진가져오기
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "select image"), REQUEST_CODE);
            }

        });
    }

    //갤러리에서 이미지 선택했을 때 이벤트
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            //uri to bitmap
            byte[] value = new byte[0];
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), filePath);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
                value = baos.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            NetworkTask networkTask = new NetworkTask(value,filePath,getActivity());
            networkTask.execute();

        }
    }
}
