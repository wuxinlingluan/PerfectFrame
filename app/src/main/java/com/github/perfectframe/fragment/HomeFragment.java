package com.github.perfectframe.fragment;

import com.github.perfectframe.R;
import com.github.perfectframe.base.BaseFragment;
import com.gyf.barlibrary.ImmersionBar;

/*
* 首页fragment
* */
public class HomeFragment extends BaseFragment {

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        super.initView();
        ImmersionBar.with(getActivity()).statusBarColor(R.color.colorchecked).init();// 避免沉浸式状态栏无效和重叠问题
    }
}
