package com.purabjain.shelter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    EditText reg_displayName, reg_email, reg_password, reg_gender;
    Button reg_create_btn;
    private FirebaseAuth mAuth;

    public androidx.appcompat.widget.Toolbar mToolbar;

    private ProgressDialog RegProgress;
    private DatabaseReference mDatabase;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        mToolbar = (Toolbar) findViewById(R.id.start_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        reg_displayName = (EditText)findViewById(R.id.register_name);

        reg_email = (EditText)findViewById(R.id.register_email);
        reg_password = (EditText)findViewById(R.id.register_password);
        reg_gender = (EditText)findViewById(R.id.register_gender);

        reg_create_btn = (Button)findViewById(R.id.create_account_btn);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference();


        RegProgress = new ProgressDialog(this);

        reg_create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String displayName = reg_displayName.getText().toString();
                String email = reg_email.getText().toString();
                String password = reg_password.getText().toString();

                if(!TextUtils.isEmpty(displayName) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){

                    RegProgress.setTitle("Registering User");
                    RegProgress.setMessage("Please wait while we create your account");
                    RegProgress.setCanceledOnTouchOutside(false);
                    RegProgress.show();

                    registerUser(displayName, email, password);

                }

            }
        });

    }

    private void registerUser(final String displayName, final String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String display_name = reg_displayName.getText().toString();

                if (task.isSuccessful()) {

                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    assert currentUser != null;
                    String uid = currentUser.getUid();
                    String gender = reg_gender.getText().toString();

                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("Name", display_name);
                    userMap.put("Gender", gender);
                    userMap.put("Email", email);

                    databaseReference.setValue(userMap);


                    RegProgress.dismiss();

                    Intent mainIntent = new Intent(RegisterActivity.this, AreasActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();

                    Toast.makeText(RegisterActivity.this, "You have successfully registered.", Toast.LENGTH_LONG).show();

                } else {
                    RegProgress.hide();
                    Toast.makeText(RegisterActivity.this, "You got some error, please check the form and try again.", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
