package com.example.hardikdesaii.databasedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class Landing extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.insert)
        {
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.update)
        {
            Intent intent=new Intent(this,Update.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.delete)
        {
            Intent intent=new Intent(this,Delete.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.allrecords)
        {
            Intent intent=new Intent(this,AllRecords.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
