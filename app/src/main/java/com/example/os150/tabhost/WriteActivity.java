package com.example.os150.tabhost;
/*
설명 : 글쓰기 TAB 입니다.
사용법 : 제목, 카테고리, 내용을 필수적으로 입력, 가격은 선택.
        완료버튼을 누르면 Datebase에 저장이 됩니다.
 */

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteActivity extends Activity {

    private final int CategoryResult = 100;
    private final int CameraResult = 200;
    private final int AlbumResult = 300;
    private final int PermissionResult = 400;
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
                    String provider = location.getProvider();
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();
                    System.out.println("처음 받은 위도 : " + longitude +" 경도 : " + latitude);
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
                    Writecheck();
                }
                //데이터 베이스 에 데이터 전송
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myContents = database.getReference();
                if (user != null) {
                    String Title = editTitle.getText().toString();   //글제목
                    String Price = editPrice.getText().toString();   //가격
                    String Contents = editContents.getText().toString();//글내용
                    String Name = btnCaSelect.getText().toString(); //글카테고리
                    double Lat = lati;
                    double Lng = longi;

                    Catedata catedata = new Catedata(Name, Price, Contents, Title,Lat,Lng);

                    myContents.child("Market").child(btnCaSelect.getText().toString()).push().setValue(catedata);
                } else {
                    Toast.makeText(WriteActivity.this, "로그인을 해주세요", Toast.LENGTH_SHORT).show();
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
            } else if(requestCode == AlbumResult) {
                if(data.getData() != null) {
                    try {
                        File albumFile = null;
                        albumFile = createImageFile();              //생성된 file Uri를 받음.
                        System.out.println("갤러리 선택시 URI" + albumFile.getAbsolutePath());
                        photoURI = data.getData();
                        albumURI = Uri.fromFile(albumFile);

                        galleryAddPic();
                        itemImg1.setImageURI(photoURI);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if(requestCode == CameraResult) {
                try{
                    System.out.println("TakePhoto의 imgUri값 : "+imgUri);
                    Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
                    ExifInterface exif = null;
                    try{
                        exif = new ExifInterface(mCurrentPhotoPath);
                    } catch (IOException e) {}

                    int exifOrientation;
                    int exifDegree;

                    if(exif != null) {
                        exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                        exifDegree = exifOrientationToDegrees(exifOrientation);
                    } else {
                        exifDegree = 0;
                    }
                    galleryAddPic();
                    itemImg1.setImageBitmap(rotate(bitmap, exifDegree));
                    //itemImg1.setImageURI(imgUri);
                } catch (Exception e) {}
            }
        }
    }

    private int exifOrientationToDegrees(int exifOrientation) {
        if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    private Bitmap rotate(Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0,0, bitmap.getWidth(),bitmap.getHeight(),matrix, true);
    }

    //앨범에서 가져오기.
    private void selectAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,AlbumResult);
    }

    //갤러리에 저장함.
    public void galleryAddPic()  {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
        Toast.makeText(this, "사진이 저장되었습니다.", Toast.LENGTH_SHORT).show();

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            System.out.println("galleryAddPic에서 읽기 권한이 있습니다!.");
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            System.out.println("galleryAddPic에서 쓰기 권한이 있습니다!.");
        }

    }

    //사진을 찍을떄.
    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);    //캡쳐 실행.
        if(intent.resolveActivity(getPackageManager())!=null) {         //intent를 수신할 앱이 null 즉 없는 경우.
            File photoFile = null;
            try{
                photoFile = createImageFile();
                //현재 값 새로 만들어진 jpg파일의 URI 잘 적용 됌.
                System.out.println("takePhoto에서 URI = " + photoFile.getAbsolutePath());
            } catch (IOException e) {}
            if(photoFile != null) {
                try {
                    imgUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName(), photoFile);
                } catch(NullPointerException e) {}
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                startActivityForResult(intent, CameraResult);           //URI를 획득.intent를 실행.
            }
        }

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            //    System.out.println("takephoto에서 카메라 권한이 있습니다!.");
        }
    }

    //사진 파일 생성. File 반환.        생성에 문제 없음.
    public File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Image_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,      /* prefix */
                ".jpg",         /* suffix */
                storageDir          /* directory */
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        System.out.println("createImageFile에서...");
        System.out.println("mCurrentPhotoPath 경로 : " + mCurrentPhotoPath);
        System.out.println("File변수 image의   경로 : " + image.getAbsolutePath());

        return image;
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
                                takePhoto();
                            }
                        })
                .setNeutralButton("앨범 선택",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //앨범 선택.
                                selectAlbum();
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

    //boolean변수인 priceZero를 설정해줍니다.
    private void Writecheck(){
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(this);
        alert_confirm.setMessage("금액을 0원으로 설정하고 완료하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("네",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(WriteActivity.this, "0원으로 설정하고 완료합니다.", Toast.LENGTH_SHORT).show();
                                Pass();
                                //파이어 베이스 글 올리는거 여기에 작성하면 됩니다!!!
                                //WriteCheckResult 불 변수를 이용해서 참, 거짓 판별 가능.

                                Clear();
                            }
                        })
                .setNegativeButton("아니오",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(WriteActivity.this, "금액을 다시 설정해주세요..", Toast.LENGTH_SHORT).show();
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

    private void Clear() {
        btnCaSelect.setText("카테고리 선택>");
        editPrice = null;
        editTitle = null;
        editContents = null;
        UseMap.setChecked(false);
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
