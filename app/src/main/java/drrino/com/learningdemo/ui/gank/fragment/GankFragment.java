package drrino.com.learningdemo.ui.gank.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import butterknife.BindView;
import drrino.com.learningdemo.R;
import drrino.com.learningdemo.base.BaseFragment;
import drrino.com.learningdemo.ui.common.fragment.SimpleFragment;

/**
 * Created by Dell1 on 2017/3/22.
 */

public class GankFragment extends BaseFragment {
    @BindView(R.id.tab_layout) TabLayout tabLayout;
    @BindView(R.id.view_pager) ViewPager viewPager;

    private String[] mTitles;


    @Override protected int getLayoutId() {
        return R.layout.fragment_gank_page;
    }


    @Override protected void initData() {
        mTitles = getResources().getStringArray(R.array.gank_classify);
        viewPager.setOffscreenPageLimit(mTitles.length);
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override public Fragment getItem(int position) {
                if (position == 1) {
                    return new SimpleFragment();
                } else if (position == 2) {
                    return new SimpleFragment();
                } else if (position==3){
                    return new SimpleFragment();
                }else{
                    return new GankMainFragment();
                }
            }


            @Override public int getCount() {
                return mTitles.length;
            }

            @Override public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }
}
