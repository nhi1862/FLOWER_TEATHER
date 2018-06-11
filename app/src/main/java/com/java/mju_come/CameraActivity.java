package com.java.mju_come;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import java.io.IOException;
import java.util.List;

public class CameraActivity extends AppCompatActivity implements SurfaceHolder.Callback{

    private SurfaceView mCameraView;
    private SurfaceHolder mCameraHolder;
    private Camera mCamera;
    private Uri filePath;

    ProgressDialog asyncDialog;

    @SuppressLint("WrongViewCast")
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mCameraView = (SurfaceView) findViewById(R.id.surfaceView);
        init();
        focus();
    }

    private void focus(){
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.camera_focus);
        frameLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mCamera.autoFocus(new Camera.AutoFocusCallback(){
                    @Override
                    public void onAutoFocus(boolean b, Camera camera) {
                        if(b){
                        }else{
                        }
                    }
                });
            }
        });
    }
    private void init(){
        mCameraHolder = mCameraView.getHolder();
        mCameraHolder.addCallback(this);
        mCamera = Camera.open();
        mCamera.setDisplayOrientation(90);
    }
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            if (mCamera == null) {
                mCamera.setPreviewDisplay(surfaceHolder);

                int m_resWidth;
                int m_resHeight;
                m_resWidth = mCamera.getParameters().getPictureSize().width;
                m_resHeight = mCamera.getParameters().getPictureSize().height;
                Camera.Parameters parameters = mCamera.getParameters();
                m_resWidth = 1280;
                m_resHeight = 720;
                parameters.setPictureSize(m_resWidth, m_resHeight);
                mCamera.setParameters(parameters);
                mCamera.startPreview();
            }
        } catch (IOException e) {
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
// View 가 존재하지 않을 때
        if (mCameraHolder.getSurface() == null) {
            return;
        }

        // 작업을 위해 잠시 멈춘다
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            // 에러가 나더라도 무시한다.
        }

        // 카메라 설정을 다시 한다.
        Camera.Parameters parameters = mCamera.getParameters();
        List<String> focusModes = parameters.getSupportedFocusModes();
        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }
        mCamera.setParameters(parameters);

        // View 를 재생성한다.
        try {
            mCamera.setPreviewDisplay(mCameraHolder);
            mCamera.startPreview();
        } catch (Exception e) {
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }


    Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
        @Override
        public void onShutter() {

        }
    };

    Camera.PictureCallback pictureCallback_RAW = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

        }
    };

    Camera.PictureCallback pictureCallback_JPG = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
            String outUriStr = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,"Captured Image", "Captured Image using Camera");
            filePath = Uri.parse(outUriStr);

            //네트워크 연결
            NetworkTask networkTask = new NetworkTask(data,filePath, CameraActivity.this);
            networkTask.execute();

        }
    };


    public void Capture(View view){
        mCamera.takePicture(shutterCallback, pictureCallback_RAW,pictureCallback_JPG);
    }
}
