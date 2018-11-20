//Android Studio chapter 6 - 3 TabHost를 이용한 예제 입니다.
//모바일 SW 프로젝트 12분반 2016244013 최유진 입니다.

package com.example.os150.tabhost;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

    private final int CategoryResult = 100;
    private final int CameraResult = 200;
    private final int AlbumResult = 300;
    private Uri imgUri, photoURI, albumURI;
    private String mCurrentPhotoPath;

    Button btnCaSelect;
    Button Write_OK;
    boolean priceZero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView txtLoginInfo = (TextView) findViewById(R.id.loginInfo);
        Button btnSales = (Button) findViewById(R.id.btnSales);
        Button btnPurchase = (Button) findViewById(R.id.btnPurchase);
        Button btnLikeProduct = (Button) findViewById(R.id.btnlikeProduct);
        Button btnMyPosts = (Button) findViewById(R.id.btnMyPosts);
        Button btnTownSetting = (Button) findViewById(R.id.btntownSetting);
        Button btnKeyword = (Button)findViewById(R.id.btnKeyword);
        Button btnSignin = (Button) findViewById(R.id.Signin);
        Button btnLogin = (Button) findViewById(R.id.login);
        Button btnAddPhoto =(Button)findViewById(R.id.photo_add);
        btnCaSelect =(Button)findViewById(R.id.category_select);
        Write_OK = (Button)findViewById(R.id.OK_btn);
        final EditText editTitle =(EditText)findViewById(R.id.edtTitle);
        final EditText editPrice =(EditText)findViewById(R.id.edtPrice);
        final EditText editContents =(EditText)findViewById(R.id.edtContents);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ImageView itemImg1 = (ImageView)findViewById(R.id.itemImg1);
        ImageView itemImg2 = (ImageView)findViewById(R.id.itemImg2);
        ImageView itemImg3 = (ImageView)findViewById(R.id.itemImg3);
        ImageView itemImg4 = (ImageView)findViewById(R.id.itemImg4);
        ImageView itemImg5 = (ImageView)findViewById(R.id.itemImg5);



        if(user!=null) {
            txtLoginInfo.setText("회원 : " + user.getEmail());
        }
        else{
            txtLoginInfo.setText("비회원입니다.");
        }
        Button cBtn1,cBtn2,cBtn3,
                cBtn4,cBtn5,cBtn6,
                cBtn7,cBtn8,cBtn9,
                cBtn10,cBtn11,cBtn12,
                cBtn13,cBtn14,cBtn15;

        TabHost tabHost = getTabHost();


        TabHost.TabSpec tabSpecMain = tabHost.newTabSpec("MAIN").setIndicator("메인");
        tabSpecMain.setContent(R.id.tab1);
        tabHost.addTab(tabSpecMain);


        TabHost.TabSpec tabSpecCategory = tabHost.newTabSpec("CATEGORY").setIndicator("카테고리");
        tabSpecCategory.setContent(R.id.tab2);
        tabHost.addTab(tabSpecCategory);


        TabHost.TabSpec tabSpecWrite = tabHost.newTabSpec("Write").setIndicator("글쓰기");
        tabSpecWrite.setContent(R.id.tab3);
        tabHost.addTab(tabSpecWrite);


        TabHost.TabSpec tabSpecInfo = tabHost.newTabSpec("INFORMATION").setIndicator("개인정보");
        tabSpecInfo.setContent(R.id.tab4);
        tabHost.addTab(tabSpecInfo);
        for (int i=0; i<tabHost.getTabWidget().getChildCount(); i++) {

            LinearLayout linearLayout = (LinearLayout)tabHost.getTabWidget().getChildAt(i);

            TextView tv = (TextView)linearLayout.getChildAt(1);
            tv.setTextSize(9);

        }


        TabHost.TabSpec tabSpecChat = tabHost.newTabSpec("CHATTING").setIndicator("채팅");
        tabSpecChat.setContent(R.id.tab5);
        tabHost.addTab(tabSpecChat);
        //tabHost.setCurrentTab(0);

        txtLoginInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if(user!=null) {
                   Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                   startActivity(intent);
               }
               else
               {
                   Toast.makeText(MainActivity.this,"로그인을 먼저 해주세요",Toast.LENGTH_SHORT).show();
               }

            }
        });


        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SigninActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
        btnSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SaleActivity.class);
                startActivity(intent);
            }
        });

        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PurchaseActivity.class);
                startActivity(intent);
            }
        });
        btnLikeProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LikeProductActivity.class);
                startActivity(intent);
            }
        });
        btnMyPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPostsActivity.class);
                startActivity(intent);
            }
        });
        btnTownSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TownSettingActivity.class);
                startActivity(intent);
            }
        });
        btnKeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), KeywordActivity.class);
                startActivity(intent);
            }
        });
        cBtn1 = (Button)findViewById(R.id.btn1);
        cBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ConnectmainActivity.class);
                startActivity(intent);
            }
        });
        cBtn2 = (Button)findViewById(R.id.btn2);
        cBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ConnectmainActivity.class);
                startActivity(intent);
            }
        });
        cBtn3 = (Button)findViewById(R.id.btn3);
        cBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ConnectmainActivity.class);
                startActivity(intent);
            }
        });
        cBtn4 = (Button)findViewById(R.id.btn4);
        cBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ConnectmainActivity.class);
                startActivity(intent);
            }
        });
        cBtn5 = (Button)findViewById(R.id.btn5);
        cBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ConnectmainActivity.class);
                startActivity(intent);
            }
        });
        cBtn6 = (Button)findViewById(R.id.btn6);
        cBtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ConnectmainActivity.class);
                startActivity(intent);
            }
        });
        cBtn7 = (Button)findViewById(R.id.btn7);
        cBtn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ConnectmainActivity.class);
                startActivity(intent);
            }
        });
        cBtn8 = (Button)findViewById(R.id.btn8);
        cBtn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ConnectmainActivity.class);
                startActivity(intent);
            }
        });
        cBtn9 = (Button)findViewById(R.id.btn9);
        cBtn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ConnectmainActivity.class);
                startActivity(intent);
            }
        });
        cBtn10 = (Button)findViewById(R.id.btn10);
        cBtn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ConnectmainActivity.class);
                startActivity(intent);
            }
        });
        cBtn11 = (Button)findViewById(R.id.btn11);
        cBtn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ConnectmainActivity.class);
                startActivity(intent);
            }
        });
        cBtn12 = (Button)findViewById(R.id.btn12);
        cBtn12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ConnectmainActivity.class);
                startActivity(intent);
            }
        });
        cBtn13 = (Button)findViewById(R.id.btn13);
        cBtn13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ConnectmainActivity.class);
                startActivity(intent);
            }
        });
        cBtn14 = (Button)findViewById(R.id.btn14);
        cBtn14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ConnectmainActivity.class);
                startActivity(intent);
            }
        });
        cBtn15 = (Button)findViewById(R.id.btn15);
        cBtn15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ConnectmainActivity.class);
                startActivity(intent);
            }
        });

        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeDialog();
            }
        });

        //판매글 올리는 곳에서 카테고리 선택하는 곳.
        btnCaSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CategoryselectActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, CategoryResult);
            }
        });

        //글 완료 버튼시 시작하는 곳.--------------------------------------------------------------------------
        Write_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString();
                int price = 0;
                try{
                    price = Integer.valueOf(editPrice.getText().toString());
                } catch(NumberFormatException e) { }
                String contents = editContents.getText().toString();
                //내용 입력 확인.
                if(title.length() == 0) {
                    Toast.makeText(MainActivity.this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if(contents.length() == 0) {
                    Toast.makeText(MainActivity.this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if(btnCaSelect.getText().toString().equals("카테고리 선택>")) {
                    Toast.makeText(MainActivity.this, "카테고리를 선택해주세요.", Toast.LENGTH_SHORT).show();
                }else if(price == 0) {
                    Pricecheck();
                }


            }
        });


        TabHost.TabSpec tabSpecMap = tabHost.newTabSpec("tab2");
        tabSpecMap.setIndicator("지도");
        Context ctx = this.getApplicationContext();
        Intent intentmap = new Intent(ctx, MapsActivity.class);
        //에러나면 아래 주석 풀어주고 바로 아랫줄 setContent부분 주석처리 해주세요.
        /*
        try {
            tabSpecMap.setContent(intentmap);
        } catch (InflateException e) { }
        */
        tabSpecMap.setContent(intentmap);
        tabHost.addTab(tabSpecMap);
    }

    //다른곳 Intent해서 값 받아오는 곳.
    //requestCode는 startActivityForResult에서 reauestCode와 같아야 원하는 정보를 받을수 있다. 중복 X
    //resultCode는 RESULT_OK로 통일.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if(requestCode == CategoryResult) {
                btnCaSelect.setText(data.getStringExtra("result"));
                System.out.println("받은 데이터 : "+ data.getStringExtra("result"));
            }

        }
    }
    //앨범에서 가져오기.
    private void selectAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setType("image/*");
        startActivityForResult(intent,AlbumResult);
    }

    private void takePhoto() {
        String state = Environment.getExternalStorageState();

        if(Environment.MEDIA_MOUNTED.equals(state)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(intent.resolveActivity(getPackageManager())!=null) {
                File photoFile = null;
                try{
                    photoFile = createImageFile();
                } catch (IOException e) {}
                if(photoFile != null) {
                    Uri providerURI = FileProvider.getUriForFile(this, getPackageName(), photoFile);
                    imgUri = providerURI;
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, providerURI);
                    startActivityForResult(intent, CameraResult);
                }
            }
            else{
                Toast.makeText(this, "저장공간 접근 불가.", Toast.LENGTH_SHORT).show();
                return;
            }

        }
    }
    public File createImageFile() throws IOException {
        String imgFileNmae = System.currentTimeMillis() + ".jpg";
        File imageFile = null;
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "ireh");
        if()
    }



    //이미지 가져오기.
    private void makeDialog(){
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MainActivity.this);
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
                                Toast.makeText(MainActivity.this, "취소", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = alert_confirm.create();
        alert.show();
    }

    //boolean변수인 priceZero를 설정해줍니다.
    private void Pricecheck(){
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MainActivity.this);
        alert_confirm.setMessage("금액을 0원으로 하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("네",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "금액을 0원으로 설정합니다.", Toast.LENGTH_SHORT).show();
                                PriceZero();
                            }
                        })
                .setNegativeButton("아니오",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "금액을 다시 설정해주세요..", Toast.LENGTH_SHORT).show();
                                PriceNonZero();
                            }
                        });
        AlertDialog alert = alert_confirm.create();
        alert.show();
    }


    //가격이 0원이여도 됩니다.
    private void PriceZero() {
        priceZero = true;
    }
    //가격이 0원이면 아니되오....
    private void PriceNonZero() {
        priceZero = false;
    }


}