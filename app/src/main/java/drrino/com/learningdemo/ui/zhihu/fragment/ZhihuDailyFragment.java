package drrino.com.learningdemo.ui.zhihu.fragment;

import android.content.Intent;
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
import drrino.com.learningdemo.ui.zhihu.activity.ZhihuDetailActivity;
import drrino.com.learningdemo.ui.zhihu.adapter.ZhihuAdapter;
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
        return R.layout.fragment_common_page;
    }


    @Override protected void initData() {
        realmHelper = new RealmHelper(mContext);
        initView();
        showContent();
        bindListener();
    }


    private void initView() {
        swipeRefresh.setRefreshing(true);

        linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,
            false);
        rvDaily.setLayoutManager(linearLayoutManager);
        mAdapter = new ZhihuAdapter(mContext);
        rvDaily.setAdapter(mAdapter);

    }


    private void showContent() {
        mRetrofitHelper.fetchDailyListInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(dailyListBean -> {
            for (int i = 0; i < dailyListBean.getStories().size(); i++) {
                dailyListBean.getStories()
                    .get(i)
                    .setReadState(
                        realmHelper.queryNewsId(dailyListBean.getStories().get(i).getId()));
            }
            mAdapter.setList(dailyListBean);
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
                    for (DailyListBean.StoriesBean item : list) {
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
        mAdapter.setOnItemClickListener((position, view, stories) -> {
            stories.setReadState(true);
            realmHelper.insertNewsId(stories.getId());
            Intent intent = new Intent(mContext, ZhihuDetailActivity.class);
            intent.putExtra("id", stories.getId());
            mContext.startActivity(intent);
            mAdapter.notifyDataSetChanged();
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


    @Override public void onDestroy() {
        super.onDestroy();
        realmHelper.close();
    }
}
