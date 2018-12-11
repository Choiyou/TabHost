package com.example.os150.tabhost;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MyAdapter extends BaseAdapter {
    //아이템을 세트로 담기 위한 Array
    private ArrayList<MyItem> mItems = new ArrayList<>();

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
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        //listview_item layout을 inflate하여 convertView 참조 획득
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item,parent,false);
        }
        //listview_item에 정의된 위젯에 대한 참조 획득
       //사진 ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView);
        final TextView tv_Title =(TextView)convertView.findViewById(R.id.tv_Title);
        final TextView tv_Price =(TextView)convertView.findViewById(R.id.tv_Price);
        final TextView tv_Contents = (TextView)convertView.findViewById(R.id.tv_Contents);
        RelativeLayout tv_item =(RelativeLayout)convertView.findViewById(R.id.tv_item);
        //각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용
        final MyItem myItem = getItem(position);

        //각 위젯에 세팅된 아이템을 뿌려준다
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
                context.startActivity(intent);
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
    public void addItem(String Title, String Price,String Contents,String Category){
        MyItem mItem = new MyItem();
        //MyItem에 아이템을 setting 한다
        mItem.setTitle(Title);
        mItem.setPrice(Price);
        mItem.setContents(Contents);
        mItem.setCategory(Category);

        //myItem에 MyItem을 추가한다
        mItems.add(mItem);

    }
}
