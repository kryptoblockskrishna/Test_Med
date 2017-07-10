package com.transenigma.mediappb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

public class Doctors_Profile extends AppCompatActivity {

    TextView name,deg,dept,hosp,avail,from;
    ImageView pic;
    int[] img, img1,img2;
    String[] Name,Deg,Dept,Hosp,Avail,From;

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

        Intent i = getIntent();
        int loc = i.getIntExtra("Calling_From", 0);
        int pos = i.getIntExtra("Position", 0);
        img1 = new int[]{R.drawable.ic_person,R.drawable.ic_user,R.drawable.ic_a2,R.drawable.ic_action_plus,R.drawable.ic_circle_arrow};
        img2 = new int[]{R.drawable.ic_person,R.drawable.ic_user,R.drawable.ic_a2,R.drawable.ic_action_plus,R.drawable.ic_circle_arrow,
                R.drawable.ic_person,R.drawable.ic_user,R.drawable.ic_a2,R.drawable.ic_action_plus,R.drawable.ic_circle_arrow};
        name =(TextView)findViewById(R.id.doc_pro_name);
        deg =(TextView)findViewById(R.id.doc_pro_deg);
        dept =(TextView)findViewById(R.id.doc_pro_dept);
        hosp =(TextView)findViewById(R.id.doc_pro_hosp);
        avail =(TextView)findViewById(R.id.doc_pro_avail2);
        from =(TextView)findViewById(R.id.doc_pro_time);
        pic = (ImageView)findViewById(R.id.doc_pro_pic);
        if(loc==0){
            Name= getResources().getStringArray(R.array.rcy_pin_doc_name);
            Deg = getResources().getStringArray(R.array.rcy_pin_deg);
            Dept = getResources().getStringArray(R.array.rcy_pin_speciality);
            Hosp =getResources().getStringArray(R.array.rcy_pin_hosp);
            Avail = getResources().getStringArray(R.array.rcy_pin_avail);
            From = getResources().getStringArray(R.array.rcy_pin_from);
            img = img1;
        }
        else {
            Name= getResources().getStringArray(R.array.doctor);
            Deg = getResources().getStringArray(R.array.degree);
            Dept = getResources().getStringArray(R.array.search_dept);
            Hosp =getResources().getStringArray(R.array.hospitals);
            Avail = getResources().getStringArray(R.array.availability);
            From = getResources().getStringArray(R.array.search_from);
            img = img2;
        }

        name.setText("Dr. " + Name[pos]);
        deg.setText("has done " + Deg[pos]);
        dept.setText("is a " +  Dept[pos]);
        hosp.setText("at " + Hosp[pos]);
        avail.setText(": "+Avail[pos]);
        from.setText(From[pos]);
        pic.setImageResource(img[pos]);

    }

}
