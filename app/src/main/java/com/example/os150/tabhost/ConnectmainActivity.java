package com.example.os150.tabhost;
/*

설명 : 메인 화면을 구성하는 activity 이다

작성 :2014244094 성해성
*/
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.view.View;

import java.util.ArrayList;
import java.util.Iterator;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;


public class ConnectmainActivity extends AppCompatActivity {
    private ListView mListView;
    static boolean calledAlready = false;
    AutoScrollViewPager autoViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        ArrayList<String> data = new ArrayList<>(); //이미지 url를 저장하는 arraylist
        data.add("http://wish.welfare.seoul.kr/editorImage/_20140925114734624.jpg");
        data.add("data:image/jpeg;base64,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");
        autoViewPager = (AutoScrollViewPager)findViewById(R.id.autoViewPager);

        AutoScrollAdapter scrollAdapter = new AutoScrollAdapter(this, data);
        autoViewPager.setAdapter(scrollAdapter); //Auto Viewpager에 Adapter 장착
        autoViewPager.setInterval(5000); // 페이지 넘어갈 시간 간격 설정
        autoViewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_CYCLE);
        autoViewPager.startAutoScroll(); //Auto Scroll 시작


        if(!calledAlready)
        {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            //다른 인스턴스보다 먼저 실행되어야 한다.
            calledAlready =true;
        }

        //위젯과 멤버 변수 참조 획득
        mListView = (ListView)findViewById(R.id.listView);
        //아이템추가 및 어댑터 등록
        dataSetting();


    }
    private void dataSetting() {

        final MyAdapter mMyAdapter = new MyAdapter();



        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Market");
        databaseReference.child("Main").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //올릴 데이터 초기화.



                    for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                        //하위키들 value 가져오기

                        String strContents = fileSnapshot.child("contents").getValue(String.class);
                        String strTitle = fileSnapshot.child("title").getValue(String.class);
                        String strPrice = fileSnapshot.child("price").getValue(String.class) + "원";
                        String strCategory = fileSnapshot.child("category").getValue(String.class);
                        String strUserEmail = fileSnapshot.child("userEmail").getValue(String.class);
                        String strUserName = fileSnapshot.child("userName").getValue(String.class);

                        mMyAdapter.addItem(strTitle, strPrice, strContents, strCategory, strUserEmail, strUserName);

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

