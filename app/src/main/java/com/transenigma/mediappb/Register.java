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
                            String UID = curUser.getUid();
                            dB_ref = dB.getReference().child("User").child(UID);

                            HashMap<String, String> nwUsrMap = new HashMap<String, String>();
                            nwUsrMap.put("F_Name","");
                            nwUsrMap.put("L_Name","");
                            nwUsrMap.put("Sex","");
                            nwUsrMap.put("M_Stat","");
                            nwUsrMap.put("Email",emailID);
                            nwUsrMap.put("Contact", contactNo);
                            nwUsrMap.put("Em_Contact","");
                            nwUsrMap.put("Em_Contact_type","");
                            nwUsrMap.put("Country","");
                            nwUsrMap.put("State","");
                            nwUsrMap.put("City","");
                            nwUsrMap.put("Add_line1","");
                            nwUsrMap.put("Add_line2","");
                            nwUsrMap.put("Pin","");
                            nwUsrMap.put("UniqueID","");
                            nwUsrMap.put("dOb","");
                            //nwUsrMap.put("dOb_d","");
                            //nwUsrMap.put("dOb_m","");
                            //nwUsrMap.put("dOb_y","");

                            dB_ref.setValue(nwUsrMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        registerUserDB.dismiss();

                                        editor.putString("USER_KIND", "NewUser");
                                        editor.putString("NUMBER" , contactNo);
                                        editor.putString("EMAIL" , emailID);
                                        editor.apply();

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
