package com.lihao.blob.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lihao.blob.R;

/**
 * classname
 *
 * @author lihao
 * &#064;date  2024/11/30--16:19
 * @since 1.0
 */
public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private BottomNavigationView bottomNavigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // 设置 ViewPager 的适配器
        viewPager.setAdapter(new ViewPagerAdapter(this));

        // 默认显示首页，设置 ViewPager 默认页面为首页，并且选中首页图标
        viewPager.setCurrentItem(1, false);
        bottomNavigationView.setSelectedItemId(R.id.nav_home); // 选中首页图标

        // ViewPager 页面切换监听
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // 页面切换时同步更新底部导航栏的选中项
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }
        });

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_publish) {
                viewPager.setCurrentItem(0, false);
                return true;
            } else if (itemId == R.id.nav_home) {
                viewPager.setCurrentItem(1, false);
                return true;
            } else if (itemId == R.id.nav_profile) {
                viewPager.setCurrentItem(2, false);
                return true;
            } else {
                return false;
            }
        });
    }
}
