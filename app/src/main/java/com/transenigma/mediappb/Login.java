package com.transenigma.mediappb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class Login extends AppCompatActivity {

    EditText email;
    EditText password;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    Button checkLogin;

    private ProgressDialog loginProgDB;

    private SharedPreferences usrDetails ;
    SharedPreferences.Editor editor ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usrDetails = PreferenceManager.getDefaultSharedPreferences(Login.this);
        editor = usrDetails.edit();

        email = (EditText) findViewById(R.id.login_contactInput);
        password = (EditText) findViewById(R.id.login_pass);
        checkLogin = (Button) findViewById(R.id.login_button);

        mAuth = FirebaseAuth.getInstance();

        checkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLoginMethod(v);
            }
        });

        loginProgDB = new ProgressDialog(Login.this);
    }

    public void checkLoginMethod(View view){

        final String strEmail = email.getText().toString();
        String strPass = password.getText().toString();


        if(strEmail.isEmpty()){
            Toast.makeText(Login.this, "Type In Email", Toast.LENGTH_SHORT).show();
        }
        else if(strPass.isEmpty()){
            Toast.makeText(Login.this, "Type In Password", Toast.LENGTH_SHORT).show();
        }
        else {

            loginProgDB.setTitle("Logging In User");
            loginProgDB.setMessage("Please Wait While We Check Your Credentials");
            loginProgDB.setCanceledOnTouchOutside(false);
            loginProgDB.show();

            mAuth.signInWithEmailAndPassword(strEmail, strPass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                user = mAuth.getCurrentUser();

                                editor.putString( getResources().getString(R.string.USERID), user.getUid());
                                editor.putString( getString(R.string.USER_KIND) , getString(R.string.type_ExistingUser));
                                editor.putString( getString(R.string.EMAIL_TAG) , strEmail );
                                editor.commit();

                                loginProgDB.dismiss();

                                Intent toLoadData = new Intent(Login.this, LoadData.class);
                                startActivity(toLoadData);
                                finish();
                            } else {

                                loginProgDB.hide();

                                Toast.makeText(Login.this, "ERROR: Wrong Credentials.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void toForgetPassword(View view){
        Toast.makeText(Login.this, "Feature Under Construction.", Toast.LENGTH_LONG).show();
    }

    public void toSignUp(View view){
        Intent register = new Intent(Login.this, Register.class);
        startActivity(register);
        finish();
    }
}
