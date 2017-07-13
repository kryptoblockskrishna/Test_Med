package com.transenigma.mediappb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Register extends AppCompatActivity {

    EditText number;
    EditText email;
    EditText pass;
    EditText rePass;
    Button register;

    private SharedPreferences usrDetails ;
    SharedPreferences.Editor editor ;

    private FirebaseAuth mAuth;

    FirebaseDatabase dB = FirebaseDatabase.getInstance();
    DatabaseReference dB_ref ;

    FirebaseUser curUser;
    String UID;

    private ProgressDialog registerUserDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usrDetails = PreferenceManager.getDefaultSharedPreferences(Register.this);
        editor = usrDetails.edit();

        number = (EditText) findViewById(R.id.register_contactInput);
        email = (EditText) findViewById(R.id.register_email);
        pass = (EditText) findViewById(R.id.register_pass);
        rePass = (EditText) findViewById(R.id.register_passRe);
        register = (Button) findViewById(R.id.register_button);

        mAuth = FirebaseAuth.getInstance();

        registerUserDB = new ProgressDialog(Register.this);

        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String contactNo = number.getText().toString();
                String emailID = email.getText().toString();
                String passW = pass.getText().toString();
                String RePassW = rePass.getText().toString();

                if(contactNo.isEmpty()){
                    Toast.makeText(Register.this, "Type In Contact Number and Try Again",Toast.LENGTH_LONG).show();
                }
                else if(emailID.isEmpty()){
                    Toast.makeText(Register.this, "Type In Email-Id and Try Again",Toast.LENGTH_LONG).show();
                }
                else if(passW.isEmpty()){
                    Toast.makeText(Register.this, "Type In Password and Try Again",Toast.LENGTH_LONG).show();
                }
                else if(RePassW.isEmpty()){
                    Toast.makeText(Register.this, "Re-Enter Password and Try Again",Toast.LENGTH_LONG).show();
                }
                else if (passW.equals(RePassW)){

                    registerUserDB.setTitle("Registering User");
                    registerUserDB.setMessage("Please Wait While We Create your Account");
                    registerUserDB.setCanceledOnTouchOutside(false);
                    registerUserDB.show();

                    register_user(contactNo, emailID, passW);
                }
                else{
                    Toast.makeText(Register.this, "ERROR: Pasword Mis-Match, Try Again",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void register_user(final String contactNo, final String emailID, String passW) {

        mAuth.createUserWithEmailAndPassword(emailID, passW)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {

                            curUser = FirebaseAuth.getInstance().getCurrentUser();
                            UID = curUser.getUid();
                            dB_ref = dB.getReference().child("User").child(UID);

                            HashMap<String, String> nwUsrMap = new HashMap<String, String>();
                            nwUsrMap.put("F_Name", getString(R.string.DB_NOTSET));
                            nwUsrMap.put("L_Name", getString(R.string.DB_NOTSET));
                            nwUsrMap.put("Sex", getString(R.string.DB_NOTSET));
                            nwUsrMap.put("M_Stat", getString(R.string.DB_NOTSET));
                            nwUsrMap.put("Email",emailID);
                            nwUsrMap.put("Contact", contactNo);
                            nwUsrMap.put("Em_Contact", getString(R.string.DB_NOTSET));
                            nwUsrMap.put("Em_Contact_type", getString(R.string.DB_NOTSET));
                            nwUsrMap.put("Country", getString(R.string.DB_NOTSET));
                            nwUsrMap.put("State", getString(R.string.DB_NOTSET));
                            nwUsrMap.put("City", getString(R.string.DB_NOTSET));
                            nwUsrMap.put("Add_line1", getString(R.string.DB_NOTSET));
                            nwUsrMap.put("Add_line2", getString(R.string.DB_NOTSET));
                            nwUsrMap.put("Pin", getString(R.string.DB_NOTSET));
                            nwUsrMap.put("ProfilePic", getString(R.string.DB_NOTSET));
                            nwUsrMap.put("UniqueID", getString(R.string.DB_NOTSET));
                            nwUsrMap.put("dOb", getString(R.string.DB_NOTSET));
                            //nwUsrMap.put("dOb_d","");
                            //nwUsrMap.put("dOb_m","");
                            //nwUsrMap.put("dOb_y","");

                            dB_ref.setValue(nwUsrMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                        editor.putString(getString(R.string.USER_KIND) , getString(R.string.type_NewUser));
                                        editor.putString(getResources().getString(R.string.USERID), UID);
                                        editor.putString(getString(R.string.CONTACT_TAG) , contactNo);
                                        editor.putString(getString(R.string.EMAIL_TAG) , emailID);
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

                                        registerUserDB.dismiss();

                                        Intent toBase = new Intent(Register.this, BaseActivity.class);
                                        startActivity(toBase);
                                        finish();
                                    }
                                }
                            });
                        }
                        else{

                            registerUserDB.hide();
                            Toast.makeText(Register.this, "Error Occured With Auth", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void toMainAsGuest(View view){
        Toast.makeText(Register.this, "Feature Under Construction.", Toast.LENGTH_LONG).show();
    }

    public void backToSignIn(View view){
        Intent toSignIn = new Intent(Register.this, Login.class);
        startActivity(toSignIn);
        finish();
    }
}
