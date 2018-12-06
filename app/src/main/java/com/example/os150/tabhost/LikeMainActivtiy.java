package com.example.os150.tabhost;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.example.os150.tabhost.model.ListAdapter;
import com.example.os150.tabhost.model.itemData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by os150 on 2018-12-05.
 */

public class LikeMainActivtiy extends AppCompatActivity {
    private ListView m_oListView = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likemain);
        getIntent();
        final ArrayList<itemData> oData = new ArrayList<>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference databaseReference =database.getReference("Market");
        database.getReference().child(user.getDisplayName()).child("select").orderByChild("item0");
        Toast.makeText(getApplicationContext(),database.toString(),Toast.LENGTH_LONG).show();
        databaseReference.child("뷰티,미용").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                oData.clear();                                                  //올릴 데이터 초기화.
                for(DataSnapshot fileSnapshot : dataSnapshot.getChildren()){
                    //하위키들 value 가져오기
                    itemData item = new itemData();
                    String strContents = fileSnapshot.child("contents").getValue(String.class);
                    String strTitle =fileSnapshot.child("title").getValue(String.class);
                    String strPrice=fileSnapshot.child("price").getValue(String.class);
                    item.title = strTitle;
                    item.price = strPrice;
                    item.content = strContents;
                    oData.add(item);
                }
                m_oListView = (ListView)findViewById(R.id.listView);
                ListAdapter oAdapter = new ListAdapter(oData);
                m_oListView.setAdapter(oAdapter);
                oAdapter.notifyDataSetChanged();        //원본 다시 읽어 재생성.
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        Toast.makeText(getApplicationContext(),"Main",Toast.LENGTH_LONG).show();
    }
}