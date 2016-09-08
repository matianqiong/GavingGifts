package com.example.mtq.gavinggifts;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.mtq.gavinggifts.fragment.GuideFragment;
import com.example.mtq.gavinggifts.fragment.HotFragment;
import com.example.mtq.gavinggifts.fragment.MyFragment;
import com.example.mtq.gavinggifts.fragment.SelectFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RadioGroup mRadioGroup;
    private FragmentManager mFragmentManager=getSupportFragmentManager();
    private List<Fragment> fragments;
    private Fragment guideFragment,hotFragment,selectFragment,myFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();//将actionBar隐藏
        initFindView();
        initFragment();
        initListenner();
    }

    private void initFragment() {
        fragments=new ArrayList<>();
        guideFragment=new GuideFragment();
        hotFragment=new HotFragment();
        selectFragment=new SelectFragment();
        myFragment=new MyFragment();
        fragments.add(guideFragment);
        fragments.add(hotFragment);
        fragments.add(selectFragment);
        fragments.add(myFragment);

    }

    private void initListenner() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int CheckedId) {
                int count=radioGroup.getChildCount();
                for (int i =0;i<count;i++){
                    RadioButton button= (RadioButton) mRadioGroup.getChildAt(i);
                    if (button.isChecked()){
                        FragmentTransaction transaction=mFragmentManager.beginTransaction();
                        transaction.replace(R.id.fl_container,fragments.get(i)).commit();
                    }
                }
            }
        });
        mFragmentManager.beginTransaction().replace(R.id.fl_container,guideFragment).commit();
    }

    private void initFindView() {
        mRadioGroup= (RadioGroup) findViewById(R.id.radio_group);
        int count = mRadioGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            RadioButton button = (RadioButton) mRadioGroup.getChildAt(i);
            //设置图片大小,drawables的长度为4，分别代表left,top,right,bottom
            Drawable[] drawables = button.getCompoundDrawables();
            drawables[1].setBounds(0, 0, 55, 55);
            button.setCompoundDrawables(null, drawables[1], null, null);
        }

    }
}
