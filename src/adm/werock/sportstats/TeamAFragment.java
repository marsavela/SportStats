package adm.werock.sportstats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adm.werock.sportstats.R;
import adm.werock.sportstats.TeamBOnlineFragment.myAdapter;
import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

public class TeamAFragment extends Fragment {
	private int[] states;
	private String[] licNumbers;
	private String[] numbers;
	private String[] names;

	public int aux;

	public int captainCounter = 0;
	public int starterCounter = 0;
	public int activeCounter = 0;
	public int totalPlayers = 0;
	public boolean ok = true;
	public ListView list;

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
					.findViewById(R.id.player_icon_offline);

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
					/*
					 * Parameters parent The AdapterView where the click
					 * happened. view The view within the AdapterView that was
					 * clicked (this will be a view provided by the adapter)
					 * position The position of the view in the adapter. id The
					 * row id of the item that was clicked.
					 */
					/*
					 * EditText yourEditText = (EditText)
					 * view.findViewById(R.id.player_number);
					 * parent.setDescendantFocusability
					 * (ViewGroup.FOCUS_AFTER_DESCENDANTS);
					 * yourEditText.requestFocus();
					 */
					final ImageView icon = (ImageView) v
							.findViewById(R.id.player_icon_offline);
					aux = position;

					starterCounter = 0;
					captainCounter = 0;
					activeCounter = 0;
					totalPlayers = 0;

					int state = 0;
					states[aux]++;

					for (int i = 0; i < states.length; i++) {
						if (states[i] == 1)
							activeCounter++;
						if (states[i] == 2)
							starterCounter++;

						if (states[i] == 3)
							captainCounter++;
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

			// Adding the license number to the selected row
			final EditText licenseNumber = (EditText) itemView
					.findViewById(R.id.license_offline);
			licenseNumber.setText(licNumbers[position]);
			licenseNumber.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					licNumbers[position] = licenseNumber.getText().toString();

				}
			});
			// Adding the name to the selected row
			final EditText playerName = (EditText) itemView
					.findViewById(R.id.player_name_offline);
			playerName.setText(names[position]);
			playerName.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					names[position] = playerName.getText().toString();

				}
			});
			// Adding the player number to the selected row
			final EditText playerNumber = (EditText) itemView
					.findViewById(R.id.player_number_offline);
			playerNumber.setText(numbers[position]);
			playerNumber.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					numbers[position] = playerNumber.getText().toString();

				}
			});
			return itemView;
		}
	}

	public myAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// onPause();

		View rootView = inflater.inflate(
				adm.werock.sportstats.R.layout.layout_team_a_tab, container,
				false);
		list = (ListView) rootView.findViewById(R.id.teamAplayersOffline);

		HashMap<String, Object> item = null;
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		list.setItemsCanFocus(true);
		for (int i = 0; i < 12; i++) {
			item = new HashMap<String, Object>();
			item.put("Licence", "");
			item.put("Name", "");
			item.put("Number", "");
			data.add(item);
		}

		adapter = new myAdapter(this.getActivity(), data,
				R.layout.team_item_offline, new String[] { "License", "Name",
						"Number" }, new int[] { R.id.license_offline,
						R.id.player_name_offline, R.id.player_number_offline });
		list.setAdapter(adapter);

		states = new int[adapter.getCount()];
		licNumbers = new String[adapter.getCount()];
		numbers = new String[adapter.getCount()];
		names = new String[adapter.getCount()];
		for (int i = 0; i < states.length; i++) {
			states[i] = 0;
			numbers[i] = "";
			licNumbers[i] = "";
			names[i] = "";
		}

		return rootView;
	}

	public void onPause() {

		super.onPause();
		SharedPreferences pref = this.getActivity().getSharedPreferences(
				"myPrefs", Context.MODE_MULTI_PROCESS);
		Editor editor = pref.edit();
		Log.i("captainCounter", captainCounter + "");
		Log.i("activeCounter", activeCounter + "");
		editor.putInt("prefCaptainCounterHome", captainCounter);
		editor.putInt("prefActiveCounterHome", activeCounter);
		editor.commit();
		super.onResume();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

}