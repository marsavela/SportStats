package adm.werock.sportstats;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.ScrollView;

public class FragmentBasketEventList extends Fragment{
	
	private ActivityBasketStats parent;
	private View view;
	private ViewGroup container;
	
	public static android.support.v4.app.Fragment newInstance() {
		 
        // Instantiate a new fragment
		FragmentBasketEventList fragment = new FragmentBasketEventList();
 
        // Save the parameters
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        fragment.setRetainInstance(true);
 
        return fragment;
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_event_list, container, false);
		
		view = v;
		this.container = container;
		Log.i("BasketStatsFragment","VIEW CREATED");
		
		return v;
	}
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);

	    Activity activity = getActivity();
    	
    	if(activity instanceof ActivityBasketStats){
    		parent = ((ActivityBasketStats)activity);
    	}
    	
    	LinearLayout eventsContainer = (LinearLayout)view.findViewById(R.id.eventsContainer);
    	ArrayList<ActEvent> events = parent.getEvents();
    	
    	int currentQuarter = 0;
    	int currentMinute = -1;
    	int maximumMinute = parent.getMaximumMinute();
    	
    	for(int i=0; i<=maximumMinute; i++){
    		int eventQuarter = (i/10)+1;
    		
    		if(eventQuarter > currentQuarter){
    			currentQuarter = eventQuarter;
    			
    			// Add quarter row
    			View eventQuarterItem = getLayoutInflater(savedInstanceState).inflate(R.layout.layout_event_quarter_item, null);
    			Button quarterButton = (Button) eventQuarterItem.findViewById(R.id.buttonEventQuarter);
    			
    			switch(currentQuarter){
    				case 1:
    					quarterButton.setText(getResources().getString(R.string.headerFirstQuarter));
    					break;
    				case 2:
    					quarterButton.setText(getResources().getString(R.string.headerSecondQuarter));
    					break;
    				case 3:
    					quarterButton.setText(getResources().getString(R.string.headerThirdQuarter));
    					break;
    				case 4:
    					quarterButton.setText(getResources().getString(R.string.headerFourthQuarter));
    					break;
    				default:
    					quarterButton.setText(getResources().getString(R.string.headerOvertime)+" "+String.valueOf(currentQuarter-4));
    					break;
    			}
    			
    			eventsContainer.addView(eventQuarterItem);
    		}
    		
    		for(int j=0; j<events.size(); j++){
    			if(events.get(j).getMinute() == i){
    				View eventItem;
    				ActEvent event = events.get(j);
    				ActPlayer player = parent.getPlayer(event.isHomePlayer(), event.getPlayerPosition());
    	    		
    	    		if(event.isHomePlayer()){
    	    			eventItem = getLayoutInflater(savedInstanceState).inflate(R.layout.layout_event_home_item, null);
    	    		}else{
    	    			eventItem = getLayoutInflater(savedInstanceState).inflate(R.layout.layout_event_away_item, null);
    	    		}
    	    		
    	    		Button eventButton = (Button) eventItem.findViewById(R.id.buttonEvent);
    	    		eventButton.setOnTouchListener(new HandlerEventButton(event, eventButton));
    	    		eventButton.setText("("+String.valueOf(player.getNumber())+") "+player.toString()+" "+event.toString());
    	    		
    	    		eventsContainer.addView(eventItem);
    			}
    		}
    	}
    	
    	
    	
	}
	
	private class HandlerEventButton implements OnTouchListener
	{
		ActEvent actEvent;
		Button buttonEvent;
		
		public HandlerEventButton(ActEvent event, Button buttonEvent){
			this.actEvent = event;
			this.buttonEvent = buttonEvent;
		}
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			
			switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					buttonEvent.setBackgroundResource(R.drawable.square_red_64);
					buttonEvent.setTextColor(Color.parseColor("#FFFFFF"));
					break;
				case MotionEvent.ACTION_UP:
					if(actEvent.isHomePlayer()){
						buttonEvent.setBackgroundResource(R.drawable.square_almost_white_32);
						buttonEvent.setTextColor(Color.parseColor("#000000"));
					}else{
						buttonEvent.setBackgroundResource(R.drawable.square_light_gray_32);
						buttonEvent.setTextColor(Color.parseColor("#000000"));
					}
					
					showPopupMenu(v,actEvent);
					
					break;
				default:
					if(actEvent.isHomePlayer()){
						buttonEvent.setBackgroundResource(R.drawable.square_almost_white_32);
						buttonEvent.setTextColor(Color.parseColor("#000000"));
					}else{
						buttonEvent.setBackgroundResource(R.drawable.square_light_gray_32);
						buttonEvent.setTextColor(Color.parseColor("#000000"));
					}
					break;
			}
			return true;
		}
	}
	
	// Event Popup Menu
	
	private void showPopupMenu(View v, ActEvent e){
		
		PopupMenu popupMenu = new PopupMenu(parent, v);
		popupMenu.getMenuInflater().inflate(R.menu.event_item_popup_menu, popupMenu.getMenu());
		
		popupMenu.setOnMenuItemClickListener(new HandlerEventPopup(e));
		
		popupMenu.show();
	}
	
	private class HandlerEventPopup implements OnMenuItemClickListener{

		ActEvent event;
		
		public HandlerEventPopup(ActEvent e){
			this.event = e;
		}
		
		@Override
		public boolean onMenuItemClick(MenuItem item) {
			boolean ret = parent.searchAndRemoveEvent(event);
			parent.rebuildFromEvents();
			parent.rebuild();
			return ret;
		}
	}
		   

}
