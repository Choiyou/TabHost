package com.example.os150.tabhost;
/*
설명 : ViewActivity의 class 파일입니다 connectmainActivity.class로 부터 intent를 받아옵니다.

작성 : 2014244094 성해성
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Intent intent  = getIntent();

        ImageView imgView = (ImageView)findViewById(R.id.imageView3);
        TextView txtView = (TextView)findViewById(R.id.textView3);
        TextView txtName = (TextView) findViewById(R.id.viewName);
        TextView txtCategory = (TextView)findViewById(R.id.viewCategory);
        TextView txtContents = (TextView)findViewById(R.id.viewContents);
        TextView txtPrice = (TextView)findViewById(R.id.viewPay);
        Button btnChat = (Button)findViewById(R.id.chatbtn);

        txtName.setText(intent.getStringExtra("title"));
        txtContents.setText(intent.getStringExtra("contents"));
        txtPrice.setText(intent.getStringExtra("price"));
        txtCategory.setText(intent.getStringExtra("category"));
        txtView.setText(intent.getStringExtra("userEmail"));

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ViewActivity.this,ChatMain.class);
                startActivity(intent1);
            }
        });

    }
}
