package adm.werock.sportstats;

import java.util.ArrayList;

import adm.werock.sportstats.R.id;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * This Fragment is used to enter events related to a selected player.
 * 
 * @author Josep
 */
public class PlayerBasketDataFragment extends Fragment{
	
	private Button buttonAdd2Points;
	private Button buttonAdd3Points;
	private Button buttonRemove2Points;
	private Button buttonRemove3Points;
	private Button buttonAddFreeThrowMade;
	private Button buttonAddFreeThrowMissed;
	private Button buttonRemoveFreeThrowMade;
	private Button buttonRemoveFreeThrowMissed;
	private Button buttonAddFoul;
	private Button buttonRemoveFoul;
	private Button buttonConfirm;
	
	private Button headerPlayerName;
	private Button headerPlayerStatsPrevious;
	private Button headerPlayerStatsToAddPointsFouls;
	private Button headerPlayerStatsToAddFreeThrows;
	
	private ActPlayer player = null;
	
	private int points2Count = 0;
	private int points3Count = 0;
	private int freeThrowsInCount = 0;
	private int freeThrowsOutCount = 0;
	private int foulsCount = 0;
	
	private int currentMinute = 0;
	private boolean bHomePlayer;
	private int playerPosition;
	private ArrayList<ActEvent> events = new ArrayList<ActEvent>();
	
	private ActivityBasketStats parent;
	
	public static android.support.v4.app.Fragment newInstance() {
		 
        // Instantiate a new fragment
		PlayerBasketDataFragment fragment = new PlayerBasketDataFragment();
 
        // Save the parameters
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        fragment.setRetainInstance(true);
 
        return fragment;
    }
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_player, container, false);
		
		buttonAdd2Points = (Button) v.findViewById(R.id.buttonAdd2Points);
		buttonAdd3Points = (Button) v.findViewById(R.id.buttonAdd3Points);
		buttonRemove2Points = (Button) v.findViewById(R.id.buttonRemove2Points);
		buttonRemove3Points	= (Button) v.findViewById(R.id.buttonRemove3Points);
		buttonAddFreeThrowMade = (Button) v.findViewById(R.id.buttonAddFreeThrowMade);
		buttonAddFreeThrowMissed = (Button) v.findViewById(R.id.buttonAddFreeThrowMissed);
		buttonRemoveFreeThrowMade = (Button) v.findViewById(R.id.buttonRemoveFreeThrowMade);
		buttonRemoveFreeThrowMissed = (Button) v.findViewById(R.id.buttonRemoveFreeThrowMissed);
		buttonAddFoul = (Button) v.findViewById(R.id.buttonAddFoul);
		buttonRemoveFoul = (Button)v.findViewById(R.id.buttonRemoveFoul);
		buttonConfirm = (Button)v.findViewById(R.id.buttonConfirm);
		
		headerPlayerName = (Button)v.findViewById(R.id.headerPlayerName);
		headerPlayerStatsPrevious = (Button)v.findViewById(R.id.headerPreviousStats);
		headerPlayerStatsToAddPointsFouls = (Button)v.findViewById(R.id.headerStatsToAddPointsFouls);
		headerPlayerStatsToAddFreeThrows = (Button)v.findViewById(R.id.headerStatsToAddFreeThrows);
		
		//buttonPlayerHome1.setOnClickListener(new HandlerButtonHome(1));
		buttonAdd2Points.setOnTouchListener(new HandlerActionButton(buttonAdd2Points, 1));
		buttonAdd3Points.setOnTouchListener(new HandlerActionButton(buttonAdd3Points, 2));
		buttonRemove2Points.setOnTouchListener(new HandlerActionButton(buttonRemove2Points, 3));
		buttonRemove3Points.setOnTouchListener(new HandlerActionButton(buttonRemove3Points, 4));
		buttonAddFreeThrowMade.setOnTouchListener(new HandlerActionButton(buttonAddFreeThrowMade, 5));
		buttonAddFreeThrowMissed.setOnTouchListener(new HandlerActionButton(buttonAddFreeThrowMissed, 6));
		buttonRemoveFreeThrowMade.setOnTouchListener(new HandlerActionButton(buttonRemoveFreeThrowMade, 7));
		buttonRemoveFreeThrowMissed.setOnTouchListener(new HandlerActionButton(buttonRemoveFreeThrowMissed, 8));
		buttonAddFoul.setOnTouchListener(new HandlerActionButton(buttonAddFoul, 9));
		buttonRemoveFoul.setOnTouchListener(new HandlerActionButton(buttonRemoveFoul, 10));
		buttonConfirm.setOnTouchListener(new HandlerConfirm());

		
		return v;
	}
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);

	    Activity activity = getActivity();
    	
    	if(activity instanceof ActivityBasketStats){
    		parent = ((ActivityBasketStats)activity);
    	}
    	
    	// Get the current player selected and preview its stats
    	player = parent.getSelectedPlayer();
    	playerPosition = parent.getSelectedPlayerIndex();
    	bHomePlayer = parent.isHomePlayerSelected();
    	
    	currentMinute = parent.getCurrentMinute();
    	
    	headerPlayerName.setText(player.toString());
    	headerPlayerStatsPrevious.setText(getResources().getString(R.string.labelPoints)+player.getPoints()+" "+
    									  getResources().getString(R.string.labelFouls)+player.getFouls());
    	updateStatsToAdd();
    	
	}
	
	/**
	 * This method updates the text of the stats to add.
	 * @author Josep
	 */
	private void updateStatsToAdd()
	{
		headerPlayerStatsToAddPointsFouls.setText(getResources().getString(R.string.labelPoints)+(points2Count*2+points3Count*3)+" "+
		  		  								  getResources().getString(R.string.labelFouls)+(foulsCount));
		headerPlayerStatsToAddFreeThrows.setText(getResources().getString(R.string.labelFreeThrows)+(freeThrowsInCount)+"/"+(freeThrowsOutCount));
	}
	
	/**
	 * This method creates events according to the user info entered.
	 * @author Josep
	 */
	private void confirmActions()
	{
		// Points
		if(points2Count >= 1){
			for(int i=0; i<points2Count; i++){
				events.add(new ActEvent(bHomePlayer, playerPosition, "P", 2, currentMinute));
			}
		}else if(points2Count <= -1){
			for(int i=0; i<-points2Count; i++){
				events.add(new ActEvent(bHomePlayer, playerPosition, "P", -2, currentMinute));
			}
		}
		
		if(points3Count >= 1){
			for(int i=0; i<points3Count; i++){
				events.add(new ActEvent(bHomePlayer, playerPosition, "P", 3, currentMinute));
			}
		}else if(points3Count <= -1){
			for(int i=0; i<-points3Count; i++){
				events.add(new ActEvent(bHomePlayer, playerPosition, "P", -3, currentMinute));
			}
		}
		
		// Free Throws
		if(freeThrowsInCount >= 1){
			for(int i=0; i<freeThrowsInCount; i++){
				events.add(new ActEvent(bHomePlayer, playerPosition, "FI", 1, currentMinute));
			}
		}else if(freeThrowsInCount <= -1){
			for(int i=0; i<-freeThrowsInCount; i++){
				events.add(new ActEvent(bHomePlayer, playerPosition, "FI", -1, currentMinute));
			}
		}
		
		if(freeThrowsOutCount >= 1){
			for(int i=0; i<freeThrowsOutCount; i++){
				events.add(new ActEvent(bHomePlayer, playerPosition, "FO", 1, currentMinute));
			}
		}else if(freeThrowsOutCount <= -1){
			for(int i=0; i<-freeThrowsOutCount; i++){
				events.add(new ActEvent(bHomePlayer, playerPosition, "FO", -1, currentMinute));
			}
		}
		
		// Fouls
		if(foulsCount >= 1){
			for(int i=0; i<foulsCount; i++){
				events.add(new ActEvent(bHomePlayer, playerPosition, "F", 1, currentMinute));
			}
		}else if(foulsCount <= -1){
			for(int i=0; i<-foulsCount; i++){
				events.add(new ActEvent(bHomePlayer, playerPosition, "F", -1, currentMinute));
			}
		}
		
		for(int i=0; i<events.size(); i++){
			ActEvent event = events.get(i);
			
			if(parent.addEvent(event)){
				parent.rebuildFromEvents();
			}
		}
	}
	
	// HANDLERS
	
	/**
	 * Implements an OnTouchListener for the various action buttons in the fragment.
	 * @see OnTouchListener
	 * @author Josep
	 */
	private class HandlerActionButton implements OnTouchListener
	{
		private Button button;
		private int action;
		
		/**
		 * Creates a HandlerActionButton.
		 * 
		 * @param button the button this handler is set to.
		 * @param action the action this button will execute.
		 * @see HandlerActionButton
		 * @author Josep
		 */
		public HandlerActionButton(Button button, int action)
		{
			this.button = button;
			this.action = action;
		}
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			
			switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					button.setBackgroundResource(R.drawable.button_player_selected);
					break;
				case MotionEvent.ACTION_UP:
					button.setBackgroundResource(R.drawable.button_player);
					doAction(action);
					break;
				default:
					break;
			}
			return true;
		}
		
		private void doAction(int action){
			int result;
			switch(action){
				// Add 2 Points
				case 1:
					points2Count++;
					break;
					
				// Add 3 Points
				case 2:
					points3Count++;
					break;
					
				// Remove 2 Points	
				case 3:
					if(points2Count > 0){
						points2Count--;
					}
					break;
					
				// Remove 3 Points
				case 4:
					if(points3Count > 0){
						points3Count--;
					}
					break;
					
				// Add 1 Free Throw Made
				case 5:
					freeThrowsInCount++;
					break;
					
				// Add 1 Free Throw Missed
				case 6:
					freeThrowsOutCount++;
					break;
					
				// Remove 1 Free Throw Made
				case 7:
					if(freeThrowsInCount > 0){
						freeThrowsInCount--;
					}
					break;
					
				// Remove 1 Free Throw Missed
				case 8:
					if(freeThrowsOutCount > 0){
						freeThrowsOutCount--;
					}
					break;
					
				// Add 1 Foul
				case 9:
					foulsCount++;
					break;
					
				// Remove 1 Foul
				case 10:
					if(foulsCount > 0){
						foulsCount--;
					}
					break;
					
				default:
					break;
			}
			updateStatsToAdd();
		}
	}
	
	
	/**
	 * Implements an OnTouchListener for the "confirm" button.
	 * @see OnTouchListener
	 * @author Josep
	 *
	 */
	private class HandlerConfirm implements OnTouchListener
	{	
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			
			switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					buttonConfirm.setBackgroundResource(R.drawable.button_player_selected);
					break;
				case MotionEvent.ACTION_UP:
					buttonConfirm.setBackgroundResource(R.drawable.button_player);
					confirmActions();
					parent.playerActionsConfirmed();
					break;
				default:
					break;
			}
			return true;
		}
	}
	
}
