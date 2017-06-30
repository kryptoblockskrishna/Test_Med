package com.transenigma.mediappb;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyProfile extends AppCompatActivity {

    TabLayout tl;
    ViewPager vp;
    MyProfileViewPagerAdapter vpadap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_profile_toolbar);
        setSupportActionBar(toolbar);

        tl=(TabLayout)findViewById(R.id.my_profile_tablayout);
        tl.addTab(tl.newTab().setText("Personal Details"));
        tl.addTab(tl.newTab().setText("Hospital Id(s)"));

        vp=(ViewPager)findViewById(R.id.my_profile_viewpager);
        vpadap= new MyProfileViewPagerAdapter(getSupportFragmentManager(),tl.getTabCount());
        vp.setAdapter(vpadap);
        vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tl));
        tl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // Increase Size of tab text
        ViewGroup vg = (ViewGroup)tl.getChildAt(0);
        int i = vg.getChildCount();
        for(int j=0; j<i; j++){
            ViewGroup vgtab = (ViewGroup)vg.getChildAt(j);
            int tabchildscount = vgtab.getChildCount();
            for (int k=0; k<tabchildscount; k++){
                View tabViewChild = vgtab.getChildAt(k);
                if(tabViewChild instanceof TextView){
                    ((TextView)tabViewChild).setTextSize(18);
                }
            }
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
