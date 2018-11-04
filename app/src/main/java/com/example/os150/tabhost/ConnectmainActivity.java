package com.example.os150.tabhost;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class ConnectmainActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getApplicationContext(),"카테고리 별 레이아웃 입니다.", Toast.LENGTH_SHORT).show();
    }
}
