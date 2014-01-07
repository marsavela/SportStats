package adm.werock.sportstats;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adm.werock.sportstats.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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

public class TeamAOnlineFragment extends Fragment {
	private int[] states;
	public int captainCounter = 0;
	public int activeCounter = 0;
	View view1;
	int state1;
	ImageView icon1;


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
		View rootView = inflater.inflate(adm.werock.sportstats.R.layout.layout_team_a_online_fragment, container, false);
		final ListView list = (ListView) rootView.findViewById(R.id.teamAplayers);
		HashMap<String, Object> item = null;
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();

		///////////////Introducción manual (de momento) de datos

		item = new HashMap<String, Object>();
		item.put("License", "1");
		item.put("Name", "Erving Julius");
		item.put("Number", "39");
		data.add(item);
		item = new HashMap<String, Object>();
		item.put("License", "2");
		item.put("Name", "Juanito Sánchez");
		data.add(item);

		item = new HashMap<String, Object>();
		item.put("License", "3");
		item.put("Name", "Pepito pérez");
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
		item.put("Name", "Poncho Sánchez");
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
		for(int i = 0; i<states.length;i++){

			states[i] = 0;


		}

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				view1 = view;
				/*Parameters
parent	The AdapterView where the click happened.
view	The view within the AdapterView that was clicked (this will be a view provided by the adapter)
position	The position of the view in the adapter.
id	The row id of the item that was clicked.*/
				/* EditText yourEditText = (EditText) view.findViewById(R.id.player_number);
				    parent.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS); 
				    yourEditText.requestFocus(); */
				activeCounter = 0;
				captainCounter = 0;
				int state = 0;
				states[position]++;	

				for(int i=0;i<states.length; i++){
					if(states[i]==2) activeCounter++;
					if(states[i]==3) captainCounter++;
				}
				if (activeCounter > 5 && captainCounter==1)
					states[position] = 4;
				else{
					if (activeCounter > 5)
						states[position]++;
					if(captainCounter > 1 )
						states[position]++;
				}

				if(states[position]>3) states[position] =0;
				state = states[position];

				View row = parent.findViewById((int) id);
				ImageView icon =(ImageView)  view.findViewById(R.id.player_icon);
				icon1 = icon;
				state1 = state;
				switch(state){
				/*
				 0: Jugador inactivo.
				 1: Jugador activo (suplente).
				 2. Titular
				 3. Capitán

				 */
				case 0 :  icon.setImageResource(R.drawable.ic_inactive_player);break;
				case 1 :  icon.setImageResource(R.drawable.ic_suplent);break;
				case 2 :  icon.setImageResource(R.drawable.ic_active_player);break;
				case 3 :  icon.setImageResource(R.drawable.ic_captain);break;					
				}
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
		Log.i("activeCounter", activeCounter+"");
		switch(state1){
		/*
				 0: Jugador inactivo.
				 1: Jugador activo (suplente).
				 2. Titular
				 3. Capitán

		 */ 
		case 0 :  editor.putInt("prefIcon", R.drawable.ic_inactive_player);break;
		case 1 :  editor.putInt("prefIcon", R.drawable.ic_suplent);break;
		case 2 :  editor.putInt("prefIcon", R.drawable.ic_active_player);break;
		case 3 :  editor.putInt("prefIcon", R.drawable.ic_captain);break;					
		}

		editor.putInt("prefCaptainCounterHome", captainCounter);
		editor.putInt("prefActiveCounterHome", activeCounter);
		editor.putInt("prefState",state1 );
		Log.i("prefState", state1+"");
		editor.commit();
		super.onResume();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//		ImageView hola;
		//		SharedPreferences pref = this.getActivity().getSharedPreferences("myPref", Context.MODE_MULTI_PROCESS);
		//		hola = ((ImageView) getActivity().findViewById(R.id.player_icon));
		//		hola.setImageResource(pref.getInt("prefIcon", 0));
	}

}