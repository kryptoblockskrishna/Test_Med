package com.transenigma.mediappb;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import layout.AppointmentFragment;
import layout.LogoutFragment;
import layout.RecentFragment;
import layout.ServicesFragment;
import layout.SettingsFragment;
import layout.TrackFragment;

public class BaseActivity extends AppCompatActivity {

    ImageView imageView;
    int[] img_home ={R.drawable.welcome,R.drawable.appointment,R.drawable.services};
    int img= img_home[0];
    int j=0;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            android.app.FragmentManager fM = getFragmentManager();
            FragmentTransaction fT = fM.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    img = img_home[0];
                    ServicesFragment hF = new ServicesFragment();
                    fT.replace( R.id.content, hF);
                    fT.commit();
                    return true;
                case R.id.navigation_appointment:
                    img = img_home[1];
                    AppointmentFragment aF = new AppointmentFragment();
                    fT.replace( R.id.content, aF);
                    fT.commit();
                    return true;
                case R.id.navigation_track:
                    img=img_home[2];
                    TrackFragment tF = new TrackFragment();
                    fT.replace( R.id.content, tF);
                    fT.commit();

                    return true;
            }
            return false;
        }

    };

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FragmentTransaction fragmentTransaction;
    NavigationView navigationView;
    android.app.FragmentManager fM ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        toolbar = (Toolbar) findViewById(R.id.toolbarLayout);
        setSupportActionBar(toolbar);

        imageView =(ImageView)findViewById(R.id.home_image);
        Runnable r = new Runnable(){
            public void run(){
                imageView.setImageResource(img);
                j++;
                if (j>= 0){
                    j=0;
                }
                imageView.postDelayed(this,3);
            }
        };
        imageView.postDelayed(r,3);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,
                R.string.drawer_close);

        //Depricated drawerLayout.setDrawerListener();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        fM = getFragmentManager();
        fragmentTransaction = fM.beginTransaction();
        ServicesFragment sF = new ServicesFragment();
        fragmentTransaction.add(R.id.content, sF);
        fragmentTransaction.commit();

        getSupportActionBar().setTitle("transHealth");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // --------------------------------------------------------------------------------------------------

        navigationView = (NavigationView) findViewById(R.id.navigationView);
        Menu menu= navigationView.getMenu();
        MenuItem account = menu.findItem(R.id.navdraw_menu);
        SpannableString s = new SpannableString(account.getTitle());
        s.setSpan(new TextAppearanceSpan(this,R.style.NavDraw_Menu_Title),0,s.length(),0);
        account.setTitle(s);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                fM = getFragmentManager();
                switch (item.getItemId()){
                    case R.id.my_profile :
                        Intent i = new Intent(BaseActivity.this,MyProfile.class);
                        startActivity(i);

                        getSupportActionBar().setTitle("transHealth");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.my_health :
                        Intent i1 = new Intent(BaseActivity.this,MyHealth.class);
                        startActivity(i1);


                        getSupportActionBar().setTitle("transHealth");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.insurance :
                        Intent i2 = new Intent(BaseActivity.this,Insurance.class);
                        startActivity(i2);

                        getSupportActionBar().setTitle("transHealth");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.recent :
                        fragmentTransaction = fM.beginTransaction();
                        fragmentTransaction.replace(R.id.content, new RecentFragment());
                        fragmentTransaction.commit();

                        getSupportActionBar().setTitle("transHealth");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.ac_settings :
                        fragmentTransaction = fM.beginTransaction();
                        fragmentTransaction.replace(R.id.content, new SettingsFragment());
                        fragmentTransaction.commit();

                        getSupportActionBar().setTitle("transHealth");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.logout :
                        fragmentTransaction = fM.beginTransaction();
                        fragmentTransaction.replace(R.id.content, new LogoutFragment());
                        fragmentTransaction.commit();

                        getSupportActionBar().setTitle("transHealth");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                }

                return true;
            }
        });


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void doctors(View V){
        Intent i= new Intent(BaseActivity.this, DoctorsList.class);
        startActivity(i);

    }

}
