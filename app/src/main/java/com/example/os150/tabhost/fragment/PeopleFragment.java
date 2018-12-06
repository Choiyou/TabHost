package com.example.os150.tabhost.fragment;

import android.app.ActivityOptions;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.os150.tabhost.Chat.MessageActivity;
import com.example.os150.tabhost.R;
import com.example.os150.tabhost.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import static com.example.os150.tabhost.R.anim.toleft;

public class PeopleFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_people,container,false);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.peoplefragment_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        recyclerView.setAdapter(new PeopleFragmentRecyclerViewAdapter());

        return view;
    }

    class PeopleFragmentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        List<UserModel> userModels;

        //DB 접속을 위한 constructor
        public PeopleFragmentRecyclerViewAdapter() {
            userModels = new ArrayList<>();

            final String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            FirebaseDatabase.getInstance().getReference().child("users").addValueEventListener(new ValueEventListener() {
                @Override
                //서버에서 넘어온 데이터
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    userModels.clear();
                    for(DataSnapshot snapshot :dataSnapshot.getChildren()){
                        userModels.add(snapshot.getValue(UserModel.class));

//                        if(userModels.equals(myUid)){
//                            continue;
//                        }userModels.add((UserModel) userModels);
                    }
                    notifyDataSetChanged();//새로고침
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend,parent,false);

            return new CustomViewHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {


            Glide.with
                    (holder.itemView.getContext())
                    .load(userModels.get(position).profileImageUrl)
                    .apply(new RequestOptions().circleCrop())
                    .into(((CustomViewHolder)holder).imageView);
            ((CustomViewHolder)holder).textView.setText(userModels.get(position).userName);

            Log.v("알림","uid2"+userModels.get(position).uid);
            Log.v("알림","name2"+userModels.get(position).userName);
            Log.v("알림","image2"+userModels.get(position).profileImageUrl);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(),MessageActivity.class);
                    intent.putExtra("dstinationUid",userModels.get(position).uid);
                    ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(view.getContext(),R.anim.toright,R.anim.toleft);
                    startActivity(intent,activityOptions.toBundle());
                }
            });
        }

        @Override
        public int getItemCount() {
            return userModels.size();
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {
            public ImageView imageView;
            public TextView textView;

            public CustomViewHolder(View view) {
                super(view);
                imageView = view.findViewById(R.id.frienditem_imageview);
                textView = view.findViewById(R.id.frienditem_textview);
            }
        }
    }
}