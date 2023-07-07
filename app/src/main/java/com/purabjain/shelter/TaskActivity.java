package com.purabjain.shelter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class TaskActivity extends AppCompatActivity {

    ImageButton upload_hostel_btn, search_hostel_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        upload_hostel_btn = findViewById(R.id.upload_hostel_btn);
        search_hostel_btn = findViewById(R.id.search_hostel_btn);


        upload_hostel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent uploadIntent = new Intent(TaskActivity.this, HostelDetailsActivity.class);
                startActivity(uploadIntent);

            }
        });

        search_hostel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

    }
}
