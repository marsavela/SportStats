package adm.werock.sportstats;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import adm.werock.sportstats.basics.User;
import android.content.Intent;
import android.widget.Button;

import dao.DAOUsers;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;



public class RegisterActivity extends Activity{

	EditText userMail;
	EditText userName;
	EditText userPassword;
	EditText userPassword2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		final DAOUsers daoUser = new DAOUsers(this);
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		
		// Check email format
		userMail = (EditText) findViewById(R.id.registerMail);
		userMail.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				String answerString = userMail.getText().toString();
				EditText userMail = (EditText)findViewById(R.id.registerMail);
				if( userMail.getText().toString().length() == 0 )
					userMail.setError( "Mail is required!" );
				else if( !isEmailValid(answerString) )
					userMail.setError( "Mail format not correct!" );
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
			}
		  });

		  // Check that the passwords are the same
		  userName = (EditText) findViewById(R.id.registerName);
		  userPassword = (EditText) findViewById(R.id.registerPassword);
		  userPassword2 = (EditText) findViewById(R.id.registerPassword2);
		  
		  userPassword2.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				String passwordString = userPassword.getText().toString();
				String password2String = userPassword2.getText().toString();
				if (!password2String.equals(passwordString))
					userPassword2.setError("Passwords are not the same!");
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
			}
		});
		
		// Cancel button
		Button bCancel = (Button) findViewById(R.id.ButtonCancelRegistration);
		bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	launchCancelRegistration();
            }
        });
        
		// Register Button
        Button bRegister = (Button) findViewById(R.id.ButtonRegister);
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	if(daoUser.addUser(new User(userMail.getText().toString().trim(), userName.getText().toString().trim(), userPassword.getText().toString().trim())))
            		launchMyActs();
            	
            }
        });
	
	}


	// If cancel button clicked, we finish the register activity and go back to login
	protected void launchCancelRegistration() {
		// TODO Auto-generated method stub
		this.finish();
	}


	// isEmailValid checks if the email is correct
	public static boolean isEmailValid(String email) {
	    boolean isValid = false;

	    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	    CharSequence inputStr = (CharSequence) email;

	    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(inputStr);
	    if (matcher.matches()) {
	        isValid = true;
	    }
	    return isValid;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}
	
	// launch MyActs activity
	private void launchMyActs(){
		Intent i = new Intent(this, MyActsActivity.class);
        startActivity(i);
	}
	
}
