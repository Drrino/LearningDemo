package drrino.com.learningdemo.ui.zhihu.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import drrino.com.learningdemo.R;
import drrino.com.learningdemo.model.bean.DailyListBean;
import drrino.com.learningdemo.ui.zhihu.activity.ZhihuDetailActivity;
import java.util.List;

public class TopAdapter extends PagerAdapter {

    private List<DailyListBean.TopStoriesBean> topStoriesBeen;
    private Context mContext;


    public TopAdapter(Context mContext, List<DailyListBean.TopStoriesBean> topStoriesBeen) {
        this.mContext = mContext;
        this.topStoriesBeen = topStoriesBeen;
    }


    @Override
    public int getCount() {
        return topStoriesBeen.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext)
            .inflate(R.layout.zhihu_item_top_pager, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_top_image);
        TextView textView = (TextView) view.findViewById(R.id.tv_top_title);
        Glide.with(mContext).load(topStoriesBeen.get(position).getImage()).into(imageView);
        textView.setText(topStoriesBeen.get(position).getTitle());
        view.setOnClickListener(view1 -> {
            Intent intent = new Intent(mContext, ZhihuDetailActivity.class);
            intent.putExtra("id",topStoriesBeen.get(position).getId());
            mContext.startActivity(intent);
        });
        container.addView(view);
        return view;
    }
}