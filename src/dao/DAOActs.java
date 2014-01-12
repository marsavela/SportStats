/**
 * 
 */
package dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import adm.werock.sportstats.JSONParser;
import adm.werock.sportstats.basics.Act;
import adm.werock.sportstats.basics.User;
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
		params.add(new BasicNameValuePair("email_users", user.getEmail()));

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
					Date date = null;

					SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
					try {  
						date = format.parse(dateString);
					} catch (ParseException e) {  
						// TODO Auto-generated catch block  
						e.printStackTrace();  
					}

					playersList.add(new Act(id, date, email, idLocal, idGuest));
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		//new GetAllTeams().execute(league);

		return playersList;
	}

}