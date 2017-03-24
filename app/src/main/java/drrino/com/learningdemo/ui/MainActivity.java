package drrino.com.learningdemo.ui;

import android.support.v4.app.Fragment;
import butterknife.BindView;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import drrino.com.learningdemo.R;
import drrino.com.learningdemo.base.BaseActivity;
import drrino.com.learningdemo.ui.common.adapter.ClassifyViewPagerAdapter;
import drrino.com.learningdemo.ui.zhihu.fragment.ZhihuDailyFragment;

public class MainActivity extends BaseActivity {
    @BindView(R.id.bottom_navigation) AHBottomNavigation bottomNavigation;
    @BindView(R.id.view_pager) AHBottomNavigationViewPager viewPager;

    private Fragment currentFragment;
    private ZhihuDailyFragment zhihuDailyFragment = new ZhihuDailyFragment();
    private ClassifyViewPagerAdapter adapter;
    private String[] mBottomTitles;


    @Override protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override protected void initData() {
        mBottomTitles = getResources().getStringArray(R.array.main_classify);
        // Create items
        AHBottomNavigationItem zhihu = new AHBottomNavigationItem(mBottomTitles[0],
            R.drawable.ic_zhihu);
        AHBottomNavigationItem gank = new AHBottomNavigationItem(mBottomTitles[1],
            R.drawable.ic_favorites);
        AHBottomNavigationItem mine = new AHBottomNavigationItem(mBottomTitles[2],
            R.drawable.ic_favorites);
        bottomNavigation.addItem(zhihu);
        bottomNavigation.addItem(gank);
        bottomNavigation.addItem(mine);

        bottomNavigation.setTranslucentNavigationEnabled(true);

        bottomNavigation.setOnTabSelectedListener((position, wasSelected) -> {
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
            return true;
        });

        viewPager.setOffscreenPageLimit(3);
        adapter = new ClassifyViewPagerAdapter(getSupportFragmentManager(), mBottomTitles);
        viewPager.setAdapter(adapter);

        currentFragment = adapter.getCurrentFragment();
    }

}
