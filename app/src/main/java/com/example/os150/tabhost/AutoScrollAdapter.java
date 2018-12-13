package com.example.os150.tabhost;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AutoScrollAdapter extends PagerAdapter {
    Context context;
    ArrayList<String> data;

    public AutoScrollAdapter(Context context, ArrayList<String> data) {
        this.context = context;
        this.data = data;

    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        //뷰페이지 슬라이딩 할 레이아웃 인플레이션
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.auto_viewpager,null);
        ImageView image_container = (ImageView) v.findViewById(R.id.image_container);
        Glide.with(context).load(data.get(position)).into(image_container);
        container.addView(v);
        //클릭시 설명으로 가져라...
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(position) {
                    case 1:
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                        intent.setPackage("com.android.chrome");
                        context.startActivity(intent);
                        break;
                    default:
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                        intent.setPackage("com.android.chrome");
                        context.startActivity(intent);

                }
            }
        });
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View)object);

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
