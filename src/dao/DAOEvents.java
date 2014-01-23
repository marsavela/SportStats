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
import adm.werock.sportstats.basics.Act;
import adm.werock.sportstats.basics.Event;
import android.util.Log;

/**
 * @author SergiuDaniel
 *
 */
public class DAOEvents {

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_EVENTS = "events";
	private static final String TAG_EID = "id";
	private static final String TAG_AID = "id_acts";
	private static final String TAG_MINUTE = "minute";
	private static final String TAG_TYPE = "type";
	private static final String TAG_VALUE = "value";
	private static final String TAG_PLNUMBER = "licensnumber_players";

	public static ArrayList<Event> getEventsOfAAct(Act act) {

		ArrayList<Event> eventsList = new ArrayList<Event>();

		// url to create new product
		String url_check_user = "http://sergiu.es/sportstats/check_players.php";

		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id_acts", Integer.toString(act.getId())));

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
				JSONArray events = json.getJSONArray(TAG_EVENTS);

				// looping through All Products
				for (int i = 0; i < events.length(); i++) {
					JSONObject c = events.getJSONObject(i);

					// Storing each json item in variable
					int id = c.getInt(TAG_EID);
					int idAct = c.getInt(TAG_AID);
					int minute = c.getInt(TAG_MINUTE);
					String type = c.getString(TAG_TYPE);
					String value = c.getString(TAG_VALUE);
					int playersLicensnumber = c.getInt(TAG_PLNUMBER);

					eventsList.add(new Event(id, idAct, minute, type, value, playersLicensnumber));
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return eventsList;
	}

	public static Boolean insertEvent(Event event) {

		// url to create new product
		String url_check_user = "http://sergiu.es/sportstats/new_event.php";

		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(TAG_AID, event.getType()));
		params.add(new BasicNameValuePair(TAG_MINUTE, Integer.toString(event.getMinute())));
		params.add(new BasicNameValuePair(TAG_TYPE, event.getType()));
		params.add(new BasicNameValuePair(TAG_VALUE, event.getValue()));
		params.add(new BasicNameValuePair(TAG_PLNUMBER, Integer.toString(event.getPlayer())));

		// getting JSON Object
		// Note that create product url accepts POST method
		JSONParser jsonParser = new JSONParser();
		JSONObject json = jsonParser.makeHttpRequest(url_check_user, "POST",
				params);

		// Check your log cat for JSON response
		Log.d("All String: ", json.toString());

		// Checking for SUCCESS TAG
		try {
			int success;
			success = json.getInt(TAG_SUCCESS);

			if (success == 1) {
				return true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
	
}
