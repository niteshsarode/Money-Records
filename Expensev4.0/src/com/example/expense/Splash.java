package com.example.expense;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity{

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		getActionBar().hide();
		
		LoginDataBaseAdapter logindatabaseadapter = new LoginDataBaseAdapter(this);
		logindatabaseadapter.open();
		
		if(logindatabaseadapter.checkForTables()==false)
			logindatabaseadapter.insertEntry("Monster", "12345");
		
		
		Thread timer = new Thread(){
			public void run(){
				try{
					sleep(2250);
				}catch(InterruptedException e){
					e.printStackTrace();
				}finally{
					startActivity(new Intent(Splash.this,Login.class));

				}
			}
		};
		
		timer.start();
		
		
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		   
	}
	
}
