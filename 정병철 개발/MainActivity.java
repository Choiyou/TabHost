package com.example.admin.personaldev;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.InflateException;
import android.widget.TabHost;

public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost = getTabHost();

        TabHost.TabSpec tabSpecmain = tabHost.newTabSpec("tab1").setIndicator("메인");
        tabSpecmain.setContent(R.id.tab1);
        tabHost.addTab(tabSpecmain);

        //화면의 일부분만 intent로 전환, 기능은 MapsActivity에 작성.
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


        TabHost.TabSpec tabSpecchat = tabHost.newTabSpec("tab3").setIndicator("채팅");
        tabSpecchat.setContent(R.id.tab3);
        tabHost.addTab(tabSpecchat);

        TabHost.TabSpec tabSpecblank = tabHost.newTabSpec("tab4").setIndicator("빈칸");
        tabSpecblank.setContent(R.id.tab4);
        tabHost.addTab(tabSpecblank);

        TabHost.TabSpec tabSpecInfo = tabHost.newTabSpec("tab5").setIndicator("내정보");
        tabSpecInfo.setContent(R.id.tab5);
        tabHost.addTab(tabSpecInfo);
    }
}