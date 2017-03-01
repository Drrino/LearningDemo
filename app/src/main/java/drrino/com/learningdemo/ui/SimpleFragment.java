package drrino.com.learningdemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import drrino.com.learningdemo.R;

/**
 * Created by drrino on 2017/3/1.
 */

public class SimpleFragment extends Fragment {
    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_simple, null);
    }
}
