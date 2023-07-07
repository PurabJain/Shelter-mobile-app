package com.purabjain.shelter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OwnerGatewayActivity extends AppCompatActivity {

    Button gateway_login_btn, gateway_signup_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_gateway);

        gateway_login_btn = findViewById(R.id.gateway_login_btn);
        gateway_signup_btn = findViewById(R.id.gateway_signup_btn);

        gateway_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent loginIntent = new Intent(OwnerGatewayActivity.this, LoginActivity.class);
                startActivity(loginIntent);


            }
        });

        gateway_signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent signupIntent = new Intent(OwnerGatewayActivity.this, OwnerSignupActivity.class);
                startActivity(signupIntent);

            }
        });

    }
}
