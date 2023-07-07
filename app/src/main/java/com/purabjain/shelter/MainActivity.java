package com.purabjain.shelter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.pdf.PdfDocument;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class MainActivity extends AppCompatActivity {
    ProgressDialog mProgressDialog;

    FirebaseListAdapter firebaseListAdapter;
    FirebaseAuth mAuth;
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

    public androidx.appcompat.widget.Toolbar mToolbar;

    ArrayList<String> arrayList = new ArrayList<>();
    ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Shelter");

        lv = findViewById(R.id.main_lv);

        arrayList.add("Google");arrayList.add("Microsoft");arrayList.add("Oracle");
        arrayList.add("Yahoo");arrayList.add("TATA");arrayList.add("Facebook");
        arrayList.add("GMAIL");arrayList.add("Reddif");arrayList.add("Redmi");
        arrayList.add("Samsung");arrayList.add("Apple");arrayList.add("Moto");
        arrayList.add("Redhat");arrayList.add("Blackhat");arrayList.add("Hackers");
        arrayList.add("Amazon");arrayList.add("Flipkart");arrayList.add("Myntra");
        arrayList.add("Benz");arrayList.add("Mercedez");arrayList.add("Jockey");
        arrayList.add("Outlook");arrayList.add("Teams");arrayList.add("Shein");

        final ImageView iv = findViewById(R.id.stdHostelImageView);
        String uid = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Owner").child(uid);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String i1 = dataSnapshot.child("image1").getValue(String.class);
                Picasso.get().load(i1).into(iv);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databaseReference.addListenerForSingleValueEvent(valueEventListener);

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.hostel_row_students, R.id.stdHostelName, arrayList);
        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, i, Toast.LENGTH_SHORT).show();
            }
        });


//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                //String clickedItem = (String) lv.getItemAtPosition(i);
//                Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_LONG).show();
//
//            }
//        });


//
//        Button b = findViewById(R.id.b);
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//               // new getHostelInfo().execute();
//
//
//            }
//        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {

            sendToStart();

        }
    }

    private void sendToStart() {
        Intent startIntent = new Intent(MainActivity.this, SelectPurposeActivity.class);
        startActivity(startIntent);
        finish();
    }

  /*  public class getHostelInfo extends AsyncTask<Void,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(MainActivity.this);
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
                            mProgressDialog.dismiss();
                            Intent hostelIntent = new Intent(MainActivity.this, HostelsActivity.class);
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
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.main_logout_btn) {

            FirebaseAuth.getInstance().signOut();
            sendToStart();
            /*
            Intent startIntent = new Intent(MainActivity.this,StartActivity.class);
            startActivity(startIntent);
            finish();
            */
        } else if (item.getItemId() == R.id.main_about_btn) {

            Intent aboutIntent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(aboutIntent);

        } else if (item.getItemId() == R.id.main_account_btn) {

            Intent accountIntent = new Intent(MainActivity.this, AccountActivity.class);
            startActivity(accountIntent);

        } else if (item.getItemId() == R.id.main_wishlist_btn) {


        } else if (item.getItemId() == R.id.main_area_btn) {

            Intent areaintent = new Intent(MainActivity.this, AreasActivity.class);
            startActivity(areaintent);
            finish();

        }
        return true;
    }
}
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString("lastActivity", getClass().getName());
//        editor.commit();
//    }
//}
//class Dispatcher extends Activity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        Class<?> activityClass;
//
//        try {
//            SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
//            activityClass = Class.forName(
//                    prefs.getString("lastActivity", MainActivity.class.getName()));
//        } catch(ClassNotFoundException ex) {
//            activityClass = MainActivity.class;
//        }
//
//        startActivity(new Intent(this, activityClass));
//    }
//}