package com.purabjain.shelter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AreasActivity extends AppCompatActivity {
    Button kothrud, vimannagar;
    private Toolbar mToolbar;

    ProgressDialog mProgressDialog;

    FirebaseListAdapter firebaseListAdapter;
    DatabaseReference databaseReference;
    ArrayList<String> facility = new ArrayList<>();
    ArrayList<String> restriction = new ArrayList<>();
    ArrayList<String> address = new ArrayList<>();
    ArrayList<String> phno = new ArrayList<>();
    ArrayList<String> image1 = new ArrayList<>();
    ArrayList<String> image2 = new ArrayList<>();
    ArrayList<String> image3 = new ArrayList<>();
    ArrayList<String> title = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_areas);

        mToolbar = (Toolbar)findViewById(R.id.areas_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Choose The Area of Hostel/PG");

        kothrud = (Button)findViewById(R.id.area_kothrud);
        kothrud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new getHostelInfo().execute();

            }
        });

        vimannagar = (Button)findViewById(R.id.area_vimannagar);
        vimannagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent testIntent = new Intent(AreasActivity.this, HostelsActivity.class);
                testIntent.putExtra("Area",1);
                startActivity(testIntent);
            }
        });

    }

    public class getHostelInfo extends AsyncTask<Void,Void,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(AreasActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Retreiving Image");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {

            databaseReference = FirebaseDatabase.getInstance().getReference().child("Data");

//       String facility, restriction, address, image1, image2, image3, phno, title;
            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
                    Log.d("ABCDE: ", dataSnapshot.getKey());
                    for(DataSnapshot snap: dataSnapshot.getChildren()){
                        Log.d("PQR: ", snap.getKey());

                        if(snap.getKey().equals("address")){
                            address.add(snap.getValue().toString());
                        }
                        if(snap.getKey().equals("facility")){
                            facility.add(snap.getValue().toString());
                        }
                        if(snap.getKey().equals("image1")){
                            image1.add(snap.getValue().toString());
                        }
                        if(snap.getKey().equals("image2")){
                            image2.add(snap.getValue().toString());
                        }
                        if(snap.getKey().equals("image3")){
                            image3.add(snap.getValue().toString());
                        }
                        if(snap.getKey().equals("phno")){
                            phno.add(snap.getValue().toString());
                        }
                        if(snap.getKey().equals("restriction")){
                            restriction.add(snap.getValue().toString());
                        }
                        if(snap.getKey().equals("title")){
                            title.add(snap.getValue().toString());
                        }

                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent hostelIntent = new Intent(AreasActivity.this, HostelsActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putStringArrayList("address", address);
                            bundle.putStringArrayList("facility", facility);
                            bundle.putStringArrayList("restriction", restriction);
                            bundle.putStringArrayList("phno", phno);
                            bundle.putStringArrayList("title", title);
                            bundle.putStringArrayList("image1", image1);
                            bundle.putStringArrayList("image2", image2);
                            bundle.putStringArrayList("image3", image3);
                            hostelIntent.putExtras(bundle);
                            startActivity(hostelIntent);
                            finish();
                            mProgressDialog.dismiss();


                        }


                    },0);


                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            return null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(AreasActivity.this, MainActivity.class);
        startActivity(i);
                finish();
    }
}
