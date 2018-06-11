package com.java.mju_come;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.java.mju_come.lost.enroll.LostEnrollActivity;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class MemoryFragment extends Fragment implements View.OnClickListener{

    //file upload code
    private static final int REQUEST_CODE = 1234;
    private ImageView imageView;
    private Button btnBrowse, btnUpload, btnShowList;
    private EditText imageTxt;
    //image uri
    private Uri filePath;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    public String FB_Storage_Path ="image/";
    public String FB_Database_Path ="image";
    //gps
    private GoogleApiClient mGoogleApiClient;
    private double longitude;
    private double latitude;
    private LocationManager mlocationManager;
    private LocationListener mlocationListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_memory,container,false);
        initView(v);

        return v;
    }

    private void initView(View v){
        btnBrowse =(Button)v.findViewById(R.id.btnBrowse);
        btnUpload =(Button)v.findViewById(R.id.btnUpload);
        btnShowList =(Button)v.findViewById(R.id.btnShowList);
        imageView =(ImageView)v.findViewById(R.id.imageView);
        imageTxt =(EditText)v.findViewById(R.id.txt);

        btnBrowse.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
        btnShowList.setOnClickListener(this);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("image");

    }

    //파일 업로드시 파일 이름이 겹치지 않게 하기 위해..
    public String getImageExt(Uri uri){
        ContentResolver contentResolver =getContext().getContentResolver();
        MimeTypeMap mimeTypemap =MimeTypeMap.getSingleton();

        return mimeTypemap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    //갤러리에 갈때 호출
    public void btnBrowseClick(){
        Intent intent =new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "select image"), REQUEST_CODE);
    }
    //location
    public void initLocation() {
        //gps
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .addApi(LocationServices.API).build();
        }
        mGoogleApiClient.connect();

        String[] permissions = new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                int result = PermissionChecker.checkSelfPermission(getContext(), permission);
                if (result == PermissionChecker.PERMISSION_GRANTED) ;
                else ActivityCompat.requestPermissions(getActivity(), permissions, 1);
            }
        }

        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getContext().getApplicationContext(), android.Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext().getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext().getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;

        mlocationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        mlocationListener = new MyLocationListener();
        mlocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, mlocationListener);
        mlocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, mlocationListener);
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
        }

    }
    private class MyLocationListener implements LocationListener {
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }

        public void onProviderDisabled(String p) {
        }

        public void onProviderEnabled(String p) {
        }

        public void onStatusChanged(String p, int s, Bundle e) {
        }

    }
    public void setLocation(){
       initLocation();
    }

    @SuppressWarnings("VisibleForTests")
    //파일 업로드 버튼 누르면 호출
    public void btnUploadClick() {
        //location
        setLocation();

        if (filePath != null) {
            final ProgressDialog progressDialog =new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            //파일 명
            StorageReference riversRef = storageReference.child(FB_Storage_Path + System.currentTimeMillis()+"."+getImageExt(filePath));

            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        //성공시
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            ImageUpload imageUpload =new ImageUpload(imageTxt.getText().toString(), taskSnapshot.getDownloadUrl().toString(), longitude, latitude);

                            String uploadurl =databaseReference.push().getKey();
                            databaseReference.child(uploadurl).setValue(imageUpload);
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
        }else{
        }
    }

    //리스트 보여주기
    public void btnShowListClick(){
        Intent intent =new Intent(getActivity(), ImageListActivity.class);
        startActivity(intent);
    }

    @Override
    //갤러리에서 이미지 선택하면 이전 화면에 이미지 보임
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==REQUEST_CODE && resultCode ==RESULT_OK && data !=null && data.getData() !=null){
            filePath =data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onClick(View v){
        if(v ==btnBrowse){
            btnBrowseClick();
        }else if(v ==btnUpload){
            btnUploadClick();
        }else if(v ==btnShowList){
            btnShowListClick();
        }
    }

}
