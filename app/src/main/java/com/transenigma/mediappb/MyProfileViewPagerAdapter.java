package com.transenigma.mediappb;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Kishore on 6/17/2017.
 */

public class MyProfileViewPagerAdapter extends FragmentPagerAdapter {
   int count;

    public MyProfileViewPagerAdapter(FragmentManager fm,int no_of_tabs){
        super(fm);
        this.count = no_of_tabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                MyProTab1 f1 = new MyProTab1();
                return f1;
            case 1:
                MyProTab2 f2 = new MyProTab2();
                return f2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return count;
    }

}
