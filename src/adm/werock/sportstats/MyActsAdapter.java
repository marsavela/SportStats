package adm.werock.sportstats;

import java.util.List;
import java.util.Map;

import adm.werock.sportstats.basics.Act;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import dao.DAOTeams;

public class MyActsAdapter extends SimpleAdapter {
	//private final Activity activity=null;
    private List<Act> list;
    TextView teamHome;
    TextView teamGuest;
    TextView pointsHome;
    TextView pointsGuest;
    private static int position;
    
	public String homeTeamString;
	public String guestTeamString;


		public MyActsAdapter(Context context, List<? extends Map<String, ?>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		// TODO Auto-generated constructor stub
	}
/*
    public View getView(int position, View convertView, ViewGroup parent) {
    	this.position=position;
        LayoutInflater inflater = convertView.get.getLayoutInflater();
        
        View rootView = inflater.inflate(adm.werock.sportstats.R.layout.activity_my_acts_row, parent,false);
        TextView date = (TextView) rootView.findViewById(R.id.date);
        pointsGuest = (TextView) rootView.findViewById(R.id.pointsGuest);
        pointsHome = (TextView) rootView.findViewById(R.id.pointsHome);
        teamGuest = (TextView) rootView.findViewById(R.id.teamGuest);
        teamHome = (TextView) rootView.findViewById(R.id.teamHome);
        teamHome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			
			}
        });	
        new Task().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
       
        
        
        pointsHome.setText("10");
        pointsGuest.setText("30");
        teamHome.setText(homeTeamString);
    	teamGuest.setText(guestTeamString);
        

     //   date.setText((CharSequence) list.get(position).getDate());
        
        
     //   Log.v("teamHome:", DAOTeams.getTeamByID(3).getName());
        
        return rootView;
    }
*/
    public int getCount() {
        return list.size();
    }

    public Object getItem(int arg0) {
        return list.get(arg0);
    }

    public long getItemId(int position) {
        return position;
    }

    
    
private class Task extends AsyncTask<Void, Void, Void> {
      
        @Override
        protected void onPreExecute() {
        	super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
        // TODO Auto-generated method stub
        	
        	homeTeamString = DAOTeams.getTeamByID(list.get(position).getIdTeamHome()).getName();
        	guestTeamString = DAOTeams.getTeamByID(list.get(position).getIdTeamGuest()).getName();
        //	pointsGuest = DAOEvents.getEventsOfAAct(this);
        	Log.i("pedooooooooo", homeTeamString);
        	Log.i("pedooooooooo", guestTeamString);

			return null;
        }

        @Override
        protected void onPostExecute(Void params) {
        	teamHome.setText(homeTeamString);
        	teamGuest.setText(guestTeamString);
       }

    }
}