package com.example.mtq.gavinggifts.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.MessageQueue;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mtq.gavinggifts.GuideDetailsActivity;
import com.example.mtq.gavinggifts.R;
import com.example.mtq.gavinggifts.adapter.GuideNormalRecycleAdapter;
import com.example.mtq.gavinggifts.adapter.GuideRecyclerViewAdapter;
import com.example.mtq.gavinggifts.entity.GuideNormalRecycler;
import com.example.mtq.gavinggifts.view.MyApplication;
import com.example.mtq.gavinggifts.view.MyItemDecoration;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by mtq on 2016/8/18.
 */
@SuppressLint("ValidFragment")
public class GuideNormalFragment extends Fragment {
    private FragmentActivity context;
    private View view;
    private RecyclerView mRecyclerView;
    private List<GuideNormalRecycler.DataBean.ItemsBean> items;
    private GuideNormalRecycleAdapter adapter;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private String path;

    @SuppressLint("ValidFragment")
    public GuideNormalFragment(String path) {
        this.path=path;
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        this.context=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (view==null){
            view=inflater.inflate(R.layout.guide_normal_fragment,null,false);
            initView();
            initData();
            initListener();

        }else {
            ViewGroup parent= (ViewGroup) container.getParent();
            if (parent!=null){
                parent.removeView(view);
            }
        }
        return view;
    }

    private void initListener() {

    }

    private void initData() {
        StringRequest stringrequest=new StringRequest(path, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                GuideNormalRecycler guideNormalRecycler=gson.fromJson(response,GuideNormalRecycler.class);
                GuideNormalRecycler.DataBean data=guideNormalRecycler.getData();
                items=data.getItems();
                initAdapter();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        MyApplication.getmQueue().add(stringrequest);

    }

    private void initAdapter() {
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(layoutManager);

        adapter=new GuideNormalRecycleAdapter(context, items, new GuideRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(context, GuideDetailsActivity.class);
                intent.putExtra("DetailsUrl",items.get(position).getContent_url());
                startActivity(intent);
            }
        });
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.addItemDecoration(new MyItemDecoration(15));
    }

    private void initView() {
        mRecyclerView= (RecyclerView) view.findViewById(R.id.guide_normal_recycler);
    }
}
