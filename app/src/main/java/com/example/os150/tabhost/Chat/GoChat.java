package com.example.os150.tabhost.Chat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.os150.tabhost.R;
import com.example.os150.tabhost.fragment.PeopleFragment;
import com.example.os150.tabhost.model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

public class GoChat extends AppCompatActivity {

    private String userUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_chat);

        userUid = getIntent().getStringExtra("destinationUid");

        Intent intent = new Intent(GoChat.this,MessageActivity.class);
        intent.putExtra("destinationUid",userUid);
        startActivity(intent);


        getFragmentManager().beginTransaction().replace(R.id.gochat_framlayout,new PeopleFragment()).commit();
    }
}
