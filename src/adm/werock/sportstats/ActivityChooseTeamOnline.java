package adm.werock.sportstats;

import java.util.ArrayList;

import dao.DAOLeagues;
import dao.DAOTeams;
import adm.werock.sportstats.basics.League;
import adm.werock.sportstats.basics.Team;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
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
	ArrayList<League> leaguesList = new ArrayList<League>();
	ArrayList<Team> teamsList = new ArrayList<Team>();
	ArrayList<String> leagues = new ArrayList<String>();
	ArrayList<String> teams = new ArrayList<String>();
	
	Adapter adapter2;
	Adapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_choose_team_online);


		//League spinner:
		//Get leagues:
		new TaskLeagues().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		Spinner leagueSpinner = (Spinner) findViewById(R.id.leagueSpinner);
		
		// Create an ArrayAdapter using the string array and a default spinner layout
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, leagues);
		((ArrayAdapter<String>) adapter).setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		// And apply the adapter to the spinner
		leagueSpinner.setAdapter((SpinnerAdapter) adapter);
		leagueSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
				//Get teams from the selected league
				new TaskTeams().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				
				Spinner localTeamSpinner = (Spinner) findViewById(R.id.localTeamSpinner);
				Spinner awayTeamSpinner = (Spinner) findViewById(R.id.awayTeamSpinner);
				adapter2 = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item, teams);
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
	private class TaskLeagues extends AsyncTask<Void, Void, Void> {

		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(ActivityChooseTeamOnline.this);
			pDialog.setTitle("Contacting Servers");
			pDialog.setMessage("Downloading data...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			//leaguesList = daoLeagues.getAllLeagues();

			//DAOLeagues daoLeagues = new DAOLeagues();
			leaguesList = DAOLeagues.getAllLeagues();
			Log.v("LIGAS: ",Integer.toString(leaguesList.size()));
			//teamsList = DAOTeams.getAllTeams(new League(1, "ACB"));
			//Log.v("EQUIPOS EN LA ACB:",Integer.toString(teamsList.size()));
			return null;
		}

		@Override
		protected void onPostExecute(Void params) {
			pDialog.dismiss();
			leagues.clear();
			for(int i=0;i<leaguesList.size();i++)
				leagues.add(leaguesList.get(i).getName());
			Spinner leagueSpinner = (Spinner) findViewById(R.id.leagueSpinner);
			leagueSpinner.setAdapter((SpinnerAdapter) adapter);
		}

	}
	private class TaskTeams extends AsyncTask<Void, Void, Void> {

		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(ActivityChooseTeamOnline.this);
			pDialog.setTitle("Contacting Servers");
			pDialog.setMessage("Downloading data...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub			
			int id = 0;
			Spinner leagueSpinner = (Spinner) findViewById(R.id.leagueSpinner);
			String league = leagueSpinner.getSelectedItem().toString();
			for(int i=0;i<leaguesList.size();i++)
				if(leaguesList.get(i).getName().compareTo(league) == 0)
					id = leaguesList.get(i).getLeagueId();

			teamsList = DAOTeams.getAllTeams(new League(id, league));
			Log.v("EQUIPOS EN LA ACB:",Integer.toString(teamsList.size()));
			return null;
		}

		@Override
		protected void onPostExecute(Void params) {
			pDialog.dismiss();
			teams.clear();
			for(int i=0;i<teamsList.size();i++)
				teams.add(teamsList.get(i).getName());	
			Spinner localTeamSpinner = (Spinner) findViewById(R.id.localTeamSpinner);
			localTeamSpinner.setAdapter((SpinnerAdapter) adapter2);
			Spinner awayTeamSpinner = (Spinner) findViewById(R.id.awayTeamSpinner);
			awayTeamSpinner.setAdapter((SpinnerAdapter) adapter2);
		}
		
	}
}
