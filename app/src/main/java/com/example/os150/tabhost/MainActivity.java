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
import android.view.InflateException;
import android.view.View;
import android.view.WindowManager;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);    //스테이터스바 제거.
        setContentView(R.layout.activity_main);
        final TextView txtLoginInfo = (TextView) findViewById(R.id.loginInfo);
        Button btnSales = (Button) findViewById(R.id.btnSales);
        Button btnPurchase = (Button) findViewById(R.id.btnPurchase);
        Button btnLikeProduct = (Button) findViewById(R.id.btnlikeProduct);
        Button btnMyPosts = (Button) findViewById(R.id.btnMyPosts);
        Button btnTownSetting = (Button) findViewById(R.id.btntownSetting);
        Button btnKeyword = (Button) findViewById(R.id.btnKeyword);
        Button btnSignin = (Button) findViewById(R.id.Signin);
        Button btnLogin = (Button) findViewById(R.id.login);
        Context ctx = this.getApplicationContext();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        if (user != null) {
            txtLoginInfo.setText("회원 : " + user.getEmail());
        } else {
            txtLoginInfo.setText("비회원입니다.");
        }


        TabHost tabHost = getTabHost();


        TabHost.TabSpec tabSpecMain = tabHost.newTabSpec("MAIN").setIndicator("메인");
        Intent intentMain = new Intent(this,ConnectmainActivity.class);
        tabSpecMain.setContent(intentMain);
        tabHost.addTab(tabSpecMain);


        TabHost.TabSpec tabSpecCategory = tabHost.newTabSpec("CATEGORY").setIndicator("카테고리");

        Intent intentCategory = new Intent(this, CategoryActivity.class);

        /* try{
            tabSpecCategory.setContent(intentCategory);
        }catch(InflateException e){} */
        tabSpecCategory.setContent(intentCategory);
        tabHost.addTab(tabSpecCategory);


        TabHost.TabSpec tabSpecWrite = tabHost.newTabSpec("tab2");
        tabSpecWrite.setIndicator("글쓰기");
        Intent intentwrite = new Intent(ctx, WriteActivity.class);
        intentwrite.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        tabSpecWrite.setContent(intentwrite);
        tabHost.addTab(tabSpecWrite);


        TabHost.TabSpec tabSpecInfo = tabHost.newTabSpec("INFORMATION").setIndicator("개인정보");
        tabSpecInfo.setContent(R.id.tab4);
        tabHost.addTab(tabSpecInfo);
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {

            LinearLayout linearLayout = (LinearLayout) tabHost.getTabWidget().getChildAt(i);

            TextView tv = (TextView) linearLayout.getChildAt(1);
            tv.setTextSize(9);

        }


        TabHost.TabSpec tabSpecChat = tabHost.newTabSpec("CHATTING").setIndicator("채팅");
        Intent intentChat = new Intent(this, ChatMain.class);

        /* try{
            tabSpecChat.setContent(intentChat);
        }catch(InflateException e){} */
        tabSpecChat.setContent(intentChat);
        tabHost.addTab(tabSpecChat);
        // tabHost.setCurrentTab(0);


        txtLoginInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user != null) {
                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "로그인을 먼저 해주세요", Toast.LENGTH_SHORT).show();
                }

            }
        });
        TabHost.TabSpec tabSpecMap = tabHost.newTabSpec("tab2");
        tabSpecMap.setIndicator("지도");
        Intent intentmap = new Intent(ctx, MapsActivity.class);
        intentmap.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        tabSpecMap.setContent(intentmap);
        tabHost.addTab(tabSpecMap);


        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SigninActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
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

    }
}