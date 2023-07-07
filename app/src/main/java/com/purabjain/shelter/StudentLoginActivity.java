package com.purabjain.shelter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class StudentLoginActivity extends AppCompatActivity {

    Button login_btn, forgetPassword_btn;
    EditText login_email, login_password;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    FirebaseUser currentUser;
    ProgressDialog LoginProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Students");

        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);

        login_btn = findViewById(R.id.login_btn);

        LoginProgress = new ProgressDialog(this);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = login_email.getText().toString();
                String password = login_password.getText().toString();

                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

                    LoginProgress.setTitle("Logging In");
                    LoginProgress.setMessage("Please wait while we check your credentials");
                    LoginProgress.setCanceledOnTouchOutside(false);
                    LoginProgress.show();

                    loginUser(email, password);

                }else{
                    Toast.makeText(StudentLoginActivity.this, "Please fill all the credentials mentioned to login.", Toast.LENGTH_SHORT).show();
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



                    Intent mainIntent = new Intent(StudentLoginActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();

                } else {

                    LoginProgress.hide();
                    Toast.makeText(StudentLoginActivity.this, "You got some error, please check the form and try again.", Toast.LENGTH_LONG).show();

                }

            }
        });
    }
}
