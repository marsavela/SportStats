package adm.werock.sportstats;



import adm.werock.sportstats.R;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TeamBFragment extends Fragment implements OnClickListener{



	public void playerPressed(View view, EditText playerArg, TextView licArg, 
			TextView numArg, ImageView captainArg){
		getActivity().setContentView(R.layout.layout_team_a_tab);
		final EditText player;
		final TextView  lic, num;
		final ImageView captain;
		player = playerArg;
		lic = licArg;
		num = numArg;
		captain = captainArg;
		// custom dialog
		final Dialog dialog = new Dialog(this.getActivity());
		dialog.setContentView(R.layout.layout_custom_dialog);
		dialog.setTitle("Player Options");

		// click on starting player in dialog
		TextView startingPlayer = (TextView) dialog.findViewById(R.id.StartingPlayer);
		startingPlayer.setOnClickListener(new View.OnClickListener() {
		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
			//	setStartingPlayer(v,player,lic,num);
				dialog.dismiss();
			}
		});

		// click on not called player in dialog
		TextView playerNotCalled = (TextView) dialog.findViewById(R.id.PlayerNotCalled);
		playerNotCalled.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Que la función setNotCalledPlayer modifique la interfaz
			//	setNotCalledPlayer(v,player,lic,num);
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
				//setCaptainPlayer(v,captain);
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

	public void setStartingPlayer(View view, EditText playerArg, TextView licArg, TextView numArg){
		EditText playerName;
		TextView playerLic, playerNum;
		playerName = playerArg;
		playerLic = licArg;
		playerNum = numArg;
		playerName.setBackgroundColor(Color.YELLOW);
		playerLic.setBackgroundColor(Color.YELLOW);
		playerNum.setBackgroundColor(Color.YELLOW);
	}
	public void setNotCalledPlayer(View view, EditText playerArg, TextView licArg, TextView numArg){
		EditText playerName;
		TextView playerLic, playerNum;
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


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(adm.werock.sportstats.R.layout.layout_team_b_tab, container, false);

		
		EditText player1 = (EditText) v.findViewById(R.id.Player1b);
		player1.setOnClickListener(this);
		EditText player2 = (EditText) v.findViewById(R.id.Player2b);
		player2.setOnClickListener(this);
		EditText player3 = (EditText) v.findViewById(R.id.Player3b);
		player3.setOnClickListener(this);
		EditText player4 = (EditText) v.findViewById(R.id.Player4b);
		player4.setOnClickListener(this);
		EditText player5 = (EditText) v.findViewById(R.id.Player5b);
		player5.setOnClickListener(this);
		EditText player6 = (EditText) v.findViewById(R.id.Player6b);
		player6.setOnClickListener(this);
		EditText player7 = (EditText) v.findViewById(R.id.Player7b);
		player7.setOnClickListener(this);
		EditText player8 = (EditText) v.findViewById(R.id.Player8b);
		player8.setOnClickListener(this);
		EditText player9 = (EditText) v.findViewById(R.id.Player9b);
		player9.setOnClickListener(this);
		EditText player10 = (EditText) v.findViewById(R.id.Player10b);
		player10.setOnClickListener(this);
		EditText player11 = (EditText) v.findViewById(R.id.Player11b);
		player11.setOnClickListener(this);
		EditText player12 = (EditText) v.findViewById(R.id.Player12b);
		player12.setOnClickListener(this);


		return v;
	}

	@Override
	public void onClick(View v) {
		
		final TextView lic1, lic2, lic3, lic4, 
		lic5, lic6, lic7, lic8,
		lic9, lic10, lic11, lic12;
	
		final TextView num1, num2, num3, num4, 
		num5, num6, num7, num8,
		num9, num10, num11, num12;
		final ImageView captain1, captain2, captain3, captain4,
		captain5, captain6, captain7, captain8, captain9,
		captain10, captain11, captain12;
	

		
		
		
		lic1 = (TextView) v.findViewById(R.id.Lic1b);
		num1 = (TextView)v.findViewById(R.id.PlayerNum1b);
		lic2 = (TextView) v.findViewById(R.id.Lic2b);
		num2 = (TextView)v.findViewById(R.id.PlayerNum2b);
		lic3 = (TextView) v.findViewById(R.id.Lic3b);
		num3 = (TextView)v.findViewById(R.id.PlayerNum3b);
		lic4 = (TextView) v.findViewById(R.id.Lic4b);
		num4 = (TextView)v.findViewById(R.id.PlayerNum4b);
		lic5 = (TextView) v.findViewById(R.id.Lic5b);
		num5 = (TextView)v.findViewById(R.id.PlayerNum5b);
		lic6 = (TextView) v.findViewById(R.id.Lic6b);
		num6 = (TextView)v.findViewById(R.id.PlayerNum6b);
		lic7 = (TextView) v.findViewById(R.id.Lic7b);
		num7 = (TextView)v.findViewById(R.id.PlayerNum7b);
		lic8 = (TextView) v.findViewById(R.id.Lic8b);
		num8 = (TextView)v.findViewById(R.id.PlayerNum8b);
		lic9 = (TextView) v.findViewById(R.id.Lic9b);
		num9  = (TextView)v.findViewById(R.id.PlayerNum9b);
		lic10 = (TextView) v.findViewById(R.id.Lic10b);
		num10 = (TextView)v.findViewById(R.id.PlayerNum10b);
		lic11 = (TextView) v.findViewById(R.id.Lic11b);
		num11 = (TextView)v.findViewById(R.id.PlayerNum11b);
		lic12 = (TextView) v.findViewById(R.id.Lic12b);
		num12 = (TextView)v.findViewById(R.id.PlayerNum12b);
		
	
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
		
		
		
		
		
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.Player1b: playerPressed(v,(EditText)v.findViewById(R.id.Player1b),lic1,num1,captain1);
		break;
		case R.id.Player2b:playerPressed(v,(EditText)v.findViewById(R.id.Player2b),lic1,num1,captain1);
			break;
		case R.id.Player3b:playerPressed(v,(EditText)v.findViewById(R.id.Player3b),lic1,num1,captain1);
			break;
		case R.id.Player4b:playerPressed(v,(EditText)v.findViewById(R.id.Player4b),lic1,num1,captain1);
			break;
		case R.id.Player5b:playerPressed(v,(EditText)v.findViewById(R.id.Player5b),lic1,num1,captain1);
			break;
		case R.id.Player6b:playerPressed(v,(EditText)v.findViewById(R.id.Player6b),lic1,num1,captain1);
			break;
		case R.id.Player7b:playerPressed(v,(EditText)v.findViewById(R.id.Player7b),lic1,num1,captain1);
			break;
		case R.id.Player8b:playerPressed(v,(EditText)v.findViewById(R.id.Player8b),lic1,num1,captain1);
			break;
		case R.id.Player9b:playerPressed(v,(EditText)v.findViewById(R.id.Player9b),lic1,num1,captain1);
			break;
		case R.id.Player10b:playerPressed(v,(EditText)v.findViewById(R.id.Player10b),lic1,num1,captain1);
			break;
		case R.id.Player11b:playerPressed(v,(EditText)v.findViewById(R.id.Player11b),lic1,num1,captain1);
			break;
		case R.id.Player12b:playerPressed(v,(EditText)v.findViewById(R.id.Player12b),lic1,num1,captain1);
			break;   		
		}
	}



}


