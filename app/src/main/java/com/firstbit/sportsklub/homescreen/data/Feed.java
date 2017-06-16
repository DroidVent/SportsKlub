package com.firstbit.sportsklub.homescreen.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hp-pc on 24-05-2017.
 */

public class Feed {
    @SerializedName("ID")
    private String id;

    @SerializedName("Name")
    private String name;

    @SerializedName("ImageName")
    private String image;

    @SerializedName("LiveUrl")
    private String postUrl;

    @SerializedName("Dated")
    private String date;

    @SerializedName("DateStr")
    private String formattedDate;

    @SerializedName("CategoryID")
    private String categoryId;

    @SerializedName("CategoryName")
    private String categoryName;

    @SerializedName("UpdateUrl")
    private String updateUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }
}
