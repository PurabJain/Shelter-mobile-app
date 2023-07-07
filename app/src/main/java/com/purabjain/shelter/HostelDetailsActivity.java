package com.purabjain.shelter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class HostelDetailsActivity extends AppCompatActivity {

    // EditText details_hostelName, details_address, details_restriction, details_facility;
    // DatabaseReference databaseReference;
    // FirebaseAuth mAuth;
    // Button submit_details_btn;
    // ProgressDialog uploadProgress;

    EditText hostel_name, owner_name, address_tv, restrictions_tv, facilities_tv, hostel_phno;
    Button hd_next_btn;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    ProgressDialog uploadProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel_details);
/*
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String owner_uid = currentUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Owners").child(owner_uid);

        details_address = findViewById(R.id.details_address);
        details_hostelName = findViewById(R.id.details_hostelName);
        details_restriction = findViewById(R.id.details_restriction);
        details_facility = findViewById(R.id.details_facility);
        submit_details_btn = findViewById(R.id.submit_details_btn);

        uploadProgress = new ProgressDialog(this);

        submit_details_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadProgress.setTitle("Uploading...");
                uploadProgress.setMessage("Please wait while we upload the information");
                uploadProgress.setCanceledOnTouchOutside(false);
                uploadProgress.show();

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("Hostel Name", details_hostelName.getText().toString());
                hashMap.put("Address", details_address.getText().toString());
                hashMap.put("Facilities", details_facility.getText().toString());
                hashMap.put("Restrictions", details_restriction.getText().toString());

                //databaseReference.setValue(hashMap);

                uploadProgress.dismiss();

                Intent imageIntent = new Intent(HostelDetailsActivity.this, ImageUploadActivity.class);
                imageIntent.putExtra("Details Hash Map", hashMap);
                startActivity(imageIntent);
                finish();

            }
        });


*/

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Owner");

        hostel_name = findViewById(R.id.hostel_name);
        owner_name = findViewById(R.id.owner_name);
        address_tv = findViewById(R.id.address_tv);
        restrictions_tv = findViewById(R.id.restriction_tv);
        facilities_tv = findViewById(R.id.facilities_tv);
        hostel_phno = findViewById(R.id.hostel_phno);
        hd_next_btn = findViewById(R.id.hd_next_btn);


        uploadProgress = new ProgressDialog(this);


        Intent intent = getIntent();
        final HashMap<String, String> hashMap1 = (HashMap<String, String>) intent.getSerializableExtra("Owner Signup Details");
        Log.d("HashMapTest", hashMap1.get("Username"));
        Log.d("HashMapTest", hashMap1.get("Email"));

        hd_next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(HostelDetailsActivity.this);
                builder.setTitle("Confirm");
                builder.setMessage("Please check the information you entered.\nInformation cannot be changed during the further process.\nDo you still want to continue?");
                //builder.setIcon(R.drawable.ic_launcher);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        final String hname, oname, address, restriction, facility, phno;
                        hname = hostel_name.getText().toString();
                        oname = owner_name.getText().toString();
                        address = address_tv.getText().toString();
                        restriction = restrictions_tv.getText().toString();
                        facility = facilities_tv.getText().toString();
                        phno = hostel_phno.getText().toString();

                        if (!hname.isEmpty() && !oname.isEmpty() && !address.isEmpty() && !restriction.isEmpty() && !facility.isEmpty() && !phno.isEmpty()) {


                            uploadProgress.setTitle("Uploading...");
                            uploadProgress.setMessage("Please wait while we upload your information");
                            uploadProgress.setCanceledOnTouchOutside(false);
                            uploadProgress.show();

//                HashMap<String, String> hashMap = new HashMap<>();
//                hashMap.put("Hostel Name", hname);
//                hashMap.put("Owner Name", oname);
//                hashMap.put("Address", address);
//                hashMap.put("Restrictions", restriction);
//                hashMap.put("Facilities", facility);
//                hashMap.put("Phone Number", phno);

                            Intent intent1 = new Intent(HostelDetailsActivity.this, ImageUploadActivity.class);
                            //intent1.putExtra("Hostel Details", hashMap);
                            intent1.putExtra("Owner Signup Details", hashMap1);
                            intent1.putExtra("Hostel Name", hname);
                            intent1.putExtra("Owner Name", oname);
                            intent1.putExtra("Address", address);
                            intent1.putExtra("Restrictions", restriction);
                            intent1.putExtra("Facilities", facility);
                            intent1.putExtra("Phone Number", phno);
//                intent1.putExtra("Username",hashMap1.get("Username"));
//                intent1.putExtra("Email", hashMap1.get("Email"));
                            startActivity(intent1);
                            finish();
                        } else {
                            Toast.makeText(HostelDetailsActivity.this, "Please fill all the details to proceed", Toast.LENGTH_SHORT).show();
                        }

//                databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//
//                        if(task.isSuccessful()){
//                            uploadProgress.dismiss();
//                            Intent imageIntent = new Intent(HostelDetailsActivity.this, ImageUploadActivity.class);
//                            startActivity(imageIntent);
//                            finish();
//                            Toast.makeText(HostelDetailsActivity.this, "Details uploaded successfully", Toast.LENGTH_LONG).show();
//                        }
//                        else {
//                            Toast.makeText(HostelDetailsActivity.this, "Something went wrong. Try again later.\nSorry for inconvenience", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();


//
           }
        });


    }

}
