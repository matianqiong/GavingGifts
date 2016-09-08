package com.example.mtq.gavinggifts.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mtq.gavinggifts.R;

import java.util.List;

/**
 * Created by mtq on 2016/8/18.
 */
public class SpinnerAdapter extends BaseAdapter {
    private String [] stringList;
    private Context context;
    public SpinnerAdapter(Context context,String[] list){
        this.context=context;
        this.stringList=list;
    }
    @Override
    public int getCount() {
        return stringList.length;
    }

    @Override
    public Object getItem(int i) {
        return stringList[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.spinner_grid_item,null);
        }
        TextView textView= (TextView) view.findViewById(R.id.grid_title);
        textView.setText(stringList[i]);
        return view;
    }
}
