package adm.werock.sportstats;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MyActsActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Act act = new Act("Duncan Your Face","Wake and Blake",170,169,"Hola");
		List<Act> actsList = new ArrayList<Act>();
		actsList.add(act);
		setContentView(R.layout.activity_my_acts);
		MyActsAdapter acts = new MyActsAdapter(this, actsList);
		Log.v("ownad", act.getTeamHome());
		
		setListAdapter(new MyActsAdapter(this, actsList));
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
	
	// Crear nueva acta
	private void addNewAct() {
        Intent i = new Intent(this, CreateActActivity.class);
        startActivity(i);
    }
	

}
