//Android Studio chapter 6 - 3 TabHost를 이용한 예제 입니다.
//모바일 SW 프로젝트 12분반 2016244013 최유진 입니다.

package com.example.os150.tabhost;

import android.app.ActionBar;
import android.app.TabActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txtLoginInfo = (TextView) findViewById(R.id.loginInfo);
        Button btnSales = (Button) findViewById(R.id.btnSales);
        Button btnPurchase = (Button) findViewById(R.id.btnPurchase);
        Button btnLikeProduct = (Button) findViewById(R.id.btnlikeProduct);
        Button btnMyPosts = (Button) findViewById(R.id.btnMyPosts);
        Button btnTownSetting = (Button) findViewById(R.id.btntownSetting);
        Button btnKeyword = (Button)findViewById(R.id.btnKeyword);
        Button btnSignin = (Button) findViewById(R.id.Signin);
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
            tv.setTextSize(10);

        }


        TabHost.TabSpec tabSpecChat = tabHost.newTabSpec("CHATTING").setIndicator("채팅");
        tabSpecChat.setContent(R.id.tab5);
        tabHost.addTab(tabSpecChat);
        //tabHost.setCurrentTab(0);


        txtLoginInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginInfoActivity.class);
                startActivity(intent);
            }
        });

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SigninActivity.class);
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