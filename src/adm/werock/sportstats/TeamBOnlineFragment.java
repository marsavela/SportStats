package adm.werock.sportstats;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adm.werock.sportstats.basics.Player;
import adm.werock.sportstats.basics.Team;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import dao.DAOPlayers;

public class TeamBOnlineFragment extends Fragment {
	private int[] states;
	int starterCounter = 0;
	int captainCounter = 0;
	int activeCounter = 0;
	int totalPlayers = 0;
	public String homeTeam, awayTeam;
	public int homeTeamId, awayTeamId;
	public int leagueID;
	class myAdapter extends SimpleAdapter{

		public myAdapter(Context context,
				List<? extends Map<String, ?>> data, int resource,
						String[] from, int[] to) {
			super(context, data, resource, from, to);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View itemView = super.getView(position, convertView, parent);
			ImageView imageView = (ImageView) itemView.findViewById(R.id.player_icon);


			switch(states[position]){
			/*
			 0: Jugador inactivo.
			 1: Jugador activo (suplente).
			 2. Titular
			 3. Capitán

			 */
			case 0 :  imageView.setImageResource(R.drawable.ic_inactive_player); break;
			case 1 :  imageView.setImageResource(R.drawable.ic_suplent); break;
			case 2 :  imageView.setImageResource(R.drawable.ic_active_player); break;
			case 3 :  imageView.setImageResource(R.drawable.ic_captain); break;

			}


			return itemView;
		}

	}
	public myAdapter adapter;
	
	ArrayList<Player> playersList1 = new ArrayList<Player>();
	public HashMap<String, Object> item = null;
	ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = this.getActivity().getIntent().getExtras();
		if (extras != null) {
		   leagueID = extras.getInt("leagueID");
		   awayTeam = extras.getString("awayTeam");
		   awayTeamId = extras.getInt("awayTeamId");
		}
		new TaskPlayers().execute();


	}


	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		View rootView = inflater.inflate(adm.werock.sportstats.R.layout.layout_team_b_online_fragment, container, false);
		final ListView list = (ListView) rootView.findViewById(R.id.teamBplayers);
				
		adapter = new myAdapter
				(this.getActivity(), 
						data, R.layout.team_item,
						new String[]{"License", "Name"}, 
						new int[]{R.id.license, R.id.player_name});
		list.setAdapter(adapter);
		list.setItemsCanFocus(true);
		list.setClickable(false);
		states = new int [adapter.getCount()];
		for(int i = 0; i<states.length;i++){
			
			states[i] = 0;
			
		
		}
		list.setOnItemClickListener(new OnItemClickListener() {
		
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				onPause();
				/*Parameters
parent	The AdapterView where the click happened.
view	The view within the AdapterView that was clicked (this will be a view provided by the adapter)
position	The position of the view in the adapter.
id	The row id of the item that was clicked.*/
				starterCounter = 0;
				captainCounter = 0;
				activeCounter = 0;
				totalPlayers = 0;
				
				int state = 0;
				states[position]++;		
				
				for(int i=0;i<states.length; i++){
					if(states[i]==1) activeCounter++;
					if(states[i]==2) starterCounter++;
					if(states[i]==3) captainCounter++;

				}
				if (starterCounter > 5 && captainCounter==1)
					states[position] = 4;
				else{
					if (starterCounter > 5)
						states[position]++;
					if(captainCounter > 1 )
						states[position]++;
				}
				
				if(states[position]>3) states[position] =0;
				state = states[position];

				View row = parent.findViewById((int) id);
				ImageView icon =(ImageView)  view.findViewById(R.id.player_icon);
				
				
				switch(state){
				/*
				 0: Jugador inactivo.
				 1: Jugador activo (suplente).
				 2. Titular
				 3. Capitán
				 
				  */
				case 0 :  icon.setImageResource(R.drawable.ic_inactive_player); break;
				case 1 :  icon.setImageResource(R.drawable.ic_active_player);break;
				case 2 :  icon.setImageResource(R.drawable.ic_suplent);break;
				case 3 :  icon.setImageResource(R.drawable.ic_captain); break;
				
				}
				totalPlayers = starterCounter+captainCounter+activeCounter;;
				onPause();
				
			}
		
		 
		 });

		


		
		return rootView;

	}
	public void onPause(){
		super.onPause();
		SharedPreferences pref = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putInt("prefCaptainCounterVisitor", captainCounter);
		editor.putInt("prefStarterCounterVisitor", starterCounter);
		editor.putInt("prefTotalPlayersVisitor", totalPlayers);
		Log.i("captainCounterB", captainCounter+"");
		Log.i("activeCounterB", activeCounter+"");
		editor.commit();
		super.onResume(); 
	}
	private class TaskPlayers extends AsyncTask<Void, Void, Void> {

		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(TeamBOnlineFragment.this.getActivity());
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


			playersList1 = DAOPlayers.getPlayersOfATeam(new Team(awayTeamId, awayTeam, leagueID));
			Log.v("JUGADORES:",Integer.toString(playersList1.size()));
			return null;
		}

		@Override
		protected void onPostExecute(Void params) {
			pDialog.dismiss();
			for(int i=0;i<playersList1.size();i++){
				item = new HashMap<String, Object>();
				item.put("Name", playersList1.get(i).getName());
				item.put("License", playersList1.get(i).getLicenseNumber());
				data.add(item);
			}
			states = new int [adapter.getCount()];
		}

	}
}