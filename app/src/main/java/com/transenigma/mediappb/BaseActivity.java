package com.transenigma.mediappb;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import de.hdodenhof.circleimageview.CircleImageView;
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
    String usr_SP,UserKind,dB_email,dB_fName,dB_lName,dB_Gender,dB_contact,dob,dB_pin,
            dB_Add1,dB_Add2,dB_City,dB_State,dB_Country,dB_MS,dB_emCont,dB_emContType,dB_Unique_Id;
    String Uid;
    DatabaseReference dB_ref;
    FirebaseUser user;

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

    TextView navHeaderName;
    TextView navHeaderEmail;
    CircleImageView profilePic;
    private final int GALLERY_PIC = 1;

    private SharedPreferences usrDetails ;
    SharedPreferences.Editor editor ;

    private FirebaseAuth mAuth;
    private FirebaseUser curUser;
    private StorageReference userProfilePic;
    private DatabaseReference dB_user;

    ProgressDialog ImageUploadProgressDiaglog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        mAuth = FirebaseAuth.getInstance();
        String UserId;
        if(mAuth.getCurrentUser() != null){
            UserId = mAuth.getCurrentUser().getUid();
            dB_user = FirebaseDatabase.getInstance().getReference().child("User").child(UserId);
            userProfilePic = FirebaseStorage.getInstance().getReference();
        }

        usrDetails = PreferenceManager.getDefaultSharedPreferences(BaseActivity.this);
        editor = usrDetails.edit();




        toolbar = (Toolbar) findViewById(R.id.toolbarLayout);
        setSupportActionBar(toolbar);

        // ---------------------------------------Setting Shared Preference ----------------------------------------
        final Context context = BaseActivity.this;
        //usrDetails =  context.getSharedPreferences("USER", MODE_PRIVATE);
        usrDetails = PreferenceManager.getDefaultSharedPreferences(context);
        editor = usrDetails.edit();

        FirebaseAuth.AuthStateListener mAuthListener;

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
        View headerView = navigationView.getHeaderView(0);

        navHeaderName = (TextView) headerView.findViewById(R.id.navHeader_name);
        navHeaderEmail = (TextView) headerView.findViewById(R.id.navHeader_email);
        profilePic = (CircleImageView) headerView.findViewById(R.id.navHeader_img);

        String fName, lName, email, Ppic;
        fName = usrDetails.getString(getString(R.string.FIRST_NAME_TAG), "");
        lName = usrDetails.getString(getString(R.string.LAST_NAME_TAG), "");
        email = usrDetails.getString(getString(R.string.EMAIL_TAG), "");
        Ppic = usrDetails.getString(getString(R.string.PROFILE_PIC_TAG),getString(R.string.DB_NOTSET));

        if( fName.equals(getString(R.string.DB_NOTSET)) ){
            navHeaderName.setText("Update MyProfile");
        }
        else{
            navHeaderName.setText(fName + " " + lName);
        }

        if( email.equals(getString(R.string.DB_NOTSET))){
            navHeaderEmail.setText( " " );
        }else{
            navHeaderEmail.setText(email);
        }

        if( ! Ppic.equals(getString(R.string.DB_NOTSET))){
            Picasso.with(BaseActivity.this).load(Ppic).into(profilePic);
        }

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GalleryIntent = new Intent();
                GalleryIntent.setType("image/*");
                GalleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(GalleryIntent,"SELECT IMAGE"), GALLERY_PIC);
            }
        });


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
                        //mAuth.removeAuthStateListener(mAuthListener);

                        editor.putString( getString(R.string.USER_KIND) , getString(R.string.DB_NOTSET));
                        editor.putString( getResources().getString(R.string.USERID), getString(R.string.DB_NOTSET));
                        editor.putString( getString(R.string.CONTACT_TAG) , getString(R.string.DB_NOTSET));
                        editor.putString( getString(R.string.EMAIL_TAG) , getString(R.string.DB_NOTSET));
                        editor.putString( getString(R.string.FIRST_NAME_TAG), getString(R.string.DB_NOTSET));
                        editor.putString( getString(R.string.LAST_NAME_TAG), getString(R.string.DB_NOTSET));
                        editor.putString( getString(R.string.DOB_TAG), getString(R.string.DB_NOTSET));
                        editor.putString( getString(R.string.M_STATUS_TAG), getString(R.string.DB_NOTSET));
                        editor.putString( getString(R.string.GENDER_TAG), getString(R.string.DB_NOTSET));
                        editor.putString( getString(R.string.EM_CONTACT_TAG), getString(R.string.DB_NOTSET));
                        editor.putString( getString(R.string.EM_CONTACT_TYPE_TAG), getString(R.string.DB_NOTSET));
                        editor.putString( getString(R.string.ADD_LINE01_TAG), getString(R.string.DB_NOTSET));
                        editor.putString( getString(R.string.ADD_LINE02_TAG), getString(R.string.DB_NOTSET));
                        editor.putString( getString(R.string.PIN_TAG), getString(R.string.DB_NOTSET));
                        editor.putString( getString(R.string.CITY_TAG), getString(R.string.DB_NOTSET));
                        editor.putString( getString(R.string.STATE_TAG), getString(R.string.DB_NOTSET));
                        editor.putString( getString(R.string.COUNTRY_TAG), getString(R.string.DB_NOTSET));
                        editor.putString( getString(R.string.UNIQUE_ID_TAG), getString(R.string.DB_NOTSET));
                        editor.apply();

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
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if( user == null) {
            Intent login = new Intent(BaseActivity.this, Login.class);
            startActivity(login);
            finish();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_PIC && resultCode == RESULT_OK){

            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setAspectRatio(1,1)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            curUser = FirebaseAuth.getInstance().getCurrentUser();
            // String Uid = usrDetails.getString(getString(R.string.USERID),"not_Known");
            String Uid;
            if(curUser != null) {
                Uid = curUser.getUid();
            }else{
                Uid = "NULL";
            }

            if (!Uid.equals("NULL") && resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                ImageUploadProgressDiaglog = new ProgressDialog(BaseActivity.this);
                ImageUploadProgressDiaglog.setTitle("Uploading Image");
                ImageUploadProgressDiaglog.setMessage("Please Wait While We Upload Image");
                ImageUploadProgressDiaglog.setCanceledOnTouchOutside(false);
                ImageUploadProgressDiaglog.show();

                StorageReference filePath = userProfilePic.child("UserProfile_pic").child(Uid + ".jpg");

                filePath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if( task.isSuccessful()){
                            //Toast.makeText(BaseActivity.this,"Image Upload Successful",Toast.LENGTH_LONG).show();

                            @SuppressWarnings("VisibleForTests")
                            final String downloadUrl = task.getResult().getDownloadUrl().toString();

                            dB_user.child("ProfilePic").setValue(downloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        ImageUploadProgressDiaglog.dismiss();
                                        Toast.makeText(BaseActivity.this,"Image Upload Successful",Toast.LENGTH_SHORT).show();
                                        editor.putString(getString(R.string.PROFILE_PIC_TAG),downloadUrl);
                                        Picasso.with(BaseActivity.this).load(downloadUrl).into(profilePic);
                                    }else{
                                        ImageUploadProgressDiaglog.dismiss();
                                        Toast.makeText(BaseActivity.this,"Couldn't Update User",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else{
                            ImageUploadProgressDiaglog.dismiss();
                            Toast.makeText(BaseActivity.this,"Image Upload Failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                String strError = error.getMessage();
                if(!Uid.equals("NULL")) {
                    Toast.makeText(BaseActivity.this, strError, Toast.LENGTH_SHORT).show();
                }
            }
        }
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
