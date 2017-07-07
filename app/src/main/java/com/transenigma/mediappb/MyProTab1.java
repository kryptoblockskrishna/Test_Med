package com.transenigma.mediappb;


import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.support.v7.widget.CardView;
import android.text.format.DateFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyProTab1 extends Fragment {

    TextView t1,t2;
    CardView c0,c1,c2,c3;
    int i1,i2;
    ImageView iv1,iv2;

    /*Spinner spinner;
    int[] a ={R.array.Marital_status,R.array.Gender,R.array.Email,R.array.Contact_No,
    R.array.Emergency_contact_no};
    int[] i = {R.id.mypro_spinner_marriage,R.id.mypro_spinner_gender,R.id.mypro_spinner_email,
            R.id.mypro_spinner_contactno, R.id.mypro_spinner_emergncy_cont_no};
    ArrayAdapter<CharSequence> adapter;*/

    private TextView dateedit;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TextView age;

    public MyProTab1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_pro_tab1, container, false);
       /* for (int j=0;j<5;j++){
            spinner =(Spinner)view.findViewById(i[j]);
            adapter =ArrayAdapter.createFromResource(getContext(),a[j],android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }*/

        t1 = (TextView)view.findViewById(R.id.my_pro_title1);
        t2 = (TextView)view.findViewById(R.id.my_pro_title2);
        c1 = (CardView)view.findViewById(R.id.my_pro_card1);
        c0 = (CardView)view.findViewById(R.id.my_pro_card0);
        c2 = (CardView)view.findViewById(R.id.my_pro_card2);
        c3 = (CardView)view.findViewById(R.id.my_pro_card3);
        iv1 = (ImageView)view.findViewById(R.id.my_pro_arrow1);
        iv2 = (ImageView)view.findViewById(R.id.my_pro_arrow2);
        c1.setVisibility(View.GONE);        // c1, c2 are main cards, c0,c3 are hint messages.
        c2.setVisibility(View.GONE);
        c0.setVisibility(View.VISIBLE);
        c3.setVisibility(View.VISIBLE);
        iv1.animate().rotation(0).setDuration(100).start();
        iv2.animate().rotation(0).setDuration(100).start();
        i1=0;i2=0;

        t1.setOnClickListener(new AdapterView.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(i1==0){
                    c1.setVisibility(View.VISIBLE);
                    c0.setVisibility(View.GONE);
                    c2.setVisibility(View.GONE);
                    c3.setVisibility(View.VISIBLE);
                    i1=1;i2=0;
                    iv1.animate().rotation(180).setDuration(100).start();
                    iv2.animate().rotation(0).setDuration(100).start();
                }
                else {
                    c1.setVisibility(View.GONE);
                    c0.setVisibility(View.VISIBLE);
                    c2.setVisibility(View.GONE);
                    c3.setVisibility(View.VISIBLE);
                    i1=0;i2=0;

                    iv1.animate().rotation(0).setDuration(100).start();
                    iv2.animate().rotation(0).setDuration(100).start();
                }

            }
        });
        t2.setOnClickListener(new AdapterView.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(i2==0){
                    c1.setVisibility(View.GONE);
                    c0.setVisibility(View.VISIBLE);
                    c2.setVisibility(View.VISIBLE);
                    c3.setVisibility(View.GONE);
                    i1=0;
                    i2=1;
                    iv1.animate().rotation(0).setDuration(100).start();
                    iv2.animate().rotation(180).setDuration(100).start();
                }
                else {
                    c1.setVisibility(View.GONE);
                    c0.setVisibility(View.VISIBLE);
                    c2.setVisibility(View.GONE);
                    c3.setVisibility(View.VISIBLE);
                    i1=0;i2=0;
                    iv1.animate().rotation(0).setDuration(100).start();
                    iv2.animate().rotation(0).setDuration(100).start();
                }
            }
        });

        final LinearLayout l = (LinearLayout)view.findViewById(R.id.mypro_hidden_layout);
        l.setVisibility(View.GONE);
        final LinearLayout aadhaar = (LinearLayout)view.findViewById(R.id.mypro_aadhaar);
        final LinearLayout passport = (LinearLayout)view.findViewById(R.id.mypro_passport);
        final Spinner s = (Spinner)view.findViewById(R.id.mypro_spinner_country);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position == 1){

                    l.setVisibility(View.VISIBLE);
                    aadhaar.setVisibility(View.VISIBLE);
                    passport.setVisibility(View.GONE);
                }
                else if (position == 2){
                    l.setVisibility(View.VISIBLE);
                    aadhaar.setVisibility(View.GONE);
                    passport.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Spinner s1 = (Spinner)view.findViewById(R.id.mypro_spinner_city);
        final RelativeLayout r = (RelativeLayout) view.findViewById(R.id.mypro_edittext_city_other);
        r.setVisibility(View.GONE);
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 4){
                    r.setVisibility(View.VISIBLE);
                }
                else r.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dateedit = (TextView) view.findViewById(R.id.mypro_text_dob);
        age = (TextView)view.findViewById(R.id.mypro_age);
        dateedit.setOnClickListener(new View.OnClickListener(){
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                Date date1 = null;
                String str = dateedit.getText().toString();
                String str1="dd/mm/yyyy";

                if(str.equals(str1)){
                    date1 = Calendar.getInstance().getTime();
                    cal.setTime(date1);
                }
                else {

                    try {
                        date1 = outputFormat.parse(str);
                        cal.setTime(date1);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,year,month,day);
                dialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis()+10000);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                dateSetListener = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        SimpleDateFormat inputFormat = new SimpleDateFormat("d/M/yyyy",Locale.ENGLISH);
                        String s2 = dayOfMonth + "/" + month + "/" + year;

                        Date date = null;
                        String s3 = null;

                        try {
                            date = inputFormat.parse(s2);
                            s3 = outputFormat.format(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Calendar dob = Calendar.getInstance();
                        Calendar today = Calendar.getInstance();
                        dob.setTime(date);
                        int age1 = today.get(Calendar.YEAR)-dob.get(Calendar.YEAR);
                        String s4 = age1+"";
                        age.setText(s4);
                        dateedit.setText(s3);
                    }

                };
            }
        });




        /*final Calendar c = Calendar.getInstance();
        final TextView t = (TextView)view.findViewById(R.id.mypro_text_dob);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR,year);
                c.set(Calendar.MONTH,month);
                c.set(Calendar.DAY_OF_MONTH,month);
                Updatelabel();
            }

            }


            t.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(this,date,c.get(Calendar.YEAR),
                            c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)).show();
                }
            });

        private void Updatelabel() {

            String myFormat = "MM/dd/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            t.setText(sdf.format(c.getTime()));

        }*/

        return  view;

    }




}
