package adm.werock.sportstats;

import java.util.ArrayList;
import java.util.Hashtable;

import dao.ActDBHelper;

import adm.werock.sportstats.basics.*;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

/**
 * This activity contains all the fragments needed for adding events and to preview
 * events and stats. This activity also contains all de data needed by the fragments
 * such as events, players, minutes or fouls. When a Fragment needs some specific
 * data, the activity provides it.
 * 
 * @author Josep
 *
 */
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
    private Hashtable<Integer,Integer> homeLicenses = new Hashtable<Integer,Integer>();
    private Hashtable<Integer,Integer> awayLicenses = new Hashtable<Integer,Integer>();
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
    private int maxEventID = 0;
    
    // Database
    private int actID;
    private ActDBHelper helper;
    
    /**
     * Creates the ViewPager with the Fragments event list, stats preview and data insert.
     * Gets the ActID from the Intent extras and extract the players from the database.
     * 
     * @author Josep
     *
     */
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
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
        
        // Get the Act ID from the bundle
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            actID = extras.getInt("actID");
            //Log.i("BasketStats.onCreate()","Act ID: " + actID);
        }else{
        	//Log.e("BasketStats.onCreate()","No data received through the Extras.");
        }
        
        // Initialize the DB helper
        helper = new ActDBHelper(getApplicationContext());
        
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
		
	}

    
    /**
     * Enables or disables the overlay help fragment if the "?" icon is clicked.
     * 
     * @author Josep
     *
     */
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
	
	
	/**
     * Forces the ViewPager to redraw all the fragments.
     * 
     * @author Josep
     *
     */
	public void rebuild(){
		pager.setAdapter(pagerAdapter);
	}
    
	
	/**
     * Returns to the main page in the ViewPager if the current page is not the main.
     * Otherwise returns to the previous activity.
     * 
     * @author Josep
     *
     */
    @Override
	public void onBackPressed() {
		if(pager.getCurrentItem() == 2){
			super.onBackPressed();
		}else{
			pager.setCurrentItem(2);
		}
	}
    
    // TODO: HELP
    
    /**
     * Draws a semi-opaque Fragment over the ViewPager containing graphical help about the
     * current Fragment.
     * 
     * @author Josep
     *
     */
    public void enableHelp()
    {
    	FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        
        ft.add(R.id.overlayFragment,fragmentHelp);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    
    /**
     * Deletes the semi-opaque help Fragment.
     * 
     * @see enableHelp
     * @author Josep
     *
     */
    public void disableHelp()
    {
    	FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        
        ft.remove(fragmentHelp);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
    
    // TODO: EVENTS
    
    
    /**
     * Checks if the ActEvent type is valid and adds an ActEvent to the current list of
     * events.
     * 
     * @author Josep
     *
     */
    public boolean addEvent(ActEvent event){
    	
    	String type = event.getType();
    	int value = event.getValue();
    	
    	if(type.equals("P")){
    		if(value > 0){
    			events.add(event);
    			return true;
    		}
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
    		}
    	}
    	else if(type.equals("FI")){
    		if(value > 0){
    			events.add(event);
    			return true;
    		}
    	}
    	else if(type.equals("FO")){
    		if(value > 0){
    			events.add(event);
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    
    /**
     * Given an ActEvent to delete, this will search and delete the latest ActEvent in the
     * list with the same Minute, Player, Type and Value .
     * 
     * @return true if the ActEvent has been deleted, false otherwise.
     * @param e ActEvent to delete.
     * @author Josep
     *
     */
    public boolean searchAndRemoveEvent(ActEvent e){
    	for(int i=events.size()-1; i>=0; i--){
    		ActEvent event = events.get(i);
    		if(event.isHomePlayer() == e.isHomePlayer() &&
    		   event.getPlayerPosition() == e.getPlayerPosition() &&
    		   event.getValue() == e.getValue() &&
    		   event.getType().equals(e.getType())){
    			events.remove(i);
    			//Log.d("ActivityBasketStats","Event deleted");
    			return true;
    		}
    	}
    	//Log.e("ActivityBasketStats","Event not found");
    	return false;
    }
    
    
    /**
     * Sorts all the ActEvents in the list by minute and inserts them to the local database.
     * 
     * @author Josep
     *
     */
    public void saveEventsInDatabase()
    {
    	maxEventID++;
    	
    	// Preprocessing the event list to save in local db a list of events sorted by minute.
    	//Log.d("BasketStats.saveEventsInDatabase()","Preprocessing");
    	ArrayList<ArrayList<ActEvent>> actEvents = new ArrayList<ArrayList<ActEvent>>();
    	
    	for(int i=0; i<=maximumMinute; i++){
    		actEvents.add(new ArrayList<ActEvent>());
    	}
    	
    	for(int i=0; i<events.size(); i++){
			ActEvent event = events.get(i);
			actEvents.get(event.getMinute()).add(event);
		}
    	
    	// Add the events to de local db
    	//Log.d("BasketStats.saveEventsInDatabase()","Adding events");
    	for(int i=0; i<=maximumMinute; i++)
    	{
    		ArrayList<ActEvent> eventsInMinute = actEvents.get(i);
    		
    		for(int j=0; j<eventsInMinute.size(); j++)
    		{
    			// Convert the "ActEvent" to "Event"
    			ActEvent actEvent = eventsInMinute.get(j);
    			
    			String type = actEvent.getType();
    			String value = String.valueOf(actEvent.getValue());
    			int playerLicense;
    			
    			if(actEvent.isHomePlayer()){
    				playerLicense = homePlayers.get(actEvent.getPlayerPosition()).getLicenseNumber();
    			}else{
    				playerLicense = awayPlayers.get(actEvent.getPlayerPosition()).getLicenseNumber();
    			}
    			
    			// Insert in the database
    			helper.insertEvent(new Event(maxEventID, actID, i, type, value, playerLicense));
    			maxEventID++;
    		}
    	}
    	//Log.d("BasketStats.saveEventsInDatabase()","Finished");
    }
    
    
    /**
     * Method for obtain the current list of events in this activity. Called by its Fragments.
     * 
     * @return list of ActEvents in the activtity.
     * @author Josep
     *
     */
    public ArrayList<ActEvent> getEvents(){
    	return events;
    }
    
    
    /**
     * Reconstruct all the players stats by consulting all the ActEvents in the event list.
     * 
     * @author Josep
     *
     */
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
    		
    		else if(event.getType().equals("FI")){
    			if(event.isHomePlayer()){
    				homePlayers.get(event.getPlayerPosition()).addFreeThrowsMade(event.getValue());
    				homeTeamScore += event.getValue();
    			}else{
    				awayPlayers.get(event.getPlayerPosition()).addFreeThrowsMade(event.getValue());
    				awayTeamScore += event.getValue();
    			}
    		}
    		
    		else if(event.getType().equals("FO")){
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
    
    
    /**
     * Obtains the number of fouls of each team in the given quarter of the match. Invokes
     * the same method in the InsertDataFragment to show results.
     *  
     * @param quarter Quarter of the match.
     * @see insertDataFragment#updateFouls
     * @author Josep
     *
     */
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
    
    
    /**
     * Obtains the identifier of the current Act and reads it from the local database. Gets
     * the teams ID's and reads their players. Reads the events and uses them to obtain the
     * lists of active home and away players in the match.
     *  
     * @author Josep
     *
     */
    public void setPlayers(){
    	/*homePlayers.add(new ActPlayer(4,"Sergiu", "Marsavela",1));
    	homePlayers.add(new ActPlayer(5,"Fran", "Martin",2));
    	homePlayers.add(new ActPlayer(6,"Patrick", "Mondria",3));
    	homePlayers.add(new ActPlayer(7,"Diego", "Panadero",4));
    	homePlayers.add(new ActPlayer(8,"Alvaro", "Peris",5));
    	homePlayers.add(new ActPlayer(9,"Josep", "Tomas",6));
    	
    	awayPlayers.add(new ActPlayer(32,"Earvin","Johnson", 7));
    	awayPlayers.add(new ActPlayer(33,"Larry","Bird", 8));
    	awayPlayers.add(new ActPlayer(34,"Charles","Barkley", 9));
    	awayPlayers.add(new ActPlayer(6,"LeBron","James", 10));
    	awayPlayers.add(new ActPlayer(23,"Michael","Jordan", 11));
    	awayPlayers.add(new ActPlayer(24,"Kobe","Bryant", 12));
    	awayPlayers.add(new ActPlayer(55,"Dikembe","Mutombo", 13));*/
    	
    	// NEW
    	//log.i("BasketStats.setPlayers()","requesting act");
    	Act act = helper.selectAct(actID);
    	
    	//log.i("BasketStats.setPlayers()", "Home Team ID: " + act.getIdTeamHome());
    	//log.i("BasketStats.setPlayers()", "Away Team ID: " + act.getIdTeamGuest());
    	
    	// Import the players from the database
    	ArrayList<Player> homeActPlayers = helper.selectPlayers(act.getIdTeamHome());
    	//log.i("BasketStats.setPlayers()", "Imported "+ String.valueOf(homeActPlayers.size())+" HOME players.");
    	ArrayList<Player> awayActPlayers = helper.selectPlayers(act.getIdTeamGuest());
    	//log.i("BasketStats.setPlayers()", "Imported "+ String.valueOf(awayActPlayers.size())+" AWAY players.");
    	
    	// Convert the home players
    	for(int i=0; i<homeActPlayers.size(); i++){
    		//homePlayers.add(new ActPlayer(homeActPlayers.get(i)));
    		homeLicenses.put(new Integer(homeActPlayers.get(i).getLicenseNumber()), new Integer(i));
    	}
    	//log.i("BasketStats.setPlayers()", "Home license numbers obtained.");
    	
    	// Convert the away players
    	for(int i=0; i<awayActPlayers.size(); i++){
    		//awayPlayers.add(new ActPlayer(awayActPlayers.get(i)));
    		awayLicenses.put(new Integer(awayActPlayers.get(i).getLicenseNumber()), new Integer(i));
    	}
    	//log.i("BasketStats.setPlayers()", "Away license numbers obtained.");
    	
    	
    	// Get the assign events from the list
    	//Log.i("BasketStats.setPlayers()", "Requesting events.");
    	ArrayList<Event> actEvents = helper.selectEvents(actID);
    	
    	//log.i("BasketStats.setPlayers()", "Received: "+ actEvents.size() +" events.");
    	
    	int homeNumbersAssigned = 0;
    	int homeStartersAssigned = 0;
    	int homeCaptainAssigned = 0;
    	
    	int awayNumbersAssigned = 0;
    	int awayStartersAssigned = 0;
    	int awayCaptainAssigned = 0;
    	
    	for(int i=0; i<actEvents.size(); i++)
    	{
    		Event event = actEvents.get(i);
    		String type = event.getType();
    		String value = event.getValue();
    		boolean bHome = false;
    		int playerLicense = event.getPlayer();
    		
    		if(homeLicenses.containsKey(new Integer(playerLicense)))
    		{
    			bHome = true;
    		}
    		
    		if(type.equals("AN") || type.equals("A"))
    		{
    			Player player;
    			
    			if(bHome){
    				if(homeLicenses.containsKey(new Integer(playerLicense))){
    					player = homeActPlayers.get(homeLicenses.get(new Integer(playerLicense)));
    					ActPlayer actPlayer = new ActPlayer(player);
    					actPlayer.setNumber(Integer.valueOf(value));
    					homePlayers.add(actPlayer);
    					homeNumbersAssigned++;
    				}
    			}else{
    				if(awayLicenses.containsKey(new Integer(playerLicense))){
	    				player = awayActPlayers.get(awayLicenses.get(new Integer(playerLicense)));
	    				ActPlayer actPlayer = new ActPlayer(player);
    					actPlayer.setNumber(Integer.valueOf(value));
    					awayPlayers.add(actPlayer);
	    				awayNumbersAssigned++;
    				}
    			}
    		}
    		else if(type.equals("AS"))
    		{
    			homeStartersAssigned++;
    			if(bHome){
    				homePlayers.get(homeLicenses.get(playerLicense)).setAsStarter();
    				homeStartersAssigned++;
    			}else{
    				awayPlayers.get(awayLicenses.get(playerLicense)).setAsStarter();
    				awayStartersAssigned++;
    			}
    		}
    		else if(type.equals("AC"))
    		{
    			homeCaptainAssigned++;
    			if(bHome){
    				homePlayers.get(homeLicenses.get(playerLicense)).setAsCaptain();
    				homeCaptainAssigned++;
    			}else{
    				awayPlayers.get(awayLicenses.get(playerLicense)).setAsCaptain();
    				awayCaptainAssigned++;
    			}
    		}
    	}
    	
    	// Check events received
    	//log.i("BasketStats.setPlayers()","HOME  NUMBERS: " + homeNumbersAssigned + " OUT OF " + homePlayers.size());
    	//log.i("BasketStats.setPlayers()","HOME STARTERS: " + homeStartersAssigned);
    	//log.i("BasketStats.setPlayers()","HOME CAPTAINS: " + homeCaptainAssigned);
    	//log.i("BasketStats.setPlayers()","AWAY  NUMBERS: " + awayNumbersAssigned + " OUT OF " + awayPlayers.size());
    	//log.i("BasketStats.setPlayers()","AWAY STARTERS: " + awayStartersAssigned);
    	//log.i("BasketStats.setPlayers()","AWAY CAPTAINS: " + awayCaptainAssigned);
    }
    
    
    /** 
     * @return list of the home players in this match.
     * @author Josep
     */
    public ArrayList<ActPlayer> getHomePlayers(){
    	return homePlayers;
    }
    
    
    /** 
     * @return list of the away players in this match.
     * @author Josep
     */
    public ArrayList<ActPlayer> getAwayPlayers(){
    	return awayPlayers;
    }
    
    public ActPlayer getSelectedPlayer(){
    	return selectedPlayer;
    }
    
    
    /**
     * Returns the ActPlayer of a given team in a given position of the ArrayList of players.
     * 
     * @param bHome if the player belongs to the home team or not
     * @param index position in the list of players
     * @return selected player
     * @author Josep
     *
     */
    public ActPlayer getPlayer(boolean bHome, int index){
    	if(bHome){
    		return homePlayers.get(index);
    	}else{
    		return awayPlayers.get(index);
    	}
    }
    
    
    /**
     * Returns the position in the ArrayList of the current selected player.
     *  
     * @author Josep
     *
     */
    public int getSelectedPlayerIndex(){
    	return selectedPlayerIndex;
    }
	
    
    /**
     * @return true if the selected player belongs to the home team, false otherwise
     * @author Josep
     *
     */
	public boolean isHomePlayerSelected(){
		return bSelectedHomePlayer;
	}
	
	
	/**
     * @return current score of the home team
     * @author Josep
     *
     */
	public int getHomeTeamScore(){
		return homeTeamScore;
	}
	
	
	/**
     * @return current score of the away team
     * @author Josep
     *
     */
	public int getAwayTeamScore(){
		return awayTeamScore;
	}
	
	
	/**
     * Adds points to one of the teams and updates the fragment which shows the current scores.
     * 
     * @param bHome true if the points are added to the home team, false otherwise 
     * @param points amount of points to add
     * @author Josep
     *
     */
	public void addPoints(int points, boolean bHome){
		if(bHome){
			homeTeamScore += points;
		}else{
			awayTeamScore += points;
		}
		insertDataFragment.updateScore();
	}
	
	
	/**
     * @return number of fouls of the home team in the current quarter 
     * @author Josep
     *
     */
	public int getHomeTeamFouls(){
		return homeTeamFouls;
	}
	
	
	/**
     * @return number of fouls of the away team in the current quarter
     * @author Josep
     *
     */
	public int getAwayTeamFouls(){
		return awayTeamFouls;
	}
	
	/**
     * Adds fouls to one of the teams in the current quarter and updates the fragment which
     * shows the bonus indicators.
     * 
     * @param bHome true if the fouls are added to the home team, false otherwise 
     * @param fouls amount of fouls to add
     * @author Josep
     *
     */
	public void addFouls(int fouls, boolean bHome){
		if(bHome){
			homeTeamFouls += fouls;
		}else{
			awayTeamFouls += fouls;
		}
		insertDataFragment.updateFouls();
	}
	
	
	/**
     * InsertBasketDataFragment invokes this method to add a new instance of PlayerBasketDataFragment
     * to the ViewPager in which the events will be added to the selected player. If the number
     * of fouls of the selected player is less than 5 this new Fragment will be added, if not,
     * it will show a dialog informing the user that the player selected is fouled out.
     * 
     * @param number position in the players array
     * @param bHome if the player belongs to the home team or not
     * @author Josep
     * @see InsertBasketDataFragment.HandlerButtonPlayer
     *
     */
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
	
	
	/**
     * Deletes the last page in the ViewPager because it's no longer useful.
     *  
     * @see PlayerBasketDataFragment.HandlerConfirm 
     * @author Josep
     */
	public void playerActionsConfirmed(){
		bActionsConfirmed=true;
		pagerAdapter.deleteFragment(pagerAdapter.getItem(3));
		pagerAdapter.notifyDataSetChanged();
		
		pager.setCurrentItem(2);
		pager.postDelayed(deletePlayerFragment, 200);
		bActionsConfirmed=false;
	}
	
	
	/**
     * Deletes the last page in the ViewPager because it's no longer useful.
     *   
     * @author Josep
     */
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
	 * This method increments the current minute of the game and checks for quarter transitions
	 * and the end of the match.
	 * @author Josep
	 */
	public void addMinute()
	{
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
	 * This method decrements the current minute of the game. Also checks for quarter trasitions
	 * and updates the foul count on the quarter.
	 * 
	 * @author Josep
	 */
	public void substractMinute()
	{
		int nextQuarter;
		disableEnd();
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
	
	
	/**
     * @return current minute of the match 
     * @author Josep
     */
	public int getCurrentMinute(){
		return currentMinute;
	}
	
	
	/**
     * @return current quarter of the match
     * @author Josep
     */
	public int getCurrentQuarter(){
		return currentQuarter;
	}
	
	
	/**
     * @return the maximum minute reached (if the user has gone back in the minutes)
     * @author Josep
     */
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
		        	   saveEventsInDatabase();
		        	   Intent intent = new Intent(ActivityBasketStats.this, MyActsActivity.class);
		        	   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        	   startActivity(intent);
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