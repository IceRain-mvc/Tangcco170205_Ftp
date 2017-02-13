package com.tangcco170205_ftp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.tangcco170205_ftp.R;
import com.tangcco170205_ftp.bean.AllDataBean;
import com.tangcco170205_ftp.service.CallUploadService;
import com.tangcco170205_ftp.ui.fragment.GroupsFragment;
import com.tangcco170205_ftp.ui.fragment.HomeFragment;
import com.tangcco170205_ftp.ui.fragment.ListsFragment;
import com.tangcco170205_ftp.ui.fragment.ProfileFragment;
import com.tangcco170205_ftp.utils.FloatUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static butterknife.ButterKnife.findById;

public class HomeActivity extends AppCompatActivity {
    @Bind(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.main_fab)
    FloatingActionButton mFloatingActionButton;

    private ActionBarDrawerToggle mDrawerToggle;

    private int mCurrentMenu;

    private HomeFragment mHomeFragment;

    private GroupsFragment mGroupsFragment;

    private ListsFragment mListsFragment;

    private ProfileFragment mProfileFragment;
    private List<AllDataBean> allDataBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        Toolbar toolbar = findById(this, R.id.toolbar);
        //初始化actionBar
        setSupportActionBar(toolbar);
        //初始化左上角和侧滑
        setupNavigationView(toolbar);
        //初始化悬浮按钮
        setupFloatingActionButton();
        //
        switchFragment(R.id.home);
        //启动服务
        startService(new Intent(this, CallUploadService.class));
    }
    private void setupNavigationView(Toolbar toolbar) {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        NavigationView navigationView = findById(this, R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switchFragment(menuItem.getItemId());
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    //设置悬浮按钮
    private void setupFloatingActionButton() {
        FloatUtils.setFabLayoutParams(mFloatingActionButton, new FloatUtils.OnCanSetLayoutParamsListener() {
            @Override
            public void onCanSetLayoutParams() {
                // setMargins to fix floating action button's layout bug
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mFloatingActionButton
                        .getLayoutParams();
                params.setMargins(0, 0, 0, 0);
                mFloatingActionButton.setLayoutParams(params);
            }
        });
    }


    private void switchFragment(int menuId) {
        mCurrentMenu = menuId;
        ActionBar actionBar = getSupportActionBar();
        switch (menuId) {
            case R.id.home:
                if (actionBar != null) {
                    actionBar.setTitle(R.string.home);
                }
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment.newInstance();
                }
                mFloatingActionButton.setVisibility(View.VISIBLE);
                replaceMainFragment(mHomeFragment);
                break;
            case R.id.groups:
                if (actionBar != null) {
                    actionBar.setTitle(R.string.groups);
                }
                if (mGroupsFragment == null) {
                    mGroupsFragment = GroupsFragment.newInstance();
                }
                mFloatingActionButton.setVisibility(View.GONE);
                replaceMainFragment(mGroupsFragment);
                break;
            case R.id.lists:
                if (actionBar != null) {
                    actionBar.setTitle(R.string.lists);
                }
                if (mListsFragment == null) {
                    mListsFragment = ListsFragment.newInstance();
                }
                mFloatingActionButton.setVisibility(View.GONE);
                replaceMainFragment(mListsFragment);
                break;
            case R.id.profile:
                if (actionBar != null) {
                    actionBar.setTitle(R.string.profile);
                }
                if (mProfileFragment == null) {
                    mProfileFragment = ProfileFragment.newInstance();
                }
                mFloatingActionButton.setVisibility(View.GONE);
                replaceMainFragment(mProfileFragment);
                break;
            default:
                break;
        }
    }
    //切换Fragment
    private void replaceMainFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.main_container, fragment)
                .commit();
    }

}
