package adm.werock.sportstats;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adm.werock.sportstats.R;
import android.app.Activity;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class TeamAOnlineFragment extends Fragment {
	private int[] states;
	
	public void clickedPlayer(){

		Log.i("asdasd", "masidaisad");
		
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


	}


	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		
		
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

	///////////////Introducci�n manual (de momento) de datos

		item = new HashMap<String, Object>();
		item.put("License", "1");
		item.put("Name", "Erving Julius");
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
				/*Parameters
parent	The AdapterView where the click happened.
view	The view within the AdapterView that was clicked (this will be a view provided by the adapter)
position	The position of the view in the adapter.
id	The row id of the item that was clicked.*/
				
				states[position]++;				
				if(states[position]>3) states[position] =0;
				int state = states[position];

				View row = parent.findViewById((int) id);
				ImageView icon =(ImageView)  view.findViewById(R.id.player_icon);
				
				
				switch(state){
				/*
				 0: Jugador inactivo.
				 1: Jugador activo (suplente).
				 2. Titular
				 3. Capit�n
				 
				  */
				case 0 :  icon.setImageResource(R.drawable.ic_inactive_player); break;
				case 1 :  icon.setImageResource(R.drawable.ic_suplent); break;
				case 2 :  icon.setImageResource(R.drawable.ic_active_player); break;
				case 3 :  icon.setImageResource(R.drawable.ic_captain); break;
			
				
				}
				
				
			}
		
		 
		 });

		


		
		return rootView;
	}

}