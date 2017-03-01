package drrino.com.learningdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import drrino.com.learningdemo.ui.ClassifyViewPagerAdapter;
import drrino.com.learningdemo.ui.SimpleFragment;
import drrino.com.learningdemo.ui.ZhihuDailyFragment;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.bottom_navigation) AHBottomNavigation bottomNavigation;
    @BindView(R.id.view_pager) AHBottomNavigationViewPager viewPager;

    private Fragment currentFragment;
    private ZhihuDailyFragment zhihuDailyFragment = new ZhihuDailyFragment();
    private SimpleFragment simpleFragment = new SimpleFragment();
    private ClassifyViewPagerAdapter adapter;
    private List<Fragment> fragments = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initViews();
    }


    private void initViews() {
        fragments.add(zhihuDailyFragment);
        fragments.add(simpleFragment);
        fragments.add(zhihuDailyFragment);
        // Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("zhihu",
            R.drawable.ic_favorites);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("aaa",
            R.drawable.ic_favorites);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("bbbb",
            R.drawable.ic_favorites);
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);

        bottomNavigation.setTranslucentNavigationEnabled(true);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (currentFragment == null) {
                    currentFragment = adapter.getCurrentFragment();
                }

                if (wasSelected) {
                    if (currentFragment == zhihuDailyFragment) {
                        zhihuDailyFragment.refresh();
                    }
                    return true;
                }

                if (currentFragment != null) {
                    if (currentFragment == zhihuDailyFragment) {
                        zhihuDailyFragment.willBeHidden();
                    }
                }

                viewPager.setCurrentItem(position, false);
                currentFragment = adapter.getCurrentFragment();
                if (currentFragment == zhihuDailyFragment) {
                    zhihuDailyFragment.willBeDisplayed();
                }

                if (position == 1) {
                    bottomNavigation.setNotification("", 1);

                }
                return true;
            }
        });

        viewPager.setOffscreenPageLimit(3);
        adapter = new ClassifyViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);

        currentFragment = adapter.getCurrentFragment();
    }
}
