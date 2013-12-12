package adm.werock.sportstats;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class PreviewBasketStatsFragment extends Fragment{
	
	private ActivityBasketStats parent;
	
	public static android.support.v4.app.Fragment newInstance() {
		 
        // Instantiate a new fragment
		PreviewBasketStatsFragment fragment = new PreviewBasketStatsFragment();
 
        // Save the parameters
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        fragment.setRetainInstance(true);
 
        return fragment;
    }
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_preview_stats, container, false);
		
		return v;
	}
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);

	    Activity activity = getActivity();
    	
    	if(activity instanceof ActivityBasketStats){
    		parent = ((ActivityBasketStats)activity);
    	}
	}

}
