package com.example.os150.tabhost;
/*
설명 : 카테고리 탭의 Activity입니다.

 */
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CategoryActivity extends Activity {
    Button cBtn1,cBtn2,cBtn3,
            cBtn4,cBtn5,cBtn6,
            cBtn7,cBtn8,cBtn9,
            cBtn10,cBtn11,cBtn12,
            cBtn13,cBtn14,cBtn15;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


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
    }



}
