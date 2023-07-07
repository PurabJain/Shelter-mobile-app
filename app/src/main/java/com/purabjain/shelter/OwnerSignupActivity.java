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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class OwnerSignupActivity extends AppCompatActivity {

    EditText owner_signup_email, owner_signup_password, owner_signup_confirmPassword, owner_signup_displayName;
    Button owner_signup_nextBtn, signup_alreadyUser_btn;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    ProgressDialog regProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_signup);

//        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Owner");

        owner_signup_displayName = findViewById(R.id.owner_signup_displayName);
        owner_signup_email = findViewById(R.id.owner_signup_email);
        owner_signup_password = findViewById(R.id.owner_signup_password);
        owner_signup_confirmPassword = findViewById(R.id.owner_signup_confirmPassword);
        owner_signup_nextBtn = findViewById(R.id.owner_signup_nextBtn);
        signup_alreadyUser_btn = findViewById(R.id.signup_alreadyUser_btn);

        regProgress = new ProgressDialog(this);

        owner_signup_nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String owner_displayName = owner_signup_displayName.getText().toString();
                String owner_email = owner_signup_email.getText().toString();
                String owner_password = owner_signup_password.getText().toString();
                String owner_confirmPassword = owner_signup_confirmPassword.getText().toString();

                if(!TextUtils.isEmpty(owner_displayName) && !TextUtils.isEmpty(owner_email) && !TextUtils.isEmpty(owner_password) && !TextUtils.isEmpty(owner_confirmPassword))
                {
                    if(owner_confirmPassword.equals(owner_password)){


                    regProgress.setTitle("Registering...");
                    regProgress.setMessage("Please wait while we are creating your account.");
                    regProgress.setCanceledOnTouchOutside(false);
                    regProgress.show();


                    registerOwner(owner_displayName, owner_email, owner_password);
                    }
                    else{
                        Toast.makeText(OwnerSignupActivity.this, "Password Doesn't Match\n Please re-enter your password.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(OwnerSignupActivity.this, "Please fill all the credentials mentioned above.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        signup_alreadyUser_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent loginIntent = new Intent(OwnerSignupActivity.this, LoginActivity.class);
                startActivity(loginIntent);

            }
        });

    }

    private void registerOwner(final String owner_displayName, final String owner_email, String owner_password) {

        mAuth.createUserWithEmailAndPassword(owner_email, owner_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    String owner_uid = currentUser.getUid();

                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Owner").child(owner_uid);

                    HashMap<String, String> hashMap1 = new HashMap<>();
                    hashMap1.put("Username", owner_displayName);
                    hashMap1.put("Email", owner_email);

                    //databaseReference.setValue(hashMap);

                    regProgress.dismiss();

                    Intent hostelDetails = new Intent(OwnerSignupActivity.this, HostelDetailsActivity.class);
                    hostelDetails.putExtra("Owner Signup Details", hashMap1);
                    startActivity(hostelDetails);
                    finish();
                    //finish();
//                    Intent next = new Intent(OwnerSignupActivity.this, HostelDetailsActivity.class);
//                    startActivity(next);
//                    finish();
                    Toast.makeText(OwnerSignupActivity.this, "You have successfully registered.", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(OwnerSignupActivity.this, "You got some error, please check and try again.\n"+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    regProgress.hide();
                }

            }
        });

    }
}
