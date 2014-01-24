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
 * @author Sergiu Daniel Marsavela DAO to manage the online Teams
 */
public class DAOTeams {

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_TEAMS = "teams";
	private static final String TAG_TID = "id";
	private static final String TAG_NAME = "name";
	private static final String TAG_LID = "id_leagues";

	/**
	 * Pulls all the teams of a League
	 * 
	 * @param league
	 * @return list with all teams
	 */
	public static ArrayList<Team> getAllTeams(League league) {

		ArrayList<Team> teamsList = new ArrayList<Team>();

		// url to check team
		String url_check_user = "http://sergiu.es/sportstats/check_teams.php";

		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(TAG_LID, Integer.toString(league
				.getLeagueId())));

		// getting JSON Object
		// Note that check team url accepts POST method
		JSONParser jsonParser = new JSONParser();
		JSONObject json = jsonParser.makeHttpRequest(url_check_user, "POST",
				params);

		try {
			// Check your log cat for JSON reponse
			Log.d("All String Team: ", json.toString());

			// Checking for SUCCESS TAG
			int success = json.getInt(TAG_SUCCESS);

			if (success == 1) {
				// teams found
				// Getting Array of teams
				JSONArray teams = json.getJSONArray(TAG_TEAMS);

				// looping through All teams
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

		return teamsList;
	}

	/**
	 * Pull a single team from the DB
	 * 
	 * @param id
	 * @return requested team
	 */
	public static Team getTeamByID(int id) {

		// url to check team
		String url_check_user = "http://sergiu.es/sportstats/check_team_by_id.php";

		Log.d("ID de mierda: ", Integer.toString(id));

		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(TAG_TID, Integer.toString(id)));

		// getting JSON Object
		// Note that check team url accepts POST method
		JSONParser jsonParser = new JSONParser();
		JSONObject json = jsonParser.makeHttpRequest(url_check_user, "POST",
				params);

		// Check your log cat for JSON reponse
		Log.d("Team: ", json.toString());

		try {
			if (json.getInt(TAG_SUCCESS) == 1) {
				// team found
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