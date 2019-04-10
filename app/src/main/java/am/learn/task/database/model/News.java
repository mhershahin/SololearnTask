package am.learn.task.database.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

import static am.learn.task.key.ConsKey.NEWS_CARD;


public class News extends RealmObject  implements Parcelable {

    private String id;
    private String sectionId;
    private String sectionName;
    private String webTitle;
    private String thumbnail;
    private String bodyText;
    private byte[] newsImg;
    private Integer state=0;

    public News() {
    }


    protected News(Parcel in) {
        id = in.readString();
        sectionId = in.readString();
        sectionName = in.readString();
        webTitle = in.readString();
        thumbnail = in.readString();
        bodyText = in.readString();
        newsImg = in.createByteArray();
        if (in.readByte() == 0) {
            state = null;
        } else {
            state = in.readInt();
        }
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }


    public byte[] getNewsImg() {
        return newsImg;
    }

    public void setNewsImg(byte[] newsImg) {
        this.newsImg = newsImg;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(sectionId);
        dest.writeString(sectionName);
        dest.writeString(webTitle);
        dest.writeString(thumbnail);
        dest.writeString(bodyText);
        dest.writeByteArray(newsImg);
        if (state == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(state);
        }
    }

    private int viewType = NEWS_CARD;

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

}


