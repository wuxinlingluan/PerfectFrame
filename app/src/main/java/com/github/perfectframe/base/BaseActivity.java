package com.github.perfectframe.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.github.perfectframe.MainActivity;
import com.github.perfectframe.R;
import com.gyf.barlibrary.ImmersionBar;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import butterknife.ButterKnife;

/**
 * Created by ${sheldon} on 2017/8/16.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;
    protected Toolbar mToolbar;//Toolbar
    protected Context mContext;//上下文环境
    protected boolean mBack = true;
    protected static List<Activity> mActivities = new LinkedList<Activity>();
    private long mPreTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ImmersionBar.with(this).init();
        mToolbar = ButterKnife.findById(this, R.id.toolbar);
        mContext = this;
        ButterKnife.bind(this);
        mProgressDialog=new ProgressDialog(this);
        synchronized (mActivities) {
            mActivities.add(this);
        }
        initInject();
        initVariables();
        if (mToolbar != null) {
            //初始化Toolbar
            initToolbar();
            //让组件支持Toolbar
            setSupportActionBar(mToolbar);
            this.getSupportActionBar().setTitle("");
            if (mBack)mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        initWidget();
        initDatas();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mProgressDialog.dismiss();
    }

    /**
     * 注入依赖
     */
    protected void initInject() {

    }
    /**
     * 完成请求
     */
    protected void finishTask() {
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //如果用以下这种做法则不保存状态，再次进来的话会显示默认tab
        //总是执行这句代码来调用父类去保存视图层的状态
        //super.onSaveInstanceState(outState);
    }


    /**
     * 销毁
     */
    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        synchronized (mActivities) {
            mActivities.remove(this);
        }
        super.onDestroy();
    }

    /**
     * 初始化Toolbar
     */
    protected void initToolbar() {
        if (mBack) mToolbar.setNavigationIcon(R.drawable.icon_back);
    }
    //开启activity
    protected void startActivity(Class activity) {
        startActivity(activity, true);
    }
    protected void startActivity(Class activity, boolean finish) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
        if (finish) {
            finish();
        }
    }
    /**
     * 布局文件
     *
     * @return 布局文件
     */
    protected abstract
    @LayoutRes
    int getLayoutId();

    /**
     * 初始化控件
     */
    protected void initWidget() {
    }

    /**
     * 加载数据
     */
    protected void loadData() {
    }

    /**
     * 初始化数据
     */
    protected void initDatas() {
        loadData();
    }

    /**
     * 初始化变量
     */
    protected void initVariables() {
    }


    protected void showProgress(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(true);
        }
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    protected void hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    protected void showToast(String msg) {
        Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
    /**
     * 隐藏View
     *
     * @param views 视图
     */
    protected void gone(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }

    public void showDialog(String s) {
        showProgress(s);
    }
    /**
     * 显示View
     *
     * @param views 视图
     */
    protected void visible(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    /**
     * 隐藏View
     *
     * @param id
     */
    protected void gone(final @IdRes int... id) {
        if (id != null && id.length > 0) {
            for (int resId : id) {
                View view = $(resId);
                if (view != null)
                    gone(view);
            }
        }

    }

    /**
     * 显示View
     *
     * @param id
     */
    protected void visible(final @IdRes int... id) {
        if (id != null && id.length > 0) {
            for (int resId : id) {
                View view = $(resId);
                if (view != null)
                    visible(view);
            }
        }
    }

    private View $(@IdRes int id) {
        View view;
        if (this != null) {
            view = this.findViewById(id);
            return view;
        }
        return null;
    }

    /**
     * 退出应用
     */
    public static void exitApp() {
        // 遍历所有的activity，finish
        ListIterator<Activity> iterator = mActivities.listIterator();
        while (iterator.hasNext()) {
            Activity next = iterator.next();
            next.finish();
        }
    }
    /**
     * 返回键的处理
     */
    @Override
    public void onBackPressed() {
        if (this instanceof MainActivity) {
            if (System.currentTimeMillis() - mPreTime > 2000) {
                Toast.makeText(this, "再按一次退出应用",
                        Toast.LENGTH_SHORT).show();
                mPreTime = System.currentTimeMillis();
                return;
            } else {
                exitApp();
                finish();
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }
}
