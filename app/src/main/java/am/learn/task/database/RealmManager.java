package am.learn.task.database;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import am.learn.task.database.model.News;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

import static am.learn.task.key.ConsKey.REALM_NAME;

public class RealmManager {

    private RealmManager() {

    }

    private static RealmManager _instance;

    public static RealmManager getInstance() {
        if (_instance == null) {
            _instance = new RealmManager();
        }
        return _instance;
    }

    public void init(Context context) {
        Realm.init(context);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(REALM_NAME)
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public Realm getDefaultInstance(Context context) {
        try {
            Realm.getDefaultInstance();
        } catch (IllegalStateException e) {
            init(context);
        }
        return Realm.getDefaultInstance();
    }


    public void addNews(Context context, News news) {

        Realm realm = getDefaultInstance(context);
        final News finalNews = news;
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                News realmObject = realm.createObject(News.class);
                realmObject.setId(finalNews.getId());
                realmObject.setSectionId(finalNews.getSectionId());
                realmObject.setSectionName(finalNews.getSectionName());
                realmObject.setWebTitle(finalNews.getWebTitle());
                realmObject.setThumbnail(finalNews.getThumbnail());
                realmObject.setBodyText(finalNews.getBodyText());
                realmObject.setState(finalNews.getState());
                realmObject.setNewsImg(finalNews.getNewsImg());
            }
        });
    }

    public List<News> getNewsByState(Context context, int state) {
        Realm realm = getDefaultInstance(context);
        RealmResults<News> newsRealmResults = realm.where(News.class).equalTo("state", state).findAll();
        List<News> list = realm.copyFromRealm(newsRealmResults);
        return list;
    }

    public News getNews(Context context, int id, int state) {
        Realm realm = getDefaultInstance(context);
        return realm.where(News.class).equalTo("id", id).and().equalTo("state", state).findFirst();
    }


    public void removeNews(Context context, String id, int state) {
        Realm realm = getDefaultInstance(context);
        realm.beginTransaction();
        News news = realm.where(News.class).equalTo("id", id).and().equalTo("state", state).findFirst();
        news.deleteFromRealm();
        realm.commitTransaction();
    }

    public boolean isNews(Context context, String id, int state) {
        Realm realm = getDefaultInstance(context);
        News news = realm.where(News.class).equalTo("id", id).and().equalTo("state", state).findFirst();
        return news == null ? false : true;
    }

    public void removeAllNews(Context context) {
        Realm realm = getDefaultInstance(context);
        realm.beginTransaction();
        RealmResults<News> list = realm.where(News.class).findAll();
        list.deleteAllFromRealm();
        realm.commitTransaction();
    }


}
