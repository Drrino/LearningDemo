package drrino.com.learningdemo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import drrino.com.learningdemo.model.http.RetrofitHelper;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Dell1 on 2017/3/2.
 */

public abstract class BaseFragment extends SupportFragment {
    protected Context mContext;
    private Unbinder mUnBinder;
    private boolean isInited = false;
    public RetrofitHelper mRetrofitHelper = new RetrofitHelper();


    @Override public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }


    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), null);
    }


    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
        if (savedInstanceState == null) {
            if (!isHidden()) {
                isInited = true;
                initData();
            }
        } else {
            if (isSupportVisible()) {
                isInited = true;
                initData();
            }
        }
    }


    @Override public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isInited && !hidden) {
            isInited = true;
            initData();
        }
    }


    @Override public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }


    protected abstract int getLayoutId();

    protected abstract void initData();
}
