package com.example.os150.tabhost;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MyAdapter extends BaseAdapter {
    private int img;
    //아이템을 세트로 담기 위한 Array
    private ArrayList<MyItem> mItems = new ArrayList<>();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mDatabase;

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public MyItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        //listview_item layout을 inflate하여 convertView 참조 획득
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item,parent,false);
        }
        //listview_item에 정의된 위젯에 대한 참조 획득
        ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView);
        final TextView tv_Title =(TextView)convertView.findViewById(R.id.tv_Title);
        final TextView tv_Price =(TextView)convertView.findViewById(R.id.tv_Price);
        final TextView tv_Contents = (TextView)convertView.findViewById(R.id.tv_Contents);
        RelativeLayout tv_item =(RelativeLayout)convertView.findViewById(R.id.tv_item);
        //각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용
        final MyItem myItem = getItem(position);

        //각 위젯에 세팅된 아이템을 뿌려준다
        imageView.setImageResource(myItem.getIcon());
        tv_Title.setText(myItem.getTitle());
        tv_Price.setText(myItem.getPrice());
        tv_Contents.setText(myItem.getContents());
        //위젯에 대한 이벤트리스너를 지정하고 싶다면 여기에 작성하면된다
        tv_item.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),ViewActivity.class);

                intent.putExtra("title",myItem.getTitle());
                intent.putExtra("contents",myItem.getContents());
                intent.putExtra("price",myItem.getPrice());
                intent.putExtra("category",myItem.getCategory());
                intent.putExtra("userUid",myItem.getUserUid());
                intent.putExtra("userName",myItem.getUserName());
                intent.putExtra("img",Integer.toString(myItem.getIcon()));
                context.startActivity(intent);
            }
        });

        tv_item.setOnLongClickListener(new View.OnLongClickListener() {


            @Override
            public boolean onLongClick(View view) {
                CharSequence info[] = new CharSequence[] {"삭제", "판매글","관심상품 등록" };


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("게시글 관리");
                builder.setItems(info, new DialogInterface.OnClickListener() {

                    @Override

                    public void onClick(DialogInterface dialog, int which) {

                        switch(which)

                        {
                            case 0:
                                if(user!=null) {

                                    mItems.remove(position);



                                    mDatabase = FirebaseDatabase.getInstance().getReference("Market");

                                    mDatabase.child("Main").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            String fkey = dataSnapshot.getKey();
                                       mDatabase.child("Main").child(fkey).orderByChild("contents").equalTo( mItems.get(position).getContents()).getRef().removeValue();
                                            Toast.makeText(getApplicationContext(), "삭제", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                    notifyDataSetInvalidated();

                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "권한이 없습니다.", Toast.LENGTH_SHORT).show();

                                }
                                break;

                            case 1:

                                mItems.remove(which);
                                notifyDataSetInvalidated();

                                Toast.makeText(getApplicationContext(), "판매글", Toast.LENGTH_SHORT).show();
                                break;


                            case 2:

                                mItems.remove(which);
                                notifyDataSetInvalidated();
                                Intent intent =new Intent(getApplicationContext(),ViewActivity.class);
                                intent.putExtra("title",myItem.getTitle());
                                intent.putExtra("contents",myItem.getContents());
                                intent.putExtra("price",myItem.getPrice());
                                intent.putExtra("category",myItem.getCategory());
                                intent.putExtra("userUid",myItem.getUserUid());
                                intent.putExtra("userName",myItem.getUserName());
                                intent.putExtra("img", Integer.toString(myItem.getIcon()));
                                context.startActivity(intent);
                                break;
                        }

                        dialog.dismiss();

                    }

                });

                builder.show();



                return true;
            }
        });
        /*tv_Contents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context,"확인"+myItem.getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(getApplicationContext(),ViewActivity.class);

                intent.putExtra("title",myItem.getTitle());
                intent.putExtra("contents",myItem.getContents());
                intent.putExtra("price",myItem.getPrice());
                context.startActivity(intent);
            }

        });*/
        return convertView;
    }
    public void addItem(int img,String Title, String Price, String Contents, String Category, String UserUid,String UserName){
        MyItem mItem = new MyItem();
        //MyItem에 아이템을 setting 한다
        mItem.setIcon(img);
        mItem.setTitle(Title);
        mItem.setPrice(Price);
        mItem.setContents(Contents);
        mItem.setCategory(Category);
        mItem.setUserUid(UserUid);
        mItem.setUserName(UserName);

        //myItem에 MyItem을 추가한다
        mItems.add(mItem);

    }


}
