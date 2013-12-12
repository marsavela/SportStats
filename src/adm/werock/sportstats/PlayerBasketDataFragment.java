package adm.werock.sportstats;

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

public class PlayerBasketDataFragment extends Fragment{
	
	private Button buttonAdd2Points;
	private Button buttonAdd3Points;
	private Button buttonRemove1Point;
	private Button buttonRemove2Points;
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
	private int pointsToAdd = 0;
	private int foulsToAdd  = 0;
	private int freeThrowsMadeToAdd = 0;
	private int freeThrowsMissedToAdd = 0;
	
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
		buttonRemove1Point = (Button) v.findViewById(R.id.buttonRemovePoint);
		buttonRemove2Points	= (Button) v.findViewById(R.id.buttonRemove2Points);
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
		buttonRemove1Point.setOnTouchListener(new HandlerActionButton(buttonRemove1Point, 3));
		buttonRemove2Points.setOnTouchListener(new HandlerActionButton(buttonRemove2Points, 4));
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
		headerPlayerStatsToAddPointsFouls.setText(getResources().getString(R.string.labelPoints)+pointsToAdd+" "+
		  		  								  getResources().getString(R.string.labelFouls)+foulsToAdd);
		headerPlayerStatsToAddFreeThrows.setText(getResources().getString(R.string.labelFreeThrows)+freeThrowsMadeToAdd+"/"+freeThrowsMissedToAdd);
	}
	
	/**
	 * This method applies the actions to the selected player and its team.
	 * @author Josep
	 */
	private void confirmActions()
	{
		player.addPoints(pointsToAdd+freeThrowsMadeToAdd);
		parent.addPoints(pointsToAdd+freeThrowsMadeToAdd, parent.isHomePlayerSelected());
		player.addFouls(foulsToAdd);
		parent.addFouls(foulsToAdd, parent.isHomePlayerSelected());
		player.addFreeThrowsMade(freeThrowsMadeToAdd);
		player.addFreeThrowsMissed(freeThrowsMissedToAdd);
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
			switch(action){
				case 1:
					pointsToAdd += 2; break;
				case 2:
					pointsToAdd += 3; break;
				case 3:
					pointsToAdd -= 1; break;
				case 4:
					pointsToAdd -= 2; break;
				case 5:
					freeThrowsMadeToAdd += 1; break;
				case 6:
					freeThrowsMissedToAdd += 1; break;
				case 7:
					freeThrowsMadeToAdd -= 1; break;
				case 8:
					freeThrowsMissedToAdd -= 1; break;
				case 9:
					foulsToAdd += 1; break;
				case 10:
					foulsToAdd -= 1; break;
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
