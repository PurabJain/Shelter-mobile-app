package com.purabjain.shelter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ShowHostelActivity extends AppCompatActivity {

    ImageView fu_i1, fu_i2, fu_i3, fu_i4, fu_i5, fu_i6, fu_i7, fu_i8;
    Button fu_finish_btn, fu_cancel_btn;
    ImageButton fu_ph_btn, fu_map_btn, edit_hname;
    TextView fu_facility, fu_restriction, fu_address, fu_phno, fu_hname;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    ProgressDialog pg;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_hostel);

        mAuth = FirebaseAuth.getInstance();
        //FirebaseUser currentUser = mAuth.getCurrentUser();
//        String uid = currentUser.getUid();
//        databaseReference = FirebaseDatabase.getInstance().getReference().child("Owner").child(uid);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeRefreshLayout.setRefreshing(false);

            }
        });

        fu_i1 = findViewById(R.id.fu_i1);
        fu_i2 = findViewById(R.id.fu_i2);
        fu_i3 = findViewById(R.id.fu_i3);
        fu_i4 = findViewById(R.id.fu_i4);
        fu_i5 = findViewById(R.id.fu_i5);
        fu_i6 = findViewById(R.id.fu_i6);
        fu_i7 = findViewById(R.id.fu_i7);
        fu_i8 = findViewById(R.id.fu_i8);

        //fu_finish_btn = findViewById(R.id.fu_finish_btn);
        fu_cancel_btn = findViewById(R.id.fu_delete_btn);

        fu_ph_btn = findViewById(R.id.fu_ph_btn);
        fu_map_btn = findViewById(R.id.fu_map_btn);
        edit_hname = findViewById(R.id.edit_hname);

        fu_facility = findViewById(R.id.fu_facility);
        fu_restriction = findViewById(R.id.fu_restriction);
        fu_address = findViewById(R.id.fu_address);
        fu_phno = findViewById(R.id.fu_phno);
        fu_hname = findViewById(R.id.fu_hname);

//        Intent in = getIntent();
//        final HashMap<String, String> finalHashMap = (HashMap<String,String>)in.getSerializableExtra("Details");

        pg = new ProgressDialog(this);

//        fu_finish_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                pg.setTitle("Uploading...");
////                pg.setMessage("Please wait while we upload your information to our database");
////                pg.setCanceledOnTouchOutside(false);
////                pg.show();
////
////                databaseReference.setValue(finalHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
////                    @Override
////                    public void onComplete(@NonNull Task<Void> task) {
////                        if(task.isSuccessful()){
////                            pg.dismiss();
////                            Toast.makeText(ShowHostelActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
////                        }else{
////                            Toast.makeText(ShowHostelActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
////                        }
////                    }
////                });
//                Toast.makeText(ShowHostelActivity.this, "Your hostel will be live in few minutes", Toast.LENGTH_SHORT).show();
//
//                Intent i = new Intent(ShowHostelActivity.this, UploadedHostelActivity.class);
//                startActivity(i);
//                finish();
//
//            }
//        });

        fu_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ShowHostelActivity.this, "Your hostel is deleted successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ShowHostelActivity.this, "There is some error in deleting your hostel\nPlease try again later", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        fu_map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String yourAddress = fu_address.getText().toString();
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+yourAddress);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
        });

        fu_ph_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ph = fu_phno.getText().toString();
                //Toast.makeText(MainActivity.this, "Wait", Toast.LENGTH_SHORT).show();

                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", ph,null));
                startActivity(callIntent);

            }
        });

        edit_hname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(ShowHostelActivity.this, HostelDetailsActivity.class);
//                startActivity(intent);
//                finish();
                //Toast.makeText(ShowHostelActivity.this, "Work Remaining\nSome NULL POINTER EXCEPTION.", Toast.LENGTH_SHORT).show();

                FirebaseAuth.getInstance().signOut();
                sendToStart();

            }
        });

        edit_hname.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Toast.makeText(ShowHostelActivity.this, "Edit", Toast.LENGTH_LONG).show();
                return false;
            }
        });
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot ds : dataSnapshot.getChildren()){
//                    String link = ds.getValue(String.class);
//                    Log.d("boomboom", link);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        };
//        databaseReference.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();


        if(currentUser == null){

            sendToStart();

        }else{
            String uid = currentUser.getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Owner").child(uid);
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Owner").child(uid);
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //String email = dataSnapshot.child("Email").getValue(String.class);
                    String address = dataSnapshot.child("Address").getValue(String.class);
                    String facility = dataSnapshot.child("Facilities").getValue(String.class);
                    String restriction = dataSnapshot.child("Restrictions").getValue(String.class);
                    String hostelName = dataSnapshot.child("Hostel Name").getValue(String.class);
                    //String ownerName = dataSnapshot.child("Owner Name").getValue(String.class);
                    String phoneNumber = dataSnapshot.child("Phone Number").getValue(String.class);
                    String i1 = dataSnapshot.child("image1").getValue(String.class);
                    String i2 = dataSnapshot.child("image2").getValue(String.class);
                    String i3 = dataSnapshot.child("image3").getValue(String.class);
                    String i4 = dataSnapshot.child("image4").getValue(String.class);
                    String i5 = dataSnapshot.child("image5").getValue(String.class);
                    String i6 = dataSnapshot.child("image6").getValue(String.class);
                    String i7 = dataSnapshot.child("image7").getValue(String.class);
                    String i8 = dataSnapshot.child("image8").getValue(String.class);

                    fu_hname.setText(hostelName);
                    fu_address.setText(address);
                    fu_facility.setText(facility);
                    fu_restriction.setText(restriction);
                    fu_phno.setText(phoneNumber);
                    Picasso.get().load(i1).into(fu_i1);
                    Picasso.get().load(i2).into(fu_i2);
                    Picasso.get().load(i3).into(fu_i3);
                    Picasso.get().load(i4).into(fu_i4);
                    Picasso.get().load(i5).into(fu_i5);
                    Picasso.get().load(i6).into(fu_i6);
                    Picasso.get().load(i7).into(fu_i7);
                    Picasso.get().load(i8).into(fu_i8);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            databaseReference.addListenerForSingleValueEvent(valueEventListener);
        }


    }

    private void sendToStart() {

        Intent loginIntent = new Intent(ShowHostelActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {

            //Log.d(this.getClass().getName(), "back button pressed");


            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            //Yes button clicked
                            finish();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked

                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(ShowHostelActivity.this);
            builder.setMessage("Are you sure?\nYour data will be lost").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

        }

        return super.onKeyDown(keyCode, event);
    }


}
