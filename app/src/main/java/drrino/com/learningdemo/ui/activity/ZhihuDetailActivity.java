package drrino.com.learningdemo.ui.activity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import drrino.com.learningdemo.R;
import drrino.com.learningdemo.base.BaseActivity;
import drrino.com.learningdemo.util.HtmlUtil;
import drrino.com.learningdemo.util.RxUtil;

/**
 * Created by drrino on 2017/3/6.
 */

public class ZhihuDetailActivity extends BaseActivity {
    @BindView(R.id.iv_bar_img) ImageView ivBarImg;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tv_img_title) TextView tvImgTitle;
    @BindView(R.id.tv_img_source) TextView tvImgSource;
    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.web_view) WebView webView;

    private String id;


    @Override protected int getLayoutId() {
        return R.layout.activity_zhihu_detail_page;
    }


    @Override protected void initData() {
        id = getIntent().getStringExtra("id");
        setToolbar(toolbar, "");
        fetchDetailData();

        initWebView();
    }


    private void fetchDetailData() {
        mRetrofitHelper.fetchDetailInfo(id).compose(RxUtil.rxSchedulerHelper()).subscribe(
            zhihuDetailBean -> {
                Glide.with(mContext).load(zhihuDetailBean.getImage()).into(ivBarImg);
                collapsingToolbar.setTitle(zhihuDetailBean.getTitle());
                tvImgTitle.setText(zhihuDetailBean.getImage_source());
                String htmlData = HtmlUtil.createHtmlData(zhihuDetailBean.getBody(),
                    zhihuDetailBean.getCss(), zhihuDetailBean.getJs());
                webView.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
            });
    }


    private void initWebView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}
