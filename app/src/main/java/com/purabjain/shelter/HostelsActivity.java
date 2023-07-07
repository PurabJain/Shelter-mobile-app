package com.purabjain.shelter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class HostelsActivity extends AppCompatActivity {
    ProgressDialog mProgressDialog;

    private FirebaseAuth mAuth;

    ListView lv;

    ArrayList<String> Facility = new ArrayList<>();
    ArrayList<String> Restriction = new ArrayList<>();
    ArrayList<String> Address = new ArrayList<>();
    ArrayList<String> Phno = new ArrayList<>();
    ArrayList<String> Image1 = new ArrayList<>();
    ArrayList<String> Image2 = new ArrayList<>();
    ArrayList<String> Image3 = new ArrayList<>();
    ArrayList<String> Title = new ArrayList<>();

    public androidx.appcompat.widget.Toolbar mToolbar;
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostels);

        mAuth = FirebaseAuth.getInstance();

        mToolbar = (Toolbar) findViewById(R.id.start_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Shelter");

        lv = (ListView)findViewById(R.id.hostels_lv);
        hostelAdapter adapter = new hostelAdapter();
        lv.setAdapter(adapter);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

//        mProgressDialog.dismiss();
        Address = extras.getStringArrayList("address");
        Facility = extras.getStringArrayList("facility");
        Restriction = extras.getStringArrayList("restriction");
        Phno = extras.getStringArrayList("phno");
        Title = extras.getStringArrayList("title");
        Image1 = extras.getStringArrayList("image1");
        Image2 = extras.getStringArrayList("image2");
        Image3 = extras.getStringArrayList("image3");
        Log.d("GETTING",String.valueOf(Address.size()));

    }

    public class hostelAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return Address.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = getLayoutInflater().inflate(R.layout.hostel_row, null);
            TextView address = view.findViewById(R.id.mHostelAddress1);
            TextView title = view.findViewById(R.id.mHostelName);
            TextView facility = view.findViewById(R.id.restriction_textView);
            TextView restriction = view.findViewById(R.id.facility_textView);
            TextView phno = view.findViewById(R.id.mPhoneNumber);

            ImageView image1 = view.findViewById(R.id.mHostelImage1);
            ImageView image2 = view.findViewById(R.id.mHostelImage2);
            ImageView image3 = view.findViewById(R.id.mHostelImage3);

            address.setText(Address.get(i));
            title.setText(Title.get(i));
            facility.setText(Facility.get(i));
            restriction.setText(Restriction.get(i));
            phno.setText(Phno.get(i));
            Log.d("GETTING",String.valueOf(Address.size()));

            for(int j=1;j<=Address.size();j++){
                Log.d("GETTING","IMG");
                for(int i1=0;i1<Image1.size();i1++){
                    AsyncTask<String, Void, Bitmap> img= new DownloadImage().execute(Image1.get(i));
                    try {
                        image1.setImageBitmap(img.get());
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                for(int i2=0;i2<Image2.size();i2++){
                    AsyncTask<String, Void, Bitmap> img= new DownloadImage().execute(Image2.get(i));
                    try {
                        image2.setImageBitmap(img.get());
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                for(int i3=0;i3<Image3.size();i3++){
                    AsyncTask<String, Void, Bitmap> img= new DownloadImage().execute(Image3.get(i));
                    try {
                        image3.setImageBitmap(img.get());
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
            return view;
        }
    }
    // DownloadImage AsyncTask
    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {




        @Override
        protected Bitmap doInBackground(String... url) {

            String imageURL = url[0];

            Bitmap bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
//                Page.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){

            sendToStart();

        }
    }
        private void sendToStart() {

            Intent startIntent = new Intent(HostelsActivity.this, WelcomeActivity.class);
            startActivity(startIntent);
            finish();

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

        if(item.getItemId() == R.id.main_logout_btn){

            FirebaseAuth.getInstance().signOut();
            sendToStart();
        }

        else if(item.getItemId() == R.id.main_about_btn){

            Intent aboutIntent = new Intent(HostelsActivity.this,AboutActivity.class);
            startActivity(aboutIntent);

        }
        else if(item.getItemId() == R.id.main_account_btn){

            Intent accountIntent = new Intent(HostelsActivity.this, AccountActivity.class);
            startActivity(accountIntent);

        }
        else if(item.getItemId() == R.id.main_wishlist_btn){



        }
        else if(item.getItemId() == R.id.main_area_btn){

            Intent areaintent = new Intent(HostelsActivity.this, AreasActivity.class);
            startActivity(areaintent);
            finish();

        }
        return true;
    }

    */
}
