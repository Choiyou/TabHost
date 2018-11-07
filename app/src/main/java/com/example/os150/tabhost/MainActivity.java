//Android Studio chapter 6 - 3 TabHost를 이용한 예제 입니다.
//모바일 SW 프로젝트 12분반 2016244013 최유진 입니다.

package com.example.os150.tabhost;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
        Button btnAddPhoto =(Button)findViewById(R.id.photo_add);
        Button btnCaSelect =(Button)findViewById(R.id.category_select);
        EditText editTitle =(EditText)findViewById(R.id.edtTitle);
        EditText editPrice =(EditText)findViewById(R.id.edtPrice);
        EditText editContents =(EditText)findViewById(R.id.edtContents);
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

            }
        });

        btnCaSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CategoryselectActivity.class);
                startActivity(intent);

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
}