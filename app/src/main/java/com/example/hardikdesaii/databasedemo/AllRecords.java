package com.example.hardikdesaii.databasedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class AllRecords extends AppCompatActivity
{
    RecyclerView recyclerView;
    MyDatabase mydatabase;
    ArrayList<Customer> customer;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_records2);
        mydatabase=new MyDatabase(AllRecords.this);
        try
        {
            if(mydatabase!=null)
            {
                mydatabase.openDB();
                customer=mydatabase.getAllRecords();

            }
        }
        catch (Exception ex)
        {
            Toast.makeText(AllRecords.this," "+ex,Toast.LENGTH_LONG).show();
        }
        finally
        {
            if(mydatabase!=null)
            {
                mydatabase.closeDB();
            }
        }

        // recycler view code starts here
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(AllRecords.this);
        recyclerView.setLayoutManager(mLayoutManager);

        CustomersAdapter adapter=new CustomersAdapter(AllRecords.this,customer);

        recyclerView.setAdapter(adapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
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
}
