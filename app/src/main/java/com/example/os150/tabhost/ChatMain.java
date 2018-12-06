package com.example.os150.tabhost;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.os150.tabhost.fragment.PeopleFragment;

public class ChatMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//상단바 없애기

        getFragmentManager().beginTransaction().replace(R.id.chatmain_framelayout,new PeopleFragment()).commit();

    }
}
