/**
 * 
 */
package dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import adm.werock.sportstats.JSONParser;
import adm.werock.sportstats.basics.League;
import adm.werock.sportstats.basics.Team;
import android.util.Log;

/**
 * @author SergiuDaniel
 *
 */
public class DAOTeams {

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_TEAMS = "teams";
	private static final String TAG_TID = "id";
	private static final String TAG_NAME = "name";
	private static final String TAG_LID = "id_leagues";

	public static ArrayList<Team> getAllTeams(League league) {

		ArrayList<Team> teamsList = new ArrayList<Team>();

		// url to create new product
		String url_check_user = "http://sergiu.es/sportstats/check_teams.php";

		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id_leagues", Integer.toString(league.getLeagueId())));

		// getting JSON Object
		// Note that create product url accepts POST method
		JSONParser jsonParser = new JSONParser();
		JSONObject json = jsonParser.makeHttpRequest(url_check_user,"POST", params);

		try {
			// Check your log cat for JSON reponse
			Log.d("All String: ", json.toString());

			// Checking for SUCCESS TAG
			int success = json.getInt(TAG_SUCCESS);

			if (success == 1) {
				// products found
				// Getting Array of Products
				JSONArray teams = json.getJSONArray(TAG_TEAMS);

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

		//new GetAllTeams().execute(league);

		return teamsList;
	}
	
	public static Team getTeamByID(int id) {

		// url to create new product
		String url_check_user = "http://sergiu.es/sportstats/check_team_by_id.php";

		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", Integer.toString(id)));

		// getting JSON Object
		// Note that create product url accepts POST method
		JSONParser jsonParser = new JSONParser();
		JSONObject json = jsonParser.makeHttpRequest(url_check_user,"POST", params);
		

		// Check your log cat for JSON reponse
		Log.d("Team: ", json.toString());
		
		try {
			if (json.getInt(TAG_SUCCESS) == 1) {
				// products found
				// Storing each json item in variable
				String name = json.getString(TAG_NAME);
				int idLeague = json.getInt(TAG_LID);
				
				return new Team(id, name, idLeague);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}