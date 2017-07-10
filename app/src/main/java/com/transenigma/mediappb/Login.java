package com.transenigma.mediappb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class Login extends AppCompatActivity {

    EditText email;
    EditText password;
    private FirebaseAuth mAuth;
    Button checkLogin;

    private ProgressDialog loginProgDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        String strEmail = email.getText().toString();
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

                                loginProgDB.dismiss();

                                Intent toBase = new Intent(Login.this, BaseActivity.class);
                                startActivity(toBase);
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
