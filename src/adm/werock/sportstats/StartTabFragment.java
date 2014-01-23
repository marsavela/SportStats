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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import dao.ActDBHelper;

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

		//set a onclick listener  when the button gets clicked
		startButton.setOnClickListener(new OnClickListener() {
			//Start new list activity
			@Override

			public void onClick(View v) {
				finalPlayersListA.clear();
				finalPlayersListB.clear();


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


				if(nonumberA)
					Toast.makeText(getActivity().getApplicationContext(), "TeamA has players without number.", Toast.LENGTH_SHORT).show();

				if(nonumberB)
					Toast.makeText(getActivity().getApplicationContext(), "TeamB has players without number.", Toast.LENGTH_SHORT).show();

			
				
				
				
				
				if(!nonumberA&&!nonumberB){
				if(captainCounterHome != 1 || starterCounterHome<4){
					if(captainCounterHome!=1)
						Toast.makeText(getActivity().getApplicationContext(), "TeamA needs a captain", Toast.LENGTH_SHORT).show();
					if(starterCounterHome <4)
						Toast.makeText(getActivity().getApplicationContext(), "TeamA needs 4 starter players", Toast.LENGTH_SHORT).show();
				}
				else if(captainCounterVisitor != 1 || starterCounterVisitor<4){
					if(captainCounterVisitor!=1)
						Toast.makeText(getActivity().getApplicationContext(), "TeamB needs a captain", Toast.LENGTH_SHORT).show();
					if(starterCounterVisitor <4)
						Toast.makeText(getActivity().getApplicationContext(), "TeamB needs 4 starter players", Toast.LENGTH_SHORT).show();
				}
				else if(totalPlayersHome > 12)Toast.makeText(getActivity().getApplicationContext(), "TeamA have more than 12 players", Toast.LENGTH_SHORT).show();
				else if(totalPlayersVisitor > 12)Toast.makeText(getActivity().getApplicationContext(), "TeamB have more than 12 players", Toast.LENGTH_SHORT).show();
				else {
					
					//myAct = new Act(1, bigParent.date, "pepito", bigParent.homeTeamId, bigParent.awayTeamId);

					Event event;
					
					//Creación de eventos:
					for (int i = 0; i< bigParent.playerNumberA.length;i ++){
						
						//Asignación de dorsales
						event = new Event(0, 1, 0, "A", bigParent.playerNumberA[i]+"", bigParent.playersListA.get(i).getLicenseNumber()); 
						myDb.insertEvent(event);
						switch(bigParent.playerStateA[i]){
					
						case 0 : break;
						case 1 : event = new Event(0, 1, 0, "AN", bigParent.playerNumberA[i]+"", bigParent.playersListA.get(i).getLicenseNumber()); 
								myDb.insertEvent(event);
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
								myDb.insertEvent(event);
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
					Log.i("date","DATE: "+(new Date()).toString());
					Log.i("pedooo",  myAct.getId()+"");
					myDb.insertAct(myAct);
					Intent i = new Intent(getActivity(), ActivityBasketStats.class);
					i.putExtra("actID", myAct.getId());
					startActivity(i);
				}

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
