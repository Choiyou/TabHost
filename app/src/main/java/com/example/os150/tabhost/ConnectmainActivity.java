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

public class ConnectmainActivity extends AppCompatActivity {
    private ListView mListView;
    static boolean calledAlready = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
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

        //final ArrayList<MyItem> oData = new ArrayList<>();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Market");
        databaseReference.child("Main").addValueEventListener(new ValueEventListener() {

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
                    String strUserUid = fileSnapshot.child("userUid").getValue(String.class);
                    String strUserName = fileSnapshot.child("userName").getValue(String.class);

                    mMyAdapter.addItem(strTitle, strPrice, strContents,strCategory,strUserUid,strUserName);

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

