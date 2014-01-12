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
		params.add(new BasicNameValuePair("id_teams", Integer.toString(team.getTeamId())));

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
				JSONArray teams = json.getJSONArray(TAG_PLAYERS);

				// looping through All Products
				for (int i = 0; i < teams.length(); i++) {
					JSONObject c = teams.getJSONObject(i);

					// Storing each json item in variable
					int id = c.getInt(TAG_LICENSE);
					String name = c.getString(TAG_NAME);
					String surname = c.getString(TAG_SURNAME);
					int idTeam = c.getInt(TAG_TID);

					playersList.add(new Player(id, name, surname, idTeam));
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return playersList;
	}


}