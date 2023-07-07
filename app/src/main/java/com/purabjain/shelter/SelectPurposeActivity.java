package com.purabjain.shelter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.security.acl.Owner;

public class SelectPurposeActivity extends AppCompatActivity {

    ImageButton upload_hostel_btn, search_hostel_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_purpose);

        upload_hostel_btn = findViewById(R.id.upload_hostel_btn);
        search_hostel_btn = findViewById(R.id.search_hostel_btn);

        upload_hostel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ownerSignup = new Intent(SelectPurposeActivity.this, OwnerSignupActivity.class);
                startActivity(ownerSignup);

            }
        });

        search_hostel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent studentSignup = new Intent(SelectPurposeActivity.this, StudentSignupActivity.class);
                startActivity(studentSignup);

            }
        });

    }
}
