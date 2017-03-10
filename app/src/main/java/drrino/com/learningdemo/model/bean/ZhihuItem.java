package drrino.com.learningdemo.model.bean;

import java.util.List;

/**
 * Created by Dell1 on 2017/3/1.
 */

public class ZhihuItem {
    private int type;
    private String date;
    private DailyListBean.StoriesBean stories;
    private List<DailyListBean.TopStoriesBean> top_stories;


    public int getType() {
        return type;
    }


    public void setType(int type) {
        this.type = type;
    }


    public String getDate() {
        return date;
    }


    public void setDate(String date) {
        this.date = date;
    }


    public DailyListBean.StoriesBean getStories() {
        return stories;
    }


    public void setStories(DailyListBean.StoriesBean stories) {
        this.stories = stories;
    }


    public List<DailyListBean.TopStoriesBean> getTop_stories() {
        return top_stories;
    }


    public void setTop_stories(List<DailyListBean.TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }
}
