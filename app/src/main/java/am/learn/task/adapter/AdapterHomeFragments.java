package am.learn.task.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import am.learn.task.R;
import am.learn.task.fragment.OnlineNewsFragment;
import am.learn.task.fragment.OwnNewsFragment;


public class AdapterHomeFragments extends FragmentPagerAdapter {
    private Context context;

    private Fragment fragment;


    public AdapterHomeFragments(FragmentManager childFragmentManager, Context context) {
        super(childFragmentManager);
        this.context = context;

    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            default:
            case 0:
                fragment = OnlineNewsFragment.newInstance();
                break;
            case 1:
                fragment = OwnNewsFragment.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return context.getResources().getString(R.string.news_feed);
            case 1:
                return context.getResources().getString(R.string.my_news);

            default:
                return "";
        }
    }

    public Fragment getFragment() {
        return fragment;
    }
}
