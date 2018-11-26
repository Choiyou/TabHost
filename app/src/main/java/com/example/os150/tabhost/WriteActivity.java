package com.example.os150.tabhost;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteActivity extends Activity{

    private final int CategoryResult = 100;
    private final int CameraResult = 200;
    private final int AlbumResult = 300;
    public Uri imgUri, photoURI, albumURI;
    private String mCurrentPhotoPath;
    private final String[] RequiredPermission = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    Button btnCaSelect;
    Button Write_OK;
    boolean WriteCheckResult;
    ImageView itemImg1,itemImg2,itemImg3,itemImg4,itemImg5;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        ImageView itemImg1 = (ImageView) findViewById(R.id.itemImg1);
        ImageView itemImg2 = (ImageView) findViewById(R.id.itemImg2);
        ImageView itemImg3 = (ImageView) findViewById(R.id.itemImg3);
        ImageView itemImg4 = (ImageView) findViewById(R.id.itemImg4);
        ImageView itemImg5 = (ImageView) findViewById(R.id.itemImg5);
        btnCaSelect = (Button) findViewById(R.id.category_select);
        Write_OK = (Button) findViewById(R.id.OK_btn);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Button btnAddPhoto = (Button) findViewById(R.id.photo_add);
        final EditText editTitle = (EditText) findViewById(R.id.edtTitle);
        final EditText editPrice = (EditText) findViewById(R.id.edtPrice);
        final EditText editContents = (EditText) findViewById(R.id.edtContents);

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
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, CategoryResult);
            }
        });

        Write_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleCheck = editTitle.getText().toString();
                String ContentsCheck = editContents.getText().toString();
                int price = 0;
                try{
                    price = Integer.parseInt(editPrice.getText().toString());
                } catch (NumberFormatException e) {}
                if(titleCheck.length() == 0) {
                    Toast.makeText(WriteActivity.this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if(ContentsCheck.length() == 0) {
                    Toast.makeText(WriteActivity.this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if(btnCaSelect.getText().toString().equals("카테고리 선택>")) {
                    Toast.makeText(WriteActivity.this, "카테고리를 선택해주세요.", Toast.LENGTH_SHORT).show();
                } else if(price == 0) {
                    Writecheck();
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
                                checkPermission();
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
        alert_confirm.setMessage("금액을 0원으로 하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("네",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(WriteActivity.this, "금액을 0원으로 설정합니다.", Toast.LENGTH_SHORT).show();
                                Pass();
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

    //퍼미션 확인용. 카메라, 저장소 읽기, 쓰기.
    public void checkPermission() {
        int CameraPermission, WritePermission, ReadPermission;
        CameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        WritePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        ReadPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if(CameraPermission == PackageManager.PERMISSION_DENIED || WritePermission == PackageManager.PERMISSION_DENIED || ReadPermission == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, RequiredPermission,0);
        } else {
            takePhoto();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean check_result = true;
        //거부 퍼미션 확인.
        for(int result : grantResults) {
            if(result != PackageManager.PERMISSION_GRANTED){
                check_result = false;
                break;
            }
        }
        //거부한 퍼미션이 없는 경우.
        if(check_result) {
            takePhoto();
        } else {                //거부한 퍼미션이 있는 경우.
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, RequiredPermission[0]) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, RequiredPermission[1]) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, RequiredPermission[2])) {
                Toast.makeText(this, "앱을 다시 실행해서 접근 권한을 허용해주세요.", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this, RequiredPermission, 0);
            }
            else {      //거부한적없고 그냥 권한 실행하는 것.
                ActivityCompat.requestPermissions(this, RequiredPermission, 0);
            }
        }
    }


}
