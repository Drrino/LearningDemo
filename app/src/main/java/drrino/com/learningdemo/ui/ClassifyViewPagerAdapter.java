package drrino.com.learningdemo.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import java.util.List;

/**
 * Created by drrino on 2017/3/1.
 */

public class ClassifyViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private Fragment currentFragment;


    public ClassifyViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }


    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return fragments.get(1);
        } else if (position == 2) {
            return fragments.get(2);
        } else {
            return fragments.get(0);
        }
    }


    @Override
    public int getCount() {
        return fragments.size();
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
