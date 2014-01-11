package adm.werock.sportstats;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adm.werock.sportstats.R;
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
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

public class TeamAOnlineFragment extends Fragment {
	private int[] states;
	public int captainCounter = 0;
	public int starterCounter = 0;
	public int activeCounter = 0;
	public int totalPlayers = 0;
	public EditText playerNumber;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


	}


	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//onPause();


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
				 3. Capit�n

				 */
				case 0 :  imageView.setImageResource(R.drawable.ic_inactive_player); break;
				case 1 :  imageView.setImageResource(R.drawable.ic_active_player); break;
				case 2 :  imageView.setImageResource(R.drawable.ic_suplent); break;
				case 3 :  imageView.setImageResource(R.drawable.ic_captain); break;

				}

				return itemView;
			}

		}
		View rootView = inflater.inflate(adm.werock.sportstats.R.layout.layout_team_a_online_fragment, container, false);
		final ListView list = (ListView) rootView.findViewById(R.id.teamAplayers);
		HashMap<String, Object> item = null;
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();

		///////////////Introducci�n manual (de momento) de datos

		item = new HashMap<String, Object>();
		item.put("License", "1");
		item.put("Name", "Erving Julius");
		item.put("Number", "39");
		data.add(item);
		item = new HashMap<String, Object>();
		item.put("License", "2");
		item.put("Name", "Juanito S�nchez");
		data.add(item);

		item = new HashMap<String, Object>();
		item.put("License", "3");
		item.put("Name", "Pepito p�rez");
		data.add(item);
		item = new HashMap<String, Object>();
		item.put("License", "4");
		item.put("Name", "John Corr");
		data.add(item);
		item = new HashMap<String, Object>();
		item.put("License", "5");
		item.put("Name", "Peter File");
		data.add(item);

		item = new HashMap<String, Object>();
		item.put("License", "6");
		item.put("Name", "Jose Cristo");
		data.add(item);
		item = new HashMap<String, Object>();
		item.put("License", "7");
		item.put("Name", "Isadora Duncan");
		data.add(item);
		item = new HashMap<String, Object>();
		item.put("License", "8");
		item.put("Name", "Poncho S�nchez");
		data.add(item);
		item = new HashMap<String, Object>();
		item.put("License", "9");
		item.put("Name", "Luke Ase");
		data.add(item);
		item = new HashMap<String, Object>();
		item.put("License", "21");
		item.put("Name", "Pepepepe Dodododo");
		data.add(item);

		item = new HashMap<String, Object>();
		item.put("License", "32");
		item.put("Name", "Sublime Text");
		data.add(item);
		item = new HashMap<String, Object>();
		item.put("License", "142");
		item.put("Name", "Michael Jackson");
		data.add(item);
		item = new HashMap<String, Object>();
		item.put("License", "242");
		item.put("Name", "Kyre Olding");
		data.add(item);

		item = new HashMap<String, Object>();
		item.put("License", "123");
		item.put("Name", "Flask Flisk");
		data.add(item);


		item = new HashMap<String, Object>();
		item.put("License", "312");
		item.put("Name", "Follatra Dexs");
		data.add(item);
		item = new HashMap<String, Object>();
		item.put("License", "232");
		item.put("Name", "Asta pata pota");
		data.add(item);
		///////////////////////////////

		myAdapter adapter = new myAdapter
				(this.getActivity(), 
						data, R.layout.team_item,
						new String[]{"License", "Name", "Number"	}, 
						new int[]{R.id.license, R.id.player_name, R.id.player_number});
		list.setAdapter(adapter);
		list.setItemsCanFocus(true);
		list.setClickable(false);	
		states = new int [adapter.getCount()];
		for(int i = 0; i<states.length;i++)
			states[i] = 0;


		

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				/*Parameters
parent	The AdapterView where the click happened.
view	The view within the AdapterView that was clicked (this will be a view provided by the adapter)
position	The position of the view in the adapter.
id	The row id of the item that was clicked.*/
				/* EditText yourEditText = (EditText) view.findViewById(R.id.player_number);
				    parent.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS); 
				    yourEditText.requestFocus(); */
				starterCounter = 0;
				captainCounter = 0;
				activeCounter = 0;
				totalPlayers = 0;
				
				int state = 0;
				states[position]++;	

				for(int i=0;i<states.length; i++){
					if(states[i] == 1) activeCounter++;
					if(states[i] == 2) starterCounter++;
					if(states[i] == 3) captainCounter++;
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
				playerNumber = (EditText) view.findViewById(R.id.player_number);
				playerNumber.setOnClickListener(new View.OnClickListener() {
					
					public void onClick(View v) {
						// TODO Auto-generated method stub
						//playerNumber.setFocusable(true);
						playerNumber.setFocusableInTouchMode(true);
						//playerNumber.setClickable(true
					}
				});
				
				switch(state){
				/*
				 0: Jugador inactivo.
				 1: Jugador activo (suplente).
				 2. Titular
				 3. Capit�n

				 */
				case 0 :  icon.setImageResource(R.drawable.ic_inactive_player);break;
				case 1 :  icon.setImageResource(R.drawable.ic_active_player);break;
				case 2 :  icon.setImageResource(R.drawable.ic_suplent);break;
				case 3 :  icon.setImageResource(R.drawable.ic_captain);break;					
				}
				totalPlayers = starterCounter+captainCounter+activeCounter;
				onPause();
			}
			

		});




		return rootView;
	}


	public void onPause(){

		super.onPause();
		SharedPreferences pref = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_MULTI_PROCESS);
		Editor editor = pref.edit();
		Log.i("captainCounter", captainCounter+"");
		Log.i("starterCounter", starterCounter+"");
		Log.i("activeCounter", activeCounter+"");
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
			//	hola = ((ImageView) getView().findViewById(R.id.player_icon));
				//hola.setImageResource(  R.drawable.ic_inactive_player	);
	}

}