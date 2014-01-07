/**
 * 
 */
package adm.werock.sportstats;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * @author SergiuDaniel
 *
 */
public class DAOUsers {
	
	private static final String TAG_SUCCESS = "success";
	private String email = new String();
	private String name = new String();
	private String password = new String();

	
	//calls the server to add the following user
	public Boolean addUser(String email, String name, String pass) {
		
		// url to create new product
		String url_create_user = "http://sergiu.es/sportstats/new_user.php";

		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", pass));

		// getting JSON Object
		// Note that create product url accepts POST method
		JSONParser jsonParser = new JSONParser();
		JSONObject json = jsonParser.makeHttpRequest(url_create_user,"POST", params);
		
		// check log cat from response
		Log.d("Create Response", json.toString());
		
		// check for success tag
		try {
			int success = json.getInt(TAG_SUCCESS);
			if (success == 1) {
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//calls the server to check if the pair user - password are right.
	public Boolean checkUser(String email, String pass) {

		// url to create new product
		String url_check_user = "http://sergiu.es/sportstats/check_user.php";
		
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", pass));

		// getting JSON Object
		// Note that create product url accepts POST method
		JSONParser jsonParser = new JSONParser();
		JSONObject json = jsonParser.makeHttpRequest(url_check_user,"POST", params);
		
		// check log cat from response
		Log.d("Create Response", json.toString());
		
		// check for success tag
		try {
			int success = json.getInt(TAG_SUCCESS);
			if (success == 1) {
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}
}
