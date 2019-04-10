package am.learn.task.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import am.learn.task.card.NewsCard;
import am.learn.task.card.NoInternetCard;
import am.learn.task.card.TopContentCard;
import am.learn.task.database.model.News;
import am.learn.task.lisner.INewsListener;
import am.learn.task.lisner.IReloadListener;
import am.learn.task.server.model.Result;
import am.learn.task.utils.AbstractCard;

import static am.learn.task.key.ConsKey.NEWS_CARD;
import static am.learn.task.key.ConsKey.NO_INTERNET_CARD;
import static am.learn.task.key.ConsKey.TOP_CONTENT_CARD;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<News> newsResults;

    private INewsListener iNewsListener;
    private IReloadListener iReloadListener;

    public NewsAdapter(Context context, List<News> newsResults, INewsListener iNewsListener, IReloadListener iReloadListener) {
        this.context = context;
        this.newsResults = newsResults;
        this.iNewsListener = iNewsListener;
        this.iReloadListener = iReloadListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        AbstractCard card;
        switch (viewType) {
            case NEWS_CARD:
                card = new NewsCard(context, parent);
                break;
            case TOP_CONTENT_CARD:
                card = new TopContentCard(context, parent);
                break;
            case NO_INTERNET_CARD:
                card = new NoInternetCard(context, parent);
                break;
            default:
                card = new NewsCard(context, parent);
                break;
        }
        return card;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AbstractCard card = (AbstractCard) holder;
        News data = newsResults.get(position);
        switch (newsResults.get(position).getViewType()) {
            case NEWS_CARD:
                card.setINewsListener(iNewsListener);
                card.bind(data);
                break;
            case TOP_CONTENT_CARD:
                card.bind(data);
                break;
            case NO_INTERNET_CARD:
               card.setIReloadListener(iReloadListener);
                card.bind(data);
                break;

        }
    }


    @Override
    public int getItemViewType(int position) {
        return newsResults.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return newsResults.size();
    }


}
