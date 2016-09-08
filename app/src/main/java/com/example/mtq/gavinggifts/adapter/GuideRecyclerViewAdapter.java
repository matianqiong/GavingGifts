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
import com.example.mtq.gavinggifts.entity.GuideRecyclerImage;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by mtq on 2016/8/20.
 */
public class GuideRecyclerViewAdapter extends RecyclerView.Adapter<GuideRecyclerViewAdapter.MyViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPEA_NORMAL = 1;
    private Context mContext;
    private OnItemClickListener mListener;
    private List<GuideRecyclerImage.DataBean.ItemsBean> mRecyclerItemImages;
    private View mHeaderView;

    public GuideRecyclerViewAdapter(Context mContext, OnItemClickListener mListener,
                                    List<GuideRecyclerImage.DataBean.ItemsBean> itemsBeanList) {
        this.mContext = mContext;
        this.mListener = mListener;
        this.mRecyclerItemImages=itemsBeanList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder;
          if (mHeaderView!=null&&viewType==TYPE_HEADER){
              holder=new MyViewHolder(mHeaderView);
          }else {

              holder=new MyViewHolder(LayoutInflater.from(mContext).
                      inflate(R.layout.guide_main_recycler_item,parent,false));
              return holder;
          }
        return holder;
    }

    public View getmHeaderView() {
        return mHeaderView;
    }

    public void setmHeaderView(View mHeaderView) {
        this.mHeaderView = mHeaderView;
        notifyItemInserted(0);
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView==null){
            return TYPEA_NORMAL;
        }
        if (position==0){
            return TYPE_HEADER;
        }
        return TYPEA_NORMAL;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (getItemViewType(position)==TYPE_HEADER) return;
        Picasso.with(mContext).load(mRecyclerItemImages.get(position-1).getCover_image_url()).into(holder.itemImage);
        holder.itemBtn.setText(mRecyclerItemImages.get(position-1).getLikes_count()+"");
        holder.itemTxt.setText(mRecyclerItemImages.get(position-1).getTitle());
        //设置图标的大小
        Drawable[] drawables=holder.itemBtn.getCompoundDrawables();
        drawables[1].setBounds(0,0,65,65);
        holder.itemBtn.setCompoundDrawables(null,drawables[1],null,null);
        //设置点击事件
        holder.itemBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if (b){
                   holder.itemBtn.setText((mRecyclerItemImages.get(position-1).getLikes_count()+1)+"");
               }else{
                   holder.itemBtn.setText(mRecyclerItemImages.get(position-1).getLikes_count()+"");
               }
            }
        });
        if (mListener!=null){
            holder.itemImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(view,position-1);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mHeaderView==null?mRecyclerItemImages.size():mRecyclerItemImages.size()+1;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    class MyViewHolder  extends RecyclerView.ViewHolder{
        ImageView itemImage;
        CheckBox itemBtn;
        TextView itemTxt;
        public MyViewHolder(View itemView) {
            super(itemView);
            if (itemView==mHeaderView) return;
            itemImage= (ImageView) itemView.findViewById(R.id.guide_main_recyclerItemImage);
            itemBtn = (CheckBox) itemView.findViewById(R.id.guide_main_recyclerItemLike);
            itemTxt= (TextView) itemView.findViewById(R.id.guide_main_recyclerTitle);
        }
    }
}
