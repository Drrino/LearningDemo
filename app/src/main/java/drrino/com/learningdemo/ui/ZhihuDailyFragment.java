package drrino.com.learningdemo.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import drrino.com.learningdemo.R;
import drrino.com.learningdemo.http.RetrofitHelper;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Dell1 on 2017/3/1.
 */

public class ZhihuDailyFragment extends Fragment {

    @BindView(R.id.rv_daily) RecyclerView rvDaily;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.fragment_container) FrameLayout fragmentContainer;

    private RetrofitHelper mRetrofitHelper;
    private ZhihuAdapter mAdapter;
    private Context mContext;
    private LinearLayoutManager linearLayoutManager;
    private String time;


    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhihu_daily, null);
        ButterKnife.bind(this, view);
        mContext = getActivity();
        return view;
    }


    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRetrofitHelper = new RetrofitHelper();
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


    /**
     * Refresh
     */
    public void refresh() {
        if (getArguments().getInt("index", 0) > 0 && rvDaily != null) {
            rvDaily.smoothScrollToPosition(0);
        }
    }


    /**
     * Called when a fragment will be displayed
     */
    public void willBeDisplayed() {
        // Do what you want here, for example animate the content
        if (fragmentContainer != null) {
            Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
            fragmentContainer.startAnimation(fadeIn);
        }
    }


    /**
     * Called when a fragment will be hidden
     */
    public void willBeHidden() {
        if (fragmentContainer != null) {
            Animation fadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
            fragmentContainer.startAnimation(fadeOut);
        }
    }
}
