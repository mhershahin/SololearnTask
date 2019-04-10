package am.learn.task.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import am.learn.task.R;
import am.learn.task.activity.DescriptionActivity;
import am.learn.task.adapter.OwnNewsAdapter;
import am.learn.task.database.RealmManager;
import am.learn.task.database.model.News;
import am.learn.task.lisner.INewsListener;
import am.learn.task.utils.SpacesItemDecoration;
import butterknife.BindView;
import butterknife.ButterKnife;

import static am.learn.task.key.ConsKey.DESCRIPTION_STATE;
import static am.learn.task.key.ConsKey.NEWS_DATA;
import static am.learn.task.key.ConsKey.SAVE;


public class OwnNewsFragment extends Fragment implements INewsListener {

    @BindView(R.id.own_news_recycle)
    RecyclerView ownNewsRecycle;

    public static OwnNewsFragment newInstance() {
        final OwnNewsFragment fragment = new OwnNewsFragment();
        return fragment;
    }

    private Context context;
    private OwnNewsAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<News> news;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.context = context;
        }
    }
    @Override
    public void onStart() {
     createRecycler();
        super.onStart();
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_own_news, container, false);
        ButterKnife.bind(this, view);
        layoutManager = new LinearLayoutManager(inflater.getContext());
        ownNewsRecycle.setLayoutManager(layoutManager);
        ownNewsRecycle.addItemDecoration(new SpacesItemDecoration(15, (int) context.getResources().getDimension(R.dimen.view_height)));
        return view;
    }


    private void createRecycler() {
        news = RealmManager.getInstance().getNewsByState(context, SAVE);
        adapter = new OwnNewsAdapter(context, news, this);
        ownNewsRecycle.setAdapter(adapter);
    }

    @Override
    public void newsItemClick(int position, View imgView) {
        Intent intent = new Intent(context, DescriptionActivity.class);
        intent.putExtra(DESCRIPTION_STATE, SAVE);
        intent.putExtra(NEWS_DATA, news.get(position));
//        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((HomeActivity) context, (View)imgView, "description");
        startActivity(intent);
    }

    @Override
    public void newsRemove(int position) {
        RealmManager.getInstance().removeNews(context,news.get(position).getId(),SAVE);
        news.remove(position);
        adapter.notifyDataSetChanged();

    }
}
