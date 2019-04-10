package am.learn.task.server.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fields {
    @SerializedName("headline")
    @Expose
    private String headline;
    @SerializedName("starRating")
    @Expose
    private String starRating;
    @SerializedName("bodyText")
    @Expose
    private String bodyText;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail=null;



    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getStarRating() {
        return starRating;
    }

    public void setStarRating(String starRating) {
        this.starRating = starRating;
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




}
