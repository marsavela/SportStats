package adm.werock.sportstats;

import android.app.Activity;
import android.os.Bundle;

public class ActDetailsActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_act);
		
		/*

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
    		
    		awayPlayersContainer.addView(playerItem);
    	}
    	
    	
    	*/
    	
	}
	
	
}
