package com.example.os150.tabhost;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by os150 on 2018-10-30.
 */

public class LikeProductActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getApplicationContext(),"관심 상품 레이아웃 입니다.", Toast.LENGTH_SHORT).show();

    }
}
