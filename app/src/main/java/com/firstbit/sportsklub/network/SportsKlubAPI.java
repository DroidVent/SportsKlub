package com.firstbit.sportsklub.network;

import com.firstbit.sportsklub.homescreen.data.Feed;
import com.firstbit.sportsklub.homescreen.data.SportsCategory;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by hp-pc on 24-11-2016.
 */

public interface SportsKlubAPI {


    @GET("getallcategory")
    Call<ArrayList<SportsCategory>> getCategories();

    @GET("getvideobycategory")
    Call<ArrayList<Feed>> getFeed(@Query("CategoryId") int categoryId);

    @GET("getallvideo")
    Call<ArrayList<Feed>> getAllFeed();

}
