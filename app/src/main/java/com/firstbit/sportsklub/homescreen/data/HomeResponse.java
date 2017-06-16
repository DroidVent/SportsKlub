package com.firstbit.sportsklub.homescreen.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hp-pc on 20-05-2017.
 */

public class HomeResponse implements Parcelable{
    private String thumbnail;
    private String postTitle;
    private String postDate;
    private String postUrl;
    private String categoryName;
    public HomeResponse()
    {

    }
    protected HomeResponse(Parcel in) {
        thumbnail = in.readString();
        postTitle = in.readString();
        postDate = in.readString();
        postUrl = in.readString();
        categoryName = in.readString();
    }

    public static final Creator<HomeResponse> CREATOR = new Creator<HomeResponse>() {
        @Override
        public HomeResponse createFromParcel(Parcel in) {
            return new HomeResponse(in);
        }

        @Override
        public HomeResponse[] newArray(int size) {
            return new HomeResponse[size];
        }
    };

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(thumbnail);
        parcel.writeString(postTitle);
        parcel.writeString(postDate);
        parcel.writeString(postUrl);
        parcel.writeString(categoryName);
    }
}
