package com.wglxy.example.dash1;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

//import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
 

public class F2Activity extends DashboardActivity
{
	 private ProgressDialog pDialog;
	 
	    JSONParser jsonParser = new JSONParser();
	    EditText inputEvent;
	    EditText inputLocation;
	    EditText inputDescription;
	    // iss file ko baar baar clean karna pad raha h.
	     //kyu/
	    private static String url_update_row = Constants.SERVER_ADDRESS+"images/create_product.php"; //sorry :P this is bad practice. Le me tell u smarter way.
	    private static final String TAG_SUCCESS = "success";
	    
	    protected void onCreate(Bundle savedInstanceState) 
{
    super.onCreate(savedInstanceState);
    setContentView (R.layout.activity_f2);
    inputEvent = (EditText) findViewById(R.id.inputEvent);
    inputLocation = (EditText) findViewById(R.id.inputLocation);
    inputDescription = (EditText) findViewById(R.id.inputDescription);
    
 // Create button
    Button btnUpdate = (Button) findViewById(R.id.btnUpdate);

    // button click event
    btnUpdate.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            // creating new product in background thread
            new CreateNewRow().execute();
        }
    });
}
	  // happpy ? tu jo h :D :P
	    class CreateNewRow extends AsyncTask<String, String, String> {
	    		           protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(F2Activity.this);
	            pDialog.setMessage("Updating..");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(true);
	            pDialog.show();
	            System.out.println("---------------------------2------------------------------------");
	        }
	    		          	    		           
	        /**
	         * Creating product
	         * */
	        protected String doInBackground(String... args) {
	            String Event = inputEvent.getText().toString();
	            String Location = inputLocation.getText().toString();
	            String Description = inputDescription.getText().toString();
	            System.out.println("---------------------------3------------------------------------");
	            // Building Parameters
	            List<NameValuePair> params = new ArrayList<NameValuePair>();
	            params.add(new BasicNameValuePair("Event", Event));
	            params.add(new BasicNameValuePair("Location", Location));
	            params.add(new BasicNameValuePair("Description", Description));
	            System.out.println("---------------------------4------------------------------------");
	            // getting JSON Object
	            // Note that create product url accepts POST method
	            JSONObject json = JSONParser.makeHttpRequest(url_update_row, "POST", params);
	            System.out.println("---------------------------5------------------------------------");
	            // check logcat for response
	            Log.d("Create Response", json.toString());
	 
	            // check for success tag
	            try {
	                int success = json.getInt(TAG_SUCCESS);
	 
	                if (success == 1) {
	                    // successfully created product
	                    Intent i = new Intent(getApplicationContext(), F1Activity.class);
	                    startActivity(i);
	                    
	 
	                    // closing this screen
	                    finish();
	                } else {
	                    // failed to create product
	                	Intent i = new Intent(getApplicationContext(), F1Activity.class);
	                    startActivity(i);
	                }
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }
	 
	            return null;
	        }
	 
	        /**
	         * After completing background task Dismiss the progress dialog
	         * **/
	        protected void onPostExecute(String file_url) {
	            // dismiss the dialog once done
	            pDialog.dismiss();
	        }
	 
	    }}
    

