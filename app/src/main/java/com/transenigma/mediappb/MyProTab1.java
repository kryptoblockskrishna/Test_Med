package com.transenigma.mediappb;


import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyProTab1 extends Fragment {

    private SharedPreferences usrDetails ;
    SharedPreferences.Editor editor ;

    String fName, lName, dob, email ;
    //String dob_d, dob_m, dob_y;
    String gender, m_status, contact, em_contact, em_contactType ;
    String Add1, Add2, state, city, country, uniqueID, pin ;
    int pos;

    EditText fName_et, pin_et, OtherCity_et;
    EditText lName_et, email_et, contact_et, emContact_et;
    EditText Add1_et, Add2_et, uniqueID_et ;

    private TextView dateEdit, uniqueID_tv;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TextView age;

    LinearLayout myPro_StateCity;

    DatabaseReference dB_ref;
    String usrID_SP ;

    CardView personalInfo;
    CardView currentAdd;

    private ProgressDialog updateUserInfo;

    public MyProTab1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_pro_tab1, container, false);

        updateUserInfo = new ProgressDialog(view.getContext());

        // =============================== SHARED PREFERENCE =======================================

        //usrDetails = view.getContext().getSharedPreferences("USER", MODE_PRIVATE);
        usrDetails = PreferenceManager.getDefaultSharedPreferences(view.getContext());

        usrID_SP = usrDetails.getString(getResources().getString(R.string.USERID) , "");
        email = usrDetails.getString(getResources().getString(R.string.EMAIL_TAG), "");
        contact = usrDetails.getString(getResources().getString(R.string.CONTACT_TAG), "");

        fName = usrDetails.getString("FIRST_NAME","");
        lName = usrDetails.getString("LAST_NAME", "");
        dob = usrDetails.getString("DOB", "");
        //dob_d = usrDetails.getString("DOB_DAY", "");
        //dob_m = usrDetails.getString("DOB_MONTH", "");
        //dob_y = usrDetails.getString("DOB_YEAR", "");
        m_status = usrDetails.getString("M_STATUS", "");
        gender = usrDetails.getString("GENDER", "");
        em_contact = usrDetails.getString("EM_CONTACT", "");
        em_contactType = usrDetails.getString("EM_CONTACT_TYPE", "");

        Add1 = usrDetails.getString("ADD_LINE01", "");
        Add2 = usrDetails.getString("ADD_LINE02", "");
        pin = usrDetails.getString("PIN", "");
        city = usrDetails.getString("CITY", "");
        state = usrDetails.getString("STATE", "");
        country = usrDetails.getString("COUNTRY", "");
        uniqueID = usrDetails.getString("UNIQUE_ID", "");
        // -----------------------------------------------------------------------------------------

        dB_ref = FirebaseDatabase.getInstance().getReference().child("User").child(usrID_SP);

        // ============================= INITIALIZING FIELDS =======================================

                // ----------- @ fIRST cARD vIEW ---------------------------
        fName_et = (EditText) view.findViewById(R.id.mypro_firstname);
        lName_et = (EditText) view.findViewById(R.id.mypro_lastname);
        email_et = (EditText) view.findViewById(R.id.mypro_email);
        contact_et = (EditText) view.findViewById(R.id.mypro_contact);
        emContact_et = (EditText) view.findViewById(R.id.mypro_emContact);

                // ----------- @ sECOND cARD vIEW ---------------------------

        Add1_et = (EditText) view.findViewById(R.id.mypro_addLine1);
        Add2_et = (EditText) view.findViewById(R.id.mypro_addLine2);
        OtherCity_et = (EditText) view.findViewById(R.id.mypro_OtherCity);
        pin_et = (EditText) view.findViewById(R.id.mypro_pin);
        uniqueID_et = (EditText) view.findViewById(R.id.mypro_uniqueIDValue);
        uniqueID_tv = (TextView) view.findViewById(R.id.mypro_uniqueIDtype);
        myPro_StateCity = (LinearLayout) view.findViewById(R.id.mypro_StateCity);
        // -----------------------------------------------------------------------------------------

        //=================== TODO(1) : Make String Array(s) Dynamic ===============================

        final String[] City_arr = view.getResources().getStringArray(R.array.City);
        final String[] mStatus_arr = view.getResources().getStringArray(R.array.Marital_status);
        final String[] gender_arr = view.getResources().getStringArray(R.array.Gender);
        final String[] emContactType_arr = view.getResources().getStringArray(R.array.Emergency_contact_no);
        final String[] country_arr = view.getResources().getStringArray(R.array.Country);
        final String[] state_arr = view.getResources().getStringArray(R.array.State);
        // -----------------------------------------------------------------------------------------

        // ============================= INITIALIZING SPINNERS =====================================

        final Spinner maritalStat_sp = (Spinner) view.findViewById(R.id.mypro_sp_mStat);
        final Spinner gender_sp = (Spinner) view.findViewById(R.id.mypro_sp_gender);
        final Spinner emContactType_sp = (Spinner) view.findViewById(R.id.mypro_sp_emContactNo);
        final Spinner country_sp = (Spinner) view.findViewById(R.id.mypro_spinner_country);
        final Spinner state_sp = (Spinner) view.findViewById(R.id.mypro_sp_state);
        final Spinner city_sp = (Spinner) view.findViewById(R.id.mypro_sp_city);
        // -----------------------------------------------------------------------------------------

        // ============================= OnItemSelectedListeners ===================================

            // -------------- Current Address Body --------------------
        final LinearLayout l = (LinearLayout)view.findViewById(R.id.mypro_currAdd_Body);
        l.setVisibility(View.GONE);
        country_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position == 1){

                    l.setVisibility(View.VISIBLE);
                    uniqueID_tv.setText("Aadhaar ID*");
                }
                else if (position == 2){
                    l.setVisibility(View.VISIBLE);
                    uniqueID_tv.setText("Passport Number*");
                }

                if(position != 1 || position == 0){
                    myPro_StateCity.setVisibility(View.GONE);
                }
                else{
                    myPro_StateCity.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

            // --------------- Other City Portion ---------------------
        final RelativeLayout r = (RelativeLayout) view.findViewById(R.id.mypro_edittext_city_other);
        r.setVisibility(View.GONE);
        city_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (City_arr[position].equals("Other")){
                    r.setVisibility(View.VISIBLE);
                }
                else r.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

            // -------------------- Date View -------------------------
        dateEdit = (TextView) view.findViewById(R.id.mypro_text_dob);
        age = (TextView)view.findViewById(R.id.mypro_age);
        dateEdit.setOnClickListener(new View.OnClickListener(){
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);

            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                Date date1 = null;
                String str = dateEdit.getText().toString();
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

                DatePickerDialog dialog
                        = new DatePickerDialog(getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener, year, month, day);

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
                        int age1 = today.get(Calendar.YEAR)-dob.get(Calendar.YEAR) - 1;

                        int age2 = today.get(Calendar.MONTH) - dob.get(Calendar.MONTH);
                        int age3 = today.get(Calendar.DAY_OF_MONTH) - dob.get(Calendar.DAY_OF_MONTH);
                        if(age2 > 0 || (age2 == 0 && age3 >= 0)){
                            age1++;
                        }

                        // Converting Int To text
                        String s4 = String.valueOf(age1);
                        age.setText(s4);
                        dateEdit.setText(s3);
                    }
                };
            }
        });
        // -----------------------------------------------------------------------------------------


        // ============================ INITIALIZING MY PROFILE ====================================

        email_et.setText(email);
        contact_et.setText(contact);

        if( em_contact != null && !em_contact.equals(getResources().getString(R.string.DB_NOTSET)) ){

            fName_et.setText(fName);
            lName_et.setText(lName);
            emContact_et.setText(em_contact);
            dateEdit.setText(dob);

            if(!m_status.equals( getString(R.string.DB_NOTSET) )) {
                for (pos = 0; pos < mStatus_arr.length; pos++) {
                    if (mStatus_arr[pos].equals(m_status))
                        break;
                }

                if( pos == mStatus_arr.length)
                    pos = 0;

            }else{
                pos = 0;
            }
            maritalStat_sp.setSelection(pos);

            if(!gender.equals( getString(R.string.DB_NOTSET) )) {
                for (pos = 0; pos < gender_arr.length; pos++) {
                    if (gender_arr[pos].equals(gender))
                        break;
                }

                if( pos == gender_arr.length)
                    pos = 0;

            }else{
                pos = 0;
            }
            gender_sp.setSelection(pos);

            if(!em_contactType.equals( getString(R.string.DB_NOTSET) )) {
                for (pos = 0; pos < emContactType_arr.length; pos++) {
                    if (emContactType_arr[pos].equals(em_contactType))
                        break;
                }

                if( pos == emContactType_arr.length)
                    pos = 0;

            }else{
                pos = 0;
            }
            emContactType_sp.setSelection(pos);
        }

        if(pin != null && !pin.equals( getString(R.string.DB_NOTSET) )){

            l.setVisibility(View.VISIBLE);

            Add1_et.setText(Add1);
            Add2_et.setText(Add2);
            pin_et.setText(pin);
            uniqueID_et.setText(uniqueID);

            // ================================== Country ==========================================

            for (pos = 0; pos < country_arr.length; pos++) {
                if (country_arr[pos].equals(country))
                    break;
            }

            if( pos == country_arr.length)
                pos = 0;

            country_sp.setSelection(pos);

            if( country.equals("India"))
                myPro_StateCity.setVisibility(View.VISIBLE);
            else
                myPro_StateCity.setVisibility(View.GONE);

            // ==================================== State ==========================================

            if(!state.equals( getString(R.string.DB_NOTSET) ) ) {
                for (pos = 0; pos < state_arr.length; pos++) {
                    if (state_arr[pos].equals(state))
                        break;
                }

                if( pos == state_arr.length)
                    pos = 0;

            }else{
                pos = 0;
            }
            state_sp.setSelection(pos);

            // ===================================== City ==========================================

            for (pos = 0; pos < City_arr.length; pos++) {
                if (City_arr[pos].equals(city))
                    break;
            }

            if( pos == City_arr.length ) {
                pos = City_arr.length - 1;
                city_sp.setSelection(pos);

                OtherCity_et.setText(city);
                r.setVisibility(View.VISIBLE);
            }else{
                city_sp.setSelection(pos);
            }


        }

        // -----------------------------------------------------------------------------------------

        personalInfo = (CardView) view.findViewById(R.id.mypro_PIButton);
        personalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePersonalInfo(v);
            }
        });

        currentAdd = (CardView) view.findViewById(R.id.mypro_CurrAddButton);
        currentAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCurrentAdd(v);
            }
        });

        return  view;

    }

    public void savePersonalInfo (View v){

        updateUserInfo.setTitle("Updating Personal Info.");
        updateUserInfo.setMessage("Please Wait While We Update");
        updateUserInfo.setCanceledOnTouchOutside(false);
        updateUserInfo.show();

        editor = usrDetails.edit();

        final Spinner gender_sp = (Spinner) v.getRootView().findViewById(R.id.mypro_sp_gender);
        final Spinner maritalStat_sp = (Spinner) v.getRootView().findViewById(R.id.mypro_sp_mStat);
        final Spinner emContactType_sp = (Spinner) v.getRootView().findViewById(R.id.mypro_sp_emContactNo);

        if(fName_et.getText().toString().isEmpty()){
            updateUserInfo.hide();
            Toast.makeText(getContext(),"Please Fill In 'FirstName' ",Toast.LENGTH_LONG).show();
        }else if( lName_et.getText().toString().isEmpty()){
            updateUserInfo.hide();
            Toast.makeText(getContext(),"Please Fill In 'LastName' ",Toast.LENGTH_LONG).show();
        }else if( email_et.getText().toString().isEmpty()){
            updateUserInfo.hide();
            Toast.makeText(getContext(),"Please Fill In 'Email' ",Toast.LENGTH_LONG).show();
        }else if( contact_et.getText().toString().isEmpty() ){
            updateUserInfo.hide();
            Toast.makeText(getContext(),"Please Fill In 'Contact' ",Toast.LENGTH_LONG).show();
        }else if( emContact_et.getText().toString().isEmpty() ){
            updateUserInfo.hide();
            Toast.makeText(getContext(),"Please Fill In 'EmergencyContact Number' ",Toast.LENGTH_LONG).show();
        }else if( gender_sp == null ){
            updateUserInfo.hide();
            Toast.makeText(getContext(),"Please Select Your 'Gender' ",Toast.LENGTH_LONG).show();
        }else if( emContactType_sp == null ){
            updateUserInfo.hide();
            Toast.makeText(getContext(),"Please Select Your 'EmergencyContactType' ",Toast.LENGTH_LONG).show();
        }else if( dateEdit.getText().toString().equals("dd/mm/yy") ){
            updateUserInfo.hide();
            Toast.makeText(getContext(),"Please Select Your 'DOB' ",Toast.LENGTH_LONG).show();
        }else if( dateEdit.getText().toString().isEmpty() ){
            updateUserInfo.hide();
            Toast.makeText(getContext(),"Please Select Your 'DOB' its Empty. ",Toast.LENGTH_LONG).show();
        }else{

            Map<String, String> UsrMap = new HashMap<String, String>();

            if(maritalStat_sp != null) {
                String pos_ms = maritalStat_sp.getSelectedItem().toString();
                UsrMap.put("M_Stat", pos_ms);
            }

            final String pos_emC = emContactType_sp.getSelectedItem().toString();
            final String pos_g = gender_sp.getSelectedItem().toString();

            UsrMap.put("F_Name",fName_et.getText().toString());
            UsrMap.put("L_Name",lName_et.getText().toString());
            UsrMap.put("Contact", contact_et.getText().toString());
            UsrMap.put("Em_Contact",emContact_et.getText().toString());
            UsrMap.put("Email", email_et.getText().toString());
            UsrMap.put("Sex", pos_g);
            UsrMap.put("Em_Contact_type",pos_emC);
            UsrMap.put("dOb",dateEdit.getText().toString());
            UsrMap.put("Country", usrDetails.getString("COUNTRY",""));
            UsrMap.put("State", usrDetails.getString("STATE",""));
            UsrMap.put("City", usrDetails.getString("CITY",""));
            UsrMap.put("Add_line1", usrDetails.getString("ADD_LINE01",""));
            UsrMap.put("Add_line2", usrDetails.getString("ADD_LINE02",""));
            UsrMap.put("Pin", usrDetails.getString("PIN",""));
            UsrMap.put("ProfilePic", usrDetails.getString("PROFILE_PIC",""));
            UsrMap.put("UniqueID", usrDetails.getString("UNIQUE_ID",""));

            // UsrMap.put("dOb_d","");
            // UsrMap.put("dOb_m","");
            // UsrMap.put("dOb_y","");

            dB_ref.setValue(UsrMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        editor.putString("FIRST_NAME", fName_et.getText().toString());
                        editor.putString("LAST_NAME", lName_et.getText().toString());
                        editor.putString("DOB", dateEdit.getText().toString());
                        //editor.putString("DOB_DAY", dob_d);
                        //editor.putString("DOB_MONTH", dob_m);
                        //editor.putString("DOB_YEAR", dob_y);
                        if(maritalStat_sp != null) {
                            String pos_ms = maritalStat_sp.getSelectedItem().toString();
                            editor.putString("M_STATUS", pos_ms);
                        }
                        editor.putString("GENDER", pos_g);
                        editor.putString("EM_CONTACT", emContact_et.getText().toString());
                        editor.putString("EM_CONTACT_TYPE", pos_emC);
                        editor.putString(getString(R.string.EMAIL_TAG), email_et.getText().toString());
                        editor.putString(getString(R.string.CONTACT_TAG), contact_et.getText().toString());

                        editor.commit();
                        updateUserInfo.dismiss();
                        Toast.makeText(getContext(),"Personal Info. Updated Successfully",Toast.LENGTH_LONG).show();
                    }
                    else{
                        updateUserInfo.hide();
                        Toast.makeText(getContext(),"ERROR: Update Failed!! ",Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }

    public void saveCurrentAdd(View v){

        updateUserInfo.setTitle("Updating Current Address");
        updateUserInfo.setMessage("Please Wait While We Update");
        updateUserInfo.setCanceledOnTouchOutside(false);
        updateUserInfo.show();

        editor = usrDetails.edit();

        final Spinner country_sp = (Spinner) v.getRootView().findViewById(R.id.mypro_spinner_country);
        final Spinner state_sp = (Spinner) v.getRootView().findViewById(R.id.mypro_sp_state);
        final Spinner city_sp = (Spinner) v.getRootView().findViewById(R.id.mypro_sp_city);

        if(Add1_et.getText().toString().isEmpty()){
            updateUserInfo.hide();
            Toast.makeText(getContext(),"Please Fill In 'Address Line 1'",Toast.LENGTH_LONG).show();
        }else if(  pin_et.getText().toString().isEmpty() ){
            updateUserInfo.hide();
            Toast.makeText(getContext(),"Please Fill In 'Pin Code'",Toast.LENGTH_LONG).show();
        }else if( uniqueID_et.getText().toString().isEmpty() ){
            updateUserInfo.hide();
            Toast.makeText(getContext(),"Please Fill In All Required(*) Fields",Toast.LENGTH_LONG).show();
        }else if( country_sp.getSelectedItemPosition() == 1 && state_sp.getSelectedItemPosition() == 0 ){
            updateUserInfo.hide();
            Toast.makeText(getContext(),"Please Select Your 'State'",Toast.LENGTH_LONG).show();
        }else if( country_sp.getSelectedItemPosition() == 1 && city_sp.getSelectedItemPosition() == 0){
            updateUserInfo.hide();
            Toast.makeText(getContext(),"Please Select Your 'City'",Toast.LENGTH_LONG).show();
        }else{

            Map<String, String> UsrMap = new HashMap<String, String>();

            final String pos_country = country_sp.getSelectedItem().toString();

            String pos_city_check = city_sp.getSelectedItem().toString();
            if(pos_city_check.equals("Other")) {
                pos_city_check = OtherCity_et.getText().toString();
            }
            final String pos_city = pos_city_check;

            if( state_sp != null){
                final String pos_state = state_sp.getSelectedItem().toString();
                UsrMap.put("State",pos_state);
            }

            UsrMap.put("F_Name", usrDetails.getString("FIRST_NAME","") );
            UsrMap.put("L_Name",usrDetails.getString("LAST_NAME",""));
            UsrMap.put("Sex",usrDetails.getString("GENDER",""));
            UsrMap.put("M_Stat",usrDetails.getString("M_STATUS",""));
            UsrMap.put("Email",usrDetails.getString("EMAIL",""));
            UsrMap.put("Contact", usrDetails.getString("CONTACT",""));
            UsrMap.put("Em_Contact",usrDetails.getString("EM_CONTACT",""));
            UsrMap.put("Em_Contact_type",usrDetails.getString("EM_CONTACT_TYPE",""));
            UsrMap.put("dOb",usrDetails.getString("DOB",""));

            UsrMap.put("Country",pos_country);
            UsrMap.put("City",pos_city);
            UsrMap.put("Add_line1", Add1_et.getText().toString());
            UsrMap.put("Add_line2", Add2_et.getText().toString());
            UsrMap.put("Pin", pin_et.getText().toString());
            UsrMap.put("ProfilePic", usrDetails.getString("PROFILE_PIC",""));

            UsrMap.put("UniqueID",uniqueID_et.getText().toString());

            dB_ref.setValue(UsrMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        editor.putString("ADD_LINE01", Add1_et.getText().toString());
                        editor.putString("ADD_LINE02", Add2_et.getText().toString());
                        editor.putString("PIN", pin_et.getText().toString());
                        editor.putString("CITY", pos_city);

                        if( state_sp != null){
                            final String pos_state = state_sp.getSelectedItem().toString();
                            editor.putString("STATE", pos_state);
                        }

                        editor.putString("COUNTRY", pos_country);
                        editor.putString("UNIQUE_ID", uniqueID_et.getText().toString());
                        editor.commit();
                        updateUserInfo.dismiss();
                        Toast.makeText(getContext(),"Current Address Updated Successfully",Toast.LENGTH_LONG).show();
                    }
                    else{
                        updateUserInfo.hide();
                        Toast.makeText(getContext(),"ERROR: Update Failed!! ",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }
}