package com.example.expense;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class LoginDataBaseAdapter {
	static final String DATABASE_NAME = "login.db";
	static final int DATABASE_VERSION = 1;
	public static final int NAME_COLUMN = 1;
	// TODO: Create public field for each column in your table.
	public static final String ID="_id";
	public static final String FULLNAME="name";
	public static final String PASSWORD="password";

	// SQL Statement to create a new database.
	static final String DATABASE_CREATE = "create table "+"LOGIN"+
			"( " +"ID"+" integer primary key autoincrement,"+  " FULLNAME text,PASSWORD text); ";



	// Variable to hold the database instance
	public static  SQLiteDatabase db;
	// Context of the application using the database.
	private final Context context;
	// Database open/upgrade helper
	private  Mysignupdatabasehelper dbHelper;
	public  LoginDataBaseAdapter(Context _context)
	{
		context = _context;
		dbHelper = new Mysignupdatabasehelper(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	public  LoginDataBaseAdapter open() throws SQLException
	{
		db = dbHelper.getWritableDatabase();
		return this;
	}
	public void close()
	{
		db.close();
	}

	public  SQLiteDatabase getDatabaseInstance()
	{
		return db;
	}

	public void delete(Context context)
	{
		context.deleteDatabase(DATABASE_NAME);
	}

	public void insertEntry(String Name,String password)
	{
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put("FULLNAME",Name);
		newValues.put("PASSWORD",password);

		// Insert the row into your table
		db.insert("LOGIN", null, newValues);
		Toast.makeText(context, "GOOD TO GO !", Toast.LENGTH_LONG).show();
	}

	public int deleteEntry(String UserName)
	{
		//String id=String.valueOf(ID);
		String where="USERNAME=?";
		int numberOFEntriesDeleted= db.delete("LOGIN", where, new String[]{UserName}) ;
		// Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
		return numberOFEntriesDeleted;
	}    

	public String getSinlgeEntry(String password)
	{
		Cursor cursor=db.query("LOGIN", null, " PASSWORD=?", new String[]{password}, null, null, null);
		String passwordorg;

		if(cursor.getCount()<1) 
		{
			cursor.close();
			return "NOT EXIST";
		}

		cursor.moveToFirst(); 
		passwordorg= cursor.getString(cursor.getColumnIndex("PASSWORD"));
		cursor.close();
		return passwordorg;                
	}

	public String getname(String password)
	{
		String[] columns = new String[]{"ID","FULLNAME","PASSWORD"};
		Cursor cursor=db.query("LOGIN",columns, " PASSWORD=?", new String[]{password}, null, null, null);
		String name = "";

		int iName = cursor.getColumnIndex("FULLNAME");

		if(cursor.getCount()<1) 
		{
			cursor.close();
			return "NOT EXIST";
		}
		for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
		{ 
			name=cursor.getString(iName);
		}
		return name;
	}

	public boolean checkForTables(){
        boolean hasTables = false;

        
        Cursor cursor = db.rawQuery("SELECT * FROM " + "LOGIN", null);

        if(cursor.getCount() == 0){
            hasTables=false;
        }
        
        if(cursor.getCount() > 0){
            hasTables=true;
        }

        cursor.close();
        return hasTables;
    }
	

	public void  updateEntry(String Name,String old,String password)
	{

		String[] columns = new String[]{"ID","FULLNAME","PASSWORD"};
		Cursor cursor=db.query("LOGIN",columns, " PASSWORD=?", new String[]{old}, null, null, null);
		String passwordold = "";

		int iPassword = cursor.getColumnIndex("PASSWORD");

		if(cursor.getCount()<1) 
		{
			cursor.close();
		}
		for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
		{ 
			passwordold=cursor.getString(iPassword);
		}

		if(old.equals(passwordold))
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("FULLNAME", Name);
			updatedValues.put("PASSWORD", password);

			db.update("LOGIN",updatedValues,"PASSWORD" ,null); 
		}
		else
			Toast.makeText(context,"Password Incorrect ", Toast.LENGTH_SHORT).show();
	}


}
