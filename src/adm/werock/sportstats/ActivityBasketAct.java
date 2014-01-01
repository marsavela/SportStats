package adm.werock.sportstats;



import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;


@SuppressLint("ValidFragment")
public class ActivityBasketAct extends FragmentActivity implements ActionBar.TabListener,OnClickListener {

	
	
	
	
	
	
	private int hour, minute, syear, month, day;	
	TimePicker timePicker;
	TextView timeText;
	DatePicker datePicker;

	EditText dateText;

	static final int TIME_DIALOG_ID = 999;
	static final int DATE_DIALOG_ID = 888;

	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basket_act);

		

		
		
		// Tab titles
		String[] tabs = { this.getString(R.string.general), this.getString(R.string.teamA), this.getString(R.string.teamB), this.getString(R.string.start) };

		// Initilization
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		
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
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());

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
	}
};
////////////////////////END SET DATE/////////////////////////////////

@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	
}

@Override
public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
	// TODO Auto-generated method stub
	
}

@Override
public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
	// TODO Auto-generated method stub
	
}


}













