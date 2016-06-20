package com.example.expense;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivity extends Activity {

	
	LoginDataBaseAdapter logindatabaseadapter;
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
       LoginDataBaseAdapter obj =new LoginDataBaseAdapter(this);
       String name="Welcome";
       
       if(getIntent().hasExtra("thepassword"))
       {
    	String password=getIntent().getExtras().getString("thepassword");
        name=obj.getname(password);
		getActionBar().setTitle("Welcome" + " " + name);
       }
       else
    	   getActionBar().setTitle(name);
       
       
       
       ImageButton readybtn = (ImageButton) findViewById(R.id.readybtn);
       readybtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			startActivity(new Intent(MainActivity.this, Apppage.class));

		}
	});
       
       
        logindatabaseadapter = new LoginDataBaseAdapter(this);
        logindatabaseadapter = logindatabaseadapter.open();
        
        
        

		
        
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
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		startActivity(new Intent(MainActivity.this,Login.class));
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
    	
    	final Dialog dialog = new Dialog(MainActivity.this);
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
			startActivity(new Intent(MainActivity.this,Login.class));
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
	        			startActivity(new Intent(MainActivity.this,Login.class));
	        			dialog.dismiss();
	        		}
	    		}
	    	});
			
			return true;
        }
  
        
        return super.onOptionsItemSelected(item);
    }

}
