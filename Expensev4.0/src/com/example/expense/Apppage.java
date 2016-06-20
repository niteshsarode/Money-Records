package com.example.expense;




import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Apppage extends Activity {
	
	String action;

	LoginDataBaseAdapter logindatabaseadapter;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apppage);
		
		getActionBar().setTitle("Select Choice");   
		
		final Myappdatabasehelper obj = new Myappdatabasehelper(this);
		 final Dialog dialog1 = new Dialog(Apppage.this);
			dialog1.setContentView(R.layout.addperson);
			dialog1.setTitle(R.string.enter_name);
		
		final EditText editaddperson =(EditText) dialog1.findViewById(R.id.editaddperson);
			
		
		Button b1=(Button) findViewById(R.id.b1);
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				dialog1.show();
			}
		});
		
		
		
		Button addbtn =(Button) dialog1.findViewById(R.id.addbtn);
		addbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(editaddperson.getText().toString().equals("") || editaddperson.getText().toString().equals(null) )
				{
					Toast.makeText(getBaseContext(), "No Person Added", Toast.LENGTH_SHORT).show();
				}
				else
				{
					obj.savename(editaddperson.getText().toString());
					Toast.makeText(getBaseContext(), "Person Added", Toast.LENGTH_SHORT).show();
					editaddperson.setText(null);
				}
				dialog1.dismiss();
			}
		});
		
		
		Button b2=(Button) findViewById(R.id.b2);
		b2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent=new Intent(Apppage.this,Amount.class);
				intent.putExtra("thestring",editaddperson.getText().toString());
				startActivity(intent);
				
			}
		});
			
		
		Button b3=(Button) findViewById(R.id.b3);
		b3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i= new Intent(Apppage.this,Data.class);
				startActivity(i);
				
			}
		});
		
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		Button b4=(Button) findViewById(R.id.b4);
		b4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
				        switch (which){
				        case DialogInterface.BUTTON_POSITIVE:
				            //Yes button clicked
				        	obj.delete(getBaseContext());
							Toast.makeText(getBaseContext(), "Database Deleted", Toast.LENGTH_SHORT).show();
				            break;

				        case DialogInterface.BUTTON_NEGATIVE:
				            //No button clicked
				            break;
				        }
				    }
				};

				
				builder.setMessage("Do you really want to delete Records?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
				
				
				
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
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		startActivity(new Intent(Apppage.this,MainActivity.class));
	}
	
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    	
    	final Dialog dialog = new Dialog(Apppage.this);
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
			startActivity(new Intent(Apppage.this, Login.class));
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
	    				Toast.makeText(getBaseContext(), "Password field cannot be vacant", Toast.LENGTH_SHORT).show();
	        			dialog.dismiss();
					}
	        		else
	        		{
	        			logindatabaseadapter.updateEntry(editname.getText().toString(),oldpass.getText().toString(),newpass.getText().toString());
	        			startActivity(new Intent(Apppage.this,Login.class));
	        			dialog.dismiss();
	        		}
				}
			});
			
			return true;
        }
        
        return super.onOptionsItemSelected(item);
    }

    public void setActionBar(String heading) {
        // TODO Auto-generated method stub

        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(heading);
        actionBar.show();

    }
    
    
}
