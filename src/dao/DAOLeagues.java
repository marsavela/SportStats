package dao;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import adm.werock.sportstats.JSONParser;
import adm.werock.sportstats.basics.League;
import adm.werock.sportstats.basics.User;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Funciones para controlar el acceso de ligas a la base de datos.
 */

/**
 * @author SergiuDaniel
 *
 */
public class DAOLeagues {
	
	private Activity activity;
	
	// league JSONArray
	JSONArray leagues = null;
	
	ArrayList<League> leaguesList = new ArrayList<League>();

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_LEAGUES = "leagues";
	private static final String TAG_PID = "id";
	private static final String TAG_NAME = "name";

	public DAOLeagues(Activity activity) {
		this.activity = activity;
	}
	
	public ArrayList<League> getAllLeagues() {

		new GetAllLeagues().execute();
		
		return leaguesList;
	}
	
	/**
     * Async Task to get and send data to My Sql database through JSON respone.
     **/
    private class GetAllLeagues extends AsyncTask<Void, Void, JSONObject> {

        private ProgressDialog pDialog;
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            
            pDialog = new ProgressDialog(activity);
            pDialog.setTitle("Contacting Servers");
            pDialog.setMessage("Downloading Leagues...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... string) {

        	// url to create new product
    		String url_check_user = "http://sergiu.es/sportstats/check_leagues.php";
    		
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
    				leagues = json.getJSONArray(TAG_LEAGUES);

    				// looping through All Products
    				for (int i = 0; i < leagues.length(); i++) {
    					JSONObject c = leagues.getJSONObject(i);

    					// Storing each json item in variable
    					int id = c.getInt(TAG_PID);
    					String name = c.getString(TAG_NAME);
    					
    					leaguesList.add(new League(id, name));
    				}
    			}
    			
    		} catch (JSONException e) {
    			e.printStackTrace();
    		}
        	
        	pDialog.dismiss();
       }
        
    }
	
}
