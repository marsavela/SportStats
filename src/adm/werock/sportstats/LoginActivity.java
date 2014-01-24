package adm.werock.sportstats;

import java.util.ArrayList;
import java.util.Arrays;

import adm.werock.sportstats.basics.Act;
import adm.werock.sportstats.basics.League;
import adm.werock.sportstats.basics.Player;
import adm.werock.sportstats.basics.Team;
import adm.werock.sportstats.basics.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.OnErrorListener;

import dao.DAOUsers;

public class LoginActivity extends Activity {
	
	EditText userMail;
	EditText userPassword;

	String userMailString="null";
 	String userPasswordString="null";
 	
 	ArrayList<League> leaguesList = new ArrayList<League>();
	ArrayList<Team> teamsList = new ArrayList<Team>();
	ArrayList<Player> playersList = new ArrayList<Player>();
	ArrayList<Act> actsList = new ArrayList<Act>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);  
		
        final DAOUsers daoUser = new DAOUsers(this);
		

		// Register Button
		Button bRegister = (Button) findViewById(R.id.ButtonNewUser);
		bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	launchRegister();
            }
        });
		
		  
		// Login button with facebook
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
	                                      Toast.makeText(null, user.getFirstName(), Toast.LENGTH_LONG).show();
	                                      launchMyActs();
	                                    }
	                                }
	                            });
	          }else if(session.isClosed()) {
	          }
	         }
	        });
	    
	 
	    // Get user mail and password to login
	    userMail = (EditText) findViewById(R.id.loginMail);
	    userPassword = (EditText) findViewById(R.id.loginPassword);
	 	
		// Boton Login
		Button bLogin = (Button) findViewById(R.id.ButtonLogin);
		bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	if(daoUser.checkUser(new User(userMail.getText().toString().trim(), userPassword.getText().toString().trim())))
            		launchMyActs();
            	}
        });
		
	}
	
	// Facebook session 
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,
				resultCode, data);
	
	}
	
	private void launchMyActs(){
		Intent i = new Intent(this, MyActsActivity.class);
        startActivity(i);
	}
	
	private void launchRegister(){
		Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
	}

}
