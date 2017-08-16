package com.github.perfectframe.fragment;

import com.github.perfectframe.R;
import com.github.perfectframe.base.BaseFragment;
import com.gyf.barlibrary.ImmersionBar;

/**
 * Created by ${sheldon} on 2017/8/16.
 */

public class ChannelFragment extends BaseFragment {
    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_channel;
    }
    @Override
    protected void initView() {
        super.initView();
        ImmersionBar.with(getActivity()).statusBarColor(R.color.colorchecked).init();
    }
}
