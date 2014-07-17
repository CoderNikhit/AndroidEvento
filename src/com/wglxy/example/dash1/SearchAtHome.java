package com.wglxy.example.dash1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("NewApi")

public class SearchAtHome extends DashboardActivity{
	 //class Arbit extends ListActivity {
	ListAc LA = new ListAc();
		ImageButton ib;
		EditText E;
	private ProgressDialog pDialog;
	ArrayList<HashMap<String, String>> graphicList;
	private static final String url_all_graphics = "http://10.0.3.2/images/api.download.php";
	 	
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_GRAPHICS = "graphics";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    JSONArray graphics = null;
	
protected void onCreate(Bundle savedInstanceState) 
{
    super.onCreate(savedInstanceState);
    setContentView (R.layout.homesearchactivity);
    setTitleFromActivityLabel (R.id.title_text);
	Intent intent = getIntent();
	@SuppressWarnings("unused")
	String queryKeyword = intent.getStringExtra("keyword");
	System.out.println(queryKeyword); // one method to check
	Log.d("queryKeyword",queryKeyword); // second method to check
	Toast.makeText(this, queryKeyword, Toast.LENGTH_LONG).show();// third way

	graphicList = new ArrayList<HashMap<String, String>>();
     new LoadAllGraphics().execute();
   ListView lv = LA.getListView();
}

	class LoadAllGraphics extends AsyncTask<String, String, String> {
		
//Before starting background thread Show Progress Dialog
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SearchAtHome.this);
            pDialog.setMessage("Loading Graphics. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show(); }
        
//getting All products from url
		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = JSONParser.makeHttpRequest(url_all_graphics, "POST", params);
 
            // Check your log cat for JSON response
            Log.d("All Graphics: ", json.toString());
            
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    graphics = json.getJSONArray(TAG_GRAPHICS);
                    
                    for (int i = 0; i < graphics.length(); i++) {
                        JSONObject c = graphics.getJSONObject(i);
 
                        // Storing each json item in variable
                        String id = c.getString(TAG_ID);
                        String name = c.getString(TAG_NAME);
 
                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();
 
                        // adding each child node to HashMap key => value
                        map.put(TAG_ID, id);
                        map.put(TAG_NAME, name);
 
                        // adding HashList to ArrayList
                        graphicList.add(map);
                    }}}
                catch (JSONException e) {
                    e.printStackTrace();
                }
     
			return null;
		}
		
		protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();	
	
	// updating UI from Background Thread
           // runOnUiThread(new Runnable() {
             //   public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * 
                    ListAdapter adapter = new SimpleAdapter(
                            AllProductsActivity.this, productsList,
                            R.layout.list_item, new String[] { TAG_PID,
                                    TAG_NAME},
                            new int[] { R.id.pid, R.id.name });
                    // updating listview
                    setListAdapter(adapter);
                }
            }); */
	
	}}}
	

   