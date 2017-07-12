package com.transenigma.mediappb;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import layout.AppointmentFragment;
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
                    img = img_home[2];
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

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser user;
    private DatabaseReference dB_ref;

    String Uid;
    String dB_fName, dB_lName, dob ;
    // String dob_d, dob_m, dob_y;
    String dB_email, dB_MS, dB_Gender, dB_contact, dB_emCont, dB_emContType;
    String dB_Add1, dB_Add2, dB_pin, dB_City, dB_State, dB_Country, dB_Unique_Id;

    String UserKind;

    private SharedPreferences usrDetails ;
    SharedPreferences.Editor editor ;

    String usr_SP;
    private ProgressDialog downloadingUserDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        toolbar = (Toolbar) findViewById(R.id.toolbarLayout);
        setSupportActionBar(toolbar);

        // ---------------------------------------Setting Shared Preference ----------------------------------------
        final Context context = BaseActivity.this;
        //usrDetails =  context.getSharedPreferences("USER", MODE_PRIVATE);
        usrDetails = PreferenceManager.getDefaultSharedPreferences(context);
        editor = usrDetails.edit();

        usr_SP = usrDetails.getString("USER_ID" , "NOT_INITIALIZED");
        UserKind = usrDetails.getString("USER_KIND", "NOT_INITIALIZED");

        // ---------------------------------------------- FIREBASE -------------------------------------------------
        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // User is not signed in
                    Intent login = new Intent(BaseActivity.this, Login.class);
                    startActivity(login);
                    finish();
                }
                else{

                    Uid = user.getUid();

                    if(! usr_SP.equals(Uid)) {

                        // editor.putString("USER_CHANGED", "YES");

                        // ------- For Newly Logged In --------

                        dB_ref = FirebaseDatabase.getInstance().getReference().child("User").child(Uid);

                        dB_ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // -------------------------- PULLING DATA FROM DB -------------------------------------------

                                dB_email = dataSnapshot.child("Email").getValue().toString();
                                dB_contact = dataSnapshot.child("Contact").getValue().toString();

                                dB_fName = dataSnapshot.child("F_Name").getValue().toString();
                                if( dB_fName != null ){
                                    if(!dB_fName.isEmpty()) {
                                        dB_lName = dataSnapshot.child("L_Name").getValue().toString();
                                        dob = dataSnapshot.child("dOb").getValue().toString();
                                        // dob_d = dataSnapshot.child("dOb_d").getValue().toString();
                                        // dob_m = dataSnapshot.child("dOb_m").getValue().toString();
                                        // dob_y = dataSnapshot.child("dOb_y").getValue().toString();
                                        dB_MS = dataSnapshot.child("M_Stat").getValue().toString();
                                        dB_Gender = dataSnapshot.child("Sex").getValue().toString();
                                        dB_emCont = dataSnapshot.child("Em_Contact").getValue().toString();
                                        dB_emContType = dataSnapshot.child("Em_Contact_type").getValue().toString();
                                    }
                                }

                                dB_pin = dataSnapshot.child("Pin").getValue().toString();
                                if(dB_pin != null ){
                                    if(!dB_pin.isEmpty()) {
                                        dB_Add1 = dataSnapshot.child("Add_line1").getValue().toString();
                                        dB_Add2 = dataSnapshot.child("Add_line2").getValue().toString();
                                        dB_City = dataSnapshot.child("City").getValue().toString();
                                        dB_State = dataSnapshot.child("State").getValue().toString();
                                        dB_Country = dataSnapshot.child("Country").getValue().toString();
                                        dB_Unique_Id = dataSnapshot.child("UniqueID").getValue().toString();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        // -------------------------- UPDATING SHARED PREFERENCE -------------------------------------------
                        editor.putString("USER_ID", Uid);
                        editor.putString(getString(R.string.EMAIL_TAG), dB_email);
                        editor.putString(getString(R.string.CONTACT_TAG), dB_contact);

                        if(dB_fName != null ){
                            if(!dB_fName.isEmpty()) {
                                editor.putString("FIRST_NAME", dB_fName);
                                editor.putString("LAST_NAME", dB_lName);
                                editor.putString("DOB", dob);
                                //editor.putString("DOB_DAY", dob_d);
                                //editor.putString("DOB_MONTH", dob_m);
                                //editor.putString("DOB_YEAR", dob_y);
                                editor.putString("M_STATUS", dB_MS);
                                editor.putString("GENDER", dB_Gender);
                                editor.putString("EM_CONTACT", dB_emCont);
                                editor.putString("EM_CONTACT_TYPE", dB_emContType);
                            }
                            else{
                                editor.putString("FIRST_NAME", "NULL");
                                editor.putString("LAST_NAME", "NULL");
                                editor.putString("DOB", "NULL");
                                //editor.putString("DOB_DAY", "");
                                //editor.putString("DOB_MONTH", "");
                                //editor.putString("DOB_YEAR", "");
                                editor.putString("M_STATUS", "NULL");
                                editor.putString("GENDER", "NULL");
                                editor.putString("EM_CONTACT", "NULL");
                                editor.putString("EM_CONTACT_TYPE", "NULL");
                            }
                        }
                        else{
                            editor.putString("FIRST_NAME", "NULL");
                            editor.putString("LAST_NAME", "NULL");
                            editor.putString("DOB", "NULL");
                            //editor.putString("DOB_DAY", "");
                            //editor.putString("DOB_MONTH", "");
                            //editor.putString("DOB_YEAR", "");
                            editor.putString("M_STATUS", "NULL");
                            editor.putString("GENDER", "NULL");
                            editor.putString("EM_CONTACT", "NULL");
                            editor.putString("EM_CONTACT_TYPE", "NULL");
                        }

                        if(dB_pin != null ){

                            if(!dB_pin.isEmpty()) {
                                editor.putString("ADD_LINE01", dB_Add1);
                                editor.putString("ADD_LINE02", dB_Add2);
                                editor.putString("PIN", dB_pin);
                                editor.putString("CITY", dB_City);
                                editor.putString("STATE", dB_State);
                                editor.putString("COUNTRY", dB_Country);
                                editor.putString("UNIQUE_ID", dB_Unique_Id);
                            }
                            else{
                                editor.putString("ADD_LINE01", "NULL");
                                editor.putString("ADD_LINE02", "NULL");
                                editor.putString("PIN", "NULL");
                                editor.putString("CITY", "NULL");
                                editor.putString("STATE", "NULL");
                                editor.putString("COUNTRY", "NULL");
                                editor.putString("UNIQUE_ID", "NULL");
                            }
                        }
                        else{
                            editor.putString("ADD_LINE01", "NULL");
                            editor.putString("ADD_LINE02", "NULL");
                            editor.putString("PIN", "NULL");
                            editor.putString("CITY", "NULL");
                            editor.putString("STATE", "NULL");
                            editor.putString("COUNTRY", "NULL");
                            editor.putString("UNIQUE_ID", "NULL");
                        }

                        editor.commit();


                    }
                }
            }
        };

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

        actionBarDrawerToggle = new ActionBarDrawerToggle(BaseActivity.this, drawerLayout, toolbar, R.string.drawer_open,
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
                        mAuth.signOut();
                        //------ ?? ---------------------------------
                        mAuth.removeAuthStateListener(mAuthListener);

                        Intent toSignIn = new Intent(BaseActivity.this, Login.class);
                        startActivity(toSignIn);
                        finish();

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
