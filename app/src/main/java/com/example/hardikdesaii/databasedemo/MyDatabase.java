package com.example.hardikdesaii.databasedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HardikDesaii on 18/01/17.
 */

public class MyDatabase extends SQLiteOpenHelper
{
    private Context context;
    private SQLiteDatabase sqLiteDatabase;
    ArrayList<Customer> customer=new ArrayList<Customer>();
    private final static String MY_DB = "my_database";
    private final static int MY_DB_VERSION = 1;

    //Table Employee
    private final static String ACC_TBL = "account";
    private final static String ACC_ID = "_id";
    private final static String ACC_NAME = "name";
    private final static String ACC_EMAIL = "email";
    private final static String ACC_MOBILE = "mobile";
    private final static String ACC_PHOTO = "photo";


    public MyDatabase(Context context) {
        super(context, MY_DB, null, MY_DB_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) // called only once for one version
    {
        String accountCreateQuery = "create table " + ACC_TBL + " ( " + ACC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ACC_NAME + " TEXT, "+ ACC_EMAIL + " TEXT, "+ ACC_MOBILE + " TEXT, " + ACC_PHOTO + " TEXT )";
        db.execSQL(accountCreateQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
    public void openDB()
    {

        sqLiteDatabase = new MyDatabase(context).getWritableDatabase();
        Toast.makeText(context,"DB OPEN",Toast.LENGTH_SHORT);

    }

    public void closeDB()
    {
        Toast.makeText(context,"DB CLOSED",Toast.LENGTH_SHORT);
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
        {
            sqLiteDatabase.close();
        }
    }
    public long insertIntoAccount(String name, String email,String mobile,String photo)
    {
        ContentValues cv = new ContentValues();
        cv.put(ACC_NAME, name);
        cv.put(ACC_EMAIL, email);
        cv.put(ACC_MOBILE, mobile);
        cv.put(ACC_PHOTO, photo);
        long _id = sqLiteDatabase.insert(ACC_TBL, null, cv);
        return _id;
    }
    public int UpdateCustomer(int id,String name,String email,String mobile)
    {

        ContentValues cv = new ContentValues();
        cv.put(ACC_NAME, name);
        cv.put(ACC_EMAIL, email);
        cv.put(ACC_MOBILE, mobile);
        int result=sqLiteDatabase.update(ACC_TBL, cv, ACC_ID + " = "+id,null);
        return result;
    }
    public boolean deleteFromDB(int id)
    {


            return sqLiteDatabase.delete(ACC_TBL, ACC_ID + "=" + id, null) > 0;

    }
    public ArrayList<Customer> getAllRecords()
    {
        Log.e(" MyDatabase" , "In method fetch All Records");

        Cursor cursor = sqLiteDatabase.query(ACC_TBL, null, null, null, null, null, null);
        Log.e("In method" , "After Cursor creation ");
        while (cursor.moveToNext())
        {
            Log.e("in while loop" , "cursor iteration ");

            String name = cursor.getString(cursor.getColumnIndex(ACC_NAME));
            String email = cursor.getString(cursor.getColumnIndex(ACC_EMAIL));
            String mobile = cursor.getString(cursor.getColumnIndex(ACC_MOBILE));
            String photo = cursor.getString(cursor.getColumnIndex(ACC_PHOTO));
            Log.e("name"," "+name);
            Log.e("email"," "+email);
            Log.e("mobile"," "+mobile);
            Log.e("photo"," "+photo);

            customer.add(new Customer(name,email,mobile,photo));
         }
        cursor.close();

        return customer;
    }
    public String[] getUpdateValues(int id)
    {
        String data[]=new String[3];
        Cursor cursor = sqLiteDatabase.query(ACC_TBL, null, ACC_ID+" ="+id, null, null, null, null);
        while (cursor.moveToNext())
        {
            Log.e("in while loop" , "cursor iteration ");

            String name = cursor.getString(cursor.getColumnIndex(ACC_NAME));
            String email = cursor.getString(cursor.getColumnIndex(ACC_EMAIL));
            String mobile = cursor.getString(cursor.getColumnIndex(ACC_MOBILE));
            Log.e("name"," "+name);
            Log.e("email"," "+email);
            Log.e("mobile"," "+mobile);
            data[0]=name;
            data[1]=email;
            data[2]=mobile;
             }
        cursor.close();


        return data;
    }

}
