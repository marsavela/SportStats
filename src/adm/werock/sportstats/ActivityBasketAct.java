package adm.werock.sportstats;


import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import adm.werock.sportstats.basics.Player;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.ParseException;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;


@SuppressLint("ValidFragment")
public class ActivityBasketAct extends FragmentActivity implements ActionBar.TabListener,OnClickListener {

	private int hour, minute, syear, month, day;	
	TimePicker timePicker;
	DatePicker datePicker;
	Date date;
	
	static final int TIME_DIALOG_ID = 999;
	static final int DATE_DIALOG_ID = 888;
	
	
	static View viewGeneral;
	static View viewTeamA;
	static View viewTeamB;
	public EditText dateText;
	public TextView timeText;
	
	public int homeTeamId, awayTeamId;
	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	
	ArrayList<Player> playersListA = new ArrayList<Player>();
	ArrayList<Player> playersListB = new ArrayList<Player>();
	public int[] playerNumberA;
	public int[] playerNumberB;
	public int[] playerStateA;
	public int[] playerStateB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basket_act);
		SharedPreferences pref = getSharedPreferences("myPref", Context.MODE_MULTI_PROCESS);
		Editor editor = pref.edit();
		editor.clear();
		editor.commit();
		//cleaning the preferences each time we create a new act
		pref = getSharedPreferences("teamPrefs", Context.MODE_MULTI_PROCESS);
		editor = pref.edit();	
		String local= pref.getString("prefLocalTeam", "LOCAL");
		String visitor= pref.getString("prefVisitorTeam", "VISITOR");
		//editor.remove("myPref");
		editor.commit();
		
		
		
		// Tab titles
		String[] tabs = { this.getString(R.string.general),local, visitor, this.getString(R.string.start) };

		// Initilization
		viewPager = (ViewPager) findViewById(R.id.pager);

		//Check the wifi and 3g connection//////////////
		ConnectivityManager manager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
		CheckConnection check = new CheckConnection();
		//For 3G check
		boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.isConnectedOrConnecting();
		//For WiFi Check
		boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.isConnectedOrConnecting();
		check.setWifi(isWifi);
		check.setThreeG(is3g);
		///////////////////////////////////////////////

		viewPager.setOffscreenPageLimit(4);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		mAdapter.myCheck(check);
		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);		

		/*Adding Tabs to Action Bar
6. In order to display tabs we don’t have to use any other UI element like TabHost. 
Action bar has the inbuilt capability of adding tabs. 
All we have to do is enable it using setNavigationMode(ActionBar.NAVIGATION_MODE_TABS) method.
Here I am adding three tabs Top Rated, Games, Movies to action bar. 
So I just stored all the tab names in a String array and added them to action bar using a for loop.*/
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}


		/*
		 View Change Listener
	14. As well if you swipe the view, you can’t see respected tab selected. 
	Here also using ViewPager setOnPageChangeListener() we have to select the respected tab manually.
		 */
		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}






	/*Tab Change Listener
If you run the project you can see the swiping views working, but if you select a tab, view won’t change automatically. 
This is because ViewPager didn’t know about the tab change event. 
We have to manually change the view using Tab change listener.*/
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case TIME_DIALOG_ID:
			// set time picker as current time
			return new TimePickerDialog(this, 
					timePickerListener, hour, minute,false);
		case DATE_DIALOG_ID:
			// set time picker as current time
			return new DatePickerDialog(this, datePickerListener, 
					syear, month,day);

		}
		return null;
	}



	//////////////////////SET TIME////////////////////////////////////
	//Set the current hour and calls the time picker dialog//////////

	public void setTime(View view){


		timePicker = (TimePicker) findViewById(R.id.timePicker1);
		timeText = (TextView) findViewById(R.id.TimeText);
		final Calendar c = Calendar.getInstance();
		hour = c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);

		// set current time into textview
		timeText.setText(
				new StringBuilder().append(pad(hour))
				.append(":").append(pad(minute)));

		// set current time into timepicker
		timePicker.setCurrentHour(hour);
		timePicker.setCurrentMinute(minute);
		//Shows time picker with the actual time
		showDialog(TIME_DIALOG_ID);
	}

	//Shows 
	private TimePickerDialog.OnTimeSetListener timePickerListener = 
			new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {
			hour = selectedHour;
			minute = selectedMinute;

			// set current time into textview
			timeText.setText(new StringBuilder().append(pad(hour))
					.append(":").append(pad(minute)));

			// set current time into timepicker
			timePicker.setCurrentHour(hour);
			timePicker.setCurrentMinute(minute);

		}
	};

	//Puts a 0 at the right of numbers from 1 to 9
	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}
	////////////////////////END SET TIME/////////////////////////////////


	/////////////////////////SET DATE////////////////////////////////////
	public void setDate(View view){	
		dateText = (EditText) findViewById(R.id.DateText);
		datePicker = (DatePicker) findViewById(R.id.datePicker1);

		final Calendar c = Calendar.getInstance();
		syear = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);	

		// set current date into textview
		dateText.setText(new StringBuilder()
		// Month is 0 based, just add 1
		.append(day).append("-").append(month + 1).append("-")
		.append(syear).append(" "));

		// set current date into datepicker
		datePicker.init(syear, month, day, null);

		//Shows date picker with the actual date
		showDialog(DATE_DIALOG_ID);
		
	}

	private DatePickerDialog.OnDateSetListener datePickerListener 
	= new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			syear = selectedYear;
			month = selectedMonth;
			day = selectedDay;

			// set selected date into textview
			dateText.setText(new StringBuilder().append(month + 1)
					.append("-").append(day).append("-").append(syear)
					.append(" "));

			// set selected date into datepicker also
			datePicker.init(syear, month, day, null);	
			try {
				date = putDateTime(dateText.getText().toString());
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	////////////////////////END SET DATE/////////////////////////////////
	public  ArrayList<Player> getPlayersA(){
		return playersListA;
	}
	public  ArrayList<Player> getPlayersB(){
		return playersListB;
	}
	public  void setPlayersA(ArrayList<Player> players){
		playersListA = players;
	}
	public  void setPlayersB(ArrayList<Player> players){
		playersListB = players;
	}
	public  void setPlayerStates(int  size){
		playerStateA = new int[size];
	}
	public  void inicializePlayerStates(){
		for(int i=0;i<playerStateA.length;i++)
			playerStateA[i] = 0;
	}
	public  void setPlayerStatesB(int  size){
		playerStateB = new int[size];
	}
	public  void inicializePlayerStatesB(){
		for(int i=0;i<playerStateB.length;i++)
			playerStateB[i] = 0;
	}
	
	public  void setPlayerNumbersA(int  size){
		playerNumberA = new int[size];
	}
	public  void inicializePlayerNumbersA(){
		for(int i=0;i<playerNumberA.length;i++)
			playerNumberA[i] = -1;
	}


	public  void setPlayerNumbersB(int  size){
		playerNumberB = new int[size];
	}
	public  void inicializePlayerNumbersB(){
		for(int i=0;i<playerNumberB.length;i++)
			playerNumberB[i] = -1;
	}

	
	private Date putDateTime(final String dateString) throws java.text.ParseException{
		Date date = null;
		SimpleDateFormat  dateFormat = new SimpleDateFormat(
		"yyyy-MM-dd HH:mm:ss", Locale.getDefault());  
		try {  
		date = (Date) dateFormat.parse(dateString);
		} catch (ParseException e) {  
		// TODO Auto-generated catch block  
		e.printStackTrace();  
		}

		return date;
		}
}













