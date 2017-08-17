package com.github.perfectframe.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.ImmersionFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ${sheldon} on 2017/7/10.
 */

public abstract class BaseFragment extends ImmersionFragment {
    Unbinder unbinder;
    private ProgressDialog mProgressDialog;
    /**
     * 贴附的activity
     */
    protected FragmentActivity mActivity;
    /**
     * 根view
     */
    protected View mRootView;

    /**
     * 是否对用户可见
     */
    protected boolean mIsVisible;
    /**
     * 是否加载完成
     * 当执行完oncreatview,View的初始化方法后方法后即为true
     */
    protected boolean mIsPrepare;

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        mRootView = inflater.inflate(setLayoutResouceId(), container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        initData(getArguments());
        mProgressDialog = new ProgressDialog(getActivity());
        initView();
        mIsPrepare = true;
        onLazyLoad();
        setListener();
        immersionInit();
        return mRootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mProgressDialog!=null){
            mProgressDialog.dismiss();
        }
    }
    /*
    * 初始化沉浸式状态栏
    * */
    @Override
    protected void immersionInit() {
        ImmersionBar.with(getActivity()).init();
    }
    /**
     * 初始化数据
     * @param arguments 接收到的从其他地方传递过来的参数
     */
    protected void initData(Bundle arguments)
    {

    }

    /**
     * 初始化View
     */
    protected void initView()
    {

    }
    /*
    * 弹出吐司
    * */
    protected void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
    /*
       * 弹出加载对话框
       * */
    public void showDialog(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setCancelable(true);
        }
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }
    public void hideDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
    /**
     * 设置监听事件
     */
    protected void setListener()
    {

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mProgressDialog.dismiss();
        ImmersionBar.with(getActivity()).destroy();
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);

        this.mIsVisible = isVisibleToUser;

        if (isVisibleToUser)
        {
            onVisibleToUser();
        }
    }

    /**
     * 用户可见时执行的操作
     */
    protected void onVisibleToUser()
    {
        if (mIsPrepare && mIsVisible)
        {
            onLazyLoad();
        }
    }
    /*
    * 开启新的activity
    * */
    protected void startActivity(Class activity) {
        startActivity(activity, false);
    }

     /*
     * 开启新的activity 传入数据
     * */
    protected void startActivity(Class activity, String key, String extra) {
        Intent intent = new Intent(getContext(), activity);
        intent.putExtra(key, extra);
        startActivity(intent);
    }

    protected void startActivity(Class activity, boolean finish) {
        Intent intent = new Intent(getContext(), activity);
        startActivity(intent);
        if (finish) {
            getActivity().finish();
        }
    }
    /**
     * 懒加载，仅当用户可见切view初始化结束后才会执行
     */
    protected void onLazyLoad()
    {
    }

    /**
     * 设置根布局资源id
     * @return
     */
    protected abstract int setLayoutResouceId();
}
