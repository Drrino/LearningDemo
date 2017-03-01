package drrino.com.learningdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import butterknife.ButterKnife;
import com.roughike.bottombar.BottomBar;
import drrino.com.learningdemo.ui.ZhihuDailyFragment;

public class MainActivity extends AppCompatActivity {
    private Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
    }


    private void initView() {
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(tabId -> {
            if (tabId == R.id.tab_friends) {
                fragment = new ZhihuDailyFragment();
            } else if (tabId == R.id.tab_favorites) {
                fragment = new ZhihuDailyFragment();
            } else {
                fragment = new ZhihuDailyFragment();
            }
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentContainer, fragment)
                .commit();
        });
        bottomBar.setOnTabReselectListener(tabId -> {
            if (tabId == R.id.tab_friends) {
                Toast.makeText(MainActivity.this, "aaaaa", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
