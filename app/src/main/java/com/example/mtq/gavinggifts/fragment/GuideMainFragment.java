package com.example.mtq.gavinggifts.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mtq.gavinggifts.GuideDetailsActivity;
import com.example.mtq.gavinggifts.R;
import com.example.mtq.gavinggifts.adapter.GuideBannerAdapter;
import com.example.mtq.gavinggifts.adapter.GuideRecyclerViewAdapter;
import com.example.mtq.gavinggifts.entity.GuideBanner;
import com.example.mtq.gavinggifts.entity.GuideRecyclerImage;
import com.example.mtq.gavinggifts.entity.GuideScrollImage;
import com.example.mtq.gavinggifts.view.MyApplication;
import com.example.mtq.gavinggifts.view.MyItemDecoration;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mtq on 2016/8/18.
 */
public class GuideMainFragment extends Fragment {
    private Handler mHandler;
    private Runnable mRunnable;
    private Context mContext;
    private View view;
    private View headerView;
    private RecyclerView recyclerView;
    private ViewPager vpBanner;
    private RadioGroup pointGroup;
    private ImageView image1,image2,image3,image4,image5,image6,image7;
    private List<ImageView> imageList;
    private List<GuideRecyclerImage.DataBean.ItemsBean> mRecyclerItemImages;
    private GuideRecyclerImage.DataBean.PagingBean mRecyclerPagingBean;
    private List<GuideBanner.DataBean.BannersBean> bannersBeanList;
    private List<GuideScrollImage.DataBean.SecondaryBannersBean> secondaryBannersBeanList;
    private GuideBannerAdapter guideBannerAdapter;
    private GuideRecyclerViewAdapter recyclerViewAdapter;
   /* private String bannerPath = "http://api.liwushuo.com/v2/banners";
    private String guideImgPath = "http://api.liwushuo.com/v2/secondary_banners?gender=1&generation=2";
    private String guideRecyclerPath = "http://api.liwushuo.com/v2/channels/101/items?ad=2&gender=2&generation=2&limit=20&offset=0";*/
    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        mContext=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (view==null){
            view=inflater.inflate(R.layout.guide_main_fragment,null,false);
            headerView=inflater.inflate(R.layout.guide_main_header,null,false);
            initView();
            initData();
            initShuffling();
            vpBanner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    RadioButton radioButto= (RadioButton) pointGroup.getChildAt(position);
                    radioButto.setChecked(true);
                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

        return view;
    }

    //实现轮播图效果
    private void initShuffling() {
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                vpBanner.setCurrentItem(msg.what);
            }
        };
        mRunnable=new Runnable() {
            @Override
            public void run() {
                int currentItem=vpBanner.getCurrentItem();
                currentItem++;
                if (currentItem>3){
                    currentItem=0;
                }
                mHandler.sendEmptyMessage(currentItem);
                mHandler.postDelayed(mRunnable,5000);
            }
        };
        new Thread(mRunnable).start();
    }

    private void initData() {
        String bannerPath = "http://api.liwushuo.com/v2/banners";
        String guideImgPath = "http://api.liwushuo.com/v2/secondary_banners?gender=1&generation=2";
        String guideRecyclerPath = "http://api.liwushuo.com/v2/channels/101/items?ad=2&gender=2&generation=2&limit=20&offset=0";
        //下载viewPager数据
        StringRequest stringRequest=new StringRequest(bannerPath, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                GuideBanner guideBanner=gson.fromJson(response,GuideBanner.class);
                GuideBanner.DataBean dataBean=guideBanner.getData();
                bannersBeanList=dataBean.getBanners();
                initAdapter();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("TAG",error.getMessage());
            }
        });
        //下载横向滚动条数据
        StringRequest stringRequest2=new StringRequest(guideImgPath, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                GuideScrollImage guideScrollImage=gson.fromJson(response,GuideScrollImage.class);
                GuideScrollImage.DataBean dataBean=guideScrollImage.getData();
                secondaryBannersBeanList=dataBean.getSecondary_banners();
                initImage();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        //下载recycleView
        StringRequest stringRequest3=new StringRequest(guideRecyclerPath, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                GuideRecyclerImage guideRecyclerImage=gson.fromJson(response,GuideRecyclerImage.class);
                GuideRecyclerImage.DataBean dataBean=guideRecyclerImage.getData();
                mRecyclerItemImages=dataBean.getItems();
                mRecyclerPagingBean=dataBean.getPaging();
                initRecyclerView();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        RequestQueue mQueue=MyApplication.getmQueue();
        mQueue.add(stringRequest);
        mQueue.add(stringRequest2);
        mQueue.add(stringRequest3);
    }

    private void initRecyclerView() {

        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter=new GuideRecyclerViewAdapter(mContext, new GuideRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(mContext, GuideDetailsActivity.class);
                intent.putExtra("DetailsUrl",mRecyclerItemImages.get(position).getContent_url());
                startActivity(intent);
            }
        },mRecyclerItemImages);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setmHeaderView(headerView);
        recyclerView.addItemDecoration(new MyItemDecoration(20));
    }

    private void initImage() {
        for (int i=0;i<7;i++){
            Picasso.with(mContext).load(secondaryBannersBeanList.get(i).
                    getImage_url()).into(imageList.get(i));
        }
    }

    private void initView() {
       recyclerView= (RecyclerView) view.findViewById(R.id.guide_main_recycler);
        vpBanner= (ViewPager) headerView.findViewById(R.id.vp_banner);
        pointGroup = (RadioGroup) headerView.findViewById(R.id.point_group);
        image1= (ImageView) headerView.findViewById(R.id.guide_img1);
        image2= (ImageView) headerView.findViewById(R.id.guide_img2);
        image3= (ImageView) headerView.findViewById(R.id.guide_img3);
        image4= (ImageView) headerView.findViewById(R.id.guide_img4);
        image5= (ImageView) headerView.findViewById(R.id.guide_img5);
        image6= (ImageView) headerView.findViewById(R.id.guide_img6);
        image7= (ImageView) headerView.findViewById(R.id.guide_img7);

        imageList=new ArrayList<>();
        imageList.add(image1);
        imageList.add(image2);
        imageList.add(image3);
        imageList.add(image4);
        imageList.add(image5);
        imageList.add(image6);
        imageList.add(image7);
    }
    public void initAdapter(){
          guideBannerAdapter=new GuideBannerAdapter(bannersBeanList,mContext);
          vpBanner.setAdapter(guideBannerAdapter);
    }
}
