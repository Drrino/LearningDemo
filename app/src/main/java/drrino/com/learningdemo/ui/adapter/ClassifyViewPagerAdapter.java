package drrino.com.learningdemo.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import drrino.com.learningdemo.ui.fragment.SimpleFragment;
import drrino.com.learningdemo.ui.fragment.ZhihuDailyFragment;

/**
 * Created by drrino on 2017/3/1.
 */

public class ClassifyViewPagerAdapter extends FragmentPagerAdapter {
    private Fragment currentFragment;
    private String[] mBottomTitle;


    public ClassifyViewPagerAdapter(FragmentManager fm, String[] mBottomTitle) {
        super(fm);
        this.mBottomTitle = mBottomTitle;
    }


    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new SimpleFragment();
        } else if (position == 2) {
            return new ZhihuDailyFragment();
        } else {
            return new ZhihuDailyFragment();
        }
    }


    @Override
    public int getCount() {
        return mBottomTitle.length;
    }


    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            currentFragment = ((Fragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }


    public Fragment getCurrentFragment() {
        return currentFragment;
    }
}
