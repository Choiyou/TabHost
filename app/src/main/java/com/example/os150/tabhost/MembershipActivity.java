package com.example.os150.tabhost;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by os150 on 2018-11-26.
 */

public class MembershipActivity extends Activity{
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mDatabase ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membershipinfo);
        final TextView txtLoginInfo = (TextView) findViewById(R.id.loginInfo);
        Button btnSales = (Button) findViewById(R.id.btnSales);
        Button btnPurchase = (Button) findViewById(R.id.btnPurchase);
        Button btnLikeProduct = (Button) findViewById(R.id.btnlikeProduct);
        Button btnMyPosts = (Button) findViewById(R.id.btnMyPosts);
        Button btnTownSetting = (Button) findViewById(R.id.btntownSetting);
        Button btnSignin = (Button) findViewById(R.id.Signin);
        Button btnLogin = (Button) findViewById(R.id.login);

        if(user!=null) {

                txtLoginInfo.setText("회원 : "+user.getDisplayName());

        }
        else{
            txtLoginInfo.setText("비회원입니다.");
        }
        txtLoginInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if(user!=null) {
                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(MembershipActivity.this,"로그인을 먼저 해주세요",Toast.LENGTH_SHORT).show();
                }

            }
        });


        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SigninActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
        btnSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SaleActivity.class);
                startActivity(intent);
            }
        });

        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PurchaseActivity.class);
                startActivity(intent);
            }
        });
        btnLikeProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), LikeProductActivity.class);
//                startActivity(intent);


                mDatabase = FirebaseDatabase.getInstance().getReference("usercheck").child(user.getDisplayName()).child("select");

                mUserItems.clear();
                mDatabase.removeValue();
                listItems = getResources().getStringArray(R.array.Product_item);
                checkedItems = new boolean[listItems.length];
//                mDatabase = FirebaseDatabase.getInstance().getReference("usercheck").child(user.getDisplayName());

                            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MembershipActivity.this);
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
        btnMyPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPostsActivity.class);
                startActivity(intent);
            }
        });
        btnTownSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TownSettingActivity.class);
                startActivity(intent);
            }
        });

    }





}

