package com.example.expense;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Myappdatabasehelper {

	private static final int DATABASE_VERSION=3;
	private static final String DATABASE_NAME="moneyrecords.db";
	private static final String TABLE_NAME="moneyrecords";
	private final Context ourContext;


	public static final String MONEYRECORDS_COLUMN_ID="_id";
	public static final String MONEYRECORDS_COLUMN_ACTION="action";
	public static final String MONEYRECORDS_COLUMN_TYPE="type";
	public static final String MONEYRECORDS_COLUMN_ONWHAT="onwhat";
	public static final String MONEYRECORDS_COLUMN_AMOUNT="amount";
	public static final String MONEYRECORDS_COLUMN_DATE="date";

	private Myappdatabase mydb;
	private SQLiteDatabase database;

	public Myappdatabasehelper(Context context)
	{
		ourContext=context;
		mydb=new Myappdatabase(context);
		database=mydb.getWritableDatabase();
	}

	public void delete(Context context)
	{
		context.deleteDatabase(DATABASE_NAME);
	}


	public void savename(String name)
	{
		ContentValues contentvalues = new ContentValues();

		contentvalues.put(MONEYRECORDS_COLUMN_ACTION, name);

		database.insert(TABLE_NAME, null, contentvalues);
	}


	public void saveAction(String act, String type, int amount) {
		// TODO Auto-generated method stub
		ContentValues contentValues= new ContentValues();
		
		String[] columns=new String[]{MONEYRECORDS_COLUMN_ID, MONEYRECORDS_COLUMN_ACTION, MONEYRECORDS_COLUMN_TYPE,MONEYRECORDS_COLUMN_ONWHAT ,MONEYRECORDS_COLUMN_AMOUNT,MONEYRECORDS_COLUMN_DATE};
		Cursor c=database.query(TABLE_NAME, columns, null,null,null,null, null, null);

		int iName=c.getColumnIndex(MONEYRECORDS_COLUMN_ACTION);
		int iAmount=c.getColumnIndex(MONEYRECORDS_COLUMN_AMOUNT);
		int amt = 0;

		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
		{ 
			if(c.getString(iName).equals(act))
			{
				if(type.equals("Borrow"))
					amt=c.getInt(iAmount)-amount;
				else if(type.equals("Lend"))
					amt=c.getInt(iAmount)+amount;
			}
		}
		
		String type1="";
		if(amt<0)
			type1="Give to";
		else if(amt>0)
			type1="Take from";
		else if(amt==0)
			type1="";
			
		
		contentValues.put(MONEYRECORDS_COLUMN_TYPE, type1);
		contentValues.put(MONEYRECORDS_COLUMN_AMOUNT, amt);
		
		database.update(TABLE_NAME,contentValues,MONEYRECORDS_COLUMN_ACTION + " = '" + act + "'",null);

	}
    
    public void calculate(String name,String item,String type,int amount){
		String[] columns=new String[]{MONEYRECORDS_COLUMN_ID, MONEYRECORDS_COLUMN_ACTION, MONEYRECORDS_COLUMN_TYPE,MONEYRECORDS_COLUMN_ONWHAT ,MONEYRECORDS_COLUMN_AMOUNT,MONEYRECORDS_COLUMN_DATE};
		Cursor c=database.query(TABLE_NAME, columns, null,null,null,null, null, null);

		int iName=c.getColumnIndex(MONEYRECORDS_COLUMN_ACTION);
		int iAmount=c.getColumnIndex(MONEYRECORDS_COLUMN_AMOUNT);
		int amt = 0;

		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
		{ 
			if(c.getString(iName).equals(name))
			{
				if(type.equals("Borrow"))
					amt=c.getInt(iAmount)+amount;
				else if(type.equals("Lend"))
					amt=c.getInt(iAmount)-amount;
			}	
		}
		
		String type1="";
		if(amt<0)
			type1="Give to";
		else if(amt>0)
			type1="Take from";
		else if(amt==0)
			type1=" ";
		
		ContentValues contentValues= new ContentValues();
		
		contentValues.put(MONEYRECORDS_COLUMN_TYPE, type1);
		contentValues.put(MONEYRECORDS_COLUMN_AMOUNT,amt);
		
		database.update(TABLE_NAME,contentValues,MONEYRECORDS_COLUMN_ACTION + " = '" + name + "'",null);
	}
    
	public void close()
	{
		mydb.close();
	}



	public Myappdatabasehelper open() throws  SQLException
	{
		mydb=new Myappdatabase(ourContext);
		database=mydb.getWritableDatabase();
		return this;

	}


	private class Myappdatabase  extends SQLiteOpenHelper  {

		Myappdatabase(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub

		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub

			db.execSQL(
					"CREATE TABLE " + TABLE_NAME + "( " +MONEYRECORDS_COLUMN_ID+ " INTEGER PRIMARY KEY, "+MONEYRECORDS_COLUMN_ACTION+" TEXT,"+MONEYRECORDS_COLUMN_TYPE+" TEXT,"+MONEYRECORDS_COLUMN_ONWHAT+" TEXT, "+MONEYRECORDS_COLUMN_AMOUNT+" TEXT, "+MONEYRECORDS_COLUMN_DATE+" TEXT)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}


	}

	public String getdata()
	{

		String[] columns=new String[]{MONEYRECORDS_COLUMN_ID, MONEYRECORDS_COLUMN_ACTION, MONEYRECORDS_COLUMN_TYPE, MONEYRECORDS_COLUMN_AMOUNT};
		Cursor c=database.query(TABLE_NAME, columns, null,null,null,null, null, null);
		String result="";

		int iName=c.getColumnIndex(MONEYRECORDS_COLUMN_ACTION);
		int iType=c.getColumnIndex(MONEYRECORDS_COLUMN_TYPE);
		int iAmount=c.getColumnIndex(MONEYRECORDS_COLUMN_AMOUNT);
		int i=1;


		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
		{ 
			result=result + i + ".   " + c.getString(iType) + " " + c.getString(iName) + "  Rs." + c.getInt(iAmount) + "    " + "\n";
			i++;
		}
		return result;

	}

	public void deletename(String name)
	{
		database.delete(TABLE_NAME, "ACTION=?", new String[]{name});
	}

	public ArrayList<String> getnames()
	{

		String[] columns=new String[]{MONEYRECORDS_COLUMN_ID, MONEYRECORDS_COLUMN_ACTION, MONEYRECORDS_COLUMN_AMOUNT,MONEYRECORDS_COLUMN_DATE};
		Cursor c=database.query(TABLE_NAME, columns, null,null, null, null, null);



		int iName=c.getColumnIndex(MONEYRECORDS_COLUMN_ACTION);

		ArrayList<String> listnames = new ArrayList<String>();


		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
		{ 
			listnames.add(c.getString(iName));
		}
		return listnames;

	}
}




class Myappdatabasehelper1 {

	private static final int DATABASE_VERSION=3;

	private static final String DATABASE_NAME1="moneyrecords1.db";
	private static final String TABLE_NAME1="moneyrecords1";
	private final Context ourContext1;

	public static final String MONEYRECORDS_COLUMN_ID="_id";
	public static final String MONEYRECORDS_COLUMN_ACTION="action";
	public static final String MONEYRECORDS_COLUMN_TYPE="type";
	public static final String MONEYRECORDS_COLUMN_ONWHAT="onwhat";
	public static final String MONEYRECORDS_COLUMN_AMOUNT="amount";
	public static final String MONEYRECORDS_COLUMN_DATE="date";

	private Myappdatabase mydb1;
	private SQLiteDatabase database1;
	Myappdatabasehelper dbhelper;

	public Myappdatabasehelper1(Context context)
	{
		ourContext1=context;
		mydb1= new Myappdatabase(context);
		database1=mydb1.getWritableDatabase();
	}

	public void delete(Context context)
	{
		context.deleteDatabase(DATABASE_NAME1);
	}
    
    public void deleteall(String name)
	{
		database1.delete(TABLE_NAME1,"ACTION=?", new String[]{name});
	}

	public void deleterecord(String onwhat){

		database1.delete(TABLE_NAME1, "ONWHAT=?", new String[]{onwhat});

	}

	public ArrayList<String> getitems(String name)
	{

		String[] columns=new String[]{MONEYRECORDS_COLUMN_ID, MONEYRECORDS_COLUMN_ACTION, MONEYRECORDS_COLUMN_TYPE,MONEYRECORDS_COLUMN_ONWHAT ,MONEYRECORDS_COLUMN_AMOUNT,MONEYRECORDS_COLUMN_DATE};
		Cursor c=database1.query(TABLE_NAME1, columns, null,null, null, null, null);


		int iName=c.getColumnIndex(MONEYRECORDS_COLUMN_ACTION);
		int iOnwhat=c.getColumnIndex(MONEYRECORDS_COLUMN_ONWHAT);

		ArrayList<String> listitems = new ArrayList<String>();

		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
		{ 
			if(c.getString(iName).equals(name))
				listitems.add(c.getString(iOnwhat));
		}
		return listitems;

	}

	public String gettype(String name,String item){

		String type="";
		String[] columns=new String[]{MONEYRECORDS_COLUMN_ID, MONEYRECORDS_COLUMN_ACTION, MONEYRECORDS_COLUMN_TYPE,MONEYRECORDS_COLUMN_ONWHAT ,MONEYRECORDS_COLUMN_AMOUNT,MONEYRECORDS_COLUMN_DATE};
		Cursor c=database1.query(TABLE_NAME1, columns, null,null, null, null, null);


		int iName=c.getColumnIndex(MONEYRECORDS_COLUMN_ACTION);
		int iOnwhat=c.getColumnIndex(MONEYRECORDS_COLUMN_ONWHAT);
		int iType=c.getColumnIndex(MONEYRECORDS_COLUMN_TYPE);

		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
		{ 
			if(c.getString(iName).equals(name))
				if(c.getString(iOnwhat).equals(item))
					type=c.getString(iType);
		}

		return type;
	}

	public int getamount(String name,String item){

		int amount = 0;
		String[] columns=new String[]{MONEYRECORDS_COLUMN_ID, MONEYRECORDS_COLUMN_ACTION, MONEYRECORDS_COLUMN_TYPE,MONEYRECORDS_COLUMN_ONWHAT ,MONEYRECORDS_COLUMN_AMOUNT,MONEYRECORDS_COLUMN_DATE};
		Cursor c=database1.query(TABLE_NAME1, columns, null,null, null, null, null);


		int iName=c.getColumnIndex(MONEYRECORDS_COLUMN_ACTION);
		int iOnwhat=c.getColumnIndex(MONEYRECORDS_COLUMN_ONWHAT);
		int iAmount=c.getColumnIndex(MONEYRECORDS_COLUMN_AMOUNT);
		
		
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
		{ 
			if(c.getString(iName).equals(name))
				if(c.getString(iOnwhat).equals(item))
					amount=c.getInt(iAmount);
		}

		return amount;
	}
    
    

	public void saveindividual(String name,String onwhat,String type,int amount,String date)
	{
		ContentValues contentvalues = new ContentValues();

		contentvalues.put(MONEYRECORDS_COLUMN_ACTION,name);
		contentvalues.put(MONEYRECORDS_COLUMN_TYPE,type);
		contentvalues.put(MONEYRECORDS_COLUMN_ONWHAT, onwhat);
		contentvalues.put(MONEYRECORDS_COLUMN_AMOUNT, amount);
		contentvalues.put(MONEYRECORDS_COLUMN_DATE,date);

		String[] columns=new String[]{MONEYRECORDS_COLUMN_ID, MONEYRECORDS_COLUMN_ACTION, MONEYRECORDS_COLUMN_TYPE,MONEYRECORDS_COLUMN_ONWHAT ,MONEYRECORDS_COLUMN_AMOUNT,MONEYRECORDS_COLUMN_DATE};
		Cursor c=database1.query(TABLE_NAME1, columns, null,null,null,null, null, null);

		int iAmount=c.getColumnIndex(MONEYRECORDS_COLUMN_AMOUNT);
		int amt = 0;

		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
		{ 
			if(c.getString(iAmount).equals(name))
				amt=amount+c.getInt(iAmount);
		}

		database1.insert(TABLE_NAME1, null, contentvalues);
	}

	public String displayindividual(String name)
	{
		String[] columns=new String[]{MONEYRECORDS_COLUMN_ID, MONEYRECORDS_COLUMN_ACTION, MONEYRECORDS_COLUMN_TYPE,MONEYRECORDS_COLUMN_ONWHAT ,MONEYRECORDS_COLUMN_AMOUNT,MONEYRECORDS_COLUMN_DATE};
		Cursor c=database1.query(TABLE_NAME1, columns, null,null,null,null, null, null);
		String result="";

		int iName=c.getColumnIndex(MONEYRECORDS_COLUMN_ACTION);
		int itype=c.getColumnIndex(MONEYRECORDS_COLUMN_TYPE);
		int ionwhat=c.getColumnIndex(MONEYRECORDS_COLUMN_ONWHAT);
		int iAmount=c.getColumnIndex(MONEYRECORDS_COLUMN_AMOUNT);
		int iDate=c.getColumnIndex(MONEYRECORDS_COLUMN_DATE);
		int i=1;
		
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
		{ 
			if(c.getString(iName).equals(name))
			{
				result=result  + i + ". " + c.getString(itype) + " for "+c.getString(ionwhat) +" Rs." + c.getString(iAmount) + " \n   on " + c.getString(iDate) + "\n\n";
				i++;
			}
		}

		return result;
	}




	public void close()
	{

		mydb1.close();
	}



	public Myappdatabasehelper1 open() throws  SQLException
	{
		mydb1=new Myappdatabase(ourContext1);
		database1=mydb1.getWritableDatabase();
		return this;

	}


	private class Myappdatabase  extends SQLiteOpenHelper  {

		Myappdatabase(Context context) {
			super(context, DATABASE_NAME1, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub

		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub

			db.execSQL(
					"CREATE TABLE " + TABLE_NAME1 + "( " +MONEYRECORDS_COLUMN_ID+ " INTEGER PRIMARY KEY, "+MONEYRECORDS_COLUMN_ACTION+" TEXT,"+MONEYRECORDS_COLUMN_TYPE+" TEXT,"+MONEYRECORDS_COLUMN_ONWHAT+" TEXT, "+MONEYRECORDS_COLUMN_AMOUNT+" TEXT, "+MONEYRECORDS_COLUMN_DATE+" TEXT)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
			onCreate(db);
		}


	}

}

