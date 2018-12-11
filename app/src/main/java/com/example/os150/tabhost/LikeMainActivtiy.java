package com.example.os150.tabhost;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mDatabase ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likemain);
        getIntent();
        final ArrayList<itemData> oData = new ArrayList<>();
        Button selectButton = (Button) findViewById(R.id.selectButton);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = database.getReference("Market");
        databaseReference.child("디지털,가전").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        oData.clear();                                                  //올릴 데이터 초기화.
                        for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                            //하위키들 value 가져오기
                            itemData item = new itemData();
                            String strContents = fileSnapshot.child("contents").getValue(String.class);
                            String strTitle = fileSnapshot.child("title").getValue(String.class);
                            String strPrice = fileSnapshot.child("price").getValue(String.class);
                            item.title = strTitle;
                            item.price = strPrice;
                            item.content = strContents;
                            oData.add(item);
                        }
                        m_oListView = (ListView) findViewById(R.id.listview);
                        ListAdapter oAdapter = new ListAdapter(oData);
                        m_oListView.setAdapter(oAdapter);
                        oAdapter.notifyDataSetChanged();        //원본 다시 읽어 재생성.
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });



        Toast.makeText(getApplicationContext(), "Main", Toast.LENGTH_LONG).show();
    selectButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            mDatabase = FirebaseDatabase.getInstance().getReference("usercheck").child(user.getDisplayName()).child("select");

            mUserItems.clear();
            mDatabase.removeValue();
            listItems = getResources().getStringArray(R.array.Product_item);
            checkedItems = new boolean[listItems.length];
//                mDatabase = FirebaseDatabase.getInstance().getReference("usercheck").child(user.getDisplayName());

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(LikeMainActivtiy.this);
            mBuilder.setTitle(R.string.dialog_title);
            mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {

                    if(isChecked){
                        mUserItems.add(position);
                    }else{
                        mUserItems.remove((Integer.valueOf(position)));
                    }
                }
            });



            mBuilder.setCancelable(false);
            mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    String item = "";
                    for (int i = 0; i < mUserItems.size(); i++) {
                        item = item + listItems[mUserItems.get(i)];
                        if (i != mUserItems.size() - 1) {
                            item = item + "\n ";

                        }

                    }

                    String date[] = item.split("\n");

                    for(int i=0 ; i<date.length ; i++)
                    {

                        System.out.println("date["+i+"] : "+date[i]);
                        mDatabase.child("item"+i).setValue(date[i]);
                    }

                    Intent intent = new Intent(getApplicationContext(),LikeMainActivtiy.class);
                    startActivity(intent);

                }

            });

            mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            AlertDialog mDialog = mBuilder.create();

            mDialog.show();


        }
    });
    }
    }

