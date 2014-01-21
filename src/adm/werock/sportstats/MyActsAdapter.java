
package adm.werock.sportstats;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import dao.DAOActs;
import dao.DAOTeams;

import adm.werock.sportstats.basics.Act;
import adm.werock.sportstats.basics.Team;
import adm.werock.sportstats.basics.User;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import dao.DAOTeams;
import adm.werock.sportstats.basics.Team;

public class MyActsAdapter extends BaseAdapter {
	private final Activity activity;
    private List<Act> list;
    TextView teamHome;
    TextView teamGuest;
    TextView pointsHome;
    TextView pointsGuest;
    private int position;

    public MyActsAdapter(Activity activity, List<Act> actsList) {
        super();
        this.activity = activity;
        this.list = actsList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
    	this.position=position;
        LayoutInflater inflater = activity.getLayoutInflater();
        
        View rootView = inflater.inflate(R.layout.activity_my_acts_row, null,true);
        TextView date = (TextView) rootView.findViewById(R.id.date);
        pointsGuest = (TextView) rootView.findViewById(R.id.pointsGuest);
        pointsHome = (TextView) rootView.findViewById(R.id.pointsHome);
        teamGuest = (TextView) rootView.findViewById(R.id.teamGuest);
        teamHome = (TextView) rootView.findViewById(R.id.teamHome);
        
     //   String homeTeam = DAOTeams.getTeamByID(list.get(position).getIdTeamHome()).getName();
    //    Team homeTeam = DAOTeams.getTeamByID(3);
        
        new Task().execute();
        
        
        

     //   date.setText((CharSequence) list.get(position).getDate());
        
        
     //   Log.v("teamHome:", DAOTeams.getTeamByID(3).getName());
        
        return rootView;
    }

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
        	teamHome.setText(DAOTeams.getTeamByID(list.get(position).getIdTeamHome()).getName());
            teamGuest.setText(DAOTeams.getTeamByID(list.get(position).getIdTeamGuest()).getName());
			return null;
        }

        @Override
        protected void onPostExecute(Void params) {
        	pointsHome.setText("10");
            pointsGuest.setText("20");
       }

    }
}



/*

package adm.werock.sportstats;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyActsAdapter extends BaseAdapter {
	private final Activity activity;
    private List<ActProv> list;

    public MyActsAdapter(Activity activity, List<ActProv> scoreList) {
        super();
        this.activity = activity;
        this.list = scoreList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View rootView = inflater.inflate(R.layout.activity_my_acts_row, null,true);
        TextView pointsGuest = (TextView) rootView.findViewById(R.id.pointsGuest);
        TextView pointsHome = (TextView) rootView.findViewById(R.id.pointsHome);
        TextView teamGuest = (TextView) rootView.findViewById(R.id.teamGuest);
        TextView teamHome = (TextView) rootView.findViewById(R.id.teamHome);
        TextView date = (TextView) rootView.findViewById(R.id.date);
        Log.v("Prueba 3:", "Prueba 3");
        teamHome.setText(list.get(position).getTeamHome());
        teamGuest.setText(list.get(position).getTeamGuest());
        pointsHome.setText(Integer.toString(list.get(position).getPointsHome()));
        pointsGuest.setText(Integer.toString(list.get(position).getPointsGuest()));
        date.setText(list.get(position).getDate());
        
        return rootView;
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int arg0) {
        return list.get(arg0);
    }

    public long getItemId(int position) {
        return position;
    }

}

*/