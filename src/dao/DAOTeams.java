/**
 * 
 */
package dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import adm.werock.sportstats.JSONParser;
import adm.werock.sportstats.basics.Team;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

/**
 * @author SergiuDaniel
 *
 */
public class DAOTeams {

	private Activity activity;
	
	// league JSONArray
	JSONArray teams = null;
	
	ArrayList<Team> teamsList = new ArrayList<Team>();

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_LEAGUES = "leagues";
	private static final String TAG_TID = "id";
	private static final String TAG_NAME = "name";
	private static final String TAG_LID = "id";

	public DAOTeams(Activity activity) {
		this.activity = activity;
	}
	
	public ArrayList<Team> getAllTeams() {

		new GetAllTeams().execute();
		
		return teamsList;
	}
	
	/**
     * Async Task to get and send data to My Sql database through JSON respone.
     **/
    private class GetAllTeams extends AsyncTask<Void, Void, JSONObject> {

        private ProgressDialog pDialog;
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            
            pDialog = new ProgressDialog(activity);
            pDialog.setTitle("Contacting Servers");
            pDialog.setMessage("Downloading Teams...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... string) {

        	// url to create new product
    		String url_check_user = "http://sergiu.es/sportstats/check_teams.php";
    		
    		// Building Parameters
    		List<NameValuePair> params = new ArrayList<NameValuePair>();

    		// getting JSON Object
    		// Note that create product url accepts POST method
    		JSONParser jsonParser = new JSONParser();
    		JSONObject json = jsonParser.makeHttpRequest(url_check_user,"GET", params);
    		
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
        	
    		try {
    			// Check your log cat for JSON reponse
    			Log.d("All Products: ", json.toString());
    			
    			// Checking for SUCCESS TAG
    			int success = json.getInt(TAG_SUCCESS);

    			if (success == 1) {
    				// products found
    				// Getting Array of Products
    				teams = json.getJSONArray(TAG_LEAGUES);

    				// looping through All Products
    				for (int i = 0; i < teams.length(); i++) {
    					JSONObject c = teams.getJSONObject(i);

    					// Storing each json item in variable
    					int id = c.getInt(TAG_TID);
    					String name = c.getString(TAG_NAME);
    					int idLeague = c.getInt(TAG_LID);
    					
    					teamsList.add(new Team(id, name, idLeague));
    				}
    			}
    			
    		} catch (JSONException e) {
    			e.printStackTrace();
    		}
        	
        	pDialog.dismiss();
       }
        
    }
	
}