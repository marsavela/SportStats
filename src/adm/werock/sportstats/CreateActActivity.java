package adm.werock.sportstats;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.View;

public class CreateActActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_act);

		Spinner sp = (Spinner) findViewById(R.id.spinner);
		/*		ArrayAdapter adapter = new ArrayAdapter(this, R.array.sports, R.layout.spinner_item);
		 */
		ArrayAdapter adapter = ArrayAdapter.createFromResource(
				this, R.array.sports, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp.setAdapter(adapter);

		sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, 
					int position, long id) {
				Toast.makeText(parentView.getContext(), getResources().getText(R.string.ToastSpinnerAdapter) + " " +
						parentView.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();

			}

			public void onNothingSelected(AdapterView<?> parentView) {

			}
		});        
	}

	public void newAct(View view){
		//Check the wifi and 3g connection//////////////
		ConnectivityManager manager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
		CheckConnection check = new CheckConnection();
		//For 3G check
		boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.isConnectedOrConnecting();
		//For WiFi Check
		boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.isConnectedOrConnecting();


		Intent i =null;
		if(is3g||isWifi)		
			i = new Intent(this, ActivityChooseTeamOnline.class);
		else i = new Intent(this, ActivityChooseTeamOffline.class);


		startActivity(i);


	}


}