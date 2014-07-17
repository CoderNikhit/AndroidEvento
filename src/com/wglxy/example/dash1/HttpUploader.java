package com.wglxy.example.dash1;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList; 
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.util.Base64;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
//Uploader class
@SuppressLint("NewApi")
@SuppressWarnings("unused")
public class HttpUploader extends AsyncTask<String, Void, String> {
	
	String imagePath;
	public HttpUploader(String path){
		imagePath = path;
	}
	
     protected String doInBackground(String... path) {
    	 
    	 System.out.println(imagePath);
         String outPut = null;
          
          	 System.out.println("entering for loop");
             Bitmap bitmapOrg = BitmapFactory.decodeFile(imagePath);
             System.out.println(imagePath);
             String delims = "[/]";
             String[] tokens = imagePath.split(delims);
             int i = tokens.length;
             String ImageName = tokens[i-1];
             long timestamp = (new java.util.Date()).getTime();
             String Name = "img_"+timestamp+".jpg";
             System.out.println("---------------"+Name);
             ByteArrayOutputStream bao = new ByteArrayOutputStream();
             F1Activity.Name = Name;
             
             
             //Resize the image
             double width = bitmapOrg.getWidth();
             double height = bitmapOrg.getHeight();
             double ratio = 400/width;
             int newheight = (int)(ratio*height);
              
             System.out.println("———-width" + width);
             System.out.println("———-height" + height);
              
             bitmapOrg = Bitmap.createScaledBitmap(bitmapOrg, 400, newheight, true);
              
             //Here you can define .PNG as well
             bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 95, bao);
             byte[] ba = bao.toByteArray();
             String ba1 = Base64.encodeToString(ba, 0);
              
                         
             ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
             nameValuePairs.add(new BasicNameValuePair("image", ba1));
              
             try {
            	 DefaultHttpClient httpClient = new DefaultHttpClient();
                 HttpPost httpPost = new HttpPost(Constants.SERVER_ADDRESS+"images/api.upload.php?Name="+Name);
                 System.out.println("---------------------------5.1------------------------------------");
               httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
               
                 System.out.println("---------------------------5.2------------------------------------");
                 
                 ResponseHandler<String> responseHandler = new BasicResponseHandler(); 
				 String  outPut1= httpClient.execute(httpPost, responseHandler);
 				Log.d("String result", outPut1);                  bitmapOrg.recycle();
                  
             } catch (Exception e) {
                 Log.e("log_tag ******", "Error in http connection " + e.toString());
             }
             
             
             
            
     //    }
         return outPut;
     }
     

     







}   
 


	
	