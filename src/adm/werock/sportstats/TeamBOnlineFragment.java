package adm.werock.sportstats;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adm.werock.sportstats.R;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class TeamBOnlineFragment extends Fragment {
	private int[] states;
	int activeCounter = 0;
	int captainCounter = 0;

	
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
		View rootView = inflater.inflate(adm.werock.sportstats.R.layout.layout_team_b_online_fragment, container, false);
		final ListView list = (ListView) rootView.findViewById(R.id.teamBplayers);
		HashMap<String, Object> item = null;
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();

	///////////////Introducción manual (de momento) de datos

		item = new HashMap<String, Object>();
		item.put("License", "100");
		item.put("Name", "Invalid Julius");
		data.add(item);
		item = new HashMap<String, Object>();
		item.put("License", "245");
		item.put("Name", "Gabriel Sánchez");
		data.add(item);

		item = new HashMap<String, Object>();
		item.put("License", "35");
		item.put("Name", "Chris Kaman");
		data.add(item);
		item = new HashMap<String, Object>();
		item.put("License", "4");
		item.put("Name", "Joseluis Sampedro");
		data.add(item);
		item = new HashMap<String, Object>();
		item.put("License", "5");
		item.put("Name", "Mamá Chicho");
		data.add(item);

		item = new HashMap<String, Object>();
		item.put("License", "6");
		item.put("Name", "Salchipapa oblonga");
		data.add(item);
		item = new HashMap<String, Object>();
		item.put("License", "7");
		item.put("Name", "Cigarro de miel");
		data.add(item);
		item = new HashMap<String, Object>();
		item.put("License", "8");
		item.put("Name", "Sonny Rollins");
		data.add(item);
		item = new HashMap<String, Object>();
		item.put("License", "9");
		item.put("Name", "Michael Doohan");
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
				onPause();
				/*Parameters
parent	The AdapterView where the click happened.
view	The view within the AdapterView that was clicked (this will be a view provided by the adapter)
position	The position of the view in the adapter.
id	The row id of the item that was clicked.*/
				activeCounter = 0;
				captainCounter = 0;
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
				int state = states[position];

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
				case 1 :  icon.setImageResource(R.drawable.ic_suplent);break;
				case 2 :  icon.setImageResource(R.drawable.ic_active_player);break;
				case 3 :  icon.setImageResource(R.drawable.ic_captain); break;
				
				}
				onPause();
				
			}
		
		 
		 });

		


		
		return rootView;
	}
	/*public void onPause(){
		super.onPause();
		SharedPreferences pref = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putInt("prefCaptainCounterVisitor", captainCounter);
		editor.putInt("prefActiveCounterVisitor", activeCounter);
//		Log.i("captainCounterB", captainCounter+"");
//		Log.i("activeCounterB", activeCounter+"");
		editor.commit();
		super.onResume();
	}*/
}