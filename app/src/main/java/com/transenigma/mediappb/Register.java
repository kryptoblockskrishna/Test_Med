package com.transenigma.mediappb;

import android.content.Intent;
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

public class Register extends AppCompatActivity {

    EditText number;
    EditText email;
    EditText pass;
    EditText rePass;
    Button register;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        number = (EditText) findViewById(R.id.register_contactInput);
        email = (EditText) findViewById(R.id.register_email);
        pass = (EditText) findViewById(R.id.register_pass);
        rePass = (EditText) findViewById(R.id.register_passRe);
        register = (Button) findViewById(R.id.register_button);

        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String No = number.getText().toString();
                String emailID = email.getText().toString();
                String passW = pass.getText().toString();
                String RePassW = rePass.getText().toString();
                if(No.isEmpty()){

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
                    register_user(No, emailID, passW);
                }
                else{
                    Toast.makeText(Register.this, "ERROR: Pasword Mis-Match, Try Again",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void register_user(String no, String emailID, String passW) {

        mAuth.createUserWithEmailAndPassword(emailID, passW)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {

                            Intent toBase = new Intent(Register.this, BaseActivity.class);
                            startActivity(toBase);
                            finish();

                        }
                        else{
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
