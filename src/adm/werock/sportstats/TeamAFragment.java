package adm.werock.sportstats;



import adm.werock.sportstats.R;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TeamAFragment extends Fragment implements OnClickListener {
	  
	public void setStartingPlayer(){
	        Toast.makeText(getActivity().getApplicationContext(), "Ha pulsado inicial", Toast.LENGTH_SHORT).show();
	    }
	     
	    public void setNotCalledPlayer(){
	        Toast.makeText(getActivity().getApplicationContext(), "Ha pulsado No convocado", Toast.LENGTH_SHORT).show();
	    }

	    
	    public void doPositiveClick(){  
	    	Toast.makeText(getActivity().getApplicationContext(), "Ha pulsado Positivo", Toast.LENGTH_SHORT).show();
	    }
	     
 public void playerPressed(View view, TextView playerArg, TextView licArg, 
			TextView numArg, ImageView captainArg){
		getActivity().setContentView(R.layout.layout_team_a_tab);
		
		final TextView player;
		 final TextView  lic;
		final TextView num;
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

				v.findViewById(R.layout.layout_team_a_tab).invalidate();
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


    
  
  
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(adm.werock.sportstats.R.layout.layout_team_a_tab, container, false);

		return v;
	}
	
	
	 public void onActivityCreated(Bundle savedInstanceState)
	    {
	        super.onActivityCreated(savedInstanceState);

		
		EditText player1 = (EditText) getView().findViewById(R.id.Player1);
		player1.setOnClickListener(this);
		EditText player2 = (EditText) getView().findViewById(R.id.Player2);
		player2.setOnClickListener(this);
		EditText player3 = (EditText) getView().findViewById(R.id.Player3);
		player3.setOnClickListener(this);
		EditText player4 = (EditText) getView().findViewById(R.id.Player4);
		player4.setOnClickListener(this);
		EditText player5 = (EditText) getView().findViewById(R.id.Player5);
		player5.setOnClickListener(this);
		EditText player6 = (EditText) getView().findViewById(R.id.Player6);
		player6.setOnClickListener(this);
		EditText player7 = (EditText) getView().findViewById(R.id.Player7);
		player7.setOnClickListener(this);
		EditText player8 = (EditText) getView().findViewById(R.id.Player8);
		player8.setOnClickListener(this);
		EditText player9 = (EditText) getView().findViewById(R.id.Player9);
		player9.setOnClickListener(this);
		EditText player10 = (EditText) getView().findViewById(R.id.Player10);
		player10.setOnClickListener(this);
		EditText player11 = (EditText) getView().findViewById(R.id.Player11);
		player11.setOnClickListener(this);
		EditText player12 = (EditText) getView().findViewById(R.id.Player12);
		player12.setOnClickListener(this);

	}
	
	public void onClick (View v) {
	
  
		
		
		
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

		
		
		
		lic1 = (TextView) this.getActivity().findViewById(R.id.Lic1);
		num1 = (TextView)this.getActivity().findViewById(R.id.PlayerNum1);
		lic2 = (TextView) this.getActivity().findViewById(R.id.Lic2);
		num2 = (TextView)this.getActivity().findViewById(R.id.PlayerNum2);
		lic3 = (TextView) this.getActivity().findViewById(R.id.Lic3);
		num3 = (TextView)this.getActivity().findViewById(R.id.PlayerNum3);
		lic4 = (TextView) this.getActivity().findViewById(R.id.Lic4);
		num4 = (TextView)this.getActivity().findViewById(R.id.PlayerNum4);
		lic5 = (TextView) this.getActivity().findViewById(R.id.Lic5);
		num5 = (TextView)this.getActivity().findViewById(R.id.PlayerNum5);
		lic6 = (TextView) this.getActivity().findViewById(R.id.Lic6);
		num6 = (TextView)this.getActivity().findViewById(R.id.PlayerNum6);
		lic7 = (TextView) this.getActivity().findViewById(R.id.Lic7);
		num7 = (TextView)this.getActivity().findViewById(R.id.PlayerNum7);
		lic8 = (TextView) this.getActivity().findViewById(R.id.Lic8);
		num8 = (TextView)this.getActivity().findViewById(R.id.PlayerNum8);
		lic9 = (TextView) this.getActivity().findViewById(R.id.Lic9);
		num9  = (TextView)this.getActivity().findViewById(R.id.PlayerNum9);
		lic10 = (TextView) this.getActivity().findViewById(R.id.Lic10);
		num10 = (TextView)this.getActivity().findViewById(R.id.PlayerNum10);
		lic11 = (TextView) this.getActivity().findViewById(R.id.Lic11);
		num11 = (TextView)this.getActivity().findViewById(R.id.PlayerNum11);
		lic12 = (TextView) this.getActivity().findViewById(R.id.Lic12);
		num12 = (TextView)this.getActivity().findViewById(R.id.PlayerNum12);
		
	
		captain1 = (ImageView) (this.getActivity().findViewById(R.id.Captain1));
		captain2 = (ImageView) (this.getActivity().findViewById(R.id.Captain2));
		captain3 = (ImageView) (this.getActivity().findViewById(R.id.Captain3));
		captain4 = (ImageView) (this.getActivity().findViewById(R.id.Captain4));
		captain5 = (ImageView) (this.getActivity().findViewById(R.id.Captain5));
		captain6 = (ImageView) (this.getActivity().findViewById(R.id.Captain6));
		captain7 = (ImageView) (this.getActivity().findViewById(R.id.Captain7));
		captain8 = (ImageView) (this.getActivity().findViewById(R.id.Captain8));
		captain9 = (ImageView) (this.getActivity().findViewById(R.id.Captain9));
		captain10 = (ImageView) (this.getActivity().findViewById(R.id.Captain10));
		captain11 = (ImageView) (this.getActivity().findViewById(R.id.Captain11));
		captain12 = (ImageView) (this.getActivity().findViewById(R.id.Captain12));
		
		
		//v.findViewById(R.id.Captain1).setVisibility(View.VISIBLE);
		// View.inflate(getActivity(), adm.werock.sportstats.R.layout.layout_team_a_tab, null);
		
	
		
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.Player1: playerPressed(v,(EditText)v.findViewById(R.id.Player1),lic1,num1,captain1);
		
		break;
		case R.id.Player2:playerPressed(v,(EditText)v.findViewById(R.id.Player2),lic1,num1,captain1);
			break;
		case R.id.Player3:playerPressed(v,(EditText)v.findViewById(R.id.Player3),lic1,num1,captain1);
			break;
		case R.id.Player4:playerPressed(v,(EditText)v.findViewById(R.id.Player4),lic1,num1,captain1);
			break;
		case R.id.Player5:playerPressed(v,(EditText)v.findViewById(R.id.Player5),lic1,num1,captain1);
			break;
		case R.id.Player6:playerPressed(v,(EditText)v.findViewById(R.id.Player6),lic1,num1,captain1);
			break;
		case R.id.Player7:playerPressed(v,(EditText)v.findViewById(R.id.Player7),lic1,num1,captain1);
			break;
		case R.id.Player8:playerPressed(v,(EditText)v.findViewById(R.id.Player8),lic1,num1,captain1);
			break;
		case R.id.Player9:playerPressed(v,(EditText)v.findViewById(R.id.Player9),lic1,num1,captain1);
			break;
		case R.id.Player10:playerPressed(v,(EditText)v.findViewById(R.id.Player10),lic1,num1,captain1);
			break;
		case R.id.Player11:playerPressed(v,(EditText)v.findViewById(R.id.Player11),lic1,num1,captain1);
			break;
		case R.id.Player12:playerPressed(v,(EditText)v.findViewById(R.id.Player12),lic1,num1,captain1);
			break;   		
		}
	}
}