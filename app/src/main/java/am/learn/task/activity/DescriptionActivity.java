package am.learn.task.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.github.loadingview.LoadingView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import am.learn.task.R;
import am.learn.task.database.RealmManager;
import am.learn.task.database.model.News;
import butterknife.BindView;
import butterknife.ButterKnife;

import static am.learn.task.key.ConsKey.DESCRIPTION_STATE;
import static am.learn.task.key.ConsKey.NEWS_DATA;
import static am.learn.task.key.ConsKey.ONLINE;
import static am.learn.task.key.ConsKey.SAVE;
import static am.learn.task.key.ConsKey.TOP;

public class DescriptionActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.description_title)
    AppCompatTextView descriptionTitle;
    @BindView(R.id.description_img)
    AppCompatImageView descriptionImg;
    @BindView(R.id.description_web_title)
    AppCompatTextView descriptionWebTitle;
    @BindView(R.id.description_news)
    AppCompatTextView descriptionNews;
    @BindView(R.id.home_appBar)
    AppBarLayout homeAppBar;
    @BindView(R.id.description_loader)
    LoadingView descriptionLoader;
    @BindView(R.id.description_loader_layout)
    LinearLayout descriptionLoaderLayout;
    @BindView(R.id.liner_add_pin)
    LinearLayout linerAddPin;
    @BindView(R.id.liner_save)
    LinearLayout linerSave;

    @BindView(R.id.scrolling_view)
    ScrollView scrollingView;

    private News news;
    private int state;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        ButterKnife.bind(this);
        linerAddPin.setOnClickListener(this);
        linerSave.setOnClickListener(this);
        state = (int) getIntent().getExtras().getInt(DESCRIPTION_STATE);
        news = (News) getIntent().getExtras().getParcelable(NEWS_DATA);

        linerAddPin.setVisibility(RealmManager.getInstance().isNews(this, news.getId(), TOP) ? View.GONE : View.VISIBLE);
        linerSave.setVisibility(RealmManager.getInstance().isNews(this, news.getId(), SAVE) ? View.GONE : View.VISIBLE);

        descriptionTitle.setText(news.getSectionName());
        descriptionWebTitle.setText(news.getWebTitle());
        descriptionNews.setText(news.getBodyText());

        if (state == ONLINE || state == TOP) {
            setOnlinePhoto();
        } else {
            setOffLinePhoto();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.liner_add_pin:
                linerAddPin.setVisibility(View.GONE);
                news.setState(TOP);
                RealmManager.getInstance().addNews(this, news);
                break;
            case R.id.liner_save:
                linerSave.setVisibility(View.GONE);
                news.setState(SAVE);
                initBitmapArray(news.getThumbnail());
                state = 2;
                break;
        }
    }

    private void setOnlinePhoto() {
        showLoader();
        if (news.getThumbnail() != null) {
            Picasso.get()
                    .load(news.getThumbnail())
                    .resize(300, 300)
                    .centerCrop()
                    .into(descriptionImg, new Callback() {
                        @Override
                        public void onSuccess() {
                            stopLoader();
                        }

                        @Override
                        public void onError(Exception e) {
                        }
                    });
        } else {
            descriptionImg.setImageResource(R.drawable.no_img);
            stopLoader();
        }

    }

    private void setOffLinePhoto() {
        stopLoader();
        ByteArrayInputStream is = new ByteArrayInputStream(news.getNewsImg());
        Drawable drw = Drawable.createFromStream(is, "articleImage");
        if (drw != null) {
            descriptionImg.setImageDrawable(drw);
        } else {
            descriptionImg.setImageResource(R.drawable.no_img);
        }
    }

    public void initBitmapArray(String src) {

        Picasso.get()
                .load(src)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        saveNews(stream.toByteArray());

                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });

    }

    private void saveNews(byte[] toByteArray) {
        news.setNewsImg(toByteArray);
        RealmManager.getInstance().addNews(this, news);
    }

    private void showLoader() {
        descriptionImg.setVisibility(View.GONE);
        descriptionLoaderLayout.setVisibility(View.VISIBLE);
        descriptionLoader.start();
    }

    private void stopLoader() {
        descriptionLoader.stop();
        descriptionLoaderLayout.setVisibility(View.GONE);
        descriptionImg.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        Log.e("scrollingView", scrollingView.getScrollY() + "");
        if (scrollingView.getScrollY() != 0) {
            scrollingView.fullScroll(ScrollView.FOCUS_UP);
        } else {
            super.onBackPressed();
        }
    }
}
