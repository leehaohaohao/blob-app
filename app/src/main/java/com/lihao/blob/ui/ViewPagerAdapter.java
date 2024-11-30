package com.lihao.blob.ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.lihao.blob.ui.home.HomeFragment;
import com.lihao.blob.ui.person.ProfileFragment;
import com.lihao.blob.ui.publish.PublishFragment;

/**
 * classname
 *
 * @author lihao
 * &#064;date  2024/11/30--16:20
 * @since 1.0
 */
public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new PublishFragment();
            case 1:
                return new HomeFragment();
            case 2:
                return new ProfileFragment();
            default:
                throw new IllegalArgumentException("Invalid position");
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

