package am.learn.task.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import am.learn.task.R;
import am.learn.task.activity.DescriptionActivity;
import am.learn.task.activity.HomeActivity;
import am.learn.task.adapter.NewsAdapter;
import am.learn.task.card.TopContentCard;
import am.learn.task.database.model.News;
import am.learn.task.lisner.INewsListener;
import am.learn.task.lisner.IReloadListener;
import am.learn.task.server.ConsAPI;
import am.learn.task.server.RetrofitClient;
import am.learn.task.server.model.Data;
import am.learn.task.server.model.Result;
import am.learn.task.utils.PhoneUtil;
import am.learn.task.utils.SpacesItemDecoration;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static am.learn.task.key.ConsKey.DESCRIPTION_STATE;
import static am.learn.task.key.ConsKey.NEWS_CARD;
import static am.learn.task.key.ConsKey.NEWS_DATA;
import static am.learn.task.key.ConsKey.NO_INTERNET_CARD;
import static am.learn.task.key.ConsKey.ONLINE;
import static am.learn.task.key.ConsKey.TOP_CONTENT_CARD;


public class OnlineNewsFragment extends Fragment implements INewsListener, IReloadListener {

    @BindView(R.id.news_recycler)
    RecyclerView newsRecycler;
    private NewsAdapter adapter;
    private GridLayoutManager gridLayoutManager;

    private Context context;
    private List<News> newsResults = new ArrayList<>();

    private int currentPage = 1;
    private long totalCount;


    private boolean loading;
    private boolean canRefresh = false;


    Observer observer ;

    public static OnlineNewsFragment newInstance() {
        final OnlineNewsFragment fragment = new OnlineNewsFragment();
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.context = context;
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        observer = new Observer();
        new Thread(observer).start();
        refreshHorizontalRecycle();
    }

    private void refreshHorizontalRecycle() {
      if(newsRecycler!=null){
         if( newsRecycler.findViewHolderForAdapterPosition(0) instanceof TopContentCard){
        TopContentCard card = (TopContentCard) newsRecycler.findViewHolderForAdapterPosition(0);
        if(card!=null){
            card.refreshRecycler();
        }
         }

      }

    }


    @Override
    public void onStop() {
        observer.stop();
        super.onStop();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_online_news, container, false);
        ButterKnife.bind(this, view);
        reloadData();
        adapter = new NewsAdapter(context, newsResults, this, this);
        gridLayoutManager = new GridLayoutManager(context, 2);

        newsRecycler.setLayoutManager(gridLayoutManager);
        newsRecycler.addItemDecoration(new SpacesItemDecoration(15, (int) context.getResources().getDimension(R.dimen.view_height)));

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (adapter.getItemViewType(position)) {
                    case NEWS_CARD:
                        return 1;
                    case TOP_CONTENT_CARD:
                        return 2;
                    default:
                        return 2;
                }
            }
        });


        newsRecycler.setAdapter(adapter);
        recyclerChanged();

        return view;
    }

    private void addNoInternetCard() {
        News noInternet = new News();
        noInternet.setViewType(NO_INTERNET_CARD);
        newsResults.add(noInternet);
    }

    private void addTopContentCard() {
        News pinContent = new News();
        pinContent.setViewType(TOP_CONTENT_CARD);
        newsResults.add(pinContent);
    }


    private void request() {
        loading = true;
        Call<Data> call = RetrofitClient.getInstance().getApi().getSearch(ConsAPI.API_KEY, ConsAPI.FORMAT, ConsAPI.SHOW_FIELDS, currentPage);
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {

                if (response.body() != null) {
                    Data data = response.body();
                    currentPage = data.getResponse().getCurrentPage();
                    totalCount = data.getResponse().getTotal();
                    for (Result result : data.getResponse().getResults()) {
                        newsResults.add(createNews(result));
                    }
                    adapter.notifyDataSetChanged();
                    loading = false;

                    if(currentPage==1){
                        totalCount = data.getResponse().getTotal();
                    }
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }

    private News createNews(Result result) {
        News news = new News();
        news.setId(result.getId());
        news.setSectionId(result.getSectionId());
        news.setSectionName(result.getSectionName());
        news.setWebTitle(result.getWebTitle());
        news.setThumbnail(result.getFields().getThumbnail());
        news.setBodyText(result.getFields().getBodyText());
        return news;
    }

    private void recyclerChanged() {

        newsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    if (gridLayoutManager.findLastVisibleItemPosition() == gridLayoutManager.getItemCount() - 1) {
                        if (currentPage < totalCount) {
                            if (!loading) {
                                currentPage++;
                                loading = true;
                                request();
                            }
                        }
                    }


                }
                if (dy < 0) {
                    tryToRefresh();
                }
            }
        });
    }

    private void isNewResults() {

        Call<Data> call = RetrofitClient.getInstance().getApi().getSearch(ConsAPI.API_KEY, ConsAPI.FORMAT, ConsAPI.SHOW_FIELDS, 1);
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (response.body() != null) {
                    Data data = response.body();
                    if (totalCount<data.getResponse().getTotal()) {
                        canRefresh = true;
                        tryToRefresh();
                    }else {
                        canRefresh=false;
                    }

                }
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                reloadData();
                Log.e("Error", t.getMessage());
            }
        });
    }

    private void tryToRefresh() {
        if (gridLayoutManager.findLastVisibleItemPosition() > 0 && gridLayoutManager.findLastVisibleItemPosition() < 6) {
            if (canRefresh) {
                if (!loading) {
                    newsResults.clear();
                    addTopContentCard();
                    currentPage = 1;
                    loading = true;
                    request();
                    canRefresh = false;
                }
            }
        }
    }

    @Override
    public void newsItemClick(int position, View imgView) {
        Intent intent = new Intent(context, DescriptionActivity.class);
        intent.putExtra(DESCRIPTION_STATE, ONLINE);
        intent.putExtra(NEWS_DATA, newsResults.get(position));
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((HomeActivity) context, (View) imgView, "description");
        startActivity(intent, options.toBundle());
    }

    @Override
    public void newsRemove(int position) {
        ///do nothing
    }

    @Override
    public void reloadData() {
        newsResults.clear();
        currentPage=1;
        if (PhoneUtil.getInstance().isInternetAvailable(context)) {
            addTopContentCard();
            request();
        } else {
            addNoInternetCard();
        }
    }


    private class Observer implements Runnable {
        private AtomicBoolean stop = new AtomicBoolean(false);

        public void stop() {
            stop.set(true);
        }

        @Override
        public void run() {
            while (!stop.get()) {
                isNewResults();
                try {
                    Thread.sleep(30000);
                } catch (Exception ignored) {
                }
            }
        }
    }
}
