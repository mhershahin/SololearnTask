package am.learn.task.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import am.learn.task.card.TopCard;
import am.learn.task.database.model.News;
import am.learn.task.lisner.INewsListener;
import am.learn.task.utils.AbstractCard;

import static am.learn.task.key.ConsKey.TOP_CARD;


public class TopContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    List<News> newsResults;
    private INewsListener iNewsListener;

    public TopContentAdapter(Context context, List<News> newsResults, INewsListener iNewsListener) {
        this.context = context;
        this.newsResults = newsResults;
        this.iNewsListener = iNewsListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new TopCard(context, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AbstractCard card = (AbstractCard) holder;
        News data = newsResults.get(position);
        card.setINewsListener(iNewsListener);
        card.bind(data);

    }


    @Override
    public int getItemViewType(int position) {
        return TOP_CARD;
    }

    @Override
    public int getItemCount() {
        return newsResults.size();
    }


}
