package adm.werock.sportstats;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
	}
	
	private void launchMyActs(){
		Intent i = new Intent(this, MyActsActivity.class);
        startActivity(i);
	}

}
