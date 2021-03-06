package adm.werock.sportstats;

import java.util.ArrayList;
import java.util.Date;

import adm.werock.sportstats.basics.Act;
import adm.werock.sportstats.basics.Event;
import adm.werock.sportstats.basics.Player;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import dao.ActDBHelper;


/*
 * Fragment from Start tab.
 * It checks all the previous info is correct and launches next activity.
 * It also creates a new act with the initial events.
 * 
 * */
public class StartTabFragment extends Fragment {
	int captainCounterHome, captainCounterVisitor;
	int starterCounterHome, starterCounterVisitor;
	int totalPlayersHome, totalPlayersVisitor;
	Button startButton;
	private SharedPreferences pref, prefTeams;

	private ActivityBasketAct bigParent;
	private ArrayList<Player> finalPlayersListA = new ArrayList<Player>();
	private ArrayList<Player> finalPlayersListB = new ArrayList<Player>();
	public ActDBHelper myDb;
	private Act myAct;
	
	
	private EditText mainRefText,scndRefText,dateText,townText,timeText,coachA,coachB = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(adm.werock.sportstats.R.layout.layout_start_tab, container, false);
		myDb = new ActDBHelper(rootView.getContext());
		pref = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
		prefTeams = this.getActivity().getSharedPreferences("teamPrefs", Context.MODE_MULTI_PROCESS);


		String local= prefTeams.getString("prefLocalTeam", "LOCAL");
		String visitor= prefTeams.getString("prefVisitorTeam", "VISITOR");
		((TextView)rootView.findViewById(R.id.localTeamFinal)).setText(local);
		((TextView)rootView.findViewById(R.id.visitorTeamFinal)).setText(visitor);
		Button startButton = (Button) rootView.findViewById(R.id.ButtonStartGame);
		
		
		
		
		mainRefText = (EditText) bigParent.viewGeneral.findViewById(R.id.MainRefereeText);
		scndRefText = (EditText) bigParent.viewGeneral.findViewById(R.id.SecondRefereeText);
		townText = (EditText) bigParent.viewGeneral.findViewById(R.id.TownText);
		dateText = (EditText) bigParent.viewGeneral.findViewById(R.id.DateText);
		timeText = (EditText) bigParent.viewGeneral.findViewById(R.id.TimeText);
		
		coachA = (EditText) bigParent.viewTeamA.findViewById(R.id.CoachText);
		coachB = (EditText) bigParent.viewTeamB.findViewById(R.id.CoachTextB);

		//set a onclick listener  when the button gets clicked
		startButton.setOnClickListener(new OnClickListener() {
			//Start new list activity
			@Override

			public void onClick(View v) {
				finalPlayersListA.clear();
				finalPlayersListB.clear();
				boolean errores = false;

				//LOCAL TEAM PLAYERS
				for(int i=0;i<bigParent.playerStateA.length;i++){
					if(bigParent.playerStateA[i] !=0)
						finalPlayersListA.add(bigParent.playersListA.get(i));
				}
				//AWAY TEAM PLAYERS
				for(int i=0;i<bigParent.playerStateB.length;i++){
					if(bigParent.playerStateB[i] !=0)
						finalPlayersListB.add(bigParent.playersListB.get(i));
				}

				myDb.insertPlayers(finalPlayersListA);
				myDb.insertPlayers(finalPlayersListB);

				//We get the number of captains and active players in order to check everything is ok
				captainCounterHome = pref.getInt("prefCaptainCounterHome", 0);
				captainCounterVisitor = pref.getInt("prefCaptainCounterVisitor", 0);
				starterCounterHome = pref.getInt("prefStarterCounterHome", 0);
				starterCounterVisitor = pref.getInt("prefStarterCounterVisitor", 0);
				totalPlayersHome = pref.getInt("prefTotalPlayersHome", 0);
				totalPlayersVisitor = pref.getInt("prefTotalPlayersVisitor", 0);

				//Comprueba que no haya jugadores activos sin dorsal.

				boolean nonumberA = false;
				if(bigParent.playerNumberA == null) nonumberA = true;
				else
					for (int i = 0; i< bigParent.playerStateA.length;i ++){
						if(bigParent.playerStateA[i]!=0 &&(bigParent.playerNumberA[i]==-1)){ 
							nonumberA =true;
							break;
						}
					}

				boolean nonumberB = false;
				if(bigParent.playerNumberB == null) nonumberB = true;
				else
					for (int i = 0; i< bigParent.playerStateB.length;i ++){
						if(bigParent.playerStateB[i]!=0 &&bigParent.playerNumberB[i]==-1){ 
							nonumberB =true;
							break;
						}

					}


				if(nonumberA){
					Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.errorPlayerNumberA), Toast.LENGTH_SHORT).show();
					errores = true;
				}
				if(nonumberB){
					Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.errorPlayerNumberB), Toast.LENGTH_SHORT).show();
					errores = true;
				}




				if(captainCounterHome != 1 || starterCounterHome<4){
					if(captainCounterHome!=1){
						Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.errorCaptainA), Toast.LENGTH_SHORT).show();
						errores = true;}
					if(starterCounterHome <4){
						Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.errorStartingPlayersA), Toast.LENGTH_SHORT).show();
						errores = true;}
				}
				if(captainCounterVisitor != 1 || starterCounterVisitor<4){
					if(captainCounterVisitor!=1){
						Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.errorCaptainB), Toast.LENGTH_SHORT).show();
						errores = true;}
					if(starterCounterVisitor <4){
						Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.errorStartingPlayersB), Toast.LENGTH_SHORT).show();
						errores = true;}
				}
				if(totalPlayersHome > 12){Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.errorTooManyPlayersA), Toast.LENGTH_SHORT).show();

				errores = true;}

				if(totalPlayersVisitor > 12){Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.errorTooManyPlayersB), Toast.LENGTH_SHORT).show();

				errores = true;}
				if(mainRefText== null || mainRefText.getText().toString().length()==0){Toast.makeText(getActivity().getApplicationContext(),getResources().getString(R.string.errorMainRef), Toast.LENGTH_SHORT).show();
				errores = true;}
				if(townText== null || townText.getText().toString().length()==0){Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.errorTown), Toast.LENGTH_SHORT).show();
				errores = true;}
				if(scndRefText== null || scndRefText.getText().toString().length()==0){Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.errorScndRef), Toast.LENGTH_SHORT).show();
				errores = true;}
				if(dateText== null || dateText.getText().toString().length()==0){Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.errorDate), Toast.LENGTH_SHORT).show();
				errores = true;}
				if(timeText== null || timeText.getText().toString().length()==0){Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.errorTime), Toast.LENGTH_SHORT).show();
				errores = true;}
				if(coachA== null || coachA.getText().toString().length()==0){Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.errorCoachA), Toast.LENGTH_SHORT).show();
				errores = true;}
				if(coachB== null || coachB.getText().toString().length()==0){Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.errorCoachB), Toast.LENGTH_SHORT).show();
				errores = true;}
				
				if(!errores){

					Event event;

					//Creaci�n de eventos:
					for (int i = 0; i< bigParent.playerNumberA.length;i ++){

						//Asignaci�n de dorsales
						event = new Event(0, 1, 0, "A", bigParent.playerNumberA[i]+"", bigParent.playersListA.get(i).getLicenseNumber()); 
						myDb.insertEvent(event);
						switch(bigParent.playerStateA[i]){

						case 0 : break;
						case 1 : event = new Event(0, 1, 0, "AN", bigParent.playerNumberA[i]+"", bigParent.playersListA.get(i).getLicenseNumber()); 
						//myDb.insertEvent(event);
						break;
						case 2 : event = new Event(0, 1, 0, "AS", bigParent.playerNumberA[i]+"", bigParent.playersListA.get(i).getLicenseNumber()); 
						myDb.insertEvent(event);
						break;
						case 3 : event = new Event(0, 1, 0, "AC", bigParent.playerNumberA[i]+"", bigParent.playersListA.get(i).getLicenseNumber()); 
						myDb.insertEvent(event);
						break;

						}


					}

					//Equipo visitante:
					for (int i = 0; i< bigParent.playerNumberB.length;i ++){
						event = new Event(0, 1, 0, "A", bigParent.playerNumberB[i]+"", bigParent.playersListB.get(i).getLicenseNumber()); 
						myDb.insertEvent(event);

						switch(bigParent.playerStateB[i]){

						case 0 : break;
						case 1 : event = new Event(0, 1, 0, "AN", bigParent.playerNumberB[i]+"", bigParent.playersListB.get(i).getLicenseNumber()); 
						//myDb.insertEvent(event);
						break;
						case 2 : event = new Event(0, 1, 0, "AS", bigParent.playerNumberB[i]+"", bigParent.playersListB.get(i).getLicenseNumber()); 
						myDb.insertEvent(event);
						break;
						case 3 : event = new Event(0, 1, 0, "AC", bigParent.playerNumberB[i]+"", bigParent.playersListB.get(i).getLicenseNumber()); 


						myDb.insertEvent(event);
						break;

						}
					}

					myAct = new Act(1, 
							new Date(), "pepito", bigParent.homeTeamId, bigParent.awayTeamId);
					
					myDb.insertAct(myAct);
					Intent i = new Intent(getActivity(), ActivityBasketStats.class);
					i.putExtra("actID", myAct.getId());
					startActivity(i);
				}


			}
		});
		return rootView;
	}
	public void onActivityCreated (Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Activity activity = getActivity();

		if(activity instanceof ActivityBasketAct){
			bigParent = ((ActivityBasketAct)activity);
		}

	}
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}



}
