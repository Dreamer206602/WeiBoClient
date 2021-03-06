package com.zzb.weibo.common;

import com.zzb.weibo.BuildConfig;

/**
 * 一些配置内容
 * Created by ZZB on 2015/8/31.
 */
public class Config {
    public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
    //    // 请上 open.weibo.com 自行注册应用并替换下面两个参数
    public static final String APP_SECRET = BuildConfig.WEIBO_APP_SECRET;
    public static final String APP_KEY = BuildConfig.WEIBO_APP_KEY;
    //    /**Bugly监控, bugly代码可以注释*/
    public static final String BUGLY_APP_ID = BuildConfig.BUGLY_APP_ID;
    public static final String BUGLY_APP_KEY = BuildConfig.BUGLY_APP_KEY;
    //一次加载微博的条数
    public static final int LOAD_WEIBO_SIZE = 20;


}
