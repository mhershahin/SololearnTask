package am.learn.task.card;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.loadingview.LoadingView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import am.learn.task.R;
import am.learn.task.database.model.News;
import am.learn.task.utils.AbstractCard;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsCard extends AbstractCard {


    @BindView(R.id.news_title)
    AppCompatTextView newsTitle;
    @BindView(R.id.news_img)
    AppCompatImageView newsImg;
    @BindView(R.id.tx_description)
    AppCompatTextView txDescription;

    @BindView(R.id.news_loader)
    LoadingView newsLoader;
    @BindView(R.id.news_loader_layout)
    LinearLayout newsLoaderLayout;

    @BindView(R.id.news_card)
    LinearLayout card;


    private Context context;


    public NewsCard(Context context, ViewGroup parent) {
        this(context, LayoutInflater.from(context).inflate(R.layout.card_news, parent, false));
        this.context = context;
    }

    private NewsCard(Context context, View view) {
        super(view, context);
        ButterKnife.bind(this, view);
    }

    @Override
    public void bind(Object data) {
        News news = (News) data;

        newsTitle.setText(news.getSectionName());
        txDescription.setText(news.getWebTitle().length() < 80 ?
                news.getWebTitle() :
                news.getWebTitle().substring(0, 80) + "...");

        showLoader();
        if (news.getThumbnail() != null) {
            Picasso.get()
                    .load(news.getThumbnail())
                    .resize(150, 150)
                    .centerCrop()
                    .into(newsImg, new Callback() {
                        @Override
                        public void onSuccess() {
                            stopLoader();
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });
        } else {
            newsImg.setImageResource(R.drawable.no_img);
            stopLoader();
        }

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsItemClick(getAdapterPosition(),newsImg);
            }
        });
    }


    private void showLoader() {
        newsImg.setVisibility(View.GONE);
        newsLoaderLayout.setVisibility(View.VISIBLE);
        newsLoader.start();
    }

    private void stopLoader() {
        newsLoader.stop();
        newsLoaderLayout.setVisibility(View.GONE);
        newsImg.setVisibility(View.VISIBLE);
    }
}

