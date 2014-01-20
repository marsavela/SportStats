package adm.werock.sportstats;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class MyActsActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		List<ActProv> actsList = new ArrayList<ActProv>();
		ActProv act = new ActProv("C.B. Sports","C.B. Stats",114,110,"02/01/2014");
		actsList.add(act);
		ActProv act1 = new ActProv("C.B. Sports","C.B. Stats",114,110,"02/01/2014");
		actsList.add(act1);
		ActProv act2 = new ActProv("C.B. Sports","C.B. Stats",114,110,"02/01/2014");
		actsList.add(act2);
		setContentView(R.layout.activity_my_acts);
		MyActsAdapter acts = new MyActsAdapter(this, actsList);
		
		
		setListAdapter(new MyActsAdapter(this, actsList));
		
		
		
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
	
/*	// Crear nueva acta
	private void addNewAct() {
        Intent i = new Intent(this, CreateActActivity.class);
        startActivity(i);
    }*/
	
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
	

}
