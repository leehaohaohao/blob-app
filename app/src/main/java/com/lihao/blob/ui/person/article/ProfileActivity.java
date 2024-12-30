package com.lihao.blob.ui.person.article;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lihao.blob.R;


/**
 * 个人中心页面
 *
 * @author lihao
 * &#064;date  2024/12/16--18:12
 * @since 1.0
 */
public class ProfileActivity extends AppCompatActivity {
    //控件
    private BottomNavigationView bottomNavigationView;
    private ViewPager2 viewPager;
    private ImageView ivBack;
    public void back(){
        OnBackPressedDispatcher onBackPressedDispatcher = getOnBackPressedDispatcher();
        onBackPressedDispatcher.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        viewPager = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        // 设置 ViewPager 的适配器
        viewPager.setAdapter(new ProfilePagerAdapter(this));
        // 默认显示首页，设置 ViewPager 默认页面为首页，并且选中首页图标
        viewPager.setCurrentItem(0, false);
        bottomNavigationView.setSelectedItemId(R.id.nav_my_articles);
        ivBack = findViewById(R.id.ivBack);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // 页面切换时同步更新底部导航栏的选中项
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }
        });
        // 设置底部导航栏点击事件
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if(itemId == R.id.nav_my_articles){
                viewPager.setCurrentItem(0,false);
                return true;
            }else if(itemId == R.id.nav_my_likes){
                viewPager.setCurrentItem(1,false);
                return true;
            }
            return false;
        });
        ivBack.setOnClickListener(v-> back());
    }
}