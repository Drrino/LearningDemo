package drrino.com.learningdemo.model.bean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Dell1 on 2017/3/10.
 */

public class ReadStateBean extends RealmObject {
    @PrimaryKey
    private String id;


    public ReadStateBean() {}


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }
}
