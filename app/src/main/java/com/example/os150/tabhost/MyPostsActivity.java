package com.example.os150.tabhost;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by os150 on 2018-10-30.
 */

public class MyPostsActivity extends Activity {

    private ListView mListView;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myposts);


        //위젯과 멤버 변수 참조 획득
        mListView = (ListView)findViewById(R.id.listView);
        //아이템추가 및 어댑터 등록
        dataSetting();
    }
    private void dataSetting() {

        final MyAdapter mMyAdapter = new MyAdapter();

        //final ArrayList<MyItem> oData = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = database.getReference("Market");
        databaseReference.child("Main").orderByChild("userName").equalTo(user.getDisplayName()).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //올릴 데이터 초기화.
                // oData.clear();

                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                    //하위키들 value 가져오기

                    String strContents = fileSnapshot.child("contents").getValue(String.class);
                    String strTitle = fileSnapshot.child("title").getValue(String.class);
                    String strPrice = fileSnapshot.child("price").getValue(String.class)+"원";
                    String strCategory = fileSnapshot.child("category").getValue(String.class);
                    String strUserEmail = fileSnapshot.child("userEmail").getValue(String.class);
                    String strUserName = fileSnapshot.child("userName").getValue(String.class);

                    String strUserUid = fileSnapshot.child("userUid").getValue(String.class);


                    mMyAdapter.addItem(R.mipmap.ic_launcher_foreground,strTitle, strPrice, strContents, strCategory, strUserEmail, strUserName);


                }


                mListView.setAdapter(mMyAdapter);

                mMyAdapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        }
}
