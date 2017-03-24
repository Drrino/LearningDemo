package drrino.com.learningdemo.ui.zhihu.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import drrino.com.learningdemo.R;
import drrino.com.learningdemo.base.BaseActivity;
import drrino.com.learningdemo.ui.common.CheckImageActivity;
import drrino.com.learningdemo.util.HtmlUtil;
import drrino.com.learningdemo.util.RxUtil;
import drrino.com.learningdemo.util.SnackbarUtil;
import drrino.com.learningdemo.util.SystemUtil;

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
    private String barImg;
    private String url;


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
                barImg = zhihuDetailBean.getImage();
                url = zhihuDetailBean.getShare_url();
                Glide.with(mContext).load(barImg).centerCrop().into(ivBarImg);
                tvImgTitle.setText(zhihuDetailBean.getTitle());
                tvImgSource.setText(zhihuDetailBean.getImage_source());
                String htmlData = HtmlUtil.createHtmlData(zhihuDetailBean.getBody(),
                    zhihuDetailBean.getCss(), zhihuDetailBean.getJs());
                webView.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
            }, throwable -> SnackbarUtil.showShort(getWindow().getDecorView(), "加载错误"));
    }


    private void initWebView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        //载入js
        webView.addJavascriptInterface(new JavascriptInterface(mContext), "imageListener");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }


            @Override public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                addImageClickListener(webView);
            }
        });
    }


    // 注入js函数监听
    public void addImageClickListener(WebView mWebView) {
        // 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        mWebView.loadUrl("javascript:(function(){" +
            "var objs = document.getElementsByTagName(\"img\"); " +
            "for(var i=0;i<objs.length;i++)  " +
            "{"
            + "    objs[i].onclick=function()  " +
            "    {  "
            + "        window.imageListener.showImage(this.src);  " +
            "    }  " +
            "}" +
            "})()");
    }


    // js通信接口
    public class JavascriptInterface {
        private Context context;


        JavascriptInterface(Context context) {
            this.context = context;
        }


        @android.webkit.JavascriptInterface
        public void showImage(String img) {
            Intent intent = new Intent(mContext, CheckImageActivity.class);
            intent.putExtra("url", img);
            startActivity(intent);
        }
    }


    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_copy, menu);
        return true;
    }


    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.copy:
                SystemUtil.copyToClipBoard(mContext, url);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.iv_bar_img)
    protected void clickBarImg() {
        Intent intent = new Intent(this, CheckImageActivity.class);
        intent.putExtra("url", barImg);
        startActivity(intent);
    }
}
