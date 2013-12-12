package adm.werock.sportstats;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ActFragmentPagerAdapter extends FragmentStatePagerAdapter {
	 
    // List of fragments which are going to set in the view pager widget
    List<Fragment> fragments;
 
     //interface for interacting with Fragment objects inside of an Activity
    public ActFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fragments = new ArrayList<Fragment>();
    }
 
    //Add a new fragment in the list.
    public void addFragment(Fragment fragment) {
        this.fragments.add(fragment);
    }
    
    public void deleteFragment(Fragment fragment) {
    	this.fragments.remove(fragment);
    }
 
    @Override
    public Fragment getItem(int arg0) {
        return this.fragments.get(arg0);
    }
 
    @Override
    public int getCount() {
        return this.fragments.size();
    }
 
}
