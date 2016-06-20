package com.example.expense;


import android.app.Activity;
import android.app.Dialog;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class Data extends Activity {
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	
	String type="";
	int amount=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.data);
		
		
		Button homebtn = (Button) findViewById(R.id.button2);
		Button indivbtn = (Button) findViewById(R.id.indivbtn);;
		final TextView tv=(TextView) findViewById(R.id.textView1);
		tv.setMovementMethod(new ScrollingMovementMethod());
		
		
		final Myappdatabasehelper1 obj1 =new Myappdatabasehelper1(this);
		final Myappdatabasehelper obj = new Myappdatabasehelper(this);
		obj.open();
		obj1.open();
		final String base=obj.getdata();
		tv.setText(base);
		
		homebtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Data.this,MainActivity.class));
				
			}
		});
		
		
		final Dialog dialog = new Dialog(Data.this);
		dialog.setContentView(R.layout.individual);
		dialog.setTitle("Select a name");
		
		final Button gobtn = (Button) dialog.findViewById(R.id.gobtn);
		final Spinner spinforindiv =(Spinner) dialog.findViewById(R.id.spinforindiv);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,obj.getnames());
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinforindiv.setAdapter(dataAdapter);
		
		
		indivbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.show();
				
			}
		});
		
		final TextView recordfortxt = (TextView) findViewById(R.id.textView2);
		
		final Dialog dialog1 = new Dialog(Data.this);
		dialog1.setContentView(R.layout.delindiv);
		dialog1.setTitle("Select a name");
		
		final Button gobtn1 = (Button) dialog1.findViewById(R.id.gobtn1);
		final Spinner spinforindiv1 =(Spinner) dialog1.findViewById(R.id.spinforindiv1);
		ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,obj.getnames());
		dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinforindiv1.setAdapter(dataAdapter1);
		
		Button delbtn = (Button) findViewById(R.id.delbtn);
		delbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog1.show();
			}
		});
		
		gobtn1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(spinforindiv1.getSelectedItem().equals("") || spinforindiv1.getSelectedItem().equals(null))
				{
					dialog1.dismiss();
				}
				else
				{
					obj1.deleteall(spinforindiv1.getSelectedItem().toString());
					obj.deletename(spinforindiv1.getSelectedItem().toString());
					String records=obj.getdata();
					tv.setText(records);
					dialog1.dismiss();
				}
			}
		});
		
		
		
		gobtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				if(spinforindiv.getSelectedItem().equals("") || spinforindiv.getSelectedItem().equals(null))
				{
					dialog.dismiss();
				}
				else
				{
					recordfortxt.setText("Records for " + spinforindiv.getSelectedItem().toString());
					String base2=obj1.displayindividual(spinforindiv.getSelectedItem().toString());
					tv.setText(base2);
					dialog.dismiss();
				}
			}
		});
		
		
		
		
		
		
		
		
		final Dialog dialog2 = new Dialog(Data.this);
		dialog2.setContentView(R.layout.delrecord);
		dialog2.setTitle("Select Name and Item");
		
		
		final Button okbtn = (Button) dialog2.findViewById(R.id.okbtn);
		final Spinner spinforname = (Spinner) dialog2.findViewById(R.id.spinforname);
		final Button gobtn2 = (Button) dialog2.findViewById(R.id.gobtn2);
		final Spinner spinforindiv2 =(Spinner) dialog2.findViewById(R.id.spinforindiv2);
		
		final ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
		
		final ArrayAdapter<String> dataAdapter3 =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,obj.getnames());
		dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinforname.setAdapter(dataAdapter3);
		
		Button delrecbtn = (Button) findViewById(R.id.delrecbtn);
		delrecbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog2.show();
			}
		});
		
		okbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				spinforindiv2.clearFocus();
				spinforindiv.refreshDrawableState();
				dataAdapter2.addAll(obj1.getitems(spinforname.getSelectedItem().toString()));
				dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinforindiv2.setAdapter(dataAdapter2);
			}
		});
		
		
		
		
		gobtn2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(spinforindiv2.getSelectedItem().equals("") || spinforindiv2.getSelectedItem().equals(null))
				{
					dialog2.dismiss();
				}
				else
				{
					type=obj1.gettype(spinforname.getSelectedItem().toString(), spinforindiv2.getSelectedItem().toString());
					amount=obj1.getamount(spinforname.getSelectedItem().toString(), spinforindiv2.getSelectedItem().toString());
					obj.calculate(spinforname.getSelectedItem().toString(),spinforindiv2.getSelectedItem().toString(),type,amount);
					obj1.deleterecord(spinforindiv2.getSelectedItem().toString());
					String base=obj.getdata();
					tv.setText(base);
					dialog2.dismiss();
				}
			}
		});
	}

	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		startActivity(new Intent(Data.this,Apppage.class));
	}


	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
        if (id == R.id.action_settings) {
        	startActivity(new Intent(this,Copyright.class));
            return true;
        }
		
		return super.onOptionsItemSelected(item);
	}
	
	
}
