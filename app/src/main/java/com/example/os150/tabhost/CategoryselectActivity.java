package com.example.os150.tabhost;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

public class CategoryselectActivity extends Activity  {
    private static final String TAG = "CategoryselectActivity";
    Button select1,select2,select3
    ,select4,select5,select6
    ,select7,select8,select9
    ,select10,select11,select12
    ,select13,select14,select15;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoryselect);
        select1= (Button)findViewById(R.id.select_btn1);

        select1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = select1.getText().toString();
                startIntent(data);
            }
        });

        select2= (Button)findViewById(R.id.select_btn2);
        select2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = select2.getText().toString();
                startIntent(data);
            }
        });
        select3= (Button)findViewById(R.id.select_btn3);
        select3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = select3.getText().toString();
                startIntent(data);
            }
        });
        select4= (Button)findViewById(R.id.select_btn4);
        select4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = select4.getText().toString();
                startIntent(data);
            }
        });
        select5= (Button)findViewById(R.id.select_btn5);
        select5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = select5.getText().toString();
                startIntent(data);
            }
        });
        select6= (Button)findViewById(R.id.select_btn6);
        select6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = select6.getText().toString();
                startIntent(data);
            }
        });
        select7= (Button)findViewById(R.id.select_btn7);
        select7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = select7.getText().toString();
                startIntent(data);
            }
        });
        select8= (Button)findViewById(R.id.select_btn8);
        select8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = select8.getText().toString();
                startIntent(data);
            }
        });
        select9= (Button)findViewById(R.id.select_btn9);
        select9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = select9.getText().toString();
                startIntent(data);
            }
        });
        select10= (Button)findViewById(R.id.select_btn10);
        select10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = select10.getText().toString();
                startIntent(data);
            }
        });
        select11= (Button)findViewById(R.id.select_btn11);
        select11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = select11.getText().toString();
                startIntent(data);
            }
        });
        select12= (Button)findViewById(R.id.select_btn12);
        select12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = select12.getText().toString();
                startIntent(data);
            }
        });
        select13= (Button)findViewById(R.id.select_btn13);
        select13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = select13.getText().toString();
                startIntent(data);
            }
        });
        select14= (Button)findViewById(R.id.select_btn14);
        select14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = select14.getText().toString();
                startIntent(data);
            }
        });
        select15= (Button)findViewById(R.id.select_btn15);
        select15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = select15.getText().toString();
                startIntent(data);
            }
        });

    }

    //intent정보 전달 메소드
    private void startIntent(String sendData) {
        Intent intent = new Intent(CategoryselectActivity.this, MainActivity.class);
        intent.putExtra("result", sendData);
        System.out.println("Main 으로 보내는 정보 : "+ sendData);
        setResult(RESULT_OK, intent);
        finish();
    }
}
