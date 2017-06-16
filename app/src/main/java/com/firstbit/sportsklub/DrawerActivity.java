package com.firstbit.sportsklub;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.firstbit.sportsklub.homescreen.HomeFragment;
import com.firstbit.sportsklub.homescreen.data.SportsCategory;
import com.firstbit.sportsklub.util.CategoryComparator;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by hp-pc on 21-05-2017.
 */

public class DrawerActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Drawer result = null;
    private AccountHeader headerResult = null;
    private IDrawerItem[] iDrawerItem;
    private ArrayList<SportsCategory> sportsCategories;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBundle();
        setContentView(R.layout.drawer_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        setUpDrawer(savedInstanceState);
        result.setSelection(0);

    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            sportsCategories = bundle.getParcelableArrayList("categories");
            SportsCategory allSportsCategory = new SportsCategory();
            allSportsCategory.setName("All");
            allSportsCategory.setId(-1);
            sportsCategories.add(allSportsCategory);
            Collections.sort(sportsCategories, new CategoryComparator());
            int size = sportsCategories.size();
            iDrawerItem = new PrimaryDrawerItem[size];
            for (int idx = 0; idx < size; idx++)
            {
                String categoryName = sportsCategories.get(idx).getName();
                PrimaryDrawerItem primaryDrawerItem = new PrimaryDrawerItem();
                primaryDrawerItem.withName(categoryName).withIdentifier(idx).withSelectable(true).withTag(idx);
                iDrawerItem[idx] = primaryDrawerItem;
            }
        }
    }

    private void setUpDrawer(Bundle savedInstanceState) {
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.header)
                .withSavedInstance(savedInstanceState)
                .build();

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withSelectedItem(0)
                .withHasStableIds(true)
                .withAccountHeader(headerResult)
                .addDrawerItems(iDrawerItem
                )
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(false)
                .withShowDrawerUntilDraggedOpened(false)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {


                        if (drawerItem != null) {
                            Intent intent = null;
                            int tag = (int) drawerItem.getTag();

                            HomeFragment nextFrag = new HomeFragment();
                            Bundle bundle = new Bundle();
                            bundle.putInt("category_id", sportsCategories.get(tag).getId());
                            nextFrag.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.frame_container, nextFrag, "home")
                                    .commit();
                        }

                        return false;
                    }
                })
                .build();

    }
}
