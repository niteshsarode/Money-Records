package com.example.expense;



import java.util.Calendar;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class Amount extends Activity {

	private Myappdatabasehelper dbhelper;
	private Myappdatabasehelper1 dbhelper1;

	private
	int year;
	int month;
	int day;

	final int DATE_PICKER_ID = 1111;	
	
	LoginDataBaseAdapter logindatabaseadapter;




	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.amount);

		final EditText editwhat=(EditText) findViewById(R.id.editonwhat);
		final EditText editamount=(EditText) findViewById(R.id.editText4);
		final EditText editdate=(EditText) findViewById(R.id.editText3);
		final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		final Spinner spinner = (Spinner) findViewById(R.id.spinner);

		Button B=(Button) findViewById(R.id.button1);


		Button datebtn = (Button) findViewById(R.id.button2);


		final Calendar c = Calendar.getInstance();
		year= c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day   = c.get(Calendar.DAY_OF_MONTH);

		editdate.setText(new StringBuilder()
		.append(month + 1).append("-").append(day).append("-")
		.append(year).append(" "));


		datebtn.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {

				// On button click show datepicker dialog
				showDialog(DATE_PICKER_ID);

			}

		});
		
		
		
		
		dbhelper=new Myappdatabasehelper(this);
		dbhelper1=new Myappdatabasehelper1(this);
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,dbhelper.getnames());
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
		spinner.setPrompt("Select a name");
		
		
		
		
		
		B.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(spinner.getSelectedItem().toString().equals("") || editamount.getText().toString().equals("") || editdate.getText().toString().equals("") )
				{
					Toast.makeText(getBaseContext(), "Field  Vacant", Toast.LENGTH_SHORT).show();
					startActivity(new Intent(Amount.this, Apppage.class));								
				}
				else
				{
					int selectedid = radioGroup.getCheckedRadioButtonId();
					RadioButton rdbtn = (RadioButton) findViewById(selectedid);
					String amt = editamount.getText().toString();
					int amount= Integer.parseInt(amt); 
					dbhelper.saveAction(spinner.getSelectedItem().toString(),rdbtn.getText().toString(),amount);
					dbhelper1.saveindividual(spinner.getSelectedItem().toString(),editwhat.getText().toString(),rdbtn.getText().toString(),amount, editdate.getText().toString());
					startActivity(new Intent(Amount.this, Apppage.class));
					Toast.makeText(getBaseContext(),"Record Added",Toast.LENGTH_SHORT).show();
				}
			}
		});




	}

	

	/* (non-Javadoc)
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		
	}



	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_PICKER_ID:

			// open datepicker dialog.
			// set date picker for current date
			// add pickerListener listner to date picker
			return new DatePickerDialog(this, pickerListener, year, month,day);
		}
		return null;
	}

	final DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		@Override
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			EditText editdate = (EditText) findViewById(R.id.editText3);
			
			year  = selectedYear;
			month = selectedMonth;
			day   = selectedDay;

			// Show selected date
			editdate.setText(new StringBuilder().append(month + 1).append("-").append(day).append("-").append(year).append(" "));

		}
	};




	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		startActivity(new Intent(Amount.this,Apppage.class));
	}

	public void setActionBar(String heading) {
		// TODO Auto-generated method stub

		ActionBar actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setTitle(heading);
		actionBar.show();

	}



	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		final Dialog dialog = new Dialog(Amount.this);
		dialog.setContentView(R.layout.changepass);
		
		final EditText oldpass=(EditText) dialog.findViewById(R.id.editold);
        final EditText newpass=(EditText) dialog.findViewById(R.id.editnew);
        final EditText editname=(EditText) dialog.findViewById(R.id.editname);
		
        Button passwordbtn =(Button) dialog.findViewById(R.id.passwordbtn);
		
		int id = item.getItemId();
        if (id == R.id.action_settings) {
        	startActivity(new Intent(this,Copyright.class));

            return true;
        }
        else if(id == R.id.logout_settings){
			startActivity(new Intent(Amount.this, Login.class));
			return true;
        }
        else if(id==R.id.changeacc_settings){
			dialog.show();
			dialog.setTitle("Change Details");
			
			passwordbtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if(newpass.getText().toString().equals(null) || newpass.getText().toString().equals(""))
					{
	        			dialog.dismiss();
					}
	        		else
	        		{
	        			logindatabaseadapter.updateEntry(editname.getText().toString(),oldpass.getText().toString(),newpass.getText().toString());
	        			startActivity(new Intent(Amount.this,Login.class));
	        			dialog.dismiss();
	        		}
				}
			});
			
			return true;
        }
        
		return super.onOptionsItemSelected(item);

	}


}
