package com.example.os150.tabhost.Chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.os150.tabhost.R;
import com.example.os150.tabhost.fragment.PeopleFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class ChatMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//상단바 없애기

        getFragmentManager().beginTransaction().replace(R.id.chatmain_framelayout,new PeopleFragment()).commit();

//        passPushTokenToServer();
    }
    void passPushTokenToServer(){

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String token = FirebaseInstanceId.getInstance().getToken();
        Map<String,Object> map = new HashMap<>();
        map.put("pushToken",token);

        FirebaseDatabase.getInstance().getReference().child("chatusers").child(uid).updateChildren(map);
    }
}
