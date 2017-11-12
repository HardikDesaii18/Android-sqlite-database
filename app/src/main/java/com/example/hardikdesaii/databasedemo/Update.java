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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Update extends AppCompatActivity {

    EditText id,name,email,mobile;
    Button idbtn,updatebtn;
    MyDatabase myDatabase;
    String values[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        id=(EditText)findViewById(R.id.update_id);

        name=(EditText)findViewById(R.id.update_name);
        email=(EditText)findViewById(R.id.update_email);
        mobile=(EditText)findViewById(R.id.update_mobile);
        name.setVisibility(View.INVISIBLE);
        email.setVisibility(View.INVISIBLE);
        mobile.setVisibility(View.INVISIBLE);

        idbtn=(Button)findViewById(R.id.update_getbtn);
        updatebtn=(Button)findViewById(R.id.update_updatebtn);

        myDatabase=new MyDatabase(Update.this);
        idbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             boolean flag=validateID(id);
                if(flag==true)
                {
                    try
                    {
                       myDatabase.openDB();
                        values=myDatabase.getUpdateValues(Integer.parseInt(id.getText().toString()));
                        if(values!=null)
                        {
                            name.setVisibility(View.VISIBLE);
                            email.setVisibility(View.VISIBLE);
                            mobile.setVisibility(View.VISIBLE);
                            name.setText(values[0].toString());
                            email.setText(values[1].toString());
                            mobile.setText(values[2].toString());

                        }
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(Update.this,"No Such ID found in Table",Toast.LENGTH_LONG).show();
                    }
                    finally
                    {

                    }
                }
            }
        });
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                int result;
                boolean flag=validateData(name,email,mobile);
                if(flag==true)
                {
                    try
                    {
                        myDatabase.openDB();
                        result=myDatabase.UpdateCustomer(Integer.parseInt(id.getText().toString()),name.getText().toString(),email.getText().toString(),mobile.getText().toString());
                        if(result>0)
                        {
                            Toast.makeText(Update.this,"Sucessfully updated Record for ID ",Toast.LENGTH_LONG).show();
                        }
                    }
                    catch (Exception ex)
                    {

                    }
                    finally
                    {
                        if(myDatabase!=null)
                        {
                            myDatabase.closeDB();
                        }
                    }
                }

            }
        });
    }

    private boolean validateData(EditText name, EditText email, EditText mobile)
    {
        String pattern="[a-zA-Z0-9._]@[a-zA-Z].[a-zA-Z]";
        Pattern p= Pattern.compile(pattern);
        Matcher m=p.matcher(email.getText().toString());
        int namelen=name.getText().length();

        int mobilelen=mobile.getText().length();

        if(namelen<=0)
        {
            name.setError("Enter Proper name");
            name.requestFocus();
            return false;
        }
        if(!(m.find()))
        {
            email.setError("Enter Proper email");
            email.requestFocus();
            return false;
        }
        if(!(mobilelen==10))
        {
            mobile.setError("Enter Proper mobile");
            mobile.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validateID(EditText id)
    {
        if(id.getText().length()<=0)
        {
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

}// main class ends here
