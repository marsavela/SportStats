package adm.werock.sportstats;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.DAOActs;
import dao.DAOLeagues;
import dao.DAOPlayers;
import dao.DAOTeams;
import dao.DAOUsers;

import adm.werock.sportstats.basics.Act;
import adm.werock.sportstats.basics.League;
import adm.werock.sportstats.basics.Team;
import adm.werock.sportstats.basics.User;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MyActsActivity extends ListActivity {

	ArrayList<Act> actsList = new ArrayList<Act>();
	MyActsAdapter acts;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		
		List<Act> actsList = new ArrayList<Act>();
	//	ArrayList<Act> actsList = new ArrayList<Act>();
		
/*		Date currentDate = new Date(System.currentTimeMillis());
		Act act = new Act(1, currentDate, "dieparo@gmail.com",1,2);
		actsList.add(act);*/
		
		
	/*	ActProv act1 = new ActProv("C.B. Sports","C.B. Stats",114,110,"02/01/2014");
		actsList.add(act1);
		ActProv act2 = new ActProv("C.B. Sports","C.B. Stats",114,110,"02/01/2014");
		actsList.add(act2);
		public Act(int id, Date date, String emailUser, int idTeamHome, int idTeamGuest)
		*/
		
	//	AsyncTask myTask = new Task(this);
		new Task(this).execute();
		
		
		setContentView(R.layout.activity_my_acts);
	
/*		acts = new MyActsAdapter(this, actsList);
		
		
		setListAdapter(new MyActsAdapter(this, actsList));*/
		
		
		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
	    super.onListItemClick(l, v, position, id);
	    //do something here using the position in the arrya
	    Intent i = new Intent(this, ActDetailsActivity.class);
        startActivity(i);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		// Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.acts_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
        case android.R.id.home:
            // This is called when the Home (Up) button is pressed in the action bar.
            // Create a simple intent that starts the hierarchical parent activity and
            // use NavUtils in the Support Package to ensure proper handling of Up.
            NavUtils.navigateUpFromSameTask(this);
            return true;
        case R.id.add_new_act:

            addNewAct();
        	
            return true;
    }
		return super.onOptionsItemSelected(item);
	}
	
	
	public void addNewAct(){
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

private class Task extends AsyncTask<Void, Void, Void> {
        
		public Activity activity;
		private ProgressDialog pDialog;

		public Task(Activity a) {
			this.activity = a;
		}
    
        @Override
        protected void onPreExecute() {
        	super.onPreExecute();
    //    	setProgressBarIndeterminateVisibility(true);
            pDialog = new ProgressDialog(MyActsActivity.this);
            pDialog.setTitle("Contacting Servers");
            pDialog.setMessage("Downloading data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
        // TODO Auto-generated method stub
        	actsList = DAOActs.getAct("dieparo@gmail.com");
        	Log.v("Actos: ",Integer.toString(actsList.size()));
        	
			return null;
        }

        @Override
        protected void onPostExecute(Void params) {
        	
        	acts = new MyActsAdapter(activity, actsList);	
    		
        	
        	setListAdapter(acts);
    //		acts.notifyDataSetChanged(); 

    		pDialog.dismiss();

       }

    }


}
