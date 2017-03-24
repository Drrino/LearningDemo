package drrino.com.learningdemo.ui.common;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.widget.ImageView;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import drrino.com.learningdemo.R;
import drrino.com.learningdemo.base.BaseActivity;
import drrino.com.learningdemo.ui.MainActivity;
import drrino.com.learningdemo.util.RxUtil;
import drrino.com.learningdemo.widget.RotateTextView;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Dell1 on 2017/3/2.
 */

public class SplashActivity extends BaseActivity {
    @BindView(R.id.splash_bg) ImageView splashBg;
    @BindView(R.id.splash_author) RotateTextView splashAuthor;

    private static final String RES = "1080*1776";


    @Override protected int getLayoutId() {
        return R.layout.activity_splash_page;
    }


    @Override protected void initData() {
        AssetManager mgr = getAssets();//得到AssetManager
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/bickley_script.otf");//根据路径得到Typeface
        splashAuthor.setTypeface(tf);//设置字体

        mRetrofitHelper.fetchWelcomeInfo(RES).compose(RxUtil.rxSchedulerHelper()).subscribe(
            welcomeBean -> {
                Glide.with(mContext).load(welcomeBean.getImg()).into(splashBg);
                splashBg.animate().scaleX(1.10f).scaleY(1.10f).setDuration(2000).start();
                showDelayJump();
            }, throwable -> showDelayJump());
    }


    private void showDelayJump() {
        Observable.timer(2, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(aLong -> {
            startActivity(new Intent(mContext, MainActivity.class));
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }


    @Override protected void onDestroy() {
        super.onDestroy();
    }
}
