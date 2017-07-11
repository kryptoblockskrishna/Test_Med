package com.transenigma.mediappb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import me.itangqi.waveloadingview.WaveLoadingView;

import static com.transenigma.mediappb.R.color.colorBottomNavView;

public class LoadData extends AppCompatActivity {

    WaveLoadingView mWaveLoadingView ;

    private FirebaseAuth mAuth;
    FirebaseUser user;
    private DatabaseReference dB_ref;

    private SharedPreferences usrDetails ;
    SharedPreferences.Editor editor ;
    TextView error;

    Button next;

    String Uid ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_data);

        mWaveLoadingView = (WaveLoadingView) findViewById(R.id.waveLoadingView);
        error = (TextView) findViewById(R.id.loadData_Description);

        // ---------------------------------------Setting Shared Preference ----------------------------------------
        final Context context = LoadData.this;
        //usrDetails =  context.getSharedPreferences("USER", MODE_PRIVATE);
        usrDetails = PreferenceManager.getDefaultSharedPreferences(context);
        editor = usrDetails.edit();

        // ---------------------------------------------- FIREBASE -------------------------------------------------
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        Uid = user.getUid();
        editor.putString(getString(R.string.USERID), Uid);

            // editor.putString("USER_CHANGED", "YES");

            // ------- For Newly Logged In --------

            dB_ref = FirebaseDatabase.getInstance().getReference().child("User").child(Uid);

            dB_ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // -------------------------- PULLING DATA FROM DB -------------------------------------------

                    editor.putString( getString(R.string.FIRST_NAME_TAG),
                            dataSnapshot.child("F_Name").getValue().toString());
                    editor.putString( getString(R.string.LAST_NAME_TAG),
                            dataSnapshot.child("L_Name").getValue().toString());
                    editor.putString( getString(R.string.DOB_TAG),
                            dataSnapshot.child("dOb").getValue().toString() );
                    startPercentMockThread( 1, 18);
                    //editor.putString("DOB_DAY", dob_d);
                    //editor.putString("DOB_MONTH", dob_m);
                    //editor.putString("DOB_YEAR", dob_y);
                    editor.putString( getString(R.string.M_STATUS_TAG),
                            dataSnapshot.child("M_Stat").getValue().toString() );
                    editor.putString( getString(R.string.GENDER_TAG),
                            dataSnapshot.child("Sex").getValue().toString() );
                    editor.putString( getString(R.string.CONTACT_TAG),
                            dataSnapshot.child("Contact").getValue().toString());
                    editor.putString( getString(R.string.EM_CONTACT_TAG),
                            dataSnapshot.child("Em_Contact").getValue().toString() );
                    startPercentMockThread( 19, 42);
                    editor.putString( getString(R.string.EM_CONTACT_TYPE_TAG),
                            dataSnapshot.child("Em_Contact_type").getValue().toString() );
                    editor.putString( getString(R.string.ADD_LINE01_TAG),
                            dataSnapshot.child("Add_line1").getValue().toString() );
                    editor.putString( getString(R.string.ADD_LINE02_TAG),
                            dataSnapshot.child("Add_line2").getValue().toString() );
                    editor.putString( getString(R.string.PIN_TAG),
                            dataSnapshot.child("Pin").getValue().toString() );
                    startPercentMockThread( 43, 66);
                    editor.putString( getString(R.string.CITY_TAG),
                            dataSnapshot.child("City").getValue().toString() );
                    editor.putString( getString(R.string.STATE_TAG),
                            dataSnapshot.child("State").getValue().toString() );
                    editor.putString( getString(R.string.COUNTRY_TAG),
                            dataSnapshot.child("Country").getValue().toString() );
                    editor.putString( getString(R.string.UNIQUE_ID_TAG),
                            dataSnapshot.child("UniqueID").getValue().toString() );

                    startPercentMockThread( 67, 85);
                    editor.apply();
                    startPercentMockThread( 86, 100);
                    Intent toBase = new Intent(LoadData.this, BaseActivity.class);
                    startActivity(toBase);
                    finish();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    error.setText(databaseError.toString());
                    error.setTextSize(16);
                }
            });


    }

    private void startPercentMockThread(final int start, final int end) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(8000);
                    for (int i = start; i <= end; i++) {
                        Thread.sleep(6650);
                        changePercent(i);
                        if( i == 100){
                            Thread.sleep(5500);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();
    }

    private void changePercent(final int percent) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                mWaveLoadingView.setProgressValue(percent);
                mWaveLoadingView.setCenterTitle(String.valueOf(percent) + "%");
            }
        });
    }
}