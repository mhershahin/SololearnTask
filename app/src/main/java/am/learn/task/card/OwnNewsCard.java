package am.learn.task.card;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.loadingview.LoadingView;

import am.learn.task.R;
import am.learn.task.database.model.News;
import am.learn.task.utils.AbstractCard;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OwnNewsCard extends AbstractCard implements View.OnClickListener {
    @BindView(R.id.own_news_loader)
    LoadingView ownLoader;
    @BindView(R.id.own_news_loader_layout)
    LinearLayout ownNewsLoaderLayout;
    @BindView(R.id.own_news_img)
    AppCompatImageView ownNewsImg;
    @BindView(R.id.own_news_section)
    AppCompatTextView ownNewsSection;
    @BindView(R.id.own_news_web_title)
    AppCompatTextView ownNewsWebTitle;

    @BindView(R.id.own_delete_liner)
    LinearLayout ownDeleteLiner;
    @BindView(R.id.own_news_card)
    CardView ownNewsCard;
    private Context context;

    public OwnNewsCard(Context context, ViewGroup parent) {
        this(context, LayoutInflater.from(context).inflate(R.layout.card_own_news, parent, false));
        this.context = context;
    }

    private OwnNewsCard(Context context, View view) {
        super(view, context);
        ButterKnife.bind(this, view);
    }

    @Override
    public void bind(Object data) {
        News news = (News) data;
        ownNewsCard.setOnClickListener(this);
        ownDeleteLiner.setOnClickListener(this);


        ownNewsSection.setText(news.getSectionName());
        ownNewsWebTitle.setText(news.getWebTitle().length() < 100 ?
                news.getWebTitle() :
                news.getWebTitle().substring(0, 100) + "...");
        setImg(news);
    }

    private void setImg(News news) {
        Bitmap bmp = BitmapFactory.decodeByteArray(news.getNewsImg(), 0, news.getNewsImg().length);
        if (bmp != null) {
            ownNewsImg.setImageBitmap(bmp);
        } else {
            ownNewsImg.setImageResource(R.drawable.no_img);
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.own_news_card:
                newsItemClick(getAdapterPosition(), ownNewsImg);
                break;
            case R.id.own_delete_liner:
                newsRemove(getAdapterPosition());
                break;
        }

    }
}