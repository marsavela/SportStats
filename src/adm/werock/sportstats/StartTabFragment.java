package adm.werock.sportstats;

import adm.werock.sportstats.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class StartTabFragment extends Fragment {
	int captainCounterHome, captainCounterVisitor;
	int activeCounterHome, activeCounterVisitor;
	Button startButton;
	private SharedPreferences pref;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(adm.werock.sportstats.R.layout.layout_start_tab, container, false);
		pref = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
			
		 
		Button startButton = (Button) rootView.findViewById(R.id.ButtonStartGame);

		    //set a onclick listener for when the button gets clicked
		 startButton.setOnClickListener(new OnClickListener() {
		        //Start new list activity
		        @Override
		        
		        public void onClick(View v) {
		        	//We get the number of captains and active players in order to check everything is ok
		    		
		    		captainCounterHome = pref.getInt("prefCaptainCounterHome", 0);
		    		captainCounterVisitor = pref.getInt("prefCaptainCounterVisitor", 0);
		    		activeCounterHome = pref.getInt("prefActiveCounterHome", 0);
		    		activeCounterVisitor = pref.getInt("prefActiveCounterVisitor", 0);
		    			        
		        	if(captainCounterHome != 1 || activeCounterHome<4){
			        	if(captainCounterHome!=1)
			        		Toast.makeText(getActivity().getApplicationContext(), "TeamA needs a captain", Toast.LENGTH_SHORT).show();
			            if(activeCounterHome <4)
			        		Toast.makeText(getActivity().getApplicationContext(), "TeamA needs 4 active players", Toast.LENGTH_SHORT).show();
		        	}
		        	else if(captainCounterVisitor != 1 || activeCounterVisitor<4){
			        	if(captainCounterVisitor!=1)
			        		Toast.makeText(getActivity().getApplicationContext(), "TeamB needs a captain", Toast.LENGTH_SHORT).show();
			            if(activeCounterVisitor <4)
			        		Toast.makeText(getActivity().getApplicationContext(), "TeamB needs 4 active players", Toast.LENGTH_SHORT).show();
		        	}
		        	else{
		            Intent i = new Intent(getActivity(), ActivityBasketStats.class);
		            startActivity(i);
		        	}
		            
		        }
		    });
		return rootView;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	
	
}
