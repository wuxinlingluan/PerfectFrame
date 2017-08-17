package com.github.perfectframe;

import android.content.res.ColorStateList;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.github.perfectframe.base.BaseActivity;
import com.github.perfectframe.fragment.ChannelFragment;
import com.github.perfectframe.fragment.FrameworkFragment;
import com.github.perfectframe.fragment.HomeFragment;
import com.github.perfectframe.fragment.WorkSpaceFragment;
import com.github.perfectframe.util.BottomNavigationViewHelper;
    //主页
public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private HomeFragment homeFragment;
    private ChannelFragment channelFragment;
    private FrameworkFragment frameworkFragment;
    private WorkSpaceFragment workSpaceFragment;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.na_home:
                    initHomeFragment();
                    return true;
                case R.id.na_channel:
                    initChannelFragment();
                    return true;
                case R.id.na_framework:
                    initFrameworkFragment();
                    return true;
                case R.id.na_workbench:
                    initWorkSpaceFragment();
                    return true;
            }
            return false;
        }

    };
    //监听返回键,判断当侧拉菜单显示时,先关闭侧拉菜单
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
    //初始化操作,对 底部状态栏 侧边栏初始化,并使用反射更改底部样式
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void initDatas() {
        super.initDatas();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);//使用反射解决去除底部切换动画效果
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked},
                new int[]{android.R.attr.state_checked}
        };
        int[] colors = new int[]{getResources().getColor(R.color.colornormal),//默认颜色
                getResources().getColor(R.color.colorchecked)//选中颜色
        };
        ColorStateList csl = new ColorStateList(states, colors);
        navigation.setItemTextColor(csl);
        navigation.setItemIconTintList(csl);
        initHomeFragment();//进入页面时 初始化 首页
    }

    //初始化首页界面
    private void initHomeFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(homeFragment == null){
            homeFragment = new HomeFragment();
            transaction.add(R.id.content, homeFragment);
        }
        //隐藏所有fragment
        hideFragment(transaction);
        //显示需要显示的fragment
        transaction.show(homeFragment);
        transaction.commit();
    }
    //初始化频道界面
    private void initChannelFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(channelFragment == null){
            channelFragment = new ChannelFragment();
            transaction.add(R.id.content, channelFragment);
        }
        //隐藏所有fragment
        hideFragment(transaction);
        //显示需要显示的fragment
        transaction.show(channelFragment);
        transaction.commit();
    }
    //初始化组织架构界面
    private void initFrameworkFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(frameworkFragment == null){
            frameworkFragment = new FrameworkFragment();
            transaction.add(R.id.content, frameworkFragment);
        }
        //隐藏所有fragment
        hideFragment(transaction);
        //显示需要显示的fragment
        transaction.show(frameworkFragment);
        transaction.commit();
    }
    //初始化工作台界面
    private void initWorkSpaceFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(workSpaceFragment == null){
            workSpaceFragment = new WorkSpaceFragment();
            transaction.add(R.id.content, workSpaceFragment);
        }
        //隐藏所有fragment
        hideFragment(transaction);
        //显示需要显示的fragment
        transaction.show(workSpaceFragment);
        transaction.commit();
    }
    //隐藏所有的fragment
    private void hideFragment(FragmentTransaction transaction){
        if(homeFragment != null){
            transaction.hide(homeFragment);
        }
        if(channelFragment != null){
            transaction.hide(channelFragment);
        }
        if(frameworkFragment != null){
            transaction.hide(frameworkFragment);
        }
        if(workSpaceFragment != null){
            transaction.hide(workSpaceFragment);
        }
    }
}
