package adm.werock.sportstats;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * This Fragment shows all the stats (for every player) of the game so far.
 * 
 * @author Josep
 */
public class PreviewBasketStatsFragment extends Fragment{
	
	private ActivityBasketStats parent;
	private View view;
	private ViewGroup container;
	
	public static android.support.v4.app.Fragment newInstance() {
		 
        // Instantiate a new fragment
		PreviewBasketStatsFragment fragment = new PreviewBasketStatsFragment();
 
        // Save the parameters
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        fragment.setRetainInstance(true);
 
        return fragment;
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_preview_stats, container, false);
		
		view = v;
		this.container = container;
		//Log.i("BasketStatsFragment","VIEW CREATED");
		
		return v;
	}
	
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);

	    Activity activity = getActivity();
    	
    	if(activity instanceof ActivityBasketStats){
    		parent = ((ActivityBasketStats)activity);
    	}
    	
    	// Home Team Stats
    	ArrayList<ActPlayer> homePlayers = parent.getHomePlayers();
    	LinearLayout homePlayersContainer = (LinearLayout)view.findViewById(R.id.homePlayersContainer);

    	for(int i=0; i<homePlayers.size(); i++){
    		ActPlayer player = homePlayers.get(i);
    		View playerItem = getLayoutInflater(savedInstanceState).inflate(R.layout.layout_player_stats_item, null);
    		
    		Button playerNumber = (Button) playerItem.findViewById(R.id.buttonPlayerStatsNumber);
    		TextView playerName = (TextView) playerItem.findViewById(R.id.textPlayerStatsName);
    		Button playerPoints = (Button) playerItem.findViewById(R.id.buttonPlayerStatsPoints);
    		Button playerFreeThrows = (Button) playerItem.findViewById(R.id.buttonPlayerStatsFreeThrows);
    		Button playerFouls = (Button) playerItem.findViewById(R.id.buttonPlayerStatsFouls);
    		
    		playerNumber.setText(String.valueOf(player.getNumber()));
    		playerName.setText(player.toString());
    		playerPoints.setText(String.valueOf(player.getPoints()));
    		playerFreeThrows.setText(String.valueOf(player.getFreeThrowsMade())+"/"+String.valueOf(player.getFreeThrowsTotal()));
    		playerFouls.setText(String.valueOf(player.getFouls()));
    		
    		if(player.isCaptain()){
    			playerName.setTypeface(null, Typeface.BOLD_ITALIC);
    		}
    		else if(player.isStarter()){
    			playerName.setTypeface(null, Typeface.BOLD);
    		}
    		
    		homePlayersContainer.addView(playerItem);
    	}
    	
    	// Away Team Stats
    	ArrayList<ActPlayer> awayPlayers = parent.getAwayPlayers();
    	LinearLayout awayPlayersContainer = (LinearLayout)view.findViewById(R.id.awayPlayersContainer);
    	
    	for(int i=0; i<awayPlayers.size(); i++){
    		ActPlayer player = awayPlayers.get(i);
    		View playerItem = getLayoutInflater(savedInstanceState).inflate(R.layout.layout_player_stats_item, null);
    		
    		Button playerNumber = (Button) playerItem.findViewById(R.id.buttonPlayerStatsNumber);
    		TextView playerName = (TextView) playerItem.findViewById(R.id.textPlayerStatsName);
    		Button playerPoints = (Button) playerItem.findViewById(R.id.buttonPlayerStatsPoints);
    		Button playerFreeThrows = (Button) playerItem.findViewById(R.id.buttonPlayerStatsFreeThrows);
    		Button playerFouls = (Button) playerItem.findViewById(R.id.buttonPlayerStatsFouls);
    		
    		playerNumber.setText(String.valueOf(player.getNumber()));
    		playerName.setText(player.toString());
    		playerPoints.setText(String.valueOf(player.getPoints()));
    		playerFreeThrows.setText(String.valueOf(player.getFreeThrowsMade())+"/"+String.valueOf(player.getFreeThrowsTotal()));
    		playerFouls.setText(String.valueOf(player.getFouls()));
    		
    		if(player.isCaptain()){
    			playerName.setTypeface(null, Typeface.BOLD_ITALIC);
    		}
    		else if(player.isStarter()){
    			playerName.setTypeface(null, Typeface.BOLD);
    		}
    		
    		awayPlayersContainer.addView(playerItem);
    	}
    	
    	//Log.i("BasketStatsFragment","ACTIVITY CREATED");
	}

	@Override
	public void onStart() {
		super.onStart();
		//Log.i("BasketStatsFragment","STARTED");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		//Log.i("BasketStatsFragment","RESUMED");
	}
	
	

}
