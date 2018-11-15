//Android Studio chapter 6 - 3 TabHost를 이용한 예제 입니다.
//모바일 SW 프로젝트 12분반 2016244013 최유진 입니다.

package com.example.os150.tabhost;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

    Button btnCaSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView txtLoginInfo = (TextView) findViewById(R.id.loginInfo);
        Button btnSales = (Button) findViewById(R.id.btnSales);
        btnCaSelect =(Button)findViewById(R.id.category_select);
        Button btnPurchase = (Button) findViewById(R.id.btnPurchase);
        Button btnLikeProduct = (Button) findViewById(R.id.btnlikeProduct);
        Button btnMyPosts = (Button) findViewById(R.id.btnMyPosts);
        Button btnTownSetting = (Button) findViewById(R.id.btntownSetting);
        Button btnKeyword = (Button)findViewById(R.id.btnKeyword);
        Button btnSignin = (Button) findViewById(R.id.Signin);
        Button btnLogin = (Button) findViewById(R.id.login);
        Button btnAddPhoto =(Button)findViewById(R.id.photo_add);
        EditText editTitle =(EditText)findViewById(R.id.edtTitle);
        EditText editPrice =(EditText)findViewById(R.id.edtPrice);
        EditText editContents =(EditText)findViewById(R.id.edtContents);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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

            }
        });


        btnCaSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoryselectActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);   //Activity존재시 재사용.
                startActivityForResult(intent, 100);

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

    //intent결과 값 받아오는창. startActivityResult에서 값 비교, Sextra에서 결과 비교해서 같을경우 가져오게 해야함.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if(requestCode == 100)
                btnCaSelect.setText(data.getStringExtra("result"));
        }
    }
}