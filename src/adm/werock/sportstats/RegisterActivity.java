package adm.werock.sportstats;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
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
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.EditText;

public class RegisterActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		final EditText myTextBox = (EditText) findViewById(R.id.registerMail);
		  myTextBox.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				String answerString = myTextBox.getText().toString();
				EditText mail = (EditText)findViewById(R.id.registerMail);
				if( mail.getText().toString().length() == 0 )
					mail.setError( "Mail is required!" );
				else if( !isEmailValid(answerString) )
					mail.setError( "Mail format not correct!" );
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
		//		String answerString = myTextBox.getText().toString();
		//		EditText email = (EditText)findViewById(R.id.registerMail);
		//		if( !isEmailValid(answerString) )
		//			email.setError( "Mail format not correct!" );
			}
		  });

		final EditText password = (EditText) findViewById(R.id.registerPassword);
		final EditText password2 = (EditText) findViewById(R.id.registerPassword2);
		password2.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				String passwordString = password.getText().toString();
				String password2String = password2.getText().toString();

				if (!password2String.equals(passwordString))
					password2.setError("Passwords are not the same!");
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
	
}
