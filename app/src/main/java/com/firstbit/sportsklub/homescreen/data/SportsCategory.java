package com.firstbit.sportsklub.homescreen.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hp-pc on 23-05-2017.
 */

public class SportsCategory implements Parcelable{
    @SerializedName("ID")
    private int id;

    @SerializedName("Name")
    private String Name;

    protected SportsCategory(Parcel in) {
        id = in.readInt();
        Name = in.readString();
    }
    public SportsCategory()
    {

    }
    public static final Creator<SportsCategory> CREATOR = new Creator<SportsCategory>() {
        @Override
        public SportsCategory createFromParcel(Parcel in) {
            return new SportsCategory(in);
        }

        @Override
        public SportsCategory[] newArray(int size) {
            return new SportsCategory[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(Name);
    }
}
