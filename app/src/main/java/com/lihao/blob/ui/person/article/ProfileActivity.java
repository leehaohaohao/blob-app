package com.lihao.blob.ui.person.article;

/**
 * classname
 *
 * @author lihao
 * &#064;date  2024/12/16--18:12
 * @since 1.0
 */
public class ProfileActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private ProfilePagerAdapter profilePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        // 创建适配器
        profilePagerAdapter = new ProfilePagerAdapter(this);
        viewPager.setAdapter(profilePagerAdapter);

        // 设置 TabLayout 和 ViewPager2 的联动
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("我的文章");
                    break;
                case 1:
                    tab.setText("我的喜欢");
                    break;
            }
        }).attach();
    }
}
