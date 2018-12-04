package com.example.os150.tabhost.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.os150.tabhost.R;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter
{
    LayoutInflater inflater = null;
    private ArrayList<itemData> m_oData = null;
    private int nListCnt = 0;

    public ListAdapter(ArrayList<itemData> _oData)
    {
        m_oData = _oData;
        nListCnt = m_oData.size();
    }

    @Override
    public int getCount()
    {
        return nListCnt;
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public android.view.View getView(int position, android.view.View convertView, ViewGroup parent) {
        if (convertView == null)
        {
            final Context context = parent.getContext();
            if (inflater == null)
            {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        TextView oTextTitle = (TextView) convertView.findViewById(R.id.gettitle);
        TextView oPrice = (TextView)convertView.findViewById(R.id.getprice);
        TextView oTextDate = (TextView) convertView.findViewById(R.id.getcontent);

        oTextTitle.setText(m_oData.get(position).title);
        oTextDate.setText(m_oData.get(position).content);
        oPrice.setText(m_oData.get(position).price);
        return convertView;
    }


}