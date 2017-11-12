package com.example.hardikdesaii.databasedemo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity {
    EditText name, email, mobile;
    ImageButton profilepic;
    ImageView dp;
    Button insert;

    String pathuri = "R.mipmap.ic_launcher", pathreal = "R.mipmap.ic_launcher";
    int sdkversion;
    MyDatabase mydatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        mobile = (EditText) findViewById(R.id.mobile);
        profilepic = (ImageButton) findViewById(R.id.profilepic);
        dp = (ImageView) findViewById(R.id.imagedp);
        insert = (Button) findViewById(R.id.insertbtn);
        // object of database class to interact with DB
        mydatabase = new MyDatabase(MainActivity.this);

        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 1. on Upload click call ACTION_GET_CONTENT intent
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                // 2. pick image only
                intent.setType("image/*");
                // 3. start activity
                startActivityForResult(intent, 0);

            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long insertid;
                boolean validateflag;
                validateflag = validate(name, email, mobile);
                Toast.makeText(MainActivity.this, "SDK version :" + sdkversion + "\nURI path :" + pathuri + "\nReal Path :" + pathreal, Toast.LENGTH_LONG);
                if (validateflag == true) {
                    try {
                        if (mydatabase != null) {
                            mydatabase.openDB();

                            insertid = mydatabase.insertIntoAccount(name.getText().toString(), email.getText().toString(), mobile.getText().toString(), pathreal);
                            if (insertid >= 0) {
                                Log.e("name", " " + name.getText().toString());
                                Log.e("email", " " + email.getText().toString());
                                Log.e("mobile", " " + mobile.getText().toString());
                                Log.e("photopath", " " + pathreal);

                                Toast.makeText(MainActivity.this, "Record Inserted in DB at position :" + insertid, Toast.LENGTH_LONG).show();

                            }
                        }


                    } catch (Exception ex) {
                        Toast.makeText(MainActivity.this, " " + ex, Toast.LENGTH_LONG).show();
                    } finally {
                        if (mydatabase != null) {
                            mydatabase.closeDB();

                        }
                    }


                }

            } //insert button onClick method ends here
        }); // insert button onClick listener ends here
    }

    private boolean validate(EditText name, EditText email, EditText mobile) {
        String pattern = "[a-zA-Z0-9._]@[a-zA-Z].[a-zA-Z]";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(email.getText().toString());
        int namelen = name.getText().length();

        int mobilelen = mobile.getText().length();

        if (namelen <= 0) {
            name.setError("Enter Proper name");
            name.requestFocus();
            return false;
        }
        if (!(m.find())) {
            email.setError("Enter Proper email");
            email.requestFocus();
            return false;
        }
        if (!(mobilelen == 10)) {
            mobile.setError("Enter Proper mobile");
            mobile.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.insert) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.update) {
            Intent intent = new Intent(this, Update.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.delete) {
            Intent intent = new Intent(this, Delete.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.allrecords) {
            Intent intent = new Intent(this, AllRecords.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data) {

        if (resCode == Activity.RESULT_OK && data != null) {
            String realPath;
            // SDK < API11
            if (Build.VERSION.SDK_INT < 11)
                realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, data.getData());

                // SDK >= 11 && SDK < 19
            else if (Build.VERSION.SDK_INT < 19)
                realPath = RealPathUtil.getRealPathFromURI_API11to18(this, data.getData());

                // SDK > 19 (Android 4.4)
            else
                realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());


            setTextViews(Build.VERSION.SDK_INT, data.getData().getPath(), realPath);
        }


    }

    private void setTextViews(int sdk, String uriPath, String realPath) {


        sdkversion = sdk;
        pathuri = uriPath;
        pathreal = realPath;


        Uri uriFromPath = Uri.fromFile(new File(realPath));

        // To display selected image
        Glide.with(MainActivity.this).load(uriFromPath).into(dp);


        //dp.setImageURI(uriFromPath);
        // dp.setImageResource(R.mipmap.ic_launcher);
    }
} //main class ends here
