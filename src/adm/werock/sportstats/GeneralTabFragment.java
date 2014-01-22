package adm.werock.sportstats;




import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class GeneralTabFragment extends Fragment {
	

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
		super.onPause();
		SharedPreferences pref = this.getActivity().getSharedPreferences("myPref", Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putString("prefTownEditText", ((EditText) getView().findViewById(R.id.TownText)).getText().toString());
		editor.putString("prefMainRefereeEditText", ((EditText) getView().findViewById(R.id.MainRefereeText)).getText().toString());
		editor.putString("prefSecondRefereeText", ((EditText) getView().findViewById(R.id.SecondRefereeText)).getText().toString());
		editor.putString("prefDateEditText", ((EditText) getView().findViewById(R.id.DateText)).getText().toString());
		editor.putString("prefTimeEditText", ((EditText) getView().findViewById(R.id.TimeText)).getText().toString());
		editor.commit();
	}
	
	@Override
	public void onResume() {
//		// TODO Auto-generated method stub
		SharedPreferences pref = this.getActivity().getSharedPreferences("myPref", Context.MODE_PRIVATE);
		((EditText)getView().findViewById(R.id.TownText)).setText(pref.getString("prefTownEditText", ""));
		((EditText)getView().findViewById(R.id.MainRefereeText)).setText(pref.getString("prefMainRefereeEditText", ""));
		((EditText)getView().findViewById(R.id.SecondRefereeText)).setText(pref.getString("prefSecondRefereeText", ""));
		((EditText)getView().findViewById(R.id.DateText)).setText(pref.getString("prefDateEditText", ""));
		((EditText)getView().findViewById(R.id.TimeText)).setText(pref.getString("prefTimeEditText", ""));
		super.onResume();
	}
	









}
