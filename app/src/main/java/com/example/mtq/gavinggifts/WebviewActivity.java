package com.example.mtq.gavinggifts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.example.mtq.gavinggifts.entity.NetworkImageHolderView;
import com.gradient.inter.GradientStyle;
import com.gradient.utils.GradientUtils;
import com.gradient.view.GradientActionBar;
import com.gradient.view.GradientScrollView;

import java.util.ArrayList;


public class WebviewActivity extends AppCompatActivity implements View.OnClickListener {
    private WebView webView;
    private String url;
    private GradientActionBar actionBar;
    private ConvenientBanner convenientBanner;
    private CheckBox checkBox;
    private TextView title,price,desco,items_text,items_gotobuy;
    private ArrayList<String> image_urls;
    private ImageView imageView;
    private LinearLayout linearLayout;
    private GradientUtils gradientUtils;
    private GradientScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_layout_webview);
        getSupportActionBar().hide();
        initfindView();
        initActionBar();
        initDatas();
        initListener();

    }

    private void initActionBar() {
        gradientUtils=new GradientUtils().setColorMode(GradientStyle.ASC,255,0,0);
        gradientUtils.bind(actionBar,scrollView);
    }


    private void initListener() {
        imageView.setOnClickListener(this);
        linearLayout.setOnClickListener(this);
        items_gotobuy.setOnClickListener(this);

    }

    //找到控件
    private void initfindView() {
        webView= (WebView) findViewById(R.id.webView);
        convenientBanner= (ConvenientBanner) findViewById(R.id.items_banner);
        title= (TextView) findViewById(R.id.items_title);
        price= (TextView) findViewById(R.id.items_price);
        desco= (TextView) findViewById(R.id.items_desco);

        scrollView= (GradientScrollView) findViewById(R.id.scrillview);
        actionBar= (GradientActionBar) findViewById(R.id.actionbar);


        imageView= (ImageView) findViewById(R.id.back_to);
        checkBox= (CheckBox) findViewById(R.id.items_check);
        items_text= (TextView) findViewById(R.id.items_check_text);
        items_gotobuy= (TextView) findViewById(R.id.click_to_buy);
        linearLayout= (LinearLayout) findViewById(R.id.items_likes);
    }
    //加载数据
    private void initDatas() {
        initBannerView();
        initTextView();
        initWebView();


    }



    private void initBannerView() {
        image_urls=getIntent().getStringArrayListExtra("image_urls");
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        },image_urls);
        if (image_urls.size()>1) {
            convenientBanner.setPageIndicator(new int[]{R.drawable.btn_check_disabled_nightmode, R.drawable.btn_check_disabled});
        }else{
            convenientBanner.setManualPageable(false);
        }
    }


    private void initTextView() {

        title.setText(getIntent().getStringExtra("titlesss"));
        price.setText("￥"+getIntent().getStringExtra("price"));
        desco.setText(getIntent().getStringExtra("desco"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_to:
                finish();
                break;

            case R.id.click_to_buy:
                final String pur_url=getIntent().getStringExtra("pur_url");
                Bundle bundle=new Bundle();
                Intent intent=new Intent();
                intent.setClass(WebviewActivity.this, GoToBuyActivity.class);
                bundle.putString("pur_urls",pur_url);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.items_likes:
                boolean checked = checkBox.isChecked();
                if (checked){
                    checkBox.setChecked(false);
                    items_text.setText("喜欢");
                }else{
                    checkBox.setChecked(true);
                    items_text.setText("不喜欢");
                }
                break;
            default:
                break;

        }
    }

    //加载网页
    private void initWebView() {
        url=getIntent().getStringExtra("url");
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        boolean b=webView.canGoBack();
        if (b){
            webView.goBack();
        }
    }


}