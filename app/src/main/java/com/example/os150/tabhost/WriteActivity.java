package com.example.os150.tabhost;
/*
설명 : 글쓰기 TAB 입니다.
사용법 : 제목, 카테고리, 내용을 필수적으로 입력, 가격은 선택.
        완료버튼을 누르면 Datebase에 저장이 됩니다.
 */

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteActivity extends Activity {


    private final int CategoryResult = 100;
    private final int FROM_CAMERA = 200;
    private final int FROm_ALBUM = 300;
    private final int PermissionResult = 400;
    private int flag;
    public Uri imgUri, photoURI, albumURI;
    private String mCurrentPhotoPath;
    private static final String[] RequiredPermission = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };
    double longi,lati; //위도, 경도
    Button btnCaSelect;
    Button Write_OK;
    boolean WriteCheckResult;
    ImageView itemImg1, itemImg2, itemImg3, itemImg4, itemImg5;
    EditText editTitle, editPrice, editContents;
    CheckBox UseMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        UseMap = (CheckBox) findViewById(R.id.Mapcheck);
        ImageView itemImg1 = (ImageView) findViewById(R.id.itemImg1);
        ImageView itemImg2 = (ImageView) findViewById(R.id.itemImg2);
        ImageView itemImg3 = (ImageView) findViewById(R.id.itemImg3);
        ImageView itemImg4 = (ImageView) findViewById(R.id.itemImg4);
        ImageView itemImg5 = (ImageView) findViewById(R.id.itemImg5);
        btnCaSelect = (Button) findViewById(R.id.category_select);
        Write_OK = (Button) findViewById(R.id.OK_btn);
        editTitle = (EditText) findViewById(R.id.edtTitle);
        editPrice = (EditText) findViewById(R.id.edtPrice);
        editContents = (EditText) findViewById(R.id.edtContents);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Button btnAddPhoto = (Button) findViewById(R.id.photo_add);

        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeDialog();
            }
        });
        btnCaSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WriteActivity.this, CategoryselectActivity.class);
                startActivityForResult(intent, CategoryResult);
            }
        });


        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        UseMap.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {        //지도 기능 확인.
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(WriteActivity.this, "현재 위치 정보를 받습니다.", Toast.LENGTH_SHORT).show();
                    if (ActivityCompat.checkSelfPermission(WriteActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(WriteActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(WriteActivity.this, "앱을 재시작하여 권한을 설정해주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    //double longitude = location.getLongitude();
                    //double latitude = location.getLatitude();
                    //System.out.println("처음 받은 위도 : " + longitude +" 경도 : " + latitude);
                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, gpsLocationListener);
                    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, gpsLocationListener);
                } else {
                    AlertDialog.Builder build = new AlertDialog.Builder(WriteActivity.this);
                    build.setMessage("지도 기능을 끄면 지도에 판매글이 표시되지 않습니다. 지도 기능을 끄시겠습니까?");
                    build.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(WriteActivity.this, "지도 기능을 끕니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    build.setNeutralButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(WriteActivity.this, "지도 기능을 유지합니다.", Toast.LENGTH_SHORT).show();
                            UseMap.setChecked(true);
                        }
                    });
                    build.show();
                }
            }
        });

        Write_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleCheck = editTitle.getText().toString();
                String ContentsCheck = editContents.getText().toString();
                int price = 0;
                try {
                    price = Integer.parseInt(editPrice.getText().toString());
                } catch (NumberFormatException e) {
                }
                if (titleCheck.length() == 0) {
                    Toast.makeText(WriteActivity.this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (ContentsCheck.length() == 0) {
                    Toast.makeText(WriteActivity.this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (btnCaSelect.getText().toString().equals("카테고리 선택>")) {
                    Toast.makeText(WriteActivity.this, "카테고리를 선택해주세요.", Toast.LENGTH_SHORT).show();
                } else if (price == 0) {
                    editPrice.setText(0);
                    Writecheck("금액을 0원으로 설정하시겠습니까?");
                } else if (price != 0) {
                    Writecheck("글을 올리시겠습니까?");
                }


            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if(requestCode == CategoryResult) {
                btnCaSelect.setText(data.getStringExtra("result"));
                //System.out.println("받은 데이터 : "+ data.getStringExtra("result"));
            } else if(requestCode == FROm_ALBUM) {
                if(data.getData() != null) {
                    try {
                        photoURI = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoURI);
                        itemImg1.setImageBitmap(bitmap);
                    } catch(Exception e) {}
                }
            } else if(requestCode == FROM_CAMERA) {
                try {
                    galleryAddPic();
                    itemImg1.setImageURI(imgUri);
                } catch(Exception e) {}
            }
        }
    }

    //이미지 가져오기.====================.====================.====================
    private void makeDialog(){
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(WriteActivity.this);
        alert_confirm.setMessage("업로드할 이미지 선택")
                .setCancelable(false)
                .setPositiveButton("사진 촬영",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //사진 찍으셈.
                                flag = 0;
                                //takePhoto();
                            }
                        })
                .setNeutralButton("앨범 선택",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //앨범 선택.
                                flag = 1;
                                //selectAlbum();
                            }
                        })
                .setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(WriteActivity.this, "취소", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = alert_confirm.create();
        alert.show();
    }

    public void takePhoto() {
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch(IOException e) {}
                if(photoFile != null) {
                    Uri providerURI = FileProvider.getUriForFile(this, getPackageName(), photoFile);
                    imgUri = providerURI;
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,providerURI);
                    startActivityForResult(intent, FROM_CAMERA);
                }
            }
        }else {
            System.out.println("저장곤간 접근 불가");
            return;
        }
    }

    public File createImageFile() throws IOException{
        String imgFileName = System.currentTimeMillis() + ".jpg";
        File imageFile = null;
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "ireh");
        if(!storageDir.exists()) {
            storageDir.mkdirs();
        }
        imageFile = new File(storageDir,imgFileName);
        mCurrentPhotoPath = imageFile.getAbsolutePath();
        return imageFile;
    }

    public void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }

    public void selectAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setType("image/*");
        startActivityForResult(intent, FROm_ALBUM);
    }



    private void Writecheck(String content) {
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(this);
        alert_confirm.setMessage(content)
                .setCancelable(false)
                .setPositiveButton("네",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(WriteActivity.this, "0원으로 설정하고 완료합니다.", Toast.LENGTH_SHORT).show();
                                Pass();

                                //데이터 베이스 에 데이터 전송
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myContents = database.getReference();
                                FirebaseStorage storage = null;

                                if (user != null) {
                                    String Title = editTitle.getText().toString();        //글제목
                                    String Price = editPrice.getText().toString();        //가격
                                    String Contents = editContents.getText().toString();  //글내용
                                    String Name = btnCaSelect.getText().toString();       //글카테고리
                                    String UserUid = user.getUid();
                                    String UserName =  user.getDisplayName();

                                    double Lat = lati;
                                    double Lng = longi;


                                    Catedata catedata = new Catedata(Name, Price, Contents, Title,UserUid,UserName,Lat,Lng);
                                    myContents.child("Market").child("Main").push().setValue(catedata);
                                    myContents.child("Market").child(btnCaSelect.getText().toString()).push().setValue(catedata);

                                    /*
                                    final String cu = user.getUid();
                                    //1. 사진을 storage에 저장하고 그 url을 알아내야함..
                                    String filename = cu + "_" + System.currentTimeMillis();
                                    StorageReference storageRef = storage.getReferenceFromUrl("본인의 Firebase 저장소").child("ImageFile/" + filename);
                                    UploadTask uploadTask;
                                    Uri file = null;
                                    if(flag ==0){
                                        //사진촬영
                                        file = Uri.fromFile(new File(mCurrentPhotoPath));
                                    }else if(flag==1){
                                        //앨범선택
                                        file = photoURI;
                                    }
                                    uploadTask = storageRef.putFile(file);
                                    final ProgressDialog progressDialog = new ProgressDialog(WriteActivity.this,R.style.AppTheme);
                                    progressDialog.setMessage("업로드중...");
                                    progressDialog.show();
                                    // Register observers to listen for when the download is done or if it fails
                                    uploadTask.addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Handle unsuccessful uploads
                                            exception.printStackTrace();
                                        }
                                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        }
                                    });
                                    */


                                } else {
                                    Toast.makeText(WriteActivity.this, "로그인을 해주세요", Toast.LENGTH_SHORT).show();
                                }
                                //파이어 베이스 글 올리는거 여기에 작성하면 됩니다!!!
                                //WriteCheckResult 불 변수를 이용해서 참, 거짓 판별 가능.

                                Clear();
                            }
                        })
                .setNegativeButton("아니오",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(WriteActivity.this, "금액을 다시 설정해주세요..", Toast.LENGTH_SHORT).show();
                                NonPass();
                            }
                        });
        AlertDialog alert = alert_confirm.create();
        alert.show();
    }


    //가격이 0원이여도 됩니다.
    private void Pass() {
        WriteCheckResult = true;
    }
    //가격이 0원이면 아니되오....
    private void NonPass() {
        WriteCheckResult = false;
    }

    //글 쓴거 초기화.
    private void Clear() {
        btnCaSelect.setText("카테고리 선택>");
        editPrice.setText(null);
        editTitle.setText(null);
        editContents.setText(null);
        NonPass();
    }

    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            String provider = location.getProvider();   //받아온 방법
            double longitude = location.getLongitude(); //경도
            double latitude = location.getLatitude();   //위도
            longi = longitude;
            lati = latitude;
            System.out.println("경도 : " + longitude + "위도 : " + latitude);
        }
        public void onStatusChanged(String provider, int status, Bundle extras) { }
        public void onProviderEnabled(String provider) { }
        public void onProviderDisabled(String provider) { } };



}
