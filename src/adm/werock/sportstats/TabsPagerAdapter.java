package adm.werock.sportstats;



/*
 I am creating a FragmentPagerAdapter class to provide views to tab fragments. 
 Create a class called TabsPagerAdapter.java under adapter package. 
 This adapter provides fragment views to tabs which we are going to create them later in this tutorial.*/

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated fragment activity
			return new GeneralTabFragment();
		case 1:
			// Games fragment activity
			return new TeamAOnlineFragment();
		case 2:
			// Movies fragment activity
			return new TeamBOnlineFragment();
		case 3:
			// Movies fragment activity
			return new StartTabFragment();
		}
		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 4;
	}
	


}
