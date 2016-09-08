package com.example.mtq.gavinggifts.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.mtq.gavinggifts.R;
import com.example.mtq.gavinggifts.entity.HotView;
import com.example.mtq.gavinggifts.entity.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by mtq on 2016/9/2.
 */
public class HotAdapter extends BaseAdapter {
    private HotView.DataBean.ItemsBean.ListsBean listsBean;
    private List<HotView.DataBean.ItemsBean.ListsBean> lists;
    private Context context;
    private ViewHolder viewHolder=null;

    public HotAdapter(List<HotView.DataBean.ItemsBean.ListsBean> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        listsBean=lists.get(i);
        if (view==null){
            viewHolder=new ViewHolder();
            view= LayoutInflater.from(context).inflate(R.layout.fragment_layout_item,null);

            view.setLayoutParams(
                    new GridView.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            730));


            viewHolder.image= (RoundedImageView) view.findViewById(R.id.fragment_hot_item_image);
            viewHolder.title= (TextView) view.findViewById(R.id.fragment_hot_item_title);
            viewHolder.price= (TextView) view.findViewById(R.id.fragment_hot_item_price);
            viewHolder.like= (TextView) view.findViewById(R.id.fragment_hot_item_like);
            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }

        double j = lists.get(i).getFavorites_count() / 1000;
        viewHolder.title.setText(lists.get(i).getName());
        viewHolder.like.setText(j+"K");
        viewHolder.price.setText(lists.get(i).getPrice());
        Picasso.with(context).load(lists.get(i).getCover_image_url()).into(viewHolder.image);
        return view;

    }

    class ViewHolder{
        public RoundedImageView image;
        public TextView title,price,like;
    }
}
