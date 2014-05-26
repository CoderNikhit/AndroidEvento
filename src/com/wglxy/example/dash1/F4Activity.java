package com.wglxy.example.dash1;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
 

public class F4Activity extends DashboardActivity 
{
	 private ProgressDialog pDialog;
	 
	    JSONParser jsonParser = new JSONParser();
	    EditText inputName;
	    EditText inputPrice;
	    EditText inputDesc;
	    
	    private static String url_create_product = "http://10.0.3.2/android_connect/create_product.php";
	    private static final String TAG_SUCCESS = "success";
	    
	    protected void onCreate(Bundle savedInstanceState) 
{
    super.onCreate(savedInstanceState);
    setContentView (R.layout.activity_f4);
    setTitleFromActivityLabel (R.id.title_text);
    inputName = (EditText) findViewById(R.id.inputName);
    inputPrice = (EditText) findViewById(R.id.inputPrice);
    inputDesc = (EditText) findViewById(R.id.inputDesc);
    
 // Create button
    Button btnCreateProduct = (Button) findViewById(R.id.btnCreateProduct);

    // button click event
    btnCreateProduct.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            // creating new product in background thread
            new CreateNewProduct().execute();
        }
    });
}
	  
	    class CreateNewProduct extends AsyncTask<String, String, String> {
	    	 
	        /**
	         * Before starting background thread Show Progress Dialog
	         * */
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(F4Activity.this);
	            pDialog.setMessage("Listing Videos..");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(true);
	            pDialog.show();
	        }
	 
	        /**
	         * Creating product
	         * */
	        protected String doInBackground(String... args) {
	            String name = inputName.getText().toString();
	            String price = inputPrice.getText().toString();
	            String description = inputDesc.getText().toString();
	 
	            // Building Parameters
	            List<NameValuePair> params = new ArrayList<NameValuePair>();
	            params.add(new BasicNameValuePair("name", name));
	            params.add(new BasicNameValuePair("price", price));
	            params.add(new BasicNameValuePair("description", description));
	 
	            // getting JSON Object
	            // Note that create product url accepts POST method
	            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
	                    "POST", params);
	 
	            // check log cat fro response
	            Log.d("Create Response", json.toString());
	 
	            // check for success tag
	            try {
	                int success = json.getInt(TAG_SUCCESS);
	 
	                if (success == 1) {
	                    // successfully created product
	                    Intent i = new Intent(getApplicationContext(), F2Activity.class);
	                    startActivity(i);
	 
	                    // closing this screen
	                    finish();
	                } else {
	                    // failed to create product
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
    

