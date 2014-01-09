package adm.werock.sportstats;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.FacebookException;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.OnErrorListener;

import dao.DAOUsers;

import adm.werock.sportstats.basics.User;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	EditText userMail;
	EditText userPassword;

//	private static DAOUsers daoUser;
	String userMailString="null";
 	String userPasswordString="null";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);  
		
        final DAOUsers daoUser = new DAOUsers(this);
		

		
		// Boton Registrar
		Button bRegister = (Button) findViewById(R.id.ButtonNewUser);
		bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	launchRegister();
            }
        });
		
		// Boton Skip
		Button bSkip = (Button) findViewById(R.id.ButtonSkip);
		bSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	launchMyActs();
            }
        });
		
		//	final TextView txtSaludo = (TextView)findViewById(R.id.txtSaludo);
		  
		// Boton Login mediante Facebook
		LoginButton authButton = (LoginButton) findViewById(R.id.login_button);
	      authButton.setOnErrorListener(new OnErrorListener() {
	       
	       @Override
	       public void onError(FacebookException error) {
	         
	       }
	    });
	      
	    authButton.setReadPermissions(Arrays.asList("basic_info","email"));
	  
	    authButton.setSessionStatusCallback(new Session.StatusCallback() {
	         
	        
			@Override
	         public void call(Session session, SessionState state, Exception exception) {
	          
	          if (session.isOpened()) {  
	        	  launchMyActs();
	        	  		Request.newMeRequest(session,
	                            new Request.GraphUserCallback() {
	                                @Override
	                                public void onCompleted(GraphUser user,Response response) {
	                                    if (user != null) {
	                     //                 txtSaludo.setText("!Bienvenido " + user.getFirstName() + "!");   
	                                      Toast.makeText(null, user.getFirstName(), Toast.LENGTH_LONG).show();
	                                      launchMyActs();
	                                    }
	                                }
	                            });
	          }else if(session.isClosed()) {
	//        	  txtSaludo.setText("!Bienvenido!");
	          }
	         }
	        });
	    
	 
	    // Obtener los datos para guardarlos en la base de datos
	    userMail = (EditText) findViewById(R.id.loginMail);
	    userPassword = (EditText) findViewById(R.id.loginPassword);
	 //   if (userMail.getText().toString().length() != 0) {
	  //  	userMailString = String.valueOf(userMail.getText());
	  //  }
	 //	userMailString=userMail.getText().toString().trim();
	 //	userPasswordString=userPassword.getText().toString();
	 	
	 	
		// Boton Login
		Button bLogin = (Button) findViewById(R.id.ButtonLogin);
		bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //	if(daoUser.checkUser(new User("franjmaca@gmail.com","123"))){
        //    	Log.i("hola", userMailString);
            	if(daoUser.checkUser(new User(userMail.getText().toString().trim(), userPassword.getText().toString().trim())))
            		launchMyActs();
            	}
            
        });
		
		
		
	}
	
	// Sesion Facebook 
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,
				resultCode, data);
	
	}
	
	private void loginUser(){
		
		//DAOUsers.checkUser(userMailString, userPasswordString);
		GetUserTask task = new GetUserTask();
        task.execute();
		
	}
	
	private void launchMyActs(){
		Intent i = new Intent(this, MyActsActivity.class);
        startActivity(i);
	}
	
	private void launchRegister(){
		Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
	}

	
	private class GetUserTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
        }

        @Override
        protected Void doInBackground(Void... params) {
        // TODO Auto-generated method stub
        	//DAOUsers.checkUser(userMailString, userPasswordString);
			return null;
            
        }

    }
}
