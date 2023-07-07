package com.purabjain.shelter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class LoginActivity extends AppCompatActivity {

//    public androidx.appcompat.widget.Toolbar mToolbar;
    EditText login_email, login_password;
    Button login_btn;
    private ProgressDialog LoginProgress;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

//        mToolbar = (Toolbar) findViewById(R.id.login_toolbar);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setTitle("Login");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        login_email = (EditText)findViewById(R.id.login_email);
        login_password = (EditText)findViewById(R.id.login_password);
        login_btn = (Button) findViewById(R.id.login_btn);

        LoginProgress = new ProgressDialog(this);



        login_btn.setOnClickListener(new View.OnClickListener() {
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

                }else{
                    Toast.makeText(LoginActivity.this, "Please fill all the credentials mentioned to login.", Toast.LENGTH_SHORT).show();
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



                    Intent mainIntent = new Intent(LoginActivity.this, ShowHostelActivity.class);
//                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();

                } else {

                    LoginProgress.hide();
                    Toast.makeText(LoginActivity.this, "You got some error, please check the form and try again.", Toast.LENGTH_LONG).show();
                }

            }
        });


    }


}
