//Android Studio chapter 6 - 3 TabHost를 이용한 예제 입니다.
//모바일 SW 프로젝트 12분반 2016244013 최유진 입니다.

package com.example.os150.tabhost;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.view.InflateException;
import android.view.View;
import android.view.WindowManager;
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

import java.io.File;
import java.io.IOException;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);    //스테이터스바 제거.
        setContentView(R.layout.activity_main);
        Context ctx = this.getApplicationContext();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



        TabHost tabHost = getTabHost();


        TabHost.TabSpec tabSpecMain = tabHost.newTabSpec("MAIN").setIndicator("메인");
        Intent intentMain = new Intent(this, ConnectmainActivity.class);
        tabSpecMain.setContent(intentMain);
        tabHost.addTab(tabSpecMain);


        TabHost.TabSpec tabSpecCategory = tabHost.newTabSpec("CATEGORY").setIndicator("카테\n고리");

        Intent intentCategory = new Intent(this, CategoryActivity.class);

        /* try{
            tabSpecCategory.setContent(intentCategory);
        }catch(InflateException e){} */
        tabSpecCategory.setContent(intentCategory);
        tabHost.addTab(tabSpecCategory);


        TabHost.TabSpec tabSpecWrite = tabHost.newTabSpec("tab2");
        tabSpecWrite.setIndicator("글쓰기");
        Intent intentwrite = new Intent(ctx, WriteActivity.class);
        intentwrite.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        tabSpecWrite.setContent(intentwrite);
        tabHost.addTab(tabSpecWrite);

        TabHost.TabSpec tabSpecInfo = tabHost.newTabSpec("INFORMATION").setIndicator("개인\n정보");
        Intent intentMinfo = new Intent(this,MembershipActivity.class);
        tabSpecInfo.setContent(intentMinfo);
        tabHost.addTab(tabSpecInfo);





        TabHost.TabSpec tabSpecChat = tabHost.newTabSpec("CHATTING").setIndicator("채팅");

        Intent intentChat = new Intent(this, ChatMain.class);
        Intent intentChatFail = new Intent(this,ChatFailActivity.class);

        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            tabSpecChat.setContent(intentChatFail);
        }else{
            tabSpecChat.setContent(intentChat);
        }

        tabHost.addTab(tabSpecChat);
        // tabHost.setCurrentTab(0);



        TabHost.TabSpec tabSpecMap = tabHost.newTabSpec("tab2");
        tabSpecMap.setIndicator("지도");
        Intent intentmap = new Intent(ctx, MapsActivity.class);
        intentmap.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        tabSpecMap.setContent(intentmap);
        tabHost.addTab(tabSpecMap);

        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {

            LinearLayout linearLayout = (LinearLayout) tabHost.getTabWidget().getChildAt(i);

            TextView tv = (TextView) linearLayout.getChildAt(1);
            tv.setTextSize(13);
        }


    }
}

