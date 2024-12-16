package com.lihao.blob.ui.person.article;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * classname
 *
 * @author lihao
 * &#064;date  2024/12/16--18:12
 * @since 1.0
 */
public class ProfilePagerAdapter extends FragmentStateAdapter {

    public ProfilePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new MyArticlesFragment(); // 文章页面
            case 1:
                return new MyLikesFragment(); // 喜欢页面
            default:
                return new MyArticlesFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2; // 只包含两个Tab
    }
}
