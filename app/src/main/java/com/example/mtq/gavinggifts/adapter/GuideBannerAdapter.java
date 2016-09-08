package com.example.mtq.gavinggifts.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mtq.gavinggifts.entity.GuideBanner;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by mtq on 2016/8/21.
 */
public class GuideBannerAdapter extends PagerAdapter {
    private List<GuideBanner.DataBean.BannersBean> bannersBeanList;
    private Context mContext;

    public GuideBannerAdapter(List<GuideBanner.DataBean.BannersBean> bannersBeanList, Context mContext) {
        this.bannersBeanList = bannersBeanList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return bannersBeanList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView=new ImageView(mContext);
        Picasso.with(mContext).load(bannersBeanList.get(position).getImage_url()).into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
