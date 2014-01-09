package adm.werock.sportstats;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import adm.werock.sportstats.basics.User;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.OnErrorListener;

import android.app.Activity;
import android.os.AsyncTask;
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
		
		
		// Comprobar formato del mail
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

		  // Comprobar que los passwords sean iguales
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
		
		
		Button bCancel = (Button) findViewById(R.id.ButtonCancelRegistration);
		bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	launchCancelRegistration();
            }
        });
		
/*		// Obtener los datos para guardarlos en la base de datos
		userNameString=userName.getText().toString();
        passwordString=password.getText().toString();
        mailString=userMail.getText().toString();*/
        
        
        Button bRegister = (Button) findViewById(R.id.ButtonRegister);
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	if(daoUser.addUser(new User(userMail.getText().toString().trim(), userName.getText().toString().trim(), userPassword.getText().toString().trim())))
            		launchMyActs();
            	
            }
        });
	
	}



	protected void launchCancelRegistration() {
		// TODO Auto-generated method stub
		this.finish();
	}
	
	protected void launchRegistration(String email, String name, String pass){
		
		PostNewUserTask task = new PostNewUserTask();
        task.execute();
		
	}



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
	
	private void launchMyActs(){
		Intent i = new Intent(this, MyActsActivity.class);
        startActivity(i);
	}
	
	
	private class PostNewUserTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
        }

        @Override
        protected Void doInBackground(Void... params) {
        // TODO Auto-generated method stub
        	//DAOUsers.addUser(mailString, userNameString, passwordString);
			return null;
            
        }

    }
	
}
