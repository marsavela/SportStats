package adm.werock.sportstats;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class ActivityChooseTeamOffline extends Activity {
	
	String[] getLeagues(){

		String[] items = new String [] {"Nacional", "Autonómica", "Preferente", "1ª Zonal", "2ª Zonal"};
		return items;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_choose_team_offline);
		
		String[] leagues = getLeagues();
		Spinner leagueSpinner = (Spinner) findViewById(R.id.leagueSpinnerOffline);
		// Create an ArrayAdapter using the string array and a default spinner layout
				Adapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, leagues);
				((ArrayAdapter<String>) adapter).setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// And apply the adapter to the spinner
				leagueSpinner.setAdapter((SpinnerAdapter) adapter);
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_choose_team_offline, menu);
		return true;
	}

	
	public void newAct(View view){
		
		Spinner leagueSpinner = (Spinner) findViewById(R.id.leagueSpinnerOffline);
		EditText localTeamEditText = (EditText) findViewById(R.id.localTeamEditText);
		EditText awayTeamEditText = (EditText) findViewById(R.id.awayTeamEditText);
		if(localTeamEditText.getText().toString().compareTo("") == 0 ||
		   awayTeamEditText.getText().toString().compareTo("") == 0)
			Toast.makeText(getApplicationContext(), R.string.emptyTeams, Toast.LENGTH_SHORT).show();
		else if(localTeamEditText.getText().toString().compareTo(awayTeamEditText.getText().toString())==0)
			Toast.makeText(getApplicationContext(), R.string.sameTeams, Toast.LENGTH_SHORT).show();
		else{
			SharedPreferences pref = getSharedPreferences("teamPrefs", Context.MODE_PRIVATE);
			Editor editor = pref.edit();
			editor.putString("prefLeague",leagueSpinner.getSelectedItem().toString());
			editor.putString("prefVisitorTeam", awayTeamEditText.getText().toString());
			editor.putString("prefLocalTeam", localTeamEditText.getText().toString());
			editor.commit();
			Intent i = new Intent(this, ActivityBasketAct.class);
			startActivity(i);
		}
	
	
}
}
