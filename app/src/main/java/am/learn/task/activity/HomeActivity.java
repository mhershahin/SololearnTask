package am.learn.task.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import am.learn.task.R;
import am.learn.task.adapter.AdapterHomeFragments;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.home_tab)
    TabLayout homeTab;
    @BindView(R.id.home_view_pager)
    ViewPager homeViewPager;

    private AdapterHomeFragments adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        adapter = new AdapterHomeFragments(getSupportFragmentManager(),this);
        homeViewPager.setAdapter(adapter);
        homeTab.setupWithViewPager(homeViewPager, true);


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("OnStarHome","OnStarHome");
    }

}
