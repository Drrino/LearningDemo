package drrino.com.learningdemo.ui.gank.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import butterknife.BindView;
import drrino.com.learningdemo.R;
import drrino.com.learningdemo.base.BaseFragment;

/**
 * Created by Dell1 on 2017/3/23.
 */

public class GankMainFragment extends BaseFragment {
    @BindView(R.id.rv_daily) RecyclerView rvDaily;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.fragment_container) FrameLayout fragmentContainer;


    @Override protected int getLayoutId() {
        return R.layout.fragment_common_page;
    }


    @Override protected void initData() {

    }
}
