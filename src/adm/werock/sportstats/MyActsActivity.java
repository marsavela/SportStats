package adm.werock.sportstats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import adm.werock.sportstats.basics.Act;
import adm.werock.sportstats.basics.Event;
import adm.werock.sportstats.basics.Player;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import dao.DAOActs;
import dao.DAOEvents;
import dao.DAOTeams;

public class MyActsActivity extends ListActivity {

	ArrayList<Act> actsList = new ArrayList<Act>();
	MyActsAdapter acts;
	public ListView list;
	ArrayList<Player> playersList = new ArrayList<Player>();
	public HashMap<String, Object> item = null;
	ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
	SimpleAdapter adapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_my_acts);  
		List<Act> actsList = new ArrayList<Act>();
		list = (ListView) findViewById(android.R.id.list);
	    new Task(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		

    	
 
	/*	list.setItemsCanFocus(true);

		MyActsAdapter adapter = new MyActsAdapter(this, data, R.layout.team_item,
				new String[] { "Local", "Visitor" }, new int[] { R.id.teamHome,
			R.id.teamGuest });
		list.setAdapter(adapter);
*/
		
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
        	
       // 	acts = new MyActsAdapter(activity, actsList);	
    	String[] equipos = {"F.C. Barcelona","Estudiantes","Valencia Básket", "Real Madrid","Unicaja","Laboral Kutxa Baskonia", "UCAM Murcia"};
           	for (int i = 0; i < actsList.size(); i++) {
    			item = new HashMap<String, Object>();
    			 actsList.get(i).getIdTeamHome();
    		
    		
    			item.put("Date", actsList.get(i).getDate().toString());
    			
    			//Habría que seleccionar el equipo con el DAO teams.
    			//item.put("Local", actsList.get(i).getIdTeamHome()+"");
    			//item.put("Visitor",actsList.get(i).getIdTeamHome()+"");
    			
    			item.put("Local", equipos[i]);
    			item.put("Visitor",equipos[i+1]);
    			
    			//Habría que calcular los puntos según el acta.
    			item.put("LocalPoints",(int)(50 + Math.random()*((120 - 50)+1))+"");
    			item.put("VisitorPoints",(int)(50 + Math.random()*((120 - 50)+1))+"");
    			
    			data.add(item);
    		}
        	adapter = new SimpleAdapter(getApplicationContext(), data, R.layout.activity_my_acts_row,
    				new String[] { "Date", "Local", "Visitor","LocalPoints","VisitorPoints" }, new int[] { R.id.date,R.id.teamHome,R.id.teamGuest,R.id.pointsHome,R.id.pointsGuest });

    	    list.setAdapter(adapter);
     //   	setListAdapter(acts);
    //		acts.notifyDataSetChanged(); 

    		pDialog.dismiss();

       }

    }


}
