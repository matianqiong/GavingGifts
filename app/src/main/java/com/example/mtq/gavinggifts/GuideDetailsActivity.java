package com.example.mtq.gavinggifts;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by mtq on 2016/9/2.
 */
public class GuideDetailsActivity extends AppCompatActivity {
    private WebView mWebView;
    private CheckBox mBack,mBtnShared,mBtnComment;
    private String mUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_details);
        getSupportActionBar().hide();
        initView();
        initWebView();
        initListener();
    }

    private void initListener() {
        mBtnShared.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                 showShare(mUrl);
            }
        });
    }

    private void initWebView() {

        Intent intent=getIntent();
        mUrl=intent.getStringExtra("DetailsUrl");
        mWebView.loadUrl(mUrl);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient());

    }

    private void initView() {
        mWebView = (WebView)findViewById(R.id.guide_details_webview);
        mBack = (CheckBox)findViewById(R.id.guide_details_checkbox);
        mBtnShared = (CheckBox)findViewById(R.id.guide_details_share);
        mBtnComment = (CheckBox)findViewById(R.id.guide_details_comment);
        Drawable[] drawables = mBtnShared.getCompoundDrawables();
        drawables[0].setBounds(0, 0, 50, 50);
        mBtnShared.setCompoundDrawables(drawables[0], null, null, null);
    }

    private void showShare(String Url) {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(Url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("分享链接中");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        //oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(Url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("分享链接中。。");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(Url);

// 启动分享GUI
        oks.show(this);
    }
}
