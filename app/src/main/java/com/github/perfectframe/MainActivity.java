package com.github.perfectframe;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.yanzhenjie.album.Album;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ArrayList<String> pathList;
    private String picPath;
    private Bitmap bitmap;
    private HomeFragment homeFragment;
    private ChannelFragment channelFragment;
    private FrameworkFragment frameworkFragment;
    private WorkSpaceFragment workSpaceFragment;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener //底部按钮监听
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.na_home: //对应第一个按钮
                     initHomeFragment();
                    return true;
                case R.id.na_channel://第二个
                    initChannelFragment();
                    return true;
                case R.id.na_framework://第三个
                    initFrameworkFragment();
                    return true;
                case R.id.na_workbench://第四个
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

    //侧拉菜单点击事件
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            Album.album(this)
                    .selectCount(1) // 最多选择几张图片。
                    .columnCount(2) // 相册展示列数，默认是2列。
                    .camera(true) // 是否有拍照功能。
                    .start(999); // 999是请求码，返回时onActivityResult()的第一个参数。
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
    //初始化布局
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
    //初始化操作,对 底部状态栏 侧边栏初始化,并使用反射更改底部样式
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void initDatas() {
        super.initDatas();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);//初始化侧边栏

        navigationView.setNavigationItemSelectedListener(this);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);//初始化底部按钮
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 999) {
            if (resultCode == RESULT_OK) { // Successfully.
                // 不要质疑你的眼睛，就是这么简单。
                pathList = Album.parseResult(data);
                picPath = pathList.get(0);
                bitmap = BitmapFactory.decodeFile(picPath);
              CircleImageView ivHead = (CircleImageView) findViewById(R.id.iv_head);
                ivHead.setImageBitmap(bitmap);
            } else if (resultCode == RESULT_CANCELED) { // User canceled.
                // 用户取消了操作。
            }
        }
    }
}
