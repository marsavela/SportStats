package adm.werock.sportstats;

import java.util.Arrays;

import com.facebook.FacebookException;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.OnErrorListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		Button bLogin = (Button) findViewById(R.id.ButtonLogin);
		bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	launchMyActs();
            }
        });
		
		Button bRegister = (Button) findViewById(R.id.ButtonNewUser);
		bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	launchRegister();
            }
        });
		
		//	final TextView txtSaludo = (TextView)findViewById(R.id.txtSaludo);
		  
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
		
	}
	
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
