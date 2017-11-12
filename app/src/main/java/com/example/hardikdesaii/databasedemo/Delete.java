package com.example.hardikdesaii.databasedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Delete extends AppCompatActivity {

    EditText id;
    Button btndelete;
    MyDatabase mydatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
    id=(EditText)findViewById(R.id.deleteid);
     btndelete=(Button)findViewById(R.id.btndelete);
        mydatabase=new MyDatabase(Delete.this);
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result=false;
                boolean flag=validate(id);
                if(flag==true)
                {
                    try
                    {
                        mydatabase.openDB();
                        result=mydatabase.deleteFromDB(Integer.parseInt(id.getText().toString()));
                        if(result==true)
                        {
                            Toast.makeText(Delete.this," Row deleted Successfully",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(Delete.this,"No record with mentioned ID found",Toast.LENGTH_LONG).show();
                        }
                    }
                    catch(Exception ex)
                    {
                        Toast.makeText(Delete.this," "+ex,Toast.LENGTH_LONG).show();
                    }
                        finally
                    {
                        if(mydatabase!=null)
                        {
                            mydatabase.closeDB();
                        }
                    }
                }
            }
        });
    }

    private boolean validate(EditText id) {
        if(id.getText().length()<=0)
        {
            id.setError("Enter id");
            id.requestFocus();
            return false;
        }
        return true;
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
