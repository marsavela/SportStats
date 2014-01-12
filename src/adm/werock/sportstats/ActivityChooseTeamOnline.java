package adm.werock.sportstats;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;




public class ActivityChooseTeamOnline extends Activity {


	String[] getLeagues(){

		String[] items = new String [] {"Nacional", "Autonómica", "Preferente", "1ª Zonal", "2ª Zonal"};
		return items;

	}


	String[] getTeams(String league){

		Log.i("league: ", league);

		if(league.compareTo("Nacional")==0)
			return new String [] {"Miami", "Escolapias", "Buñol", "L'Eliana", "Xàtiva"};
		if(league.compareTo("Autonómica")==0)
			return new String [] {"Herbalife  Gran Canaria", "Valencia Bàsket", "Castellón C.B.", "Llíria", "Petraher"};
		if(league.compareTo("Preferente")==0)
			return new String [] {"San José", "Bezier Team", "Viejas Glorias", "Peñíscola", "Lucentum Alicante"};
		if(league.compareTo("1ª Zonal")==0)
			return new String [] {"Torrent", "Los Ángeles Lakers", "Andorra", "Manresa"};
		if(league.compareTo("2ª Zonal")==0)
			return new String [] {"Utah Jazz", "Jugoplástika", "Charlotte Bobcats", "Seattle Supersonics", "Tavernes de la Valldigna"};


		return null;


	}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_choose_team_online);


		//League spinner:
		//Get leagues:

		
		
		String[] leagues = getLeagues();
		Spinner leagueSpinner = (Spinner) findViewById(R.id.leagueSpinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		Adapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, leagues);
		((ArrayAdapter<String>) adapter).setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// And apply the adapter to the spinner
		leagueSpinner.setAdapter((SpinnerAdapter) adapter);
		leagueSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {

				Spinner leagueSpinner = (Spinner) findViewById(R.id.leagueSpinner);
				String[] teams = getTeams(leagueSpinner.getSelectedItem().toString());

				Spinner localTeamSpinner = (Spinner) findViewById(R.id.localTeamSpinner);
				Spinner awayTeamSpinner = (Spinner) findViewById(R.id.awayTeamSpinner);

				Adapter adapter2 = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item, teams);
				((ArrayAdapter<String>) adapter2).setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				
				localTeamSpinner.setAdapter((SpinnerAdapter) adapter2);

				awayTeamSpinner.setAdapter((SpinnerAdapter) adapter2);



			}




			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_choose_team_online, menu);
		return true;
	}
	public void newAct(View view){

		Spinner leagueSpinner = (Spinner) findViewById(R.id.leagueSpinner);
		Spinner localTeamSpinner = (Spinner) findViewById(R.id.localTeamSpinner);
		Spinner awayTeamSpinner = (Spinner) findViewById(R.id.awayTeamSpinner);

		if(localTeamSpinner.getSelectedItemPosition() == awayTeamSpinner.getSelectedItemPosition())
			Toast.makeText(getApplicationContext(), R.string.sameTeams, Toast.LENGTH_SHORT).show();
		else{
			SharedPreferences pref = getSharedPreferences("teamPrefs", Context.MODE_PRIVATE);
			Editor editor = pref.edit();
			editor.putString("prefLeague",leagueSpinner.getSelectedItem().toString());
			editor.putString("prefVisitorTeam", awayTeamSpinner.getSelectedItem().toString());
			editor.putString("prefLocalTeam", localTeamSpinner.getSelectedItem().toString());
			editor.commit();
			Intent i = new Intent(this, ActivityBasketAct.class);
			startActivity(i);
		}

	}
}
