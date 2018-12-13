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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.os150.tabhost.Chat.GoChat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

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
        EditText editText1 =(EditText)findViewById(R.id.e1);
        EditText editText2 =(EditText)findViewById(R.id.e2);
        EditText editText3 =(EditText)findViewById(R.id.e3);
        EditText editText4 =(EditText)findViewById(R.id.e4);
        EditText editText5 =(EditText)findViewById(R.id.e5);
        txtName.setText(intent.getStringExtra("title"));
        txtContents.setText(intent.getStringExtra("contents"));
        txtPrice.setText(intent.getStringExtra("price"));
        txtCategory.setText(intent.getStringExtra("category"));
        txtView.setText(intent.getStringExtra("userEmail"));

        editText1.setFocusable(false);
        editText1.setClickable(false);
        editText2.setFocusable(false);
        editText2.setClickable(false);
        editText3.setFocusable(false);
        editText3.setClickable(false);
        editText4.setFocusable(false);
        editText4.setClickable(false);
        editText5.setFocusable(false);
        editText5.setClickable(false);

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ViewActivity.this,GoChat.class);
                Intent intent2 = new Intent(ViewActivity.this,ChatFailActivity.class);

                if(FirebaseAuth.getInstance().getCurrentUser()==null){
                    startActivity(intent2);
                }else {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    //현재유저 말고 txtView의 유저 불러오기
                    String userUid = user.getUid().toString();
                    intent1.putExtra("destinationUid",userUid);
                    startActivity(intent1);

                }
            }
        });

    }
}
