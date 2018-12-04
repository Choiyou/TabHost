package com.example.os150.tabhost;
/*

설명 : 메인 화면을 구성하는 activity 이다

작성 :2014244094 성해성
*/
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ListView;

import com.example.os150.tabhost.model.ListAdapter;
import com.example.os150.tabhost.model.itemData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConnectmainActivity extends Activity {
    private ListView m_oListView = null;

    static boolean calledAlready =false;
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


        int ndataCnt=0;
        final ArrayList<itemData> oData = new ArrayList<>();

        FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference databaseReference =database.getReference("Market");
        databaseReference.child("뷰티,미용").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot fileSnapshot : dataSnapshot.getChildren()){
                    //하위키들 value 가져오기
                    itemData item = new itemData();
                    String strContents = fileSnapshot.child("contents").getValue(String.class);
                    String strTitle =fileSnapshot.child("title").getValue(String.class);
                    String strPrice=fileSnapshot.child("price").getValue(String.class);
                    //fileList.add(strContents);
                    //fileList.add(strPrice);
                    //fileList.add(strTitle);
                    item.title = strTitle;
                    item.price = strPrice;
                    item.content = strContents;
                    oData.add(item);
                }
                m_oListView = (ListView)findViewById(R.id.listView);
                ListAdapter oAdapter = new ListAdapter(oData);
                m_oListView.setAdapter(oAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("TAG:","Failed to read value",databaseError.toException());
            }
        });
        //Toast.makeText(getApplicationContext(),"카테고리 별 레이아웃 입니다.", Toast.LENGTH_SHORT).show();
    }
}
