package com.zzb.weibo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zzb.library.utils.UrlUtils;
import com.zzb.weibo.R;
import com.zzb.weibo.data.AccessTokenKeeper;
import com.zzb.weibo.http.api.AuthApi;
import com.zzb.weibo.http.api.UserApi;
import com.zzb.weibo.http.base.ApiUrl;
import com.zzb.weibo.http.base.HttpConfig;
import com.zzb.weibo.http.base.RetrofitHelper;

import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;

public class LoginAuthActivity extends BaseActivity {
    private static final String TAG = LoginAuthActivity.class.getSimpleName();
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        initViews();

        //请求用户授权Token
        mWebView.loadUrl(ApiUrl.Auth.AUTH_URL);
    }

    private void initViews() {
        Toolbar toolbar = $(R.id.toolbar);
        toolbar.setTitle("授权登录");
        setSupportActionBar(toolbar);
        mWebView = $(R.id.web_view);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Map<String, String> params = UrlUtils.getUrlParams(url);
                String code = params.get("code");
                getAccessToken(code);
                return true;
            }

        });
    }

    private void getAccessToken(String code) {
        RetrofitHelper.getApi(HttpConfig.AUTH_END_POINT, AuthApi.class).getAccessToken(code)
                .flatMap(accessToken -> {
                    AccessTokenKeeper.writeAccessToken(LoginAuthActivity.this, accessToken);
                    return RetrofitHelper.getApi(UserApi.class).getUID();
                })
                .subscribeOn(AndroidSchedulers.mainThread()).subscribe(
                uid -> {
//                    uid.uid;//TODO SAVE uid
                    Log.d(TAG, "getAccessToken " + uid.uid);
                },
                error -> {
                    Log.e(TAG, "getAccessToken ", error);
                },
                () -> {//complete
                    Intent intent = new Intent(LoginAuthActivity.this, MainActivity.class);
                    startActivity(intent);
                }
        );
    }


    @Override
    protected String getClassName() {
        return getClass().getSimpleName();
    }
}
