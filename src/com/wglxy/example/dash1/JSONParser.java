package com.wglxy.example.dash1;
 
//import java.io.BufferedReader;
import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
 
public class JSONParser {
 
    static String is = "";
    static JSONObject jObj = null;
    static String json = "";
 
    // constructor
    public JSONParser() {
 
    }
 
    // function get json from url
    // by making HTTP POST or GET mehtod
    public static JSONObject makeHttpRequest(String url, String method,
            List<NameValuePair> params) {
 
        // Making HTTP request
        try {
        	// mobile se chal raha  hai ye ?
        	// same error.k <br> kaha hai ?output print kahan ho raha hai ?
            // check for request method
            if(method == "POST"){
                // request method is POST
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                System.out.println("---------------------------5.1------------------------------------");
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                System.out.println("---------------------------5.2------------------------------------");
                
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
				is = httpClient.execute(httpPost, responseHandler);
				Log.d("String result", is);
            }else if(method == "GET"){
                // request method is GET
                DefaultHttpClient httpClient = new DefaultHttpClient();
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);
 
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();

            }           
 
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
             // try parse the string to a JSON object
        try {
        	  System.out.println("---------------------------5.3------------------------------------");
              System.out.println(is);
            jObj = new JSONObject(is);  // yaha error h.
            System.out.println("---------------------------5.4------------------------------------");
            // ruk ruk
            // ek jagah ip change nai hua shayad
            // lemme check
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
 
        // return JSON String
        return jObj;
 
    }
}