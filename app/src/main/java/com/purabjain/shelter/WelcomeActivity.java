package com.purabjain.shelter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {

    Button registerButton, loginButton;
    ProgressDialog LoginProgress;
    EditText login_email, login_password;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mAuth = FirebaseAuth.getInstance();

        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);

        registerButton = (Button)findViewById(R.id.create_acc_btn);
        loginButton = (Button)findViewById(R.id.login_acc_btn);

//        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
//            Log.d("Login", "Login");
//        }

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent registerIntent = new Intent(WelcomeActivity.this, SelectPurposeActivity.class);
                startActivity(registerIntent);
            }
        });

        LoginProgress = new ProgressDialog(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = login_email.getText().toString();
                String password = login_password.getText().toString();

                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

                    LoginProgress.setTitle("Logging In");
                    LoginProgress.setMessage("Please wait while we check your credentials");
                    LoginProgress.setCanceledOnTouchOutside(false);
                    LoginProgress.show();

                    loginUser(email, password);

                }else if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(WelcomeActivity.this, "Please fill all the information", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void loginUser(String email, String password) {


        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if(task.isSuccessful()){

                    LoginProgress.dismiss();

                    Intent mainIntent = new Intent(WelcomeActivity.this, MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();

                } else {

                    LoginProgress.hide();
                    Toast.makeText(WelcomeActivity.this, "You got some error, please check the form and try again.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
