package adm.werock.sportstats;

import adm.werock.sportstats.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class StartTabFragment extends Fragment {

	 Button startButton;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(adm.werock.sportstats.R.layout.layout_start_tab, container, false);
		
		
		

		
		Log.i("asd", "1");
		Button startButton = (Button) rootView.findViewById(R.id.ButtonStartGame);
		 Log.i("asd", "2");
		    //set a onclick listener for when the button gets clicked
		 startButton.setOnClickListener(new OnClickListener() {
		        //Start new list activity
		        @Override
		        
		        public void onClick(View v) {
		        	 Log.i("asd", "3");
		            Intent i = new Intent(getActivity(), ActivityBasketStats.class);
		            startActivity(i);
		            
		        }
		    });
		return rootView;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}

	
	
}
