package com.example.mtq.gavinggifts.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mtq.gavinggifts.R;
import com.example.mtq.gavinggifts.WebviewActivity;
import com.example.mtq.gavinggifts.adapter.HotAdapter;
import com.example.mtq.gavinggifts.entity.HotView;
import com.example.mtq.gavinggifts.view.MyApplication;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mtq on 2016/8/18.
 */
public class HotFragment extends Fragment implements AbsListView.OnScrollListener,AdapterView.OnItemClickListener {
    private View view;
    private GridView mGridView;
    private static final String PATH="http://api.liwushuo.com/v2/items?gender=1&limit=20&offset=%d&generation=2";
    private List<HotView.DataBean.ItemsBean.ListsBean> minterItemsList = new ArrayList<>();
    private List<HotView.DataBean.ItemsBean> itemlist;
    private int offset=0;
    private HotAdapter hotAdadpter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view==null) {
            view = inflater.inflate(R.layout.fragment_hot, null, false);
            findView();
            initGridView();
            initListener();
        }else {
            ViewGroup parent= (ViewGroup) view.getParent();
            if (parent!=null){
                parent.removeView(view);
            }
        }
        return view;
    }

    private void initListener() {
        mGridView.setOnScrollListener(this);
        mGridView.setOnItemClickListener(this);

    }

    private void initGridView() {
        String path0=String.format(PATH,offset);
        StringRequest stringRequest=new StringRequest(path0, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                HotView hotView=gson.fromJson(response,HotView.class);
                HotView.DataBean data=hotView.getData();
                itemlist=data.getItems();
                for (int i=0;i<itemlist.size();i++){
                    HotView.DataBean.ItemsBean.ListsBean listsBean=itemlist.get(i).getLists();
                    minterItemsList.add(listsBean);
                }
                hotAdadpter=new HotAdapter(minterItemsList,getActivity());
                mGridView.setAdapter(hotAdadpter);
                hotAdadpter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        MyApplication.getmQueue().add(stringRequest);
    }

    private void findView() {
       mGridView= (GridView) view.findViewById(R.id.fragment_hot_layout_gridview);
        mGridView.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        HotView.DataBean.ItemsBean.ListsBean listsBean = minterItemsList.get(i);
        String cover_webp_url = listsBean.getUrl();
        List<String> image_urls = listsBean.getImage_urls();
        Bundle bundle=new Bundle();
        Intent intent=new Intent();
        intent.setClass(getContext(), WebviewActivity.class);
        bundle.putString("url",cover_webp_url);
        bundle.putStringArrayList("image_urls", (ArrayList<String>) image_urls);
        bundle.putString("titlesss",listsBean.getName());
        bundle.putString("price",listsBean.getPrice());
        bundle.putString("desco",listsBean.getDescription());
        bundle.putString("pur_url",listsBean.getPurchase_url());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        if (i+i1==i2){
            offset++;
            initGridView();
        }

    }
}
