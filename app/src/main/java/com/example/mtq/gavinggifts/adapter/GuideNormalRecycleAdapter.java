package com.example.mtq.gavinggifts.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mtq.gavinggifts.R;
import com.example.mtq.gavinggifts.entity.GuideNormalRecycler;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by mtq on 2016/9/2.
 */
public class GuideNormalRecycleAdapter extends RecyclerView.Adapter<GuideNormalRecycleAdapter.MyViewHolder> {
    public static final int TYPE_HEADER=0;
    public static final int TYPE_NORMAL=1;
    private View mHeaderView;
    private Context mContext;
    private List<GuideNormalRecycler.DataBean.ItemsBean> itemsBeanList;
    private GuideRecyclerViewAdapter.OnItemClickListener onItemClickListener;
    public GuideNormalRecycleAdapter(Context context, List<GuideNormalRecycler.DataBean.ItemsBean> itemsBeen,
                                     GuideRecyclerViewAdapter.OnItemClickListener onItemClickListener){
            this.mContext=context;
            this.onItemClickListener=onItemClickListener;
            this.itemsBeanList=itemsBeen;
    }
    @Override
    public int getItemViewType(int position) {
        if(mHeaderView == null) return TYPE_NORMAL;
        if(position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder;
        if(mHeaderView != null && viewType == TYPE_HEADER) {
            /*View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.guide_main_recyclerheader, parent, false);*/
            holder = new MyViewHolder(mHeaderView);
            return holder;
        }else {
            holder = new MyViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.guide_main_recycler_item, parent, false));
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
      if (getItemViewType(position)==TYPE_HEADER) return;
        Picasso.with(mContext).load(itemsBeanList.get(position).getCover_image_url()).into(holder.imageView);
        holder.checkBox.setText(itemsBeanList.get(position).getLikes_count()+"");
        holder.textView.setText(itemsBeanList.get(position).getTitle());
        //设置右上角图标的大小
        Drawable[] drawables=holder.checkBox.getCompoundDrawables();
        drawables[1].setBounds(0,0,65,65);
        holder.checkBox.setCompoundDrawables(null,drawables[1],null,null);

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    holder.checkBox.setText((itemsBeanList.get(position).getLikes_count()+1)+"");
                }else {
                    holder.checkBox.setText(itemsBeanList.get(position).getLikes_count()+"");
                }
            }
        });
        if (onItemClickListener!=null){
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(view,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mHeaderView==null?itemsBeanList.size():itemsBeanList.size()+1;
    }
     class MyViewHolder extends RecyclerView.ViewHolder{
         ImageView imageView;
         CheckBox checkBox;
         TextView textView;
         public MyViewHolder(View itemView) {
             super(itemView);
             if (itemView==mHeaderView) return;
             imageView= (ImageView) itemView.findViewById(R.id.guide_main_recyclerItemImage);
             checkBox= (CheckBox) itemView.findViewById(R.id.guide_main_recyclerItemLike);
             textView= (TextView) itemView.findViewById(R.id.guide_main_recyclerTitle);
         }
     }
    public interface OnItemClickListener{
        void OnItemClick(View view,int position);
    }
}
