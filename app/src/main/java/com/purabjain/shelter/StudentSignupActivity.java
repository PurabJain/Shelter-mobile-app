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

import java.util.HashMap;

public class StudentSignupActivity extends AppCompatActivity {

    EditText std_username, std_email, std_password, std_confirmPassword;
    Button std_signup_btn, std_alreadyUser_btn;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    FirebaseUser currentUser;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_signup);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Students");

        std_username = findViewById(R.id.std_signup_displayName);
        std_email = findViewById(R.id.std_signup_email);
        std_password = findViewById(R.id.std_signup_password);
        std_confirmPassword = findViewById(R.id.std_signup_confirmPassword);

        std_signup_btn = findViewById(R.id.std_signup_btn);
        std_alreadyUser_btn = findViewById(R.id.signup_alreadyUser_btn);

        progressDialog = new ProgressDialog(this);

        std_signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = std_username.getText().toString();
                String email = std_email.getText().toString();
                String password = std_password.getText().toString();
                String confirmPassword = std_confirmPassword.getText().toString();

                if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmPassword)){
                    if(password.equals(confirmPassword)){

                        progressDialog.setTitle("Registering...");
                        progressDialog.setMessage("Please wait while we are signing you up. ");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();

                        signupUser(username,email,password);
                    }else{
                        Toast.makeText(StudentSignupActivity.this, "Password Doesn't Match. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(StudentSignupActivity.this, "Please fill all the credentials mentioned above.", Toast.LENGTH_SHORT).show();
                }



            }
        });

        std_alreadyUser_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(StudentSignupActivity.this, StudentLoginActivity.class);
                startActivity(intent);

            }
        });

    }

    private void signupUser(final String username, final String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    String owner_uid = currentUser.getUid();

                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Students").child(owner_uid);

                    HashMap<String, String> hashMap1 = new HashMap<>();
                    hashMap1.put("Username", username);
                    hashMap1.put("Email", email);

                    progressDialog.dismiss();

                    Intent hostelDetails = new Intent(StudentSignupActivity.this, MainActivity.class);
                    hostelDetails.putExtra("Student Signup Details", hashMap1);
                    startActivity(hostelDetails);
                    finish();
                }else{
                    Toast.makeText(StudentSignupActivity.this, "You got some error, please check and try again.\n"+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    progressDialog.hide();
                }

            }
        });

    }


}
