package com.example.mtq.gavinggifts.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mtq.gavinggifts.R;
import com.example.mtq.gavinggifts.adapter.GuideTabAdapter;
import com.example.mtq.gavinggifts.adapter.SpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mtq on 2016/8/18.
 */
public class GuideFragment extends Fragment {
    private Context mContext;
    private View view;
    private ImageView imageSign, imageSearch;
    private TextView tvTitle;
    private RelativeLayout rlTop, rlBottom;
    private ImageView ivReduce, ivMore;
    private TabLayout tabLayout;
    private GridView gridView;
    private ViewPager viewPager;
    private String[] title;
    private List<String> titleList;
    private List<Fragment> fragmentList;
    private SpinnerAdapter mSpinnerAdapter;
    private GuideTabAdapter guideTabAdapter;
    private String PATH1 = "http://api.liwushuo.com/v2/channels/";
    private String PATH2 = "/items?gender=1&limit=20&offset=0&generation=2";
    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = View.inflate(mContext, R.layout.fragment_guide, null);
            initFindView();
            initFragment();
            initAdapter();
            initListener();
        } else {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
        return view;
    }

    private void initListener() {
        ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSpinner();
            }
        });
        ivReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSpinner();
            }
        });
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideSpinner();
                return false;
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                hideSpinner();
                tabLayout.getTabAt(i).select();
            }
        });
    }

    private void hideSpinner() {
        rlTop.setVisibility(View.GONE);
        rlBottom.setVisibility(View.VISIBLE);
        gridView.setVisibility(View.GONE);
    }

    private void showSpinner() {
        rlTop.setVisibility(View.VISIBLE);
        rlBottom.setVisibility(View.GONE);
        gridView.setVisibility(View.VISIBLE);
    }

    private void initAdapter() {
        mSpinnerAdapter = new SpinnerAdapter(mContext, title);
        gridView.setAdapter(mSpinnerAdapter);
    }

    private void initFragment() {
        titleList = new ArrayList<>();
        fragmentList = new ArrayList<>();
        title = new String[]{
                "精选", "送女票", "送基友", "送爸妈",
                "送同事", "送宝贝", "涨姿势", "生日",
                "纪念日", "感谢", "乔迁礼物"
        };
        int[] id = new int[]{
                10,26,6,17,24,120,30,31,36,35
        };
        Fragment fragment=new GuideMainFragment();
        fragmentList.add(fragment);
        titleList.add(title[0]);
        for (int i=1;i<title.length;i++){
            Fragment fragment1=new GuideNormalFragment(PATH1+id[i-1]+PATH2);
            fragmentList.add(fragment1);
            titleList.add(title[i]);
        }
        for (int i=0;i<title.length;i++){
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            tabLayout.addTab(tabLayout.newTab().setText(title[i]));
        }
        guideTabAdapter=new GuideTabAdapter(getActivity().getSupportFragmentManager(),fragmentList,titleList);
        viewPager.setAdapter(guideTabAdapter);
        tabLayout.setupWithViewPager(viewPager);//将tabLayout与viewPager关联起来
    }

    private void initFindView() {
        imageSign = (ImageView) view.findViewById(R.id.image_sign);
        imageSearch = (ImageView) view.findViewById(R.id.image_search);
        tvTitle = (TextView) view.findViewById(R.id.title_bar);
        ivReduce = (ImageView) view.findViewById(R.id.img_reduce);
        ivMore = (ImageView) view.findViewById(R.id.img_more);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        rlTop = (RelativeLayout) view.findViewById(R.id.RLayout_top);
        rlBottom = (RelativeLayout) view.findViewById(R.id.RLayout_bottom);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        gridView = (GridView) view.findViewById(R.id.grid);
    }
}
