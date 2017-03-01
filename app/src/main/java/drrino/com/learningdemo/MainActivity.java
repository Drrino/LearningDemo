package drrino.com.learningdemo;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import drrino.com.learningdemo.bean.DailyListBean;
import drrino.com.learningdemo.http.RetrofitHelper;
import drrino.com.learningdemo.ui.ZhihuAdapter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_daily) RecyclerView rvDaily;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeRefresh;
    private RetrofitHelper mRetrofitHelper;
    private ZhihuAdapter mAdapter;
    private Context mContext;
    private LinearLayoutManager linearLayoutManager;
    private DailyListBean listBean;
    private String time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;
        mRetrofitHelper = new RetrofitHelper();
        initView();
        showContent();
        //showMoreContent();
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
            listBean = dailyListBean;
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
}
