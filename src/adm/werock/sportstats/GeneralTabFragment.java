package adm.werock.sportstats;


import java.util.Calendar;

import adm.werock.sportstats.R;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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











}
