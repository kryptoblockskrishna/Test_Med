package com.transenigma.mediappb;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

public class Doctors_Profile extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    TextView name,deg,dept,hosp,avail,from;
    ImageView pic,back;
    int[] img, img1,img2;
    String[] Name,Deg,Dept,Hosp,Avail,From;
    LinearLayout ll1,ll2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors__profile);

        back = (ImageView)findViewById(R.id.doc_pro_back);
        ll1 = (LinearLayout)findViewById(R.id.doc_pro_app_date_layout);
        ll2 = (LinearLayout)findViewById(R.id.doc_pro_app_time_layout);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog d= new DatePickerDialog(v.getContext() ,dateSetListener,year,month,day);
                dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    }
                };
                d.show();
            }
        });

        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int min = c.get(Calendar.MINUTE);

                TimePickerDialog t = new TimePickerDialog(v.getContext(), timeSetListener,hour,min,false);
                timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    }
                };
                t.show();
            }

        });

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
        avail =(TextView)findViewById(R.id.doc_pro_speciality_avail);
        from =(TextView)findViewById(R.id.doc_pro_speciality_time);
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
        deg.setText(Deg[pos]);
        dept.setText(Dept[pos]);
        hosp.setText(Hosp[pos]);
        avail.setText(Avail[pos]);
        from.setText(From[pos]);
        pic.setImageResource(img[pos]);

    }

}
