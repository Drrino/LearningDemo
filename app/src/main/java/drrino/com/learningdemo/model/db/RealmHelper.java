package drrino.com.learningdemo.model.db;

import android.content.Context;
import android.util.Log;
import drrino.com.learningdemo.model.bean.ReadStateBean;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import java.util.List;

/**
 * Created by Dell1 on 2017/3/10.
 */

public class RealmHelper {
    private Realm mRealm;
    private static final String DB_NAME = "drrino.learning.realm";


    public RealmHelper(Context context) {
        Realm.init(context);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder().name(DB_NAME)
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build();
        mRealm = Realm.getInstance(realmConfig);
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
        List<ReadStateBean> readStateBeanList = mRealm.copyFromRealm(results);
        for (int i = 0; i < readStateBeanList.size(); i++) {
            if (readStateBeanList.get(i).getId().equals(id)) {
                return true;
            }
        }
        return false;
    }


    public void close() {
        mRealm.close();
    }
}
