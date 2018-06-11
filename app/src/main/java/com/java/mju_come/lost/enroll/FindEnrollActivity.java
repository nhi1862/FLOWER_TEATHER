package com.java.mju_come.lost.enroll;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.java.mju_come.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class FindEnrollActivity extends AppCompatActivity implements com.google.android.gms.location.LocationListener {
    static int GET_PICTURE_URI = 1;
    ImageView f_enroll_imageView;
    private TextInputEditText textInputEditText;
    private ImageButton locationBtn;
    private Button saveBtn;
    private TextInputEditText textInputEditTextTitle;
    private LocationManager mlocationManager;
    private LocationListener mlocationListener;
    private Spinner categorySpinner;
    private  TextInputEditText find_textbox;
    private Context mContext;
    private double latitude;
    private double longitude;
    private double altitude;
    private GoogleApiClient mGoogleApiClient;
    private String address;


    private Uri filePath;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    public String FB_Storage_Path = "find_img/";
    public String FB_Database_Path = "find_img";
    private static final int REQUEST_CODE = 1;

    //서버로 보낼 정보 초기화
    private String infor1;
    private String infor2;
    private String infor3;
    private String infor4;
    private String infor5;
    private String infor6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_enroll);

        //스피너 연결
        categorySpinner = (Spinner) findViewById(R.id.spinner_category);
        ArrayAdapter categoryAdapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        // 초기화 해주기
        ImageButton camera_btn = (ImageButton) findViewById(R.id.camera_btn);
        f_enroll_imageView = (ImageView) findViewById(R.id.f_enroll_imageView);
        find_textbox=(TextInputEditText)findViewById(R.id.find_textbox);

        // 서버 부분 초기화
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("find_img");

        //위치 부분 초기화
        mContext = getApplicationContext();
        textInputEditText = (TextInputEditText) findViewById(R.id.textInputEditTextLocation);
        textInputEditTextTitle = (TextInputEditText) findViewById(R.id.textInputEditTextTitle);
        locationBtn = (ImageButton) findViewById(R.id.location_btn);
        saveBtn = (Button) findViewById(R.id.findSave_btn);

        //위치 버튼 눌렀을 때 이벤트
        locationBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                initLocation();
                toLocation(v);
            }
        });

        //save 버튼 눌렀을 때 이벤트
        saveBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveButtonClicked();
            }
        });

        //카메라 버튼 눌러을 때 이벤트
        camera_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //사진가져오기
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "select image"), REQUEST_CODE);
            }
        });
    }

    //갤러리에서 이미지 선택하면 이전 화면에 이미지 보임
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                f_enroll_imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API).build();
        }
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        if (mGoogleApiClient != null)
            mGoogleApiClient.disconnect();
        super.onStop();
    }

    protected void onPause() {
        super.onPause();
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) this);
    }

    public void initLocation() {
        String[] permissions = new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                int result = PermissionChecker.checkSelfPermission(this, permission);
                if (result == PermissionChecker.PERMISSION_GRANTED) ;
                else ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }

        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;

        mlocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mlocationListener = new MyLocationListener();
        mlocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, mlocationListener);
        mlocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, mlocationListener);

        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
            altitude = mLastLocation.getAltitude();
        }

    }

    private class MyLocationListener implements LocationListener {
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
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

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        altitude = location.getAltitude();
    }

    public void toLocation(View view) {
        String[] permissions = new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                int result = PermissionChecker.checkSelfPermission(this, permission);
                if (result == PermissionChecker.PERMISSION_GRANTED) ;
                else ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }

        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;

        LocationRequest request = new LocationRequest();
        request.setInterval(10000);
        request.setFastestInterval(5000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, request, this);

        try {
            Geocoder geo = new Geocoder(mContext, Locale.KOREAN);
            List<Address> addr = geo.getFromLocation(latitude, longitude, 2);
            String country = addr.get(0).getCountryName();
            String addrline = addr.get(0).getAddressLine(0);
            String phone = addr.get(0).getPhone();
            String post = addr.get(0).getPostalCode();
            String total = new String(country + ", " + addrline);
            textInputEditText.setText(total);
        } catch (IOException e) {
        }
    }


    //저장버튼 눌렀을 때 호출되는 함수
    public void saveButtonClicked() {
        String title = textInputEditTextTitle.getText().toString();
        address = textInputEditText.getText().toString();

        try {
            Geocoder geo = new Geocoder(mContext, Locale.KOREAN);
            List<Address> addr = geo.getFromLocationName(address, address.length() + 1);
            latitude = addr.get(0).getLatitude();
            longitude = addr.get(0).getLongitude();
        } catch (IOException e) {
        }
        findSavePress();

    }

    //파일 업로드시 파일 이름이 겹치지 않게 하기 위해..
    public String getImageExt(Uri uri) {
        ContentResolver contentResolver = this.getContentResolver();
        MimeTypeMap mimeTypemap = MimeTypeMap.getSingleton();

        return mimeTypemap.getExtensionFromMimeType(contentResolver.getType(uri));
    }


   // 파일 업로드 버튼 누르면 호출
    public void findSavePress() {
        infor1 = textInputEditTextTitle.getText().toString();
        infor2 = textInputEditText.getText().toString();
        infor3 = find_textbox.getText().toString();
        infor4 = String.valueOf(longitude).toString();
        infor5 = String.valueOf(latitude).toString();
        infor6 = categorySpinner.getSelectedItem().toString();

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            //파일 명
            StorageReference riversRef = storageReference.child(FB_Storage_Path + System.currentTimeMillis() + "." + getImageExt(filePath));

            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        //성공시
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            if (infor1.equals("") || infor2.equals("") || infor3.equals("") || infor4.equals("") || infor5.equals("") || infor6.equals("")) {
                                progressDialog.dismiss();

                            }

                            else {
                                progressDialog.dismiss();
                                Find findUpload = new Find(infor1, infor6, infor2,
                                        infor3, taskSnapshot.getDownloadUrl().toString(), infor4, infor5);

                                String uploadurl = databaseReference.push().getKey();
                                databaseReference.child(uploadurl).setValue(findUpload);
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





