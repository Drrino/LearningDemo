package drrino.com.learningdemo.util;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Dell1 on 2017/3/2.
 */

public class RxUtil {

    /**
     * 切换线程
     */
    public static <T> Observable.Transformer<T, T> rxSchedulerHelper() {
        return observable -> observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }
}
