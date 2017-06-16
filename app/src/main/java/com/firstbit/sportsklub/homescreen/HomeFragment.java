package com.firstbit.sportsklub.homescreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firstbit.sportsklub.DrawerActivity;
import com.firstbit.sportsklub.R;
import com.firstbit.sportsklub.SportsApplication;
import com.firstbit.sportsklub.homescreen.adapter.HomeAdapter;
import com.firstbit.sportsklub.homescreen.data.Feed;
import com.firstbit.sportsklub.homescreen.data.HomeResponse;
import com.firstbit.sportsklub.media.MediaPlayer;
import com.firstbit.sportsklub.network.SportsKlubAPI;
import com.firstbit.sportsklub.util.AppUtil;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hp-pc on 21-05-2017.
 */

public class HomeFragment extends Fragment {
    private View view;
    private List<HomeResponse> postList;
    private RecyclerView recyclerViewHome;
    private HomeAdapter homeAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private AdView mAdView;
    private int categoryId;
    private ArrayList<Feed> feed;
    private ProgressBar progressBar;
    private TextView tvNoData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        categoryId = bundle.getInt("category_id");
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_layout, container, false);
        init();
        if (AppUtil.isDeviceOnline(getContext()))
            getCategoryFeed();
        else
            Toast.makeText(getContext(), "Please check internet connection", Toast.LENGTH_LONG).show();
        return view;
    }

    private void init() {
        feed = new ArrayList<>();
        tvNoData = (TextView) view.findViewById(R.id.tv_no_data);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("25EFEFDFADF8C247CE4B2A7A488A2F56")
                .build();
        mAdView.loadAd(adRequest);

        recyclerViewHome = (RecyclerView) view.findViewById(R.id.home_recycler_view);
        homeAdapter = new HomeAdapter(getContext(), feed, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
//                Intent intent = new Intent(getContext(), MediaPlayerActivity.class);
                Intent intent = new Intent(getContext(), MediaPlayer.class);
                intent.putExtra("video_path", feed.get(pos).getPostUrl());
                startActivity(intent);
                Log.e("Click ", "Item");
            }
        });
        recyclerViewHome.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewHome.setLayoutManager(mLayoutManager);

        recyclerViewHome.setAdapter(homeAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.home_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = new SearchView(((DrawerActivity) getContext()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                homeAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() == 0)
                    homeAdapter.getFilter().filter(newText);
                return true;
            }
        });
        searchView.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {

                                          }
                                      }
        );
    }

    private void getCategoryFeed() {
        progressBar.setVisibility(View.VISIBLE);
        Call<ArrayList<Feed>> feedResponseCall = null;
        SportsKlubAPI sportsAPI = SportsApplication.getInstance().getSportsAPI();
        if (categoryId != -1)
            feedResponseCall = sportsAPI.getFeed(categoryId);
        else
            feedResponseCall = sportsAPI.getAllFeed();
        feedResponseCall.enqueue(new Callback<ArrayList<Feed>>() {
            @Override
            public void onResponse(Call<ArrayList<Feed>> call, Response<ArrayList<Feed>> response) {
                progressBar.setVisibility(View.GONE);
                if (response != null)
                    if (response.code() == 200) {
                        ArrayList<Feed> posts = response.body();
                        if (posts != null && posts.size() != 0) {
                            tvNoData.setVisibility(View.GONE);
                            feed.clear();
                            feed.addAll(posts);
                            homeAdapter.notifyDataSetChanged();
                        } else
                            tvNoData.setVisibility(View.VISIBLE);

                    }
            }

            @Override
            public void onFailure(Call<ArrayList<Feed>> call, Throwable t) {
                t.printStackTrace();
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

}
