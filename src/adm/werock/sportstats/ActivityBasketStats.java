package adm.werock.sportstats;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public class ActivityBasketStats extends FragmentActivity
{
	
	// Fragments
	private InsertBasketDataFragment insertDataFragment = (InsertBasketDataFragment) InsertBasketDataFragment.newInstance();
	private PreviewBasketStatsFragment previewStatsFragment = (PreviewBasketStatsFragment) PreviewBasketStatsFragment.newInstance();
	private FragmentHelpDataInsert fragmentHelp = (FragmentHelpDataInsert) FragmentHelpDataInsert.newInstance();
	private FragmentBasketEventList fragmentEventList = (FragmentBasketEventList) FragmentBasketEventList.newInstance();
	
	ViewPager pager = null;
	
    // The pager adapter, which provides the pages to the view pager widget.
    ActFragmentPagerAdapter pagerAdapter;
    private int previousPage=0;
    private boolean bActionsConfirmed=false;
    
    // Players
    private ArrayList<ActPlayer> homePlayers = new ArrayList<ActPlayer>();
    private ArrayList<ActPlayer> awayPlayers = new ArrayList<ActPlayer>();
    private ActPlayer selectedPlayer = null;
    private int selectedPlayerIndex;
    private boolean bSelectedHomePlayer;
 
    // Time
    private int currentMinute = 0;
    private int currentQuarter = 1;
    private int maximumMinute = 0;
    private boolean bGameEnd = false;
    
    // Others
    private int homeTeamScore = 0;
    private int awayTeamScore = 0;
    private int homeTeamFouls = 0;
    private int awayTeamFouls = 0;
    
    // Pages & Help
    private boolean bHelpEnabled = false;
    
    // Events
    private ArrayList<ActEvent> events = new ArrayList<ActEvent>();
    
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        this.setContentView(R.layout.layout_basket_stats);
 
        // Instantiate a ViewPager
        this.pager = (ViewPager) this.findViewById(R.id.actViewPager);
 
        // Create an adapter with the fragments we show on the ViewPager
        pagerAdapter = new ActFragmentPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment((Fragment)fragmentEventList);
        pagerAdapter.addFragment((Fragment)previewStatsFragment);
        pagerAdapter.addFragment((Fragment)insertDataFragment);
        
        this.pager.setAdapter(pagerAdapter);
        this.pager.setOnPageChangeListener(new PageListener());
        this.pager.setCurrentItem(2);
        
        setPlayers();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_basket_stats, menu);
        return true;
    }
    
    @Override
	protected void onResume() {
		super.onResume();
		
		// Intitialize Events Array
		// Read from DB
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
        case R.id.action_help:
            if(bHelpEnabled){
            	disableHelp();
            	bHelpEnabled = false;
            }else{
            	enableHelp();
            	bHelpEnabled = true;
            }
            return true;
        default:
            return true;
     }
	}
	
	public void rebuild(){
		pager.setAdapter(pagerAdapter);
	}
    
    @Override
	public void onBackPressed() {
		if(pager.getCurrentItem() == 2){
			super.onBackPressed();
		}else{
			pager.setCurrentItem(2);
		}
	}
    
    // TODO: HELP
    
    public void enableHelp()
    {
    	FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        
        ft.add(R.id.overlayFragment,fragmentHelp);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
    
    public void disableHelp()
    {
    	FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        
        ft.remove(fragmentHelp);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
    
    // TODO: EVENTS
    
    public boolean addEvent(ActEvent event){
    	
    	String type = event.getType();
    	int value = event.getValue();
    	
    	if(type.equals("P")){
    		if(value > 0){
    			events.add(event);
    			return true;
    		}/*else{
    			event.setValue(-value);
    			return searchAndRemoveEvent(event);
    		}*/
    	}
    	else if(type.equals("F")){
    		if(value > 0){
    			events.add(event);
    			
    			if(event.isHomePlayer()){
    				homeTeamFouls = homeTeamFouls + event.getValue();
    			}else{
    				awayTeamFouls = awayTeamFouls + event.getValue();
    			}
    			
    			return true;
    		}/*else{
    			event.setValue(-value);
    			return searchAndRemoveEvent(event);
    		}*/
    	}
    	else if(type.equals("FTI")){
    		if(value > 0){
    			events.add(event);
    			return true;
    		}/*else{
    			event.setValue(-value);
    			return searchAndRemoveEvent(event);
    		}*/
    	}
    	else if(type.equals("FTO")){
    		if(value > 0){
    			events.add(event);
    			return true;
    		}/*else{
    			event.setValue(-value);
    			return searchAndRemoveEvent(event);
    		}*/
    	}
    	
    	return false;
    }
    
    public boolean searchAndRemoveEvent(ActEvent e){
    	for(int i=events.size()-1; i>=0; i--){
    		ActEvent event = events.get(i);
    		if(event.isHomePlayer() == e.isHomePlayer() &&
    		   event.getPlayerPosition() == e.getPlayerPosition() &&
    		   event.getValue() == e.getValue() &&
    		   event.getType().equals(e.getType())){
    			events.remove(i);
    			Log.d("ActivityBasketStats","Event deleted");
    			return true;
    		}
    	}
    	Log.e("ActivityBasketStats","Event not found");
    	return false;
    }
    
    public ArrayList<ActEvent> getEvents(){
    	return events;
    }
    
    public void rebuildFromEvents(){
    	for(int i=0; i<homePlayers.size(); i++){
    		homePlayers.get(i).resetStats();
    	}
    	for(int i=0; i<awayPlayers.size(); i++){
    		awayPlayers.get(i).resetStats();
    	}
    	
    	homeTeamScore = 0;
    	awayTeamScore = 0;
    	
    	for(int i=0; i<events.size(); i++){
    		ActEvent event = events.get(i);
    		
    		if(event.getType().equals("P")){
    			if(event.isHomePlayer()){
    				homePlayers.get(event.getPlayerPosition()).addPoints(event.getValue());
    				homeTeamScore += event.getValue();
    			}else{
    				awayPlayers.get(event.getPlayerPosition()).addPoints(event.getValue());
    				awayTeamScore += event.getValue();
    			}
    		}
    		
    		else if(event.getType().equals("FTI")){
    			if(event.isHomePlayer()){
    				homePlayers.get(event.getPlayerPosition()).addFreeThrowsMade(event.getValue());
    				homeTeamScore += event.getValue();
    			}else{
    				awayPlayers.get(event.getPlayerPosition()).addFreeThrowsMade(event.getValue());
    				awayTeamScore += event.getValue();
    			}
    		}
    		
    		else if(event.getType().equals("FTO")){
    			if(event.isHomePlayer()){
    				homePlayers.get(event.getPlayerPosition()).addFreeThrowsMissed(event.getValue());
    			}else{
    				awayPlayers.get(event.getPlayerPosition()).addFreeThrowsMissed(event.getValue());
    			}
    		}
    		
    		else if(event.getType().equals("F")){
    			if(event.isHomePlayer()){
    				homePlayers.get(event.getPlayerPosition()).addFouls(event.getValue());
    			}else{
    				awayPlayers.get(event.getPlayerPosition()).addFouls(event.getValue());
    			}
    		}
    	}
    	updateFouls(currentQuarter);
    }
    
    public void updateFouls(int quarter){
    	int minMinute = 0;
    	int maxMinute = 0;
    	
    	if(quarter > 4){
    		maxMinute = 39+((quarter-4)*5);
    		minMinute = maxMinute - 9;
    	}else{
    		maxMinute = (quarter*10)-1;
    		minMinute = maxMinute - 9;
    	}
    	
    	// Fouls
    	homeTeamFouls = 0;
    	awayTeamFouls = 0;
    	
    	for(int i=0; i<events.size(); i++)
    	{
    		ActEvent event = events.get(i);
    		
    		if(event.getMinute() >= minMinute &&
    		   event.getMinute() <= maxMinute &&
    		   event.getType().equals("F"))
    		{
    			if(event.isHomePlayer()){
    				homeTeamFouls ++;
    			}else{
    				awayTeamFouls ++;
    			}
    		}
    	}
    	
    	insertDataFragment.updateFouls();
    	//rebuild();
    	pagerAdapter.notifyDataSetChanged();
    }
    
    // TODO: PLAYERS
    
    public void setPlayers(){
    	homePlayers.add(new ActPlayer(4,"Sergiu", "Marsavela","001"));
    	homePlayers.add(new ActPlayer(5,"Fran", "Martin","002"));
    	homePlayers.add(new ActPlayer(6,"Patrick", "Mondria","003"));
    	homePlayers.add(new ActPlayer(7,"Diego", "Panadero","004"));
    	homePlayers.add(new ActPlayer(8,"Alvaro", "Peris","005"));
    	homePlayers.add(new ActPlayer(9,"Josep", "Tomas","006"));
    	
    	awayPlayers.add(new ActPlayer(32,"Earvin","Johnson", "007"));
    	awayPlayers.add(new ActPlayer(33,"Larry","Bird", "008"));
    	awayPlayers.add(new ActPlayer(34,"Charles","Barkley", "009"));
    	awayPlayers.add(new ActPlayer(6,"LeBron","James", "010"));
    	awayPlayers.add(new ActPlayer(23,"Michael","Jordan", "011"));
    	awayPlayers.add(new ActPlayer(24,"Kobe","Bryant", "012"));
    	awayPlayers.add(new ActPlayer(55,"Dikembe","Mutombo","013"));
    }
    
    public ArrayList<ActPlayer> getHomePlayers(){
    	return homePlayers;
    }
    
    public ArrayList<ActPlayer> getAwayPlayers(){
    	return awayPlayers;
    }
    
    public ActPlayer getSelectedPlayer(){
    	return selectedPlayer;
    }
    
    public ActPlayer getPlayer(boolean bHome, int index){
    	if(bHome){
    		return homePlayers.get(index);
    	}else{
    		return awayPlayers.get(index);
    	}
    }
    
    public int getSelectedPlayerIndex(){
    	return selectedPlayerIndex;
    }
	
	public boolean isHomePlayerSelected(){
		return bSelectedHomePlayer;
	}
	
	public void updatePlayerButton(){
	}
	
	public int getHomeTeamScore(){
		return homeTeamScore;
	}
	
	public int getAwayTeamScore(){
		return awayTeamScore;
	}
	
	public void addPoints(int points, boolean bHome){
		if(bHome){
			homeTeamScore += points;
		}else{
			awayTeamScore += points;
		}
		insertDataFragment.updateScore();
	}
	
	public int getHomeTeamFouls(){
		return homeTeamFouls;
	}
	
	public int getAwayTeamFouls(){
		return awayTeamFouls;
	}
	
	public void addFouls(int fouls, boolean bHome){
		if(bHome){
			homeTeamFouls += fouls;
		}else{
			awayTeamFouls += fouls;
		}
		insertDataFragment.updateFouls();
	}
	
	public void playerSelected(int number, boolean bHome){
		if(bHome){
			bSelectedHomePlayer = true;
			selectedPlayer = homePlayers.get(number);
			selectedPlayerIndex = number;
		}else{
			bSelectedHomePlayer = false;
			selectedPlayer = awayPlayers.get(number);
			selectedPlayerIndex = number;
		}
		
		if(selectedPlayer.getFouls() < 5)
		{
			pagerAdapter.addFragment(PlayerBasketDataFragment.newInstance());
			pagerAdapter.notifyDataSetChanged();
			pager.setCurrentItem(3);
		}
		else
		{
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(getResources().getString(R.string.playerFouledOutDialog))
			       .setCancelable(false)
			       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                //do things
			           }
			       });
			AlertDialog alert = builder.create();
			alert.show();
			
			insertDataFragment.updateFouls();
		}
	}
	
	public void playerActionsConfirmed(){
		bActionsConfirmed=true;
		pagerAdapter.deleteFragment(pagerAdapter.getItem(3));
		pagerAdapter.notifyDataSetChanged();
		
		pager.setCurrentItem(2);
		pager.postDelayed(deletePlayerFragment, 200);
		bActionsConfirmed=false;
	}
	
	public void playerActionsDiscarded(){
		pagerAdapter.deleteFragment(pagerAdapter.getItem(3));
		pagerAdapter.notifyDataSetChanged();
		
		pager.setCurrentItem(2);
		pager.postDelayed(deletePlayerFragment, 200);
	}
	
	final Runnable deletePlayerFragment = new Runnable()
	{
	    public void run() 
	    {
	    	pager.setAdapter(pagerAdapter);
	    	pager.setCurrentItem(2);
	    }
	};
	
	/**
	 * Implements an OnPageChangeListener for the ViewPager. Its function is to detect
	 * in which page the ViewPager is and act accordingly.
	 * <p>
	 * If the current page is InsertBasketDataFragment and the previous page was
	 * PlayerBasketDataFragment then remove this fragment and discard the player's actions.
	 * @author Josep
	 *
	 */
	private class PageListener implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int arg0) {}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {}

		@Override
		public void onPageSelected(int page) {
			switch(page){
				case 0:
					setTitle("Events");
					break;
				case 1:
					setTitle("Stats preview");
					break;
				case 2:
					setTitle("Match");
					break;
				case 3:
					setTitle("Player actions");
					break;
				default:
					break;
			}
			
			if(previousPage == 3 && bActionsConfirmed == false){
				playerActionsDiscarded();
			}
			previousPage = page;
		}
	}
	
	public int getCurrentPage(){
		return previousPage;
	}
	
	// TODO:  TIME
	
	/**
	 * This method increments the current minute of the game and checks for quarter transitions.
	 * @return true if the game has ended, false otherwise
	 * @author Josep
	 */
	public void addMinute(){
		/*currentMinute ++;
		
		if(currentMinute == 10){
			currentMinute = 0;
			currentQuarter ++;
			homeTeamFouls = 0;
			awayTeamFouls = 0;
			insertDataFragment.updateFouls();
			
			if(currentQuarter > 4){
				currentQuarter = 4;
				currentMinute = 10;
				return true;
			}
		}
		return false;*/
		
		int nextQuarter;
		
		currentMinute++;
		if(currentQuarter <= 4){
			nextQuarter = (currentMinute/10)+1;
		}else{
			nextQuarter = ((currentMinute%40)/5)+5;
		}
		
		if(nextQuarter != currentQuarter){
			updateFouls(nextQuarter);
			currentQuarter = nextQuarter;
		}
		
		if(currentMinute == 40 || (currentMinute > 40 && currentMinute%5 == 0)){
			if(homeTeamScore != awayTeamScore){
				enableEnd();
				bGameEnd = true;
			}
		}
		
		if(currentMinute > maximumMinute)
			maximumMinute = currentMinute;
	}
	
	/**
	 * This method decrements the current minute of the game.
	 * @author Josep
	 */
	public void substractMinute(){
		/*currentMinute --;
		
		if(currentMinute == -1){
			currentMinute = 9;
			currentQuarter --;
			if(currentQuarter < 1){
				currentMinute = 0;
				currentQuarter = 1;
			}
		}*/
		
		int nextQuarter;
		bGameEnd = false;
		
		if(currentMinute>0){
			currentMinute--;
		}
		
		if(currentMinute < 40){
			nextQuarter = (currentMinute/10)+1;
		}else{
			nextQuarter = ((currentMinute%40)/5)+5;
		}
		
		if(nextQuarter != currentQuarter){
			updateFouls(nextQuarter);
			currentQuarter = nextQuarter;
		}
	}
	
	public int getCurrentMinute(){
		return currentMinute;
	}
	
	public int getCurrentQuarter(){
		return currentQuarter;
	}
	
	public int getMaximumMinute(){
		return maximumMinute;
	}
	
	
	/**
	 * This method returns a string which indicates the current quarter and the
	 * current minute of the game.
	 * @return string with the form "Q" + currentQuarter + " - MIN. " + currentMinute.
	 */
	public String getTimeString(){
		if(bGameEnd){
			return getResources().getString(R.string.labelEnd);
		}
		
		if(currentQuarter <= 4){
			return new String("Q"+currentQuarter+" - MIN. "+(currentMinute%10));
		}else{
			return new String("OT"+String.valueOf(currentQuarter-4)+" - MIN. "+(currentMinute%5));
		}
	}
	
	// END
	
	public void enableEnd(){
		insertDataFragment.enableEnd();
	}
	
	public void disableEnd(){
		insertDataFragment.disableEnd();
	}
	
	public void finishAct(){
		// TODO:
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getResources().getString(R.string.finishActDialog));
		//builder.setCancelable(false);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                //do things
		           }
		       });
		builder.setNegativeButton("Not yet", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                //do nothing
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}
	
}