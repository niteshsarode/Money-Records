package com.example.expense;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Login extends Activity {

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	LoginDataBaseAdapter logindatabaseadapter = new LoginDataBaseAdapter(this);
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		getActionBar().hide();
		
		logindatabaseadapter=logindatabaseadapter.open();
		
		final EditText editpass =(EditText) findViewById(R.id.editpass);
		Button gobtn =(Button) findViewById(R.id.gobtn);
		
		
		ImageButton passimgbtn = (ImageButton) findViewById(R.id.passimgbtn);
		passimgbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getBaseContext(), "Default Password is 12345", Toast.LENGTH_SHORT).show();
			}
		});
		
		
		
		gobtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String passwordorg = logindatabaseadapter.getSinlgeEntry(editpass.getText().toString());

				if(editpass.getText().toString().equals("") || editpass.getText().toString().equals(null))
					Toast.makeText(getBaseContext(), "Password", Toast.LENGTH_SHORT).show();
				else if(editpass.getText().toString().equals(passwordorg) )
				{
					Intent intent =new Intent(Login.this,MainActivity.class);
					intent.putExtra("thepassword",editpass.getText().toString());
					startActivity(intent);
					Toast.makeText(getBaseContext(),"Login Successful", Toast.LENGTH_SHORT).show();
				}
				else
				{
					Toast.makeText(getBaseContext(),"Invalid Password", Toast.LENGTH_SHORT).show();
					editpass.setText("");
				}
			}
		});
		
		
	}

	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);


	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}

	
}
