package drrino.com.learningdemo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import drrino.com.learningdemo.App;
import drrino.com.learningdemo.model.http.RetrofitHelper;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

/**
 * Created by Dell1 on 2017/3/2.
 */

public abstract class BaseActivity extends SwipeBackActivity {
    public Context mContext;
    public Unbinder mUnBinder;
    public RetrofitHelper mRetrofitHelper = new RetrofitHelper();


    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mUnBinder = ButterKnife.bind(this);
        mContext = this;
        App.getInstance().addActivity(this);

        initData();
    }


    protected void setToolbar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(view -> onBackPressedSupport());
    }


    @Override protected void onDestroy() {
        super.onDestroy();
        App.getInstance().removeActivity(this);
        mUnBinder.unbind();
    }


    protected abstract int getLayoutId();
    protected abstract void initData();

}
