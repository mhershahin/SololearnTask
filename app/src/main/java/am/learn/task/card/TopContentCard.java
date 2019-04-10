package am.learn.task.card;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import am.learn.task.R;
import am.learn.task.activity.DescriptionActivity;
import am.learn.task.activity.HomeActivity;
import am.learn.task.adapter.TopContentAdapter;
import am.learn.task.database.RealmManager;
import am.learn.task.database.model.News;
import am.learn.task.lisner.INewsListener;
import am.learn.task.utils.AbstractCard;
import butterknife.BindView;
import butterknife.ButterKnife;

import static am.learn.task.key.ConsKey.DESCRIPTION_STATE;
import static am.learn.task.key.ConsKey.NEWS_DATA;
import static am.learn.task.key.ConsKey.TOP;

public class TopContentCard extends AbstractCard implements INewsListener {

    @BindView(R.id.recycle_top_content)
    RecyclerView recycleTopContent;
    @BindView(R.id.top_content_card)
    CardView topContentCard;

    private Context context;
    private List<News> topNews;
    private TopContentAdapter adapter;

    public TopContentCard(Context context, ViewGroup parent) {
        this(context, LayoutInflater.from(context).inflate(R.layout.card_top_content, parent, false));
        this.context = context;
    }

    private TopContentCard(Context context, View view) {
        super(view, context);
        ButterKnife.bind(this, view);
    }

    @Override
    public void bind(Object data) {
        topNews = RealmManager.getInstance().getNewsByState(context, TOP);
        if (topNews.size() != 0) {
            topContentCard.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            adapter = new TopContentAdapter(context, topNews, this);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            recycleTopContent.setLayoutManager(layoutManager);
            recycleTopContent.setAdapter(adapter);

        } else {
            topContentCard.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
        }
    }

    @Override
    public void newsItemClick(int position, View imgView) {
        Intent intent = new Intent(context, DescriptionActivity.class);
        intent.putExtra(DESCRIPTION_STATE, TOP);
        intent.putExtra(NEWS_DATA, topNews.get(position));
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((HomeActivity) context, (View) imgView, "description");
        context.startActivity(intent, options.toBundle());
    }

    @Override
    public void newsRemove(int position) {
        RealmManager.getInstance().removeNews(context, topNews.get(position).getId(), TOP);
        topNews.remove(position);
        adapter.notifyDataSetChanged();

    }

    public void refreshRecycler() {
        topNews.clear();
        for (News news : RealmManager.getInstance().getNewsByState(context, TOP)) {
            topNews.add(news);
        }
        adapter.notifyDataSetChanged();
    }
}