package com.deltainductions.rb.contactsrevolution;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

/**
 * Created by RB on 01-07-2015.
 */
public class databaseclass
{
    databasehelper dbh;
    public databaseclass(Context context)
    {
       dbh  = new databasehelper(context);
    }
    public long insert(String name, String number, String address, String email,String image)
    {
        SQLiteDatabase sq = dbh.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(databasehelper.NAME,name);
        contentValues.put(databasehelper.NUMBER,number);
        contentValues.put(databasehelper.ADDRESS,address);
        contentValues.put(databasehelper.EMAIL,email);
        contentValues.put(databasehelper.IMAGE,image);
        long id = sq.insert(databasehelper.TABLE_NAME,null,contentValues);
        return id;
    }
    public void update(String oldname,String name,String number,String address,String email,String image)
    {
        SQLiteDatabase db = dbh.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(databasehelper.NAME,name);
        contentValues.put(databasehelper.NUMBER,number);
        contentValues.put(databasehelper.ADDRESS,address);
        contentValues.put(databasehelper.EMAIL,email);
        contentValues.put(databasehelper.IMAGE,image);
        String[] arg = {oldname};
        db.update(databasehelper.TABLE_NAME,contentValues,databasehelper.NAME+" =?",arg);
    }
    public int delete(String name)
    {
        SQLiteDatabase db = dbh.getWritableDatabase();
        String[] arg = {name};
        int status = db.delete(databasehelper.TABLE_NAME,databasehelper.NAME+" =?",arg);
        return status;

    }
    public ArrayList<ArrayList<String>> getAllData()
    {

        SQLiteDatabase db = dbh.getWritableDatabase();
        String[] columns = {databasehelper.UID,databasehelper.NAME,databasehelper.NUMBER,databasehelper.ADDRESS,databasehelper.EMAIL,databasehelper.IMAGE};
        Cursor cursor = db.query(databasehelper.TABLE_NAME, columns, null, null, null, null, null, null);
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        ArrayList<String> list1= new ArrayList<>();
        ArrayList<String> list2= new ArrayList<>();
        ArrayList<String> list3= new ArrayList<>();
        ArrayList<String> list4= new ArrayList<>();
        ArrayList<String> list5= new ArrayList<>();
        while(cursor.moveToNext())
        {
            final String name = cursor.getString(cursor.getColumnIndex(databasehelper.NAME));
            final String number = cursor.getString(cursor.getColumnIndex(databasehelper.NUMBER));
            final String address = cursor.getString(cursor.getColumnIndex(databasehelper.ADDRESS));
            final String email = cursor.getString(cursor.getColumnIndex(databasehelper.EMAIL));
            final String image = cursor.getString(cursor.getColumnIndex(databasehelper.IMAGE));
            list1.add(name);
            list2.add(number);
            list3.add(address);
            list4.add(email);
            list5.add(image);
        }
        data.add(list1);
        data.add(list2);
        data.add(list3);
        data.add(list4);
        data.add(list5);
        return data;
    }
    class databasehelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "contactsdatabase";
        private static final String TABLE_NAME = "Contactsdetails";
        private static final int DATABASE_VERSION = 3;
        private static final String UID = "_id";
        private static final String NAME = "Name";
        private static final String NUMBER = "Number";
        private static final String EMAIL = "Email";
        private static final String ADDRESS = "Address";
        private static final String IMAGE = "Image";
        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " VARCHAR(255), " + NUMBER + " VARCHAR(255), " + EMAIL + " VARCHAR(255), " + ADDRESS + " VARCHAR(255), " + IMAGE + " VARCHAR(255));";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        private Context context;

        public databasehelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase)
        {
            sqLiteDatabase.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2)
        {
            sqLiteDatabase.execSQL(DROP_TABLE);
            onCreate(sqLiteDatabase);
        }
    }
}
