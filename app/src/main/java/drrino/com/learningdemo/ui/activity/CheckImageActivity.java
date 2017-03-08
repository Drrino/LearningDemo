package drrino.com.learningdemo.ui.activity;

import android.graphics.Bitmap;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import drrino.com.learningdemo.R;
import drrino.com.learningdemo.base.BaseActivity;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Dell1 on 2017/3/8.
 */

public class CheckImageActivity extends BaseActivity {
    @BindView(R.id.toolbar) Toolbar toolBar;
    @BindView(R.id.check_iv_detail) ImageView checkIvDetail;
    private PhotoViewAttacher attacher;
    private Bitmap bitmap;


    @Override protected int getLayoutId() {
        return R.layout.activity_check_image_page;
    }


    @Override protected void initData() {
        setToolbar(toolBar, "");
        Glide.with(mContext)
            .load(getIntent().getStringExtra("url"))
            .asBitmap()
            .into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    bitmap = resource;
                    checkIvDetail.setImageBitmap(resource);
                    attacher = new PhotoViewAttacher(checkIvDetail);
                }
            });
    }


    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picture, menu);
        return true;
    }


    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.save:
                savePicture();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void savePicture() {

    }
}
