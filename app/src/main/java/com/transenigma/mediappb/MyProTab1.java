package com.transenigma.mediappb;


import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyProTab1 extends Fragment {

    /*Spinner spinner;
    int[] a ={R.array.Marital_status,R.array.Gender,R.array.Email,R.array.Contact_No,
    R.array.Emergency_contact_no};
    int[] i = {R.id.mypro_spinner_marriage,R.id.mypro_spinner_gender,R.id.mypro_spinner_email,
            R.id.mypro_spinner_contactno, R.id.mypro_spinner_emergncy_cont_no};
    ArrayAdapter<CharSequence> adapter;*/
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
        final LinearLayout l = (LinearLayout)view.findViewById(R.id.mypro_hidden_layout);
        l.setVisibility(View.GONE);
        final LinearLayout aadhaar = (LinearLayout)view.findViewById(R.id.mypro_aadhaar);
        final LinearLayout passport = (LinearLayout)view.findViewById(R.id.mypro_passport);
        Spinner s = (Spinner)view.findViewById(R.id.mypro_spinner_country);
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
