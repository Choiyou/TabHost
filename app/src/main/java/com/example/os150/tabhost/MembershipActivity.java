package com.example.os150.tabhost;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by os150 on 2018-11-26.
 */

public class MembershipActivity extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membershipinfo);
        final TextView txtLoginInfo = (TextView) findViewById(R.id.loginInfo);
        Button btnSales = (Button) findViewById(R.id.btnSales);
        Button btnPurchase = (Button) findViewById(R.id.btnPurchase);
        Button btnLikeProduct = (Button) findViewById(R.id.btnlikeProduct);
        Button btnMyPosts = (Button) findViewById(R.id.btnMyPosts);
        Button btnTownSetting = (Button) findViewById(R.id.btntownSetting);
        Button btnKeyword = (Button)findViewById(R.id.btnKeyword);
        Button btnSignin = (Button) findViewById(R.id.Signin);
        Button btnLogin = (Button) findViewById(R.id.login);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null) {
            if (user.getDisplayName() == "") {
                txtLoginInfo.setText("이름을 설정해주세요.");
            } else {
                txtLoginInfo.setText("회원 : " + user.getDisplayName());
            }
        }
        else{
            txtLoginInfo.setText("비회원입니다.");
        }
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
                    Toast.makeText(MembershipActivity.this,"로그인을 먼저 해주세요",Toast.LENGTH_SHORT).show();
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
    }





}

