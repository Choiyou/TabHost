package com.example.os150.tabhost;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by os150 on 2018-10-30.
 */

public class TownSettingActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_townsetting);
        Toast.makeText(getApplicationContext(),"동네 설정 레이아웃 입니다.", Toast.LENGTH_SHORT).show();

        Button nextButton = (Button) findViewById(R.id.nextbutton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TownSettingActivity.this, CMaps.class);
                startActivity(intent);
            }
        });

    }

}
