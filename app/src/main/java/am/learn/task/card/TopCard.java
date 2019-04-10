package am.learn.task.card;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
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

public class TopCard extends AbstractCard implements View.OnClickListener {

    @BindView(R.id.top_title)
    AppCompatTextView topTitle;
    @BindView(R.id.top_web_title)
    AppCompatTextView topWebTitle;
    @BindView(R.id.top_loader)
    LoadingView topLoader;
    @BindView(R.id.top_loader_layout)
    LinearLayout topLoaderLayout;
    @BindView(R.id.top_img)
    AppCompatImageView topImg;

    @BindView(R.id.top_delete_liner)
    LinearLayout deleteLiner;
    @BindView(R.id.top_card)
    CardView topCard;


    private Context context;

    public TopCard(Context context, ViewGroup parent) {
        this(context, LayoutInflater.from(context).inflate(R.layout.card_top, parent, false));
        this.context = context;
    }

    private TopCard(Context context, View view) {
        super(view, context);
        ButterKnife.bind(this, view);
    }

    @Override
    public void bind(Object data) {
        News news = (News) data;
        topCard.setOnClickListener(this);
        deleteLiner.setOnClickListener(this);

        topTitle.setText(news.getSectionName());
        topWebTitle.setText(news.getWebTitle().length() < 25 ?
                news.getWebTitle() :
                news.getWebTitle().substring(0, 25) + "...");

        showLoader();
        if (news.getThumbnail()!=null) {
            Picasso.get()
                    .load(news.getThumbnail())
                    .resize(100, 100)
                    .centerCrop()
                    .into(topImg, new Callback() {
                        @Override
                        public void onSuccess() {
                            stopLoader();
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });
        } else {
            topImg.setImageResource(R.drawable.no_img);
            stopLoader();
        }

    }


    private void showLoader() {
        topImg.setVisibility(View.GONE);
        topLoaderLayout.setVisibility(View.VISIBLE);
        topLoader.start();
    }

    private void stopLoader() {
        topLoader.stop();
        topLoaderLayout.setVisibility(View.GONE);
        topImg.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.top_card:
                newsItemClick(getAdapterPosition(),topImg);
            break;
            case R.id.top_delete_liner:
                newsRemove(getAdapterPosition());
                break;
        }

    }
}

