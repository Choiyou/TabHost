package com.example.os150.tabhost;

import android.app.Activity;
import android.content.Intent;
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


public class SecondmainActivity extends Activity {
    private ListView mListView;
    static boolean calledAlready = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

       /* if(!calledAlready)
        {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            //다른 인스턴스보다 먼저 실행되어야 한다.
            calledAlready =true;
        }
*/
        //위젯과 멤버 변수 참조 획득
        mListView = (ListView)findViewById(R.id.listView);
        //아이템추가 및 어댑터 등록
        dataSetting();
    }
    private void dataSetting() {
        Intent intent = getIntent();
        final MyAdapter mMyAdapter = new MyAdapter();

        //final ArrayList<MyItem> oData = new ArrayList<>();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Market");
        switch (intent.getStringExtra("key")){
            case "1":
                databaseReference.child("디지털,가전").addValueEventListener(new ValueEventListener() {

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

                            mMyAdapter.addItem(strTitle, strPrice, strContents,strCategory,strUserUid,strUserName);
                        }


                        mListView.setAdapter(mMyAdapter);

                        mMyAdapter.notifyDataSetChanged();

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "2":
                databaseReference.child("가구,인테리어").addValueEventListener(new ValueEventListener() {

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

                            mMyAdapter.addItem(strTitle, strPrice, strContents,strCategory,strUserUid,strUserName);
                        }


                        mListView.setAdapter(mMyAdapter);

                        mMyAdapter.notifyDataSetChanged();

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "3":
                databaseReference.child("유아동,유아도서").addValueEventListener(new ValueEventListener() {

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

                            mMyAdapter.addItem(strTitle, strPrice, strContents,strCategory,strUserUid,strUserName);
                        }




                        mListView.setAdapter(mMyAdapter);

                        mMyAdapter.notifyDataSetChanged();

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "4":
                databaseReference.child("생활,가공식품").addValueEventListener(new ValueEventListener() {

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

                            mMyAdapter.addItem(strTitle, strPrice, strContents,strCategory,strUserUid,strUserName);
                        }



                        mListView.setAdapter(mMyAdapter);

                        mMyAdapter.notifyDataSetChanged();

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "5":
                databaseReference.child("여성의류").addValueEventListener(new ValueEventListener() {

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

                            mMyAdapter.addItem(strTitle, strPrice, strContents,strCategory,strUserUid,strUserName);
                        }



                        mListView.setAdapter(mMyAdapter);

                        mMyAdapter.notifyDataSetChanged();

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "6":
                databaseReference.child("여성잡화").addValueEventListener(new ValueEventListener() {

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

                            mMyAdapter.addItem(strTitle, strPrice, strContents,strCategory,strUserUid,strUserName);
                        }



                        mListView.setAdapter(mMyAdapter);

                        mMyAdapter.notifyDataSetChanged();

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "7":
                databaseReference.child("뷰티,미용").addValueEventListener(new ValueEventListener() {

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

                            mMyAdapter.addItem(strTitle, strPrice, strContents,strCategory,strUserUid,strUserName);
                        }



                        mListView.setAdapter(mMyAdapter);

                        mMyAdapter.notifyDataSetChanged();

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "8":
                databaseReference.child("남성패션,잡화").addValueEventListener(new ValueEventListener() {

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

                            mMyAdapter.addItem(strTitle, strPrice, strContents,strCategory,strUserUid,strUserName);
                        }



                        mListView.setAdapter(mMyAdapter);

                        mMyAdapter.notifyDataSetChanged();

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "9":
                databaseReference.child("스포츠,레저").addValueEventListener(new ValueEventListener() {

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

                            mMyAdapter.addItem(strTitle, strPrice, strContents,strCategory,strUserUid,strUserName);
                        }



                        mListView.setAdapter(mMyAdapter);

                        mMyAdapter.notifyDataSetChanged();

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "10":
                databaseReference.child("게임,취미").addValueEventListener(new ValueEventListener() {

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

                            mMyAdapter.addItem(strTitle, strPrice, strContents,strCategory,strUserUid,strUserName);
                        }



                        mListView.setAdapter(mMyAdapter);

                        mMyAdapter.notifyDataSetChanged();

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "11":
                databaseReference.child("도서,티켓,음반").addValueEventListener(new ValueEventListener() {

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

                            mMyAdapter.addItem(strTitle, strPrice, strContents,strCategory,strUserUid,strUserName);
                        }



                        mListView.setAdapter(mMyAdapter);

                        mMyAdapter.notifyDataSetChanged();

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "12":
                databaseReference.child("반려동물용품").addValueEventListener(new ValueEventListener() {

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

                            mMyAdapter.addItem(strTitle, strPrice, strContents,strCategory,strUserUid,strUserName);
                        }




                        mListView.setAdapter(mMyAdapter);

                        mMyAdapter.notifyDataSetChanged();

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "13":
                databaseReference.child("기타 중고물품").addValueEventListener(new ValueEventListener() {

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

                            mMyAdapter.addItem(strTitle, strPrice, strContents,strCategory,strUserUid,strUserName);
                        }



                        mListView.setAdapter(mMyAdapter);

                        mMyAdapter.notifyDataSetChanged();

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "14":
                databaseReference.child("삽니다").addValueEventListener(new ValueEventListener() {

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

                            mMyAdapter.addItem(strTitle, strPrice, strContents,strCategory,strUserUid,strUserName);
                        }



                        mListView.setAdapter(mMyAdapter);

                        mMyAdapter.notifyDataSetChanged();

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });break;
            case "15":
                databaseReference.child("무료나눔,대여").addValueEventListener(new ValueEventListener() {

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

                            mMyAdapter.addItem(strTitle, strPrice, strContents,strCategory,strUserUid,strUserName);
                        }



                        mListView.setAdapter(mMyAdapter);

                        mMyAdapter.notifyDataSetChanged();

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });break;
        }



    }
}
