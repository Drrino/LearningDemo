package drrino.com.learningdemo.ui.zhihu.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import drrino.com.learningdemo.R;
import drrino.com.learningdemo.model.bean.DailyListBean;
import drrino.com.learningdemo.model.bean.ZhihuItem;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Dell1 on 2017/2/20.
 */

public class ZhihuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    private Context mContext;
    private DailyListBean dailyListBean;
    private TopAdapter mAdapter;
    private int mCurrentPage;
    private List<ZhihuItem> mItems = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    private static final int TYPE_TOP = 0;
    private static final int TYPE_DATE = 1;
    private static final int TYPE_ITEM = 2;


    public ZhihuAdapter(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }


    public void setList(DailyListBean dailyStories) {
        this.dailyListBean = dailyStories;
        mItems.clear();
        appendList(dailyStories);
    }


    public void appendList(DailyListBean dailyStories) {
        int positionStart = mItems.size();

        if (positionStart == 0) {
            ZhihuItem header = new ZhihuItem();
            header.setType(TYPE_TOP);
            header.setTop_stories(dailyStories.getTop_stories());
            mItems.add(header);
        }
        ZhihuItem dateItem = new ZhihuItem();
        dateItem.setType(TYPE_DATE);
        dateItem.setDate(dailyStories.getDate());
        mItems.add(dateItem);

        List<DailyListBean.StoriesBean> stories = dailyStories.getStories();
        for (DailyListBean.StoriesBean story : stories) {
            ZhihuItem storyItem = new ZhihuItem();
            storyItem.setType(TYPE_ITEM);
            storyItem.setStories(story);
            mItems.add(storyItem);
        }

        int itemCount = mItems.size() - positionStart;

        if (positionStart == 0) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeChanged(positionStart, itemCount);
        }
    }


    @Override public int getItemViewType(int position) {
        return mItems.get(position).getType();
    }


    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_TOP) {
            mAdapter = new TopAdapter(mContext, mItems.get(0).getTop_stories());
            return new TopViewHolder(inflater.inflate(R.layout.zhihu_item_top, parent, false));
        } else if (viewType == TYPE_DATE) {
            return new DateViewHolder(inflater.inflate(R.layout.zhihu_item_date, parent, false));
        } else if (viewType == TYPE_ITEM) {
            return new ContentViewHolder(
                inflater.inflate(R.layout.zhihu_item_daily, parent, false));
        } else {
            return null;
        }
    }


    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TopViewHolder) {
            mCurrentPage = 1;
            ((TopViewHolder) holder).mViewpager.setAdapter(mAdapter);
            ((TopViewHolder) holder).mViewpager.setCurrentItem(mCurrentPage);
            Observable.interval(5, 5, TimeUnit.SECONDS)  // 5s的延迟，5s的循环时间
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    // 进行轮播操作
                    mCurrentPage++;
                    ((TopViewHolder) holder).mViewpager.setCurrentItem(mCurrentPage);
                });
            ((TopViewHolder) holder).mViewpager.addOnPageChangeListener(
                new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }


                    @Override public void onPageSelected(int position) {

                    }


                    @Override public void onPageScrollStateChanged(int state) {
                        if (state == ViewPager.SCROLL_STATE_IDLE) {
                            if (((TopViewHolder) holder).mViewpager.getCurrentItem() == 0) {
                                ((TopViewHolder) holder).mViewpager.setCurrentItem(
                                    dailyListBean.getTop_stories().size(), false);
                            } else if (((TopViewHolder) holder).mViewpager.getCurrentItem() ==
                                dailyListBean.getTop_stories().size() + 1) {
                                ((TopViewHolder) holder).mViewpager.setCurrentItem(1, false);
                            }
                            mCurrentPage = ((TopViewHolder) holder).mViewpager.getCurrentItem();
                        }
                    }
                });
        } else if (holder instanceof DateViewHolder) {
            ((DateViewHolder) holder).tvDate.setText(mItems.get(position).getDate());
        } else if (holder instanceof ContentViewHolder) {
            ((ContentViewHolder) holder).title.setText(
                mItems.get(position).getStories().getTitle());
            if (mItems.get(position).getStories().getReadState()) {
                ((ContentViewHolder) holder).title.setTextColor(
                    ContextCompat.getColor(mContext, R.color.news_read));
            } else {
                ((ContentViewHolder) holder).title.setTextColor(
                    ContextCompat.getColor(mContext, R.color.news_unread));
            }
            Glide.with(mContext)
                .load(mItems.get(position).getStories().getImages().get(0))
                .centerCrop()
                .into(((ContentViewHolder) holder).image);
            ((ContentViewHolder) holder).dailyItem.setOnClickListener(
                view -> onItemClickListener.onItemClick(position, view,mItems.get(position).getStories()));
        }
    }


    @Override public int getItemCount() {
        return mItems.size();
    }


    static class ContentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_daily_item_title)
        TextView title;
        @BindView(R.id.iv_daily_item_image)
        ImageView image;
        @BindView(R.id.daily_item)
        LinearLayout dailyItem;


        ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    static class DateViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_daily_date)
        TextView tvDate;


        DateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    static class TopViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.vp_top)
        ViewPager mViewpager;


        TopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener {
        void onItemClick(int position, View view, DailyListBean.StoriesBean stories);
    }
}
