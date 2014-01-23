package adm.werock.sportstats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adm.werock.sportstats.basics.Player;
import adm.werock.sportstats.basics.Team;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore.Audio.PlaylistsColumns;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import dao.ActDBHelper;
import dao.DAOPlayers;

public class TeamAOnlineFragment extends Fragment {
	private int[] states;
	private String[] numbers;

	public int captainCounter = 0;
	public int starterCounter = 0;
	public int activeCounter = 0;
	public int totalPlayers = 0;

	public String homeTeam;
	public int homeTeamId;
	public int leagueID;

	public int aux;
	public ListView list;

	public ActivityBasketAct bigParent;
	class myAdapter extends SimpleAdapter {

		public myAdapter(Context context, List<? extends Map<String, ?>> data,
				int resource, String[] from, int[] to) {
			super(context, data, resource, from, to);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			View itemView = super.getView(position, convertView, parent);

			ImageView imageView = (ImageView) itemView
					.findViewById(R.id.player_icon);


			switch (states[position]) {
			/*
			 * 0: Jugador inactivo. 1: Jugador activo (suplente). 2. Titular 3.
			 * Capitán
			 */
			case 0:
				imageView.setImageResource(R.drawable.ic_inactive_player);
				break;
			case 1:
				imageView.setImageResource(R.drawable.ic_active_player);
				break;
			case 2:
				imageView.setImageResource(R.drawable.ic_suplent);
				break;
			case 3:
				imageView.setImageResource(R.drawable.ic_captain);
				break;

			}

			itemView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					bigParent.setPlayerStates(adapter.getCount());
					bigParent.inicializePlayerStates();
					// TODO Auto-generated method stub
					final ImageView icon = (ImageView) v
							.findViewById(R.id.player_icon);
					aux = position;

					starterCounter = 0;
					captainCounter = 0;
					activeCounter = 0;
					totalPlayers = 0;

					int state = 0;
					states[aux]++;

					for (int i = 0; i < states.length; i++) {
						if (states[i] == 1){
							activeCounter++;
							bigParent.playerStateA[i] = 1;
						}
						if (states[i] == 2){
							starterCounter++;
							bigParent.playerStateA[i] = 2;
						}

						if (states[i] == 3){
							captainCounter++;
							bigParent.playerStateA[i] = 3;
						}

					}
					if (starterCounter > 5 && captainCounter == 1)
						states[aux] = 4;
					else {
						if (starterCounter > 5)
							states[aux]++;
						if (captainCounter > 1)
							states[aux]++;
					}

					if (states[aux] > 3)
						states[aux] = 0;
					state = states[aux];

					switch (state) {
					/*
					 * 0: Jugador inactivo. 1: Jugador activo (suplente). 2.
					 * Titular 3. Capitán
					 */
					case 0:
						icon.setImageResource(R.drawable.ic_inactive_player);
						break;
					case 1:
						icon.setImageResource(R.drawable.ic_active_player);
						break;
					case 2:
						icon.setImageResource(R.drawable.ic_suplent);
						break;
					case 3:
						icon.setImageResource(R.drawable.ic_captain);
						break;
					}
					totalPlayers = starterCounter + captainCounter
							+ activeCounter;
					onPause();

				}

			});

			final EditText playerNumber = (EditText) itemView
					.findViewById(R.id.player_number);
			playerNumber.setText(numbers[position]);
			playerNumber.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					
					String dorsal = playerNumber.getText().toString();
					boolean unico = true;
					for (int i = 0; i < numbers.length; i++) {
						if(numbers[i]!=null ){
							if(numbers[i].length()==0)
								numbers[i] = "";
							else if (numbers[i].compareTo(dorsal) == 0) unico = false;
						}
						else numbers[i] = "";
					
					}
					if(!unico)
						Toast.makeText(getActivity().getApplicationContext(), "Repeated number", Toast.LENGTH_SHORT).show();

					else {
						numbers[position] = playerNumber.getText().toString();

						bigParent.setPlayerNumbersA(adapter.getCount());
						bigParent.inicializePlayerNumbersA();

						for(int iterador=0;iterador<numbers.length;++iterador){
							//Log.i(numbers[iterador].length()+"",numbers[iterador]+"");
							if(numbers[iterador]!= null && numbers[iterador].length() > 0 ){
								bigParent.playerNumberA[iterador] = Integer.parseInt(numbers[iterador]);
							}
						}
					}


				}
			});

			return itemView;
		}

	}

	public myAdapter adapter;

	ArrayList<Player> playersList = new ArrayList<Player>();
	public HashMap<String, Object> item = null;
	ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Getting the values in order to start the asyntask
		Bundle extras = this.getActivity().getIntent().getExtras();
		if (extras != null) {
			homeTeam = extras.getString("homeTeam");
			homeTeamId = extras.getInt("homeTeamId");
			leagueID = extras.getInt("leagueID");
		}

		new TaskPlayers().execute();

	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {


		View rootView = inflater.inflate(
				adm.werock.sportstats.R.layout.layout_team_a_online_fragment,
				container, false);

		list = (ListView) rootView.findViewById(R.id.teamAplayers);
		list.setItemsCanFocus(true);

		adapter = new myAdapter(this.getActivity(), data, R.layout.team_item,
				new String[] { "License", "Name" }, new int[] { R.id.license,
			R.id.player_name });
		list.setAdapter(adapter);

		states = new int[adapter.getCount()];
		numbers = new String[adapter.getCount()];

		for (int i = 0; i < adapter.getCount(); i++) {
			states[i] = 0;
			numbers[i] = "";
		}


		return rootView;

	}
	@Override
	public void onActivityCreated (Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Activity activity = getActivity();

		if(activity instanceof ActivityBasketAct){
			bigParent = ((ActivityBasketAct)activity);
			bigParent.setPlayerStates(adapter.getCount());
			bigParent.inicializePlayerStates();
		}

	}
	public void onPause() {
		super.onPause();
		SharedPreferences pref = this.getActivity().getSharedPreferences(
				"myPrefs", Context.MODE_MULTI_PROCESS);
		Editor editor = pref.edit();
		editor.putInt("prefCaptainCounterHome", captainCounter);
		editor.putInt("prefStarterCounterHome", starterCounter);
		editor.putInt("prefTotalPlayersHome", totalPlayers);
		editor.commit();
		super.onResume();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	private class TaskPlayers extends AsyncTask<Void, Void, Void> {

		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(TeamAOnlineFragment.this.getActivity());
			pDialog.setTitle(R.string.downloadingData);
			pDialog.setMessage("Downloading data...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			playersList = DAOPlayers.getPlayersOfATeam(new Team(homeTeamId,
					homeTeam, leagueID));
			bigParent.homeTeamId = homeTeamId;
			Log.v("JUGADORES:", Integer.toString(playersList.size()));
			return null;
		}

		@Override
		protected void onPostExecute(Void params) {
			pDialog.dismiss();
			for (int i = 0; i < playersList.size(); i++) {
				item = new HashMap<String, Object>();
				item.put("Name", playersList.get(i).getName() + ", "
						+ playersList.get(i).getSurname());
				item.put("License", playersList.get(i).getLicenseNumber());
				data.add(item);
				bigParent.playersListA.add(playersList.get(i));
			}
			states = new int[adapter.getCount()];
			numbers = new String[adapter.getCount()];

		}

	}
}