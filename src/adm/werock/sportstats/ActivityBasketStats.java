package adm.werock.sportstats;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;


public class ActivityBasketStats extends FragmentActivity
{
	
	// Fragments
	private InsertBasketDataFragment insertDataFragment = (InsertBasketDataFragment) InsertBasketDataFragment.newInstance();
	private PreviewBasketStatsFragment previewStatsFragment = (PreviewBasketStatsFragment) PreviewBasketStatsFragment.newInstance();
	
	ViewPager pager = null;
	
    // The pager adapter, which provides the pages to the view pager widget.
    ActFragmentPagerAdapter pagerAdapter;
    private int previousPage=0;
    private boolean bActionsConfirmed=false;
    
    // Players
    private ArrayList<ActPlayer> homePlayers = new ArrayList<ActPlayer>();
    private ArrayList<ActPlayer> awayPlayers = new ArrayList<ActPlayer>();
    private ActPlayer selectedPlayer = null;
    private boolean bSelectedHomePlayer;
 
    // Time
    private int currentMinute = 0;
    private int currentQuarter = 1;
    
    // Others
    private int homeTeamScore = 0;
    private int awayTeamScore = 0;
    private int homeTeamFouls = 0;
    private int awayTeamFouls = 0;
    
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        this.setContentView(R.layout.layout_basket_stats);
 
        // Instantiate a ViewPager
        this.pager = (ViewPager) this.findViewById(R.id.actViewPager);
 
        // Create an adapter with the fragments we show on the ViewPager
        pagerAdapter = new ActFragmentPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment((Fragment)previewStatsFragment);
        pagerAdapter.addFragment((Fragment)insertDataFragment);
        
        this.pager.setAdapter(pagerAdapter);
        this.pager.setOnPageChangeListener(new PageListener());
        this.pager.setCurrentItem(1);
        
        setPlayers();
    }
    
    @Override
	public void onBackPressed() {
		if(pager.getCurrentItem() == 1){
			super.onBackPressed();
		}else{
			pager.setCurrentItem(1);
		}
	}
    
    // TODO: provisional
    public void setPlayers(){
    	homePlayers.add(new ActPlayer(4,"Sergiu", "Marsavela","001"));
    	homePlayers.add(new ActPlayer(5,"Fran", "Martin","002"));
    	homePlayers.add(new ActPlayer(6,"Patrick", "Mondria","003"));
    	homePlayers.add(new ActPlayer(7,"Diego", "Panadero","004"));
    	homePlayers.add(new ActPlayer(8,"Alvaro", "Peris","005"));
    	homePlayers.add(new ActPlayer(9,"Josep", "Tomas","006"));
    	
    	awayPlayers.add(new ActPlayer(4,"Earvin","Johnson", "007"));
    	awayPlayers.add(new ActPlayer(5,"Larry","Bird", "008"));
    	awayPlayers.add(new ActPlayer(6,"Charles","Barkley", "009"));
    	awayPlayers.add(new ActPlayer(7,"LeBron","James", "010"));
    	awayPlayers.add(new ActPlayer(5,"Michael","Jordan", "011"));
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
		}else{
			bSelectedHomePlayer = false;
			selectedPlayer = awayPlayers.get(number); 
		}
		pagerAdapter.addFragment(PlayerBasketDataFragment.newInstance());
		pagerAdapter.notifyDataSetChanged();
		pager.setCurrentItem(2);
	}
	
	public void playerActionsConfirmed(){
		bActionsConfirmed=true;
		pagerAdapter.deleteFragment(pagerAdapter.getItem(2));
		pagerAdapter.notifyDataSetChanged();
		
		pager.setCurrentItem(1);
		pager.postDelayed(deletePlayerFragment, 200);
		bActionsConfirmed=false;
	}
	
	public void playerActionsDiscarded(){
		pagerAdapter.deleteFragment(pagerAdapter.getItem(2));
		pagerAdapter.notifyDataSetChanged();
		
		pager.setCurrentItem(1);
		pager.postDelayed(deletePlayerFragment, 200);
	}
	
	final Runnable deletePlayerFragment = new Runnable()
	{
	    public void run() 
	    {
	    	pager.setAdapter(pagerAdapter);
	    	pager.setCurrentItem(1);
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
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int page) {
			switch(page){
				case 0:
					setTitle("Stats preview");
					break;
				case 1:
					setTitle("Match");
					break;
				case 2:
					setTitle("Player actions");
					break;
				default:
					break;
			}
			
			if(previousPage == 2 && bActionsConfirmed == false){
				playerActionsDiscarded();
			}
			previousPage = page;
		}
	}
	
	/**
	 * This method increments the current minute of the game and checks for quarter transitions.
	 * @return true if the game has ended, false otherwise
	 * @author Josep
	 */
	public boolean addMinute(){
		currentMinute ++;
		
		if(currentMinute == 10){
			currentMinute = 0;
			currentQuarter ++;
			// TODO: provisional
			homeTeamFouls = 0;
			awayTeamFouls = 0;
			insertDataFragment.updateFouls();
			
			if(currentQuarter > 4){
				currentQuarter = 4;
				currentMinute = 10;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method decrements the current minute of the game.
	 * @author Josep
	 */
	public void substractMinute(){
		currentMinute --;
		
		if(currentMinute == -1){
			currentMinute = 9;
			currentQuarter --;
			if(currentQuarter < 1){
				currentMinute = 0;
				currentQuarter = 1;
			}
		}
	}
	
	
	/**
	 * This method returns a string which indicates the current quarter and the
	 * current minute of the game.
	 * @return string with the form "Q" + currentQuarter + " - MIN. " + currentMinute.
	 */
	public String getTimeString(){
		if(currentQuarter == 4 && currentMinute == 10){
			return new String("END");
		}else{
			return new String("Q"+currentQuarter+" - MIN. "+currentMinute);
		}
	}
	
}