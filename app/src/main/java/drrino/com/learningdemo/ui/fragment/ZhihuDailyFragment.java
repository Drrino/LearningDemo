package drrino.com.learningdemo.ui.fragment;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.Toast;
import butterknife.BindView;
import drrino.com.learningdemo.R;
import drrino.com.learningdemo.base.BaseFragment;
import drrino.com.learningdemo.model.bean.DailyListBean;
import drrino.com.learningdemo.model.db.RealmHelper;
import drrino.com.learningdemo.model.http.RetrofitHelper;
import drrino.com.learningdemo.ui.adapter.ZhihuAdapter;
import java.util.List;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Dell1 on 2017/3/1.
 */

public class ZhihuDailyFragment extends BaseFragment {

    @BindView(R.id.rv_daily) RecyclerView rvDaily;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.fragment_container) FrameLayout fragmentContainer;


    private ZhihuAdapter mAdapter;
    private RealmHelper realmHelper;
    private LinearLayoutManager linearLayoutManager;
    private String time;


    @Override protected int getLayoutId() {
        return R.layout.fragment_zhihu_daily;
    }


    @Override protected void initData() {
        realmHelper = new RealmHelper();
        initView();
        showContent();
        bindListener();
    }


    private void initView() {
        swipeRefresh.setRefreshing(true);

        linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,
            false);
        rvDaily.setLayoutManager(linearLayoutManager);

    }


    private void showContent() {
        mRetrofitHelper.fetchDailyListInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(dailyListBean -> {
            mAdapter = new ZhihuAdapter(mContext, dailyListBean);
            List<DailyListBean.StoriesBean> list = dailyListBean.getStories();
            for(DailyListBean.StoriesBean item : list) {
                item.setReadState(realmHelper.queryNewsId(item.getId()));
            }
            rvDaily.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            new Handler().postDelayed(() -> swipeRefresh.setRefreshing(false), 1500);
            time = dailyListBean.getDate();
        });
    }


    private void showMoreContent() {
        mRetrofitHelper.fetchDailyBeforeListInfo(time).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                dailyBeforeListBean -> {
                    List<DailyListBean.StoriesBean> list = dailyBeforeListBean.getStories();
                    for(DailyListBean.StoriesBean item : list) {
                        item.setReadState(realmHelper.queryNewsId(item.getId()));
                    }
                    mAdapter.appendList(dailyBeforeListBean);
                    mAdapter.notifyDataSetChanged();
                    time = dailyBeforeListBean.getDate();
                }, throwable -> Toast.makeText(mContext, "网络错误", Toast.LENGTH_SHORT).show());
    }


    private void bindListener() {
        swipeRefresh.setOnRefreshListener(() -> showContent());
        rvDaily.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    boolean isBottom = linearLayoutManager.findLastVisibleItemPosition()
                        >= mAdapter.getItemCount() - 3;
                    if (!swipeRefresh.isRefreshing() && isBottom) {
                        new Handler().postDelayed(() -> showMoreContent(), 1000);
                    }
                }
            }
        });
    }


    public void refresh() {
        if (getArguments().getInt("index", 0) > 0 && rvDaily != null) {
            rvDaily.smoothScrollToPosition(0);
        }
    }


    public void willBeDisplayed() {
        if (fragmentContainer != null) {
            Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
            fragmentContainer.startAnimation(fadeIn);
        }
    }


    public void willBeHidden() {
        if (fragmentContainer != null) {
            Animation fadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
            fragmentContainer.startAnimation(fadeOut);
        }
    }
}
