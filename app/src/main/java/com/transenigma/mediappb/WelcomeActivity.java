package com.transenigma.mediappb;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private WelcomeViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLinearLayout;
    private TextView[] dots_txtVw_arr;
    private int[] layouts_int_arr;
    private Button btnSkip, btnNext;
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefManager = new PrefManager(this);

        // Checking for first time launch - before calling setContentView()
        if(!prefManager.isFirstTimeLaunch()){
            launchHomeScreen();
            finish();
        }

        // Making notification bar transparent
        if(Build.VERSION.SDK_INT>=21){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_welcome);


        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLinearLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnNext = (Button) findViewById(R.id.btn_next);

        //welcome layouts
        layouts_int_arr = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3,
                R.layout.welcome_slide4
        };

        addBottomDots(0);

        changeStatusBarColor();

        myViewPagerAdapter = new WelcomeViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnSkip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if(current<layouts_int_arr.length){
                    viewPager.setCurrentItem(current);
                }
                else {
                    launchHomeScreen();
                }
            }
        });

    }

    private void addBottomDots(int currentPage) {
        dots_txtVw_arr = new TextView[layouts_int_arr.length];
        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInActive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLinearLayout.removeAllViews();
        for(int i =0; i<dots_txtVw_arr.length;i++){
            dots_txtVw_arr[i] = new TextView(this);
            dots_txtVw_arr[i].setText(Html.fromHtml("&#8226;"));
            dots_txtVw_arr[i].setTextSize(35);
            dots_txtVw_arr[i].setTextColor(colorsInActive[currentPage]);
            dotsLinearLayout.addView(dots_txtVw_arr[i]);
        }
        if(dots_txtVw_arr.length>0)
            dots_txtVw_arr[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i){
        return viewPager.getCurrentItem() + i;

    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(WelcomeActivity.this, BaseActivity.class));
        finish();
    }

    //viewPager Change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener(){
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing next button text from next to gotit
            if (position == layouts_int_arr.length -1){
                btnNext.setText(getString(R.string.start));
                btnSkip.setVisibility(View.GONE);
            }
            else {
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }


    };

    // Making Notification Bar Transparent
    private void changeStatusBarColor(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    //View Pager Adapter
    public class WelcomeViewPagerAdapter extends PagerAdapter{
        private LayoutInflater layoutInflater;

        public WelcomeViewPagerAdapter(){
        }

        public Object instantiateItem(ViewGroup container, int position){
            layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view= layoutInflater.inflate(layouts_int_arr[position],container,false);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return layouts_int_arr.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
