package adm.werock.sportstats;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class FragmentHelpDataInsert extends Fragment{

	
	private ActivityBasketStats parent;
	private View view;
	
	public static android.support.v4.app.Fragment newInstance() {
		 
        // Instantiate a new fragment
		FragmentHelpDataInsert fragment = new FragmentHelpDataInsert();
 
        // Save the parameters
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        fragment.setRetainInstance(true);
 
        return fragment;
    }
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_help_data_insert, container, false);
		
		this.view = v;
		return v;
	}
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);

	    Activity activity = getActivity();
    	
    	if(activity instanceof ActivityBasketStats){
    		parent = ((ActivityBasketStats)activity);
    	}
    	
    	//
    	DisplayMetrics metrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int height = metrics.heightPixels;
		int width = metrics.widthPixels;
		 
		BitmapDrawable bmap;
		
		int currentPage = parent.getCurrentPage();
		
		if(currentPage < 4 && currentPage >= 0){
		
			switch(parent.getCurrentPage()){
				case 0:
					bmap = (BitmapDrawable) this.getResources().getDrawable(R.drawable.help_event_list);
					break; 
				case 1:
					bmap = (BitmapDrawable) this.getResources().getDrawable(R.drawable.help_preview_stats);
					break;
				case 2:
					bmap = (BitmapDrawable) this.getResources().getDrawable(R.drawable.help_players);
					break;
				case 3:
					bmap = (BitmapDrawable) this.getResources().getDrawable(R.drawable.help_player_actions);
					break;
				default:
					bmap = (BitmapDrawable) this.getResources().getDrawable(R.drawable.help_players);
					break;
			}
			
			float bmapWidth = bmap.getBitmap().getWidth();
			float bmapHeight = bmap.getBitmap().getHeight();
			 
			float wRatio = width / bmapWidth;
			float hRatio = height / bmapHeight;
			 
			float ratioMultiplier = wRatio;
			// Untested conditional though I expect this might work for landscape mode
			if (hRatio < wRatio) {
				ratioMultiplier = hRatio;
			}
			 
			int newBmapWidth = (int) (bmapWidth*ratioMultiplier);
			int newBmapHeight = (int) (bmapHeight*ratioMultiplier);
			 
			ImageView imageHelp = (ImageView) view.findViewById(R.id.imageHelpDataInsert);
			imageHelp.setLayoutParams(new LinearLayout.LayoutParams(newBmapWidth, newBmapHeight));
			imageHelp.setImageBitmap(bmap.getBitmap());
			imageHelp.setOnClickListener(new HandlerTouch());
		}
	}
	
	private class HandlerTouch implements OnClickListener
	{
		@Override
		public void onClick(View arg0) {
			parent.disableHelp();
		}
	}
}

