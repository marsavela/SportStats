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
import adm.werock.sportstats.basics.Player;
import adm.werock.sportstats.basics.Team;
import android.util.Log;

/**
 * @author sergiu
 *
 */
public class DAOPlayers {

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PLAYERS = "players";
	private static final String TAG_LICENSE = "licensnumber";
	private static final String TAG_NAME = "name";
	private static final String TAG_SURNAME = "surname";
	private static final String TAG_TID = "id_teams";

	public static ArrayList<Player> getPlayersOfATeam(Team team) {

		ArrayList<Player> playersList = new ArrayList<Player>();

		// url to create new product
		String url_check_user = "http://sergiu.es/sportstats/check_players.php";

		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(TAG_TID, Integer.toString(team.getTeamId())));

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
				JSONArray players = json.getJSONArray(TAG_PLAYERS);

				// looping through All Products
				for (int i = 0; i < players.length(); i++) {
					JSONObject c = players.getJSONObject(i);

					// Storing each json item in variable
					int licensnumber = c.getInt(TAG_LICENSE);
					String name = c.getString(TAG_NAME);
					String surname = c.getString(TAG_SURNAME);
					int idTeam = c.getInt(TAG_TID);

					playersList.add(new Player(licensnumber, name, surname, idTeam));
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return playersList;
	}
	

	public static Player getTeamByID(int licensnumber) {

		// url to create new product
		String url_check_user = "http://sergiu.es/sportstats/check_player_by_license.php";

		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("licensnumber", Integer.toString(licensnumber)));

		// getting JSON Object
		// Note that create product url accepts POST method
		JSONParser jsonParser = new JSONParser();
		JSONObject json = jsonParser.makeHttpRequest(url_check_user,"POST", params);
		

		// Check your log cat for JSON reponse
		Log.d("Player: ", json.toString());
		
		try {
			if (json.getInt(TAG_SUCCESS) == 1) {
				// products found
				// Storing each json item in variable
				String name = json.getString(TAG_NAME);
				String surname = json.getString(TAG_SURNAME);
				int idTeam = json.getInt(TAG_TID);
				
				return new Player(licensnumber, name, surname, idTeam);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}