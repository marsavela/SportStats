package adm.werock.sportstats;

import java.util.ArrayList;

import android.R.color;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class InsertBasketDataFragment extends Fragment
{	
	private ArrayList<Button> homeButtons = new ArrayList<Button>();
	private ArrayList<Button> awayButtons = new ArrayList<Button>();
	
	private Button buttonAddMinute;
	private Button buttonSubstractMinute;
	private Button buttonCurrentMinute;
	private Button buttonHomeTeamBonus;
	private Button buttonAwayTeamBonus;
	
	private TextView textHomeScore;
	private TextView textAwayScore;
	
	private ActivityBasketStats parent;
	
	public static android.support.v4.app.Fragment newInstance() {
		 
        // Instantiate a new fragment
		InsertBasketDataFragment fragment = new InsertBasketDataFragment();
 
        // Save the parameters
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        fragment.setRetainInstance(true);
 
        return fragment;
    }
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		homeButtons = new ArrayList<Button>();
		awayButtons = new ArrayList<Button>();
		
		View v = inflater.inflate(R.layout.fragment_data_insert, container, false);
		
		homeButtons.add((Button) v.findViewById(R.id.buttonHome1));
		homeButtons.add((Button) v.findViewById(R.id.buttonHome2));
		homeButtons.add((Button) v.findViewById(R.id.buttonHome3));
		homeButtons.add((Button) v.findViewById(R.id.buttonHome4));
		homeButtons.add((Button) v.findViewById(R.id.buttonHome5));
		homeButtons.add((Button) v.findViewById(R.id.buttonHome6));
		homeButtons.add((Button) v.findViewById(R.id.buttonHome7));
		homeButtons.add((Button) v.findViewById(R.id.buttonHome8));
		homeButtons.add((Button) v.findViewById(R.id.buttonHome9));
		homeButtons.add((Button) v.findViewById(R.id.buttonHome10));
		homeButtons.add((Button) v.findViewById(R.id.buttonHome11));
		homeButtons.add((Button) v.findViewById(R.id.buttonHome12));
		
		for(int i=0; i<homeButtons.size(); i++)
		{
			homeButtons.get(i).setOnTouchListener(new HandlerButtonPlayer(homeButtons.get(i),i,true));
		}
		
		awayButtons.add((Button) v.findViewById(R.id.buttonAway1));
		awayButtons.add((Button) v.findViewById(R.id.buttonAway2));
		awayButtons.add((Button) v.findViewById(R.id.buttonAway3));
		awayButtons.add((Button) v.findViewById(R.id.buttonAway4));
		awayButtons.add((Button) v.findViewById(R.id.buttonAway5));
		awayButtons.add((Button) v.findViewById(R.id.buttonAway6));
		awayButtons.add((Button) v.findViewById(R.id.buttonAway7));
		awayButtons.add((Button) v.findViewById(R.id.buttonAway8));
		awayButtons.add((Button) v.findViewById(R.id.buttonAway9));
		awayButtons.add((Button) v.findViewById(R.id.buttonAway10));
		awayButtons.add((Button) v.findViewById(R.id.buttonAway11));
		awayButtons.add((Button) v.findViewById(R.id.buttonAway12));
		
		for(int i=0; i<awayButtons.size(); i++)
		{
			awayButtons.get(i).setOnTouchListener(new HandlerButtonPlayer(awayButtons.get(i),i,false));
		}
			
		buttonAddMinute = (Button) v.findViewById(R.id.buttonAddMinute);
		buttonSubstractMinute = (Button) v.findViewById(R.id.buttonSubstractMinute);
		buttonCurrentMinute = (Button) v.findViewById(R.id.buttonCurrentMinute);
		buttonHomeTeamBonus = (Button) v.findViewById(R.id.buttonBonusHome);
		buttonAwayTeamBonus = (Button) v.findViewById(R.id.buttonBonusAway);
		textHomeScore = (TextView) v.findViewById(R.id.textHomeScore);
		textAwayScore = (TextView) v.findViewById(R.id.textAwayScore);
		
		buttonAddMinute.setOnTouchListener(new HandlerButtonMinute(buttonAddMinute, true));
		buttonSubstractMinute.setOnTouchListener(new HandlerButtonMinute(buttonSubstractMinute, false));
		textHomeScore.setText("0");
		textAwayScore.setText("0");
		
		return v;
	}
	
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);

	    Activity activity = getActivity();
    	
    	if(activity instanceof ActivityBasketStats){
    		parent = ((ActivityBasketStats)activity);
    	}
    	
    	setPlayers();
    	buttonCurrentMinute.setText(parent.getTimeString());
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		updateFouls();
		updateScore();
	}
	
	
	private class HandlerButtonMinute implements OnTouchListener
	{
		private Button button;
		private boolean bAdd;
		
		/**
		 * Creates an OnTouchListener for a Button. Its function is to add or
		 * substract when released.
		 * <p>
		 * @param button the button which this listener is set to.
		 * @param bAdd   if true the button add, if false the button substracts.
		 * @see   OnTouchListener
		 * @author Josep
		 */
		public HandlerButtonMinute(Button button, boolean bAdd){
			this.button = button;
			this.bAdd = bAdd;
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					button.setBackgroundResource(R.drawable.square_gray_64);
					break;
				case MotionEvent.ACTION_UP:
					button.setBackgroundResource(R.drawable.square_dark_gray_32);
					if(bAdd){
						parent.addMinute();
					}else{
						parent.substractMinute();
					}
					buttonCurrentMinute.setText(parent.getTimeString());
					break;
				default:
					button.setBackgroundResource(R.drawable.square_dark_gray_32);
					break;
			}
			return true;
		}
	}
	
	/**
	 * This class implements an OnTouchListener to handle all the player buttons in the fragment.
	 * It changes the button's background if pressed and does the according action.
	 * @see OnTouchListener
	 * @author Josep
	 */
	private class HandlerButtonPlayer implements OnTouchListener
	{
		private Button button;
		private int number;
		private boolean bHome;
		
		/**
		 * Creates a HandlerButtonPlayer
		 * @param button the button this handler is set to
		 * @param number the position in the players array
		 * @param bHome  if true the player belongs to the home team, otherwise it belongs to the away team.
		 * @see   HandlerButtonPlayer
		 * @author Josep
		 */
		public HandlerButtonPlayer(Button button, int number, boolean bHome){
			this.button = button;
			this.number = number;
			this.bHome = bHome;
		}
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			
			switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					button.setBackgroundResource(R.drawable.button_player_selected);
					break;
				case MotionEvent.ACTION_UP:
					button.setBackgroundResource(R.drawable.button_player);
					parent.playerSelected(number, bHome);
					break;
				default:
					button.setBackgroundResource(R.drawable.button_player);
					break;
			}
			return true;
		}
		
	}
	
	
	/**
	 * Reads the player array from its parent activity and sets the buttons' texts accordingly.
	 * If a team has less than 12 players the remaining buttons are set as inactive. 
	 * @author Josep
	 */
	@SuppressLint("UseValueOf")
	public void setPlayers()
	{
		ArrayList<ActPlayer> homePlayers = parent.getHomePlayers();
		ArrayList<ActPlayer> awayPlayers = parent.getAwayPlayers();
		
		for(int i=0; i<homeButtons.size(); i++)
		{
			if(i < homePlayers.size()){
				homeButtons.get(i).setText(new Integer(homePlayers.get(i).getNumber()).toString());
			}else{
				homeButtons.get(i).setBackgroundResource(R.drawable.button_player_out);
				homeButtons.get(i).setText("");
				homeButtons.get(i).setEnabled(false);
			}
		}
		
		for(int i=0; i<awayButtons.size(); i++)
		{
			if(i < awayPlayers.size()){
				awayButtons.get(i).setText(new Integer(awayPlayers.get(i).getNumber()).toString());
			}else{
				awayButtons.get(i).setBackgroundResource(R.drawable.button_player_out);
				awayButtons.get(i).setText("");
				awayButtons.get(i).setEnabled(false);
			}
		}
	}
	
	public void updatePlayer(){
	}
	
	
	/**
	 * This method updates the home and away score text.
	 * This method is called by its parent when the score has changed. 
	 * @author Josep
	 */
	public void updateScore(){
		textHomeScore.setText(new Integer(parent.getHomeTeamScore()).toString());
		textAwayScore.setText(new Integer(parent.getAwayTeamScore()).toString());
	}
	
	
	/**
	 * This method updates the bonus indicator on both teams if the number of fouls
	 * in the current quarter is >= 5. Also checks for players who have been fouled
	 * out, changing the background of its button to indicate this condition.
	 * @author Josep
	 */
	public void updateFouls(){
		if(parent.getHomeTeamFouls() >= 5){
			buttonHomeTeamBonus.setBackgroundResource(R.drawable.square_red_64);
			buttonHomeTeamBonus.setTextColor(Color.parseColor("WHITE"));
		}else{
			buttonHomeTeamBonus.setBackgroundResource(R.drawable.square_lightgray_64);
			buttonHomeTeamBonus.setTextColor(Color.rgb(128, 128, 128));
		}
		
		for(int i=0; i<parent.getHomePlayers().size(); i++){
			if(parent.getHomePlayers().get(i).getFouls() == 5){
				homeButtons.get(i).setBackgroundResource(R.drawable.button_player_out);
			}
		}
		
		if(parent.getAwayTeamFouls() >= 5){
			buttonAwayTeamBonus.setBackgroundResource(R.drawable.square_red_64);
			buttonAwayTeamBonus.setTextColor(Color.parseColor("WHITE"));
		}else{
			buttonAwayTeamBonus.setBackgroundResource(R.drawable.square_lightgray_64);
			buttonAwayTeamBonus.setTextColor(Color.rgb(128, 128, 128));
		}
		
		for(int i=0; i<parent.getAwayPlayers().size(); i++){
			if(parent.getAwayPlayers().get(i).getFouls() == 5){
				awayButtons.get(i).setBackgroundResource(R.drawable.button_player_out);
			}
		}
	}
}