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
import android.widget.EditText;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityChooseTeamOffline extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_choose_team_offline);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_choose_team_offline, menu);
		return true;
	}

	
	public void newAct(View view){
		
		EditText leagueEditText = (EditText) findViewById(R.id.leagueEditText);
		EditText localTeamEditText = (EditText) findViewById(R.id.localTeamEditText);
		EditText awayTeamEditText = (EditText) findViewById(R.id.awayTeamEditText);

		if(localTeamEditText.getText().toString().compareTo(awayTeamEditText.getText().toString())==0)
			Toast.makeText(getApplicationContext(), R.string.sameTeams, Toast.LENGTH_SHORT).show();
		else{
			SharedPreferences pref = getSharedPreferences("teamPrefs", Context.MODE_PRIVATE);
			Editor editor = pref.edit();
			editor.putString("prefLeague",leagueEditText.getText().toString());
			editor.putString("prefVisitorTeam", awayTeamEditText.getText().toString());
			editor.putString("prefLocalTeam", localTeamEditText.getText().toString());
			editor.commit();
			Intent i = new Intent(this, ActivityBasketAct.class);
			startActivity(i);
		}
	
	
}
}
