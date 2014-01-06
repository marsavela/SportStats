package adm.werock.sportstats;


import java.util.Calendar;

import adm.werock.sportstats.R;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class GeneralTabFragment extends Fragment {


	private int hour, minute, year = 2013, month, day;	
	TimePicker timePicker;
	TextView timeText, dateText;
	DatePicker datePicker;	
	static final int TIME_DIALOG_ID = 999;
	static final int DATE_DIALOG_ID = 888;


	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	//	setContentView(R.layout.layout_general_tab);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

	
		View rootView = inflater.inflate(adm.werock.sportstats.R.layout.layout_general_tab, container, false);

		return rootView;
	}

	@Override
	public void onPause() {
//		// TODO Auto-generated method stub
//		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onPause();
		SharedPreferences pref = this.getActivity().getSharedPreferences("myPref", Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putString("prefLocalTeamEditText", ((EditText) this.getActivity().findViewById(R.id.LocalTeamEditText)).getText().toString());
		editor.putString("prefVisitorTeamEditText", ((EditText) getActivity().findViewById(R.id.VisitorTeamEditText)).getText().toString());
		editor.putString("prefTownEditText", ((EditText) getActivity().findViewById(R.id.TownEditText)).getText().toString());
		editor.putString("prefMainRefereeEditText", ((EditText) getActivity().findViewById(R.id.MainRefereeEditText)).getText().toString());
		editor.putString("prefSecondRefereeText", ((EditText) getActivity().findViewById(R.id.SecondRefereeText)).getText().toString());
		//editor.putString("prefDateEditText", ((EditText) getActivity().findViewById(R.id.DateEditText)).getText().toString());
		//editor.putString("prefTimeEditText", ((EditText) getActivity().findViewById(R.id.TimeEditText)).getText().toString());
//		Log.i("hooooooooola", ((EditText) this.getActivity().findViewById(R.id.LocalTeamEditText)).getText().toString()+"");
		editor.commit();
		//putPref("prefLocalTeamEditText", ((EditText) this.getActivity().findViewById(R.id.LocalTeamEditText)).getText().toString(), getActivity().getApplicationContext());
	}
	
	@Override
	public void onResume() {
//		// TODO Auto-generated method stub
//		 this.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
		super.onResume();
		//((EditText)this.getActivity().findViewById(R.id.LocalTeamEditText)).setText(getPref("prefLocalTeamEditText", getActivity().getApplicationContext()));
		SharedPreferences pref = this.getActivity().getSharedPreferences("myPref", Context.MODE_PRIVATE);
		((EditText)this.getActivity().findViewById(R.id.LocalTeamEditText)).setText(pref.getString("prefLocalTeamEditText", ""));
		((EditText)getActivity().findViewById(R.id.VisitorTeamEditText)).setText(pref.getString("prefVisitorTeamEditText", ""));
		((EditText)getActivity().findViewById(R.id.TownEditText)).setText(pref.getString("prefTownEditText", ""));
		((EditText)getActivity().findViewById(R.id.MainRefereeEditText)).setText(pref.getString("prefMainRefereeEditText", ""));
		((EditText)getActivity().findViewById(R.id.SecondRefereeText)).setText(pref.getString("prefSecondRefereeText", ""));
		((EditText)getActivity().findViewById(R.id.DateEditText)).setText(pref.getString("prefDateEditText", ""));
		((EditText)getActivity().findViewById(R.id.TimeEditText)).setText(pref.getString("prefTimeEditText", ""));
	
	}
	









}
