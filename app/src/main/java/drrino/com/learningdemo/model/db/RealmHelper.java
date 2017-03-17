package drrino.com.learningdemo.model.db;

import android.content.Context;
import drrino.com.learningdemo.model.bean.ReadStateBean;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Dell1 on 2017/3/10.
 */

public class RealmHelper {
    private static final String DB_NAME = "drrino.learning.realm";

    private Realm mRealm;


    public RealmHelper(Context mContext) {
        Realm.init(mContext);
        mRealm = Realm.getDefaultInstance();
    }


    /**
     * 增加 阅读记录
     *
     * @param id 使用@PrimaryKey注解后copyToRealm需要替换为copyToRealmOrUpdate
     */
    public void insertNewsId(String id) {
        mRealm.beginTransaction();
        ReadStateBean bean = new ReadStateBean();
        bean.setId(id);
        mRealm.copyToRealmOrUpdate(bean);
        mRealm.commitTransaction();
    }


    /**
     * 查询 阅读记录
     */
    public boolean queryNewsId(String id) {
        RealmResults<ReadStateBean> results = mRealm.where(ReadStateBean.class).findAll();
        for (ReadStateBean item : results) {
            return item.getId() == id;
        }
        return false;
    }
}
