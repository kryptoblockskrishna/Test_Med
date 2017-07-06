package com.transenigma.mediappb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class Doctors_Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors__profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.doc_pro_toolbar);
        toolbar.setTitle("Doctor Details");
        setSupportActionBar(toolbar);
        //int i = getTitleColor();
        //i=000000;
        //toolbar.setTitleTextColor(i);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
