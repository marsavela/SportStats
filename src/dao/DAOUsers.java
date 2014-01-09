/**
 * Functions to manage the Users of the DB.
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import adm.werock.sportstats.JSONParser;
import adm.werock.sportstats.basics.User;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

/**
 * @author SergiuDaniel
 *
 */
public class DAOUsers {

	private Activity activity;
	
	private static final String TAG_SUCCESS = "success";
	
	public DAOUsers(Activity activity) {
		
		this.activity = activity;
	}

	//calls the server to add the following user
	public Boolean addUser(User user) {
		// getting JSON Object
		// Note that create product url accepts POST method
		try {
			JSONObject json = new AddUser().execute(user).get();
			
			// check log cat from response
			Log.d("Create Response", json.toString());
			
			// check for success tag
		
			int success = json.getInt(TAG_SUCCESS);
			if (success == 1) {
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return false;
	}
	
	//calls the server to check if the pair user - password are right.

	public Boolean checkUser(User user) {
		
		try {
			JSONObject json = new CheckUser().execute(user).get();
		
			// check log cat from response
			Log.d("Create Response", json.toString());
			
			int success = json.getInt(TAG_SUCCESS);
			if (success == 1) {
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return false;
	}
	
	    /**
	     * Async Task to get and send data to My Sql database through JSON respone.
	     **/
	    private class CheckUser extends AsyncTask<User, Void, JSONObject> {


	        private ProgressDialog pDialog;
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            
	            //activity.finish();
	            
	            pDialog = new ProgressDialog(activity);
	            pDialog.setTitle("Contacting Servers");
	            pDialog.setMessage("Logging in ...");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(true);
	            pDialog.show();
	        }

	        @Override
	        protected JSONObject doInBackground(User... user) {

	        	// url to create new product
	    		String url_check_user = "http://sergiu.es/sportstats/check_user.php";
	    		
	    		// Building Parameters
	    		List<NameValuePair> params = new ArrayList<NameValuePair>();
	    		params.add(new BasicNameValuePair("email", user[0].getEmail()));
	    		params.add(new BasicNameValuePair("password", user[0].getPassword()));

	    		// getting JSON Object
	    		// Note that create product url accepts POST method
	    		JSONParser jsonParser = new JSONParser();
	    		JSONObject json = jsonParser.makeHttpRequest(url_check_user,"POST", params);
	    		
	            return json;
	        }

	        @Override
	        protected void onPostExecute(JSONObject json) {
	        	pDialog.dismiss();
	       }
	        
	    }
	    
	    /**
	     * Async Task to get and send data to My Sql database through JSON respone.
	     **/
	    private class AddUser extends AsyncTask<User, Void, JSONObject> {


	        private ProgressDialog pDialog;
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            
	            //activity.finish();
	            
	            pDialog = new ProgressDialog(activity);
	            pDialog.setTitle("Contacting Servers");
	            pDialog.setMessage("Logging in ...");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(true);
	            pDialog.show();
	        }

	        @Override
	        protected JSONObject doInBackground(User... user) {

	        	// url to create new product
	    		String url_check_user = "http://sergiu.es/sportstats/new_user.php";
	    		
	    		// Building Parameters
	    		List<NameValuePair> params = new ArrayList<NameValuePair>();
	    		params.add(new BasicNameValuePair("email", user[0].getEmail()));
	    		params.add(new BasicNameValuePair("name", user[0].getName()));
	    		params.add(new BasicNameValuePair("password", user[0].getPassword()));

	    		// getting JSON Object
	    		// Note that create product url accepts POST method
	    		JSONParser jsonParser = new JSONParser();
	    		JSONObject json = jsonParser.makeHttpRequest(url_check_user,"POST", params);
	    		
	            return json;
	        }

	        @Override
	        protected void onPostExecute(JSONObject json) {
	        	pDialog.dismiss();
	       }
	        
	    }
	    
}
