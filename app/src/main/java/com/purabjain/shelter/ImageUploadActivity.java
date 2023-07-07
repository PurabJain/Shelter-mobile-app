package com.purabjain.shelter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;

public class ImageUploadActivity extends AppCompatActivity {

    Button add_btn;
    ImageButton i1,i2,i3,i4,i5,i6,i7,i8;
    ProgressDialog progress;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    StorageReference storageReference;
//    public static final int PICK_IMAGE = 1;
    HashMap<String, String> hashMap2 = new HashMap<>();
    HashMap<String, String> hashMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String owner_uid = currentUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Owner").child(owner_uid);
        storageReference = FirebaseStorage.getInstance().getReference().child("ImageFolder");

        i1 = findViewById(R.id.i1);
        i2 = findViewById(R.id.i2);
        i3 = findViewById(R.id.i3);
        i4 = findViewById(R.id.i4);
        i5 = findViewById(R.id.i5);
        i6 = findViewById(R.id.i6);
        i7 = findViewById(R.id.i7);
        i8 = findViewById(R.id.i8);

        add_btn = findViewById(R.id.upload_add_btn);

        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });
        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2);
            }
        });
        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 3);
            }
        });
        i4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 4);
            }
        });
        i5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 5);
            }
        });
        i6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 6);
            }
        });
        i7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 7);
            }
        });
        i8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 8);
            }
        });

        Intent intent = getIntent();
        //HashMap<String,String> hashMap = (HashMap<String, String>)intent.getSerializableExtra("Hostel Details");
        final String hname = intent.getExtras().getString("Hostel Name");
        final String oname = intent.getExtras().getString("Owner Name");
        final String address = intent.getExtras().getString("Address");
        final String restriction = intent.getExtras().getString("Restrictions");
        final String facility = intent.getExtras().getString("Facilities");
        final String phno = intent.getExtras().getString("Phone Number");
        final HashMap<String,String> hashMap1 = (HashMap<String,String>)intent.getSerializableExtra("Owner Signup Details");
        Log.d("Hash", hname);
        Log.d("Hash", oname);
        Log.d("Hash", address);
        Log.d("Hash", restriction);
        Log.d("Hash", facility);
        Log.d("Hash", phno);
        Log.d("Hash", hashMap1.get("Username"));
        Log.d("Hash", hashMap1.get("Email"));

        progress = new ProgressDialog(this);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(ImageUploadActivity.this, "Work is remaining!", Toast.LENGTH_LONG).show();

                if(i1.getDrawable()==null && i2.getDrawable()==null && i3.getDrawable()==null && i4.getDrawable()==null
                && i5.getDrawable()==null && i6.getDrawable()==null && i7.getDrawable()==null && i8.getDrawable()==null){
                    Toast.makeText(ImageUploadActivity.this, "Please upload at least one photo of the hostel", Toast.LENGTH_SHORT).show();
                }
                else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(ImageUploadActivity.this);
                    builder.setMessage("Are Your sure you want to upload your hostel?");
                    builder.setTitle("Confirm");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            hashMap.put("Hostel Name", hname);
                            hashMap.put("Owner Name", oname);
                            hashMap.put("Address", address);
                            hashMap.put("Restrictions", restriction);
                            hashMap.put("Facilities", facility);
                            hashMap.put("Phone Number", phno);
                            hashMap.putAll(hashMap1);
                            hashMap.putAll(hashMap2);

//                    Intent in = new Intent(ImageUploadActivity.this, ShowHostelActivity.class);
//                    in.putExtra("Details", hashMap);
//                    startActivity(in);
//                    finish();

                            progress.setTitle("Uploading Images");
                            progress.setMessage("Please wait while we upload all your information.");
                            progress.setCanceledOnTouchOutside(false);
                            progress.show();

                            databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        progress.dismiss();
                                        Intent intent = new Intent(ImageUploadActivity.this, ShowHostelActivity.class);
                                        startActivity(intent);
                                        finish();
                                        Toast.makeText(ImageUploadActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(ImageUploadActivity.this, "Wasn't Uploaded", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.dismiss();

                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();


                }

            }
        });








    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null && data.getData() != null) {

            //Uri uri = data.getData();

            if(requestCode == 1){
                try {
                    Uri idata1 = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), idata1);
                    i1.setImageBitmap(bitmap);
                    progress.setTitle("Uploading");
                    progress.setCanceledOnTouchOutside(false);
                    progress.show();
                    final StorageReference iname1 = storageReference.child("image" + idata1.getLastPathSegment());
                    iname1.putFile(idata1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(ImageUploadActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            iname1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    hashMap2.put("image1", String.valueOf(uri));
                                    progress.dismiss();
                                    Toast.makeText(ImageUploadActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

                                }
                            });

                        }
                    });

//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                    i1.setImageBitmap(bitmap);
//
//                    hashMap2.put("image1", String.valueOf(bitmap));


//                    databaseReference.setValue(hashMap2).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if(task.isSuccessful()){
//                                Toast.makeText(ImageUploadActivity.this, "Image Added", Toast.LENGTH_SHORT).show();
//                            }
//                            else{
//                                Toast.makeText(ImageUploadActivity.this, "Oops! Something went wrong\nPlease try again", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if(requestCode == 2){
                {
                    try {

                        Uri idata2 = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), idata2);
                        i2.setImageBitmap(bitmap);
                        progress.setTitle("Uploading");
                        progress.setCanceledOnTouchOutside(false);
                        progress.show();
                        final StorageReference iname2 = storageReference.child("image" + idata2.getLastPathSegment());
                        iname2.putFile(idata2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Toast.makeText(ImageUploadActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                iname2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        hashMap2.put("image2", String.valueOf(uri));
                                        progress.dismiss();
                                        Toast.makeText(ImageUploadActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

                                    }
                                });

                            }
                        });

//                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                        i2.setImageBitmap(bitmap);
//                        hashMap2.put("image2", String.valueOf(bitmap));

//                        databaseReference.setValue(hashMap2).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if(task.isSuccessful()){
//                                    Toast.makeText(ImageUploadActivity.this, "Image Added", Toast.LENGTH_SHORT).show();
//                                }
//                                else{
//                                    Toast.makeText(ImageUploadActivity.this, "Oops! Something went wrong\nPlease try again", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            if(requestCode == 3){
                {
                    try {

                        Uri idata3 = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), idata3);
                        i3.setImageBitmap(bitmap);
                        progress.setTitle("Uploading");
                        progress.setCanceledOnTouchOutside(false);
                        progress.show();
                        final StorageReference iname3 = storageReference.child("image"+idata3.getLastPathSegment());
                        iname3.putFile(idata3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                iname3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        hashMap2.put("image3", String.valueOf(uri));
                                        progress.dismiss();
                                        Toast.makeText(ImageUploadActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

                                    }
                                });

                            }
                        });

//                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                        i3.setImageBitmap(bitmap);
//                        HashMap<String, String> hashMap2 = new HashMap<>();
//                        hashMap2.put("image3", String.valueOf(bitmap));

//                        databaseReference.setValue(hashMap2).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if(task.isSuccessful()){
//                                    Toast.makeText(ImageUploadActivity.this, "Image Added", Toast.LENGTH_SHORT).show();
//                                }
//                                else{
//                                    Toast.makeText(ImageUploadActivity.this, "Oops! Something went wrong\nPlease try again", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            if(requestCode == 4){
                {
                    try {

                        Uri idata4 = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), idata4);
                        i4.setImageBitmap(bitmap);
                        progress.setTitle("Uploading");
                        progress.setCanceledOnTouchOutside(false);
                        progress.show();
                        final StorageReference iname4 = storageReference.child("image" + idata4.getLastPathSegment());
                        iname4.putFile(idata4).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                iname4.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        hashMap2.put("image4", String.valueOf(uri));
                                        progress.dismiss();
                                        Toast.makeText(ImageUploadActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

                                    }
                                });

                            }
                        });

//                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                        i2.setImageBitmap(bitmap);
//                        hashMap2.put("image2", String.valueOf(bitmap));

//                        databaseReference.setValue(hashMap2).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if(task.isSuccessful()){
//                                    Toast.makeText(ImageUploadActivity.this, "Image Added", Toast.LENGTH_SHORT).show();
//                                }
//                                else{
//                                    Toast.makeText(ImageUploadActivity.this, "Oops! Something went wrong\nPlease try again", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            if(requestCode == 5){
                {
                    try {

                        Uri idata5 = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), idata5);
                        i5.setImageBitmap(bitmap);
                        progress.setTitle("Uploading");
                        progress.setCanceledOnTouchOutside(false);
                        progress.show();
                        final StorageReference iname5 = storageReference.child("image" + idata5.getLastPathSegment());
                        iname5.putFile(idata5).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                iname5.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        hashMap2.put("image5", String.valueOf(uri));
                                        progress.dismiss();
                                        Toast.makeText(ImageUploadActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

                                    }
                                });

                            }
                        });

//                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                        i2.setImageBitmap(bitmap);
//                        hashMap2.put("image2", String.valueOf(bitmap));

//                        databaseReference.setValue(hashMap2).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if(task.isSuccessful()){
//                                    Toast.makeText(ImageUploadActivity.this, "Image Added", Toast.LENGTH_SHORT).show();
//                                }
//                                else{
//                                    Toast.makeText(ImageUploadActivity.this, "Oops! Something went wrong\nPlease try again", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            if(requestCode == 6){
                {
                    try {

                        Uri idata6 = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), idata6);
                        i6.setImageBitmap(bitmap);
                        progress.setTitle("Uploading");
                        progress.setCanceledOnTouchOutside(false);
                        progress.show();
                        final StorageReference iname6 = storageReference.child("image" + idata6.getLastPathSegment());
                        iname6.putFile(idata6).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                iname6.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        hashMap2.put("image6", String.valueOf(uri));
                                        progress.dismiss();
                                        Toast.makeText(ImageUploadActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

                                    }

                                });


                            }
                        });

//                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                        i2.setImageBitmap(bitmap);
//                        hashMap2.put("image2", String.valueOf(bitmap));

//                        databaseReference.setValue(hashMap2).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if(task.isSuccessful()){
//                                    Toast.makeText(ImageUploadActivity.this, "Image Added", Toast.LENGTH_SHORT).show();
//                                }
//                                else{
//                                    Toast.makeText(ImageUploadActivity.this, "Oops! Something went wrong\nPlease try again", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            if(requestCode == 7){
                {
                    try {

                        Uri idata7 = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), idata7);
                        i7.setImageBitmap(bitmap);
                        progress.setTitle("Uploading");
                        progress.setCanceledOnTouchOutside(false);
                        progress.show();
                        final StorageReference iname7 = storageReference.child("image" + idata7.getLastPathSegment());
                        iname7.putFile(idata7).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                iname7.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        hashMap2.put("image7", String.valueOf(uri));
                                        progress.dismiss();
                                        Toast.makeText(ImageUploadActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

                                    }
                                });

                            }
                        });

//                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                        i2.setImageBitmap(bitmap);
//                        hashMap2.put("image2", String.valueOf(bitmap));

//                        databaseReference.setValue(hashMap2).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if(task.isSuccessful()){
//                                    Toast.makeText(ImageUploadActivity.this, "Image Added", Toast.LENGTH_SHORT).show();
//                                }
//                                else{
//                                    Toast.makeText(ImageUploadActivity.this, "Oops! Something went wrong\nPlease try again", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            if(requestCode == 8){
                {
                    try {

                        Uri idata8 = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), idata8);
                        i8.setImageBitmap(bitmap);
                        progress.setTitle("Uploading");
                        progress.setCanceledOnTouchOutside(false);
                        progress.show();
                        final StorageReference iname8 = storageReference.child("image" + idata8.getLastPathSegment());
                        iname8.putFile(idata8).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                iname8.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        hashMap2.put("image8", String.valueOf(uri));
                                        progress.dismiss();
                                        Toast.makeText(ImageUploadActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

                                    }
                                });

                            }
                        });

//                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                        i2.setImageBitmap(bitmap);
//                        hashMap2.put("image2", String.valueOf(bitmap));

//                        databaseReference.setValue(hashMap2).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if(task.isSuccessful()){
//                                    Toast.makeText(ImageUploadActivity.this, "Image Added", Toast.LENGTH_SHORT).show();
//                                }
//                                else{
//                                    Toast.makeText(ImageUploadActivity.this, "Oops! Something went wrong\nPlease try again", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
/*
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                i1.setImageBitmap(bitmap);
//                Intent getIntent = getIntent();
//                HashMap<String, String> hashMap = (HashMap<String, String>)getIntent.getSerializableExtra("hashMap");
//                HashMap<String, String> hashMap1 = (HashMap<String, String>)getIntent.getSerializableExtra("hashMap1");

                HashMap<String, String> hashMap2 = new HashMap<>();
//                hashMap2.put("Owner_details", String.valueOf(hashMap));
//                hashMap2.put("Hostel_details", String.valueOf(hashMap1));
                hashMap2.put("image1", String.valueOf(bitmap));

                databaseReference.setValue(hashMap2).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ImageUploadActivity.this, "Image Added", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(ImageUploadActivity.this, "Oops! Something went wrong\nPlease try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            } catch (IOException e) {
                e.printStackTrace();
            }

            */

            Log.d("url of images", String.valueOf(hashMap2));


        }

    }




}
