package adm.werock.sportstats;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


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
		viewPager.setOffscreenPageLimit(4);
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
	Log.i("asddas",v.getId()+"" );	
}










/*

	public void playerPressed(View view, TextView playerArg, TextView licArg, 
			TextView numArg, ImageView captainArg){
		this.setContentView(R.layout.layout_team_a_tab);

		final TextView player;
		 final TextView  lic;
		final TextView num;
		 final ImageView captain;

		player = playerArg;
		lic = licArg;
		num = numArg;
		captain = captainArg;
		// custom dialog
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.layout_custom_dialog);
		dialog.setTitle("Player Options");


		// click on starting player in dialog
		TextView startingPlayer = (TextView) dialog.findViewById(R.id.StartingPlayer);
		startingPlayer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setStartingPlayer(v,player,lic,num);
				dialog.dismiss();
			}
		});

		// click on not called player in dialog
		TextView playerNotCalled = (TextView) dialog.findViewById(R.id.PlayerNotCalled);
		playerNotCalled.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Que la función setNotCalledPlayer modifique la interfaz
				setNotCalledPlayer(v,player,lic,num);
				dialog.dismiss();
			}
		});

		// click on select captain player in dialog
		TextView captainPlayer = (TextView) dialog.findViewById(R.id.CaptainPlayer);
		captainPlayer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO  Que la funciones siguientes modifiquen la interfaz
				//otherCaptainAlready(v);
				setCaptainPlayer(v,captain);
				dialog.dismiss();
			}
		});

		//Action for Cancel button
		Button cancelButton = (Button) dialog.findViewById(R.id.Cancel);	
		cancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();       
			}
		});

		dialog.show();
	}
	public void otherCaptainAlready(View v){
		ImageView captain1, captain2, captain3, captain4,
		captain5, captain6, captain7, captain8,
		captain9, captain10, captain11, captain12;
		captain1 = (ImageView) (v.findViewById(R.id.Captain1));
		captain2 = (ImageView) (v.findViewById(R.id.Captain2));
		captain3 = (ImageView) (v.findViewById(R.id.Captain3));
		captain4 = (ImageView) (v.findViewById(R.id.Captain4));
		captain5 = (ImageView) (v.findViewById(R.id.Captain5));
		captain6 = (ImageView) (v.findViewById(R.id.Captain6));
		captain7 = (ImageView) (v.findViewById(R.id.Captain7));
		captain8 = (ImageView) (v.findViewById(R.id.Captain8));
		captain9 = (ImageView) (v.findViewById(R.id.Captain9));
		captain10 = (ImageView) (v.findViewById(R.id.Captain10));
		captain11 = (ImageView) (v.findViewById(R.id.Captain11));
		captain12 = (ImageView) (v.findViewById(R.id.Captain12));
		captain1.setVisibility(View.INVISIBLE);
		captain2.setVisibility(View.INVISIBLE);
		captain3.setVisibility(View.INVISIBLE);
		captain4.setVisibility(View.INVISIBLE);
		captain5.setVisibility(View.INVISIBLE);
		captain6.setVisibility(View.INVISIBLE);
		captain7.setVisibility(View.INVISIBLE);
		captain8.setVisibility(View.INVISIBLE);
		captain9.setVisibility(View.INVISIBLE);
		captain10.setVisibility(View.INVISIBLE);
		captain11.setVisibility(View.INVISIBLE);
		captain12.setVisibility(View.INVISIBLE);

	}

	public void setStartingPlayer(View view, TextView playerArg, TextView licArg, TextView numArg){


		TextView playerName;
		TextView playerLic, playerNum;
		playerArg.setBackgroundColor(Color.YELLOW);
		licArg.setBackgroundColor(Color.YELLOW);
		numArg.setBackgroundColor(Color.YELLOW);
	}
	public void setNotCalledPlayer(View view, TextView playerArg, TextView licArg, TextView numArg){

		playerArg.setTextColor(Color.LTGRAY);
		licArg.setTextColor(Color.LTGRAY);
		numArg.setTextColor(Color.LTGRAY);
		playerArg.invalidate();	
	}
	public void setCaptainPlayer(View view, ImageView captainArg){
		ImageView captain = captainArg;
		if(captain.getVisibility() == View.INVISIBLE)
			captain.setVisibility(View.VISIBLE);
		else
			captain.setVisibility(View.INVISIBLE);
	}



 */




























































































































































////////////////////////PROCESSING PLAYER//////////////////////////////////////////////////////////////////

/*
	public void processingPlayer(){
		final TextView player1, player2, player3, player4, 
		player5, player6, player7, player8,
		player9, player10, player11, player12,
		player1b, player2b, player3b, player4b, 
		player5b, player6b, player7b, player8b,
		player9b, player10b, player11b, player12b;
		final TextView lic1, lic2, lic3, lic4, 
		lic5, lic6, lic7, lic8,
		lic9, lic10, lic11, lic12,
		lic1b, lic2b, lic3b, lic4b,
		lic5b, lic6b, lic7b, lic8b,
		lic9b, lic10b, lic11b, lic12b;
		final TextView num1, num2, num3, num4, 
		num5, num6, num7, num8,
		num9, num10, num11, num12,
		num1b, num2b, num3b, num4b,
		num5b, num6b, num7b, num8b,
		num9b, num10b, num11b, num12b;
		final ImageView captain1, captain2, captain3, captain4,
		captain5, captain6, captain7, captain8,
		captain9, captain10, captain11, captain12,
		captain1b, captain2b, captain3b, captain4b,
		captain5b, captain6b, captain7b, captain8b,
		captain9b, captain10b, captain11b, captain12b;





//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		////////////////////////Player1/////////////////////////////////
//		player1 =  (TextView) findViewById(R.id.Player1);
//		lic1 = (TextView) findViewById(R.id.Lic1);
//		num1 = (TextView)findViewById(R.id.PlayerNum1);
//		captain1 = (ImageView)findViewById(R.id.Captain1);
//	/*	player1.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				playerPressed(v,player1,lic1,num1,captain1);
//			}
//		});*/
//		////////////////////////Player1b/////////////////////////////////
//		player1b =  (TextView) findViewById(R.id.Player1b);
//		lic1b = (TextView) findViewById(R.id.Lic1b);
//		num1b = (TextView)findViewById(R.id.PlayerNum1b);
//		captain1b = (ImageView)findViewById(R.id.Captain1b);
//		/*player1b.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				playerPressed(v,player1b,lic1b,num1b,captain1b);
//			}
//		});
//*/
//		////////////////////////Player2/////////////////////////////////
//		player2 =  (TextView) findViewById(R.id.Player2);
//		lic2 = (TextView) findViewById(R.id.Lic2);
//		num2 = (TextView)findViewById(R.id.PlayerNum2);
//		captain2 = (ImageView)findViewById(R.id.Captain2);
//	/*	player2.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				playerPressed(v,player2,lic2,num2,captain2);
//			}
//		});*/
//		////////////////////////Player2b/////////////////////////////////
//		player2b =  (TextView) findViewById(R.id.Player2b);
//		lic2b = (TextView) findViewById(R.id.Lic2b);
//		num2b = (TextView)findViewById(R.id.PlayerNum2b);
//		captain2b = (ImageView)findViewById(R.id.Captain2b);
//	/*	player2b.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				playerPressed(v,player2b,lic2b,num2b,captain2b);
//			}
//		});*/
//		////////////////////////Player3////////////////////////////////
//		player3 =  (TextView) findViewById(R.id.Player3);
//		lic3 = (TextView) findViewById(R.id.Lic3);
//		num3 = (TextView)findViewById(R.id.PlayerNum3);
//		captain3 = (ImageView)findViewById(R.id.Captain3);
//	/*	player3.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				playerPressed(v,player3,lic3,num3,captain3);
//			}
//		});*/
//		////////////////////////Player3b////////////////////////////////
//		player3b =  (TextView) findViewById(R.id.Player3b);
//		lic3b = (TextView) findViewById(R.id.Lic3b);
//		num3b = (TextView)findViewById(R.id.PlayerNum3b);
//		captain3b = (ImageView)findViewById(R.id.Captain3b);
//	/*	player3b.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				playerPressed(v,player3b,lic3b,num3b,captain3b);
//			}
//		});*/
//		////////////////////////Player4/////////////////////////////////
//		player4 =  (TextView) findViewById(R.id.Player4);
//		lic4 = (TextView) findViewById(R.id.Lic4);
//		num4 = (TextView)findViewById(R.id.PlayerNum4);
//		captain4 = (ImageView)findViewById(R.id.Captain4);
///*		player4.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				playerPressed(v,player4,lic4,num4,captain4);
//			}
//		});*/
//		////////////////////////Player4b/////////////////////////////////
//		player4b =  (TextView) findViewById(R.id.Player4b);
//		lic4b = (TextView) findViewById(R.id.Lic4b);
//		num4b = (TextView)findViewById(R.id.PlayerNum4b);
//		captain4b = (ImageView)findViewById(R.id.Captain4b);
///*		player4b.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				playerPressed(v,player4b,lic4b,num4b,captain4b);
//			}
//		});*/
//		////////////////////////Player5/////////////////////////////////
//		player5 =  (TextView) findViewById(R.id.Player5);
//		lic5 = (TextView) findViewById(R.id.Lic5);
//		num5 = (TextView)findViewById(R.id.PlayerNum5);
//		captain5 = (ImageView)findViewById(R.id.Captain5);
///*		player5.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				playerPressed(v,player5,lic5,num5,captain5);
//			}
//		});*/
//		////////////////////////Player5b/////////////////////////////////
//		player5b =  (TextView) findViewById(R.id.Player5b);
//		lic5b = (TextView) findViewById(R.id.Lic5b);
//		num5b = (TextView)findViewById(R.id.PlayerNum5b);
//		captain5b = (ImageView)findViewById(R.id.Captain5b);
///*		player5b.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				playerPressed(v,player5b,lic5b,num5b,captain5b);
//			}
//		});*/
//		////////////////////////Player6/////////////////////////////////
//		player6 =  (TextView) findViewById(R.id.Player6);
//		lic6 = (TextView) findViewById(R.id.Lic6);
//		num6 = (TextView)findViewById(R.id.PlayerNum6);
//		captain6 = (ImageView)findViewById(R.id.Captain6);
///*		player6.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				playerPressed(v,player6,lic6,num6,captain6);
//			}
//		});*/
//		////////////////////////Player6b/////////////////////////////////
//		player6b =  (TextView) findViewById(R.id.Player6b);
//		lic6b = (TextView) findViewById(R.id.Lic6b);
//		num6b = (TextView)findViewById(R.id.PlayerNum6b);
//		captain6b = (ImageView)findViewById(R.id.Captain6b);
///*		player6b.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				playerPressed(v,player6b,lic6b,num6b,captain6b);
//			}
//		});*/
//		////////////////////////Player7/////////////////////////////////
//		player7 =  (TextView) findViewById(R.id.Player7);
//		lic7 = (TextView) findViewById(R.id.Lic7);
//		num7 = (TextView)findViewById(R.id.PlayerNum7);
//		captain7 = (ImageView)findViewById(R.id.Captain7);
///*		player7.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				playerPressed(v,player7,lic7,num7,captain7);
//			}
//		});*/
//		////////////////////////Player7b/////////////////////////////////
//		player7b =  (TextView) findViewById(R.id.Player7b);
//		lic7b = (TextView) findViewById(R.id.Lic7b);
//		num7b = (TextView)findViewById(R.id.PlayerNum7b);
//		captain7b = (ImageView)findViewById(R.id.Captain7b);
///*		player7b.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				playerPressed(v,player7b,lic7b,num7b,captain7b);
//			}
//		});*/
//		////////////////////////Player8/////////////////////////////////
//		player8 =  (TextView) findViewById(R.id.Player8);
//		lic8 = (TextView) findViewById(R.id.Lic8);
//		num8 = (TextView)findViewById(R.id.PlayerNum8);
//		captain8 = (ImageView)findViewById(R.id.Captain8);
///*		player8.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				playerPressed(v,player8,lic8,num8,captain8);
//			}
//		});*/
//		////////////////////////Player8b/////////////////////////////////
//		player8b =  (TextView) findViewById(R.id.Player8b);
//		lic8b = (TextView) findViewById(R.id.Lic8b);
//		num8b = (TextView)findViewById(R.id.PlayerNum8b);
//		captain8b = (ImageView)findViewById(R.id.Captain8b);
///*		player8b.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				playerPressed(v,player8b,lic8b,num8b,captain8b);
//			}
//		});*/
//		////////////////////////Player9/////////////////////////////////
//		player9 =  (TextView) findViewById(R.id.Player9);
//		lic9 = (TextView) findViewById(R.id.Lic9);
//		num9 = (TextView)findViewById(R.id.PlayerNum9);
//		captain9 = (ImageView)findViewById(R.id.Captain9);
///*		player9.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				playerPressed(v,player9,lic9,num9,captain9);
//			}
//		});*/
//		////////////////////////Player9b/////////////////////////////////
//		player9b =  (TextView) findViewById(R.id.Player9b);
//		lic9b = (TextView) findViewById(R.id.Lic9b);
//		num9b = (TextView)findViewById(R.id.PlayerNum9b);
//		captain9b = (ImageView)findViewById(R.id.Captain9b);
///*		player9b.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				playerPressed(v,player9b,lic9b,num9b,captain9b);
//			}
//		});*/
//		////////////////////////Player10/////////////////////////////////
//		player10 =  (TextView) findViewById(R.id.Player10);
//		lic10 = (TextView) findViewById(R.id.Lic10);
//		num10 = (TextView)findViewById(R.id.PlayerNum10);
//		captain10 = (ImageView)findViewById(R.id.Captain10);
///*		player10.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				playerPressed(v,player10,lic10,num10,captain10);
//			}
//		});*/
//		////////////////////////Player10b/////////////////////////////////
//		player10b =  (TextView) findViewById(R.id.Player10b);
//		lic10b = (TextView) findViewById(R.id.Lic10b);
//		num10b = (TextView)findViewById(R.id.PlayerNum10b);
//		captain10b = (ImageView)findViewById(R.id.Captain10b);
///*		player10b.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				playerPressed(v,player10b,lic10b,num10b,captain10b);
//			}
//		});*/
//		////////////////////////Player11/////////////////////////////////
//		player11 =  (TextView) findViewById(R.id.Player11);
//		lic11 = (TextView) findViewById(R.id.Lic11);
//		num11 = (TextView)findViewById(R.id.PlayerNum11);
//		captain11 = (ImageView)findViewById(R.id.Captain11);
///*		player11.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				playerPressed(v,player11,lic11,num11,captain11);
//			}
//		});*/
//		////////////////////////Player11b/////////////////////////////////
//		player11b =  (TextView) findViewById(R.id.Player11b);
//		lic11b = (TextView) findViewById(R.id.Lic11b);
//		num11b = (TextView)findViewById(R.id.PlayerNum11b);
//		captain11b = (ImageView)findViewById(R.id.Captain11b);
///*		player11b.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				playerPressed(v,player11b,lic11b,num11b,captain11b);
//			}
//		});*/
//		////////////////////////Player12/////////////////////////////////
//		player12 =  (TextView) findViewById(R.id.Player12);
//		lic12 = (TextView) findViewById(R.id.Lic12);
//		num12 = (TextView)findViewById(R.id.PlayerNum12);
//		captain12 = (ImageView)findViewById(R.id.Captain12);
///*		player12.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				playerPressed(v,player12,lic12,num12,captain12);
//			}
//		});*/
//		////////////////////////Player12b/////////////////////////////////
//		player12b =  (TextView) findViewById(R.id.Player12b);
//		lic12b = (TextView) findViewById(R.id.Lic12b);
//		num12b = (TextView)findViewById(R.id.PlayerNum12b);
//		captain12b = (ImageView)findViewById(R.id.Captain12b);
///*		player12b.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				playerPressed(v,player12b,lic12b,num12b,captain12b);
//			}
//		});*/
//	}

/*
	public void playerPressed(View view, TextView playerArg, TextView licArg, 
			TextView numArg, ImageView captainArg){
		final TextView player, lic, num;
		final ImageView captain;
		player = playerArg;
		lic = licArg;
		num = numArg;
		captain = captainArg;
		// custom dialog
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.layout_custom_dialog);
		dialog.setTitle("Player Options");

		// click on starting player in dialog
		TextView startingPlayer = (TextView) dialog.findViewById(R.id.StartingPlayer);
		startingPlayer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setStartingPlayer(v,player,lic,num);
				dialog.dismiss();
			}
		});

		// click on not called player in dialog
		TextView playerNotCalled = (TextView) dialog.findViewById(R.id.PlayerNotCalled);
		playerNotCalled.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setNotCalledPlayer(v,player,lic,num);
				dialog.dismiss();
			}
		});

		// click on select captain player in dialog
		TextView captainPlayer = (TextView) dialog.findViewById(R.id.CaptainPlayer);
		captainPlayer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				otherCaptainAlready();
				setCaptainPlayer(v,captain);
				dialog.dismiss();
			}
		});

		//Action for Cancel button
		Button cancelButton = (Button) dialog.findViewById(R.id.Cancel);	
		cancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();       
			}
		});

		dialog.show();
	}
	public void otherCaptainAlready(){
		ImageView captain1, captain2, captain3, captain4,
		captain5, captain6, captain7, captain8,
		captain9, captain10, captain11, captain12;
		captain1 = (ImageView) (findViewById(R.id.Captain1));
		captain2 = (ImageView) (findViewById(R.id.Captain2));
		captain3 = (ImageView) (findViewById(R.id.Captain3));
		captain4 = (ImageView) (findViewById(R.id.Captain4));
		captain5 = (ImageView) (findViewById(R.id.Captain5));
		captain6 = (ImageView) (findViewById(R.id.Captain6));
		captain7 = (ImageView) (findViewById(R.id.Captain7));
		captain8 = (ImageView) (findViewById(R.id.Captain8));
		captain9 = (ImageView) (findViewById(R.id.Captain9));
		captain10 = (ImageView) (findViewById(R.id.Captain10));
		captain11 = (ImageView) (findViewById(R.id.Captain11));
		captain12 = (ImageView) (findViewById(R.id.Captain12));
		captain1.setVisibility(View.INVISIBLE);
		captain2.setVisibility(View.INVISIBLE);
		captain3.setVisibility(View.INVISIBLE);
		captain4.setVisibility(View.INVISIBLE);
		captain5.setVisibility(View.INVISIBLE);
		captain6.setVisibility(View.INVISIBLE);
		captain7.setVisibility(View.INVISIBLE);
		captain8.setVisibility(View.INVISIBLE);
		captain9.setVisibility(View.INVISIBLE);
		captain10.setVisibility(View.INVISIBLE);
		captain11.setVisibility(View.INVISIBLE);
		captain12.setVisibility(View.INVISIBLE);

	}

	public void setStartingPlayer(View view, TextView playerArg, TextView licArg, TextView numArg){
		TextView playerLic, playerNum, playerName;
		playerName = playerArg;
		playerLic = licArg;
		playerNum = numArg;
		playerName.setBackgroundColor(Color.YELLOW);
		playerLic.setBackgroundColor(Color.YELLOW);
		playerNum.setBackgroundColor(Color.YELLOW);
	}
	public void setNotCalledPlayer(View view, TextView playerArg, TextView licArg, TextView numArg){
		TextView playerLic, playerNum, playerName;
		playerName = playerArg;
		playerLic = licArg;
		playerNum = numArg;
		playerName.setTextColor(Color.GRAY);
		playerLic.setTextColor(Color.GRAY);
		playerNum.setTextColor(Color.GRAY);
		playerName.invalidate();	
	}
	public void setCaptainPlayer(View view, ImageView captainArg){
		ImageView captain = captainArg;
		if(captain.getVisibility() == View.INVISIBLE)
			captain.setVisibility(View.VISIBLE);
		else
			captain.setVisibility(View.INVISIBLE);
	}
 */

////////////////////////////////////////////////////////////////////////////////////////////






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



}













