/**
 * 
 */
package dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import adm.werock.sportstats.JSONParser;
import adm.werock.sportstats.basics.Act;
import adm.werock.sportstats.basics.Event;
import adm.werock.sportstats.basics.User;
import android.content.Context;
import android.util.Log;

/**
 * @author sergiu
 * 
 */
public class DAOActs {

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_ACTS = "acts";
	private static final String TAG_AID = "id";
	private static final String TAG_DATE = "date";
	private static final String TAG_EMAIL = "email_users";
	private static final String TAG_LID = "id_teamlocal";
	private static final String TAG_GID = "id_teamguest";

	public static ArrayList<Act> getAct(User user) {

		ArrayList<Act> playersList = new ArrayList<Act>();

		// url to create new product
		String url_check_user = "http://sergiu.es/sportstats/check_acts.php";

		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("TAG_EMAIL", user.getEmail()));

		// getting JSON Object
		// Note that create product url accepts POST method
		JSONParser jsonParser = new JSONParser();
		JSONObject json = jsonParser.makeHttpRequest(url_check_user, "POST",
				params);

		try {
			// Check your log cat for JSON response
			Log.d("All String: ", json.toString());

			// Checking for SUCCESS TAG
			int success = json.getInt(TAG_SUCCESS);

			if (success == 1) {
				// products found
				// Getting Array of Products
				JSONArray teams = json.getJSONArray(TAG_ACTS);

				// looping through All Products
				for (int i = 0; i < teams.length(); i++) {
					JSONObject c = teams.getJSONObject(i);

					// Storing each json item in variable
					int id = c.getInt(TAG_AID);
					String dateString = c.getString(TAG_DATE);
					String email = c.getString(TAG_EMAIL);
					int idLocal = c.getInt(TAG_LID);
					int idGuest = c.getInt(TAG_GID);
					playersList.add(new Act(id, putDateTime(dateString), email, idLocal, idGuest));
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return playersList;
	}

	private Boolean insertAct(Act act) {

		// url to create new product
		String url_check_user = "http://sergiu.es/sportstats/new_act.php";

		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("TAG_DATE", getDateTime(act.getDate())));
		params.add(new BasicNameValuePair("TAG_EMAIL", act.getEmailUser()));
		params.add(new BasicNameValuePair("TAG_LID", Integer.toString(act.getIdTeamHome())));
		params.add(new BasicNameValuePair("TAG_GID", Integer.toString(act.getIdTeamGuest())));

		// getting JSON Object
		// Note that create product url accepts POST method
		JSONParser jsonParser = new JSONParser();
		JSONObject json = jsonParser.makeHttpRequest(url_check_user, "POST",
				params);

		// Check your log cat for JSON reponse
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
	
	public Boolean uploadLocalAct(Context context, int act_id) {
		
		ActDBHelper helper = new ActDBHelper(context);
		Act act = helper.selectAct(act_id);
		ArrayList<Event> eventsList = helper.selectEvents(act.getId());
		
		if(!insertAct(act)) {
			return false;
		}
		
		for(Event event : eventsList) {
			if(!DAOEvents.insertEvent(event)) {
				return false;
			}
		}
		
		return true;
	}

	// --------------- other methods ----------------//
	
	private String getDateTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
	}
	
	private static Date putDateTime(String dateString){
		Date date = null;
		SimpleDateFormat  dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.getDefault());  
		try {  
			date = dateFormat.parse(dateString);
		} catch (ParseException e) {  
			// TODO Auto-generated catch block  
			e.printStackTrace();  
		}
		
		return date;
	}

}