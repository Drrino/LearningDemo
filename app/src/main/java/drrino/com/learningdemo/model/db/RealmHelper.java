package drrino.com.learningdemo.model.db;

import drrino.com.learningdemo.model.bean.ReadStateBean;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Dell1 on 2017/3/10.
 */

public class RealmHelper {
    private static final String DB_NAME = "drrino.learning.realm";

    private Realm mRealm;


    public RealmHelper() {
        mRealm = Realm.getInstance(
            new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded()
                .name(DB_NAME)
                .build());
    }


    /**
     * 增加 阅读记录
     *
     * @param id 使用@PrimaryKey注解后copyToRealm需要替换为copyToRealmOrUpdate
     */
    public void insertNewsId(String id) {
        ReadStateBean bean = new ReadStateBean();
        bean.setId(id);
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(bean);
        mRealm.commitTransaction();
    }


    /**
     * 查询 阅读记录
     */
    public boolean queryNewsId(String id) {
        RealmResults<ReadStateBean> results = mRealm.where(ReadStateBean.class).findAll();
        for (ReadStateBean item : results) {
            if (item.getId() == id) {
                return true;
            }
        }
        return false;
    }
}
