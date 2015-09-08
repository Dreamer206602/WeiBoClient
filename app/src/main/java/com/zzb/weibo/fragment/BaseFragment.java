package com.zzb.weibo.fragment;

import com.umeng.analytics.MobclickAgent;
import com.zzb.mvp.BaseMVPFragment;

/**
 * Fragment基类
 * Created by ZZB on 2015/9/1.
 */
public abstract class BaseFragment extends BaseMVPFragment {
    protected abstract String getClassName();
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClassName());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClassName());
    }
}
