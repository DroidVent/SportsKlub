package com.firstbit.sportsklub.util;

import com.firstbit.sportsklub.homescreen.data.SportsCategory;

import java.util.Comparator;

/**
 * Created by hp-pc on 27-05-2017.
 */

public class CategoryComparator implements Comparator<SportsCategory> {
    @Override
    public int compare(SportsCategory sportsCategory1, SportsCategory sportsCategory2) {
        return sportsCategory1.getName().compareTo(sportsCategory2.getName());
    }
}
