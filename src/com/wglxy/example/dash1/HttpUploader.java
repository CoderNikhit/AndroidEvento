package com.wglxy.example.dash1;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList; 

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.util.Base64;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
    	 
    	 System.out.println("something "+imagePath);
         String outPut = null;
          
          	 System.out.println("entering for loop");
             Bitmap bitmapOrg = BitmapFactory.decodeFile(imagePath);
             ByteArrayOutputStream bao = new ByteArrayOutputStream();
              
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
              
             System.out.println("uploading image now ——" + ba1);
              
             ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
             nameValuePairs.add(new BasicNameValuePair("images", ba1));
              
             try {
                 HttpClient httpclient = new DefaultHttpClient();
                 HttpPost httppost = new HttpPost(Constants.SERVER_ADDRESS+"images/api.upload.php");
                 // fine now. fine na ? hmm chal gn :) bhai error :-o dekh le.. ab to smart ho hi gaya hai tu :P
                 // arre wo to pehle m change karke use kar raha tha kamine
                 // abhi bhula hu
                 // usske karan kuch nai tha
                 // ruk
                 // chalake dekh ek baar.. tu chala haan wohi bhai wohi.
                 httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                  System.out.println("-------1---------");
                 HttpResponse response = httpclient.execute(httppost);
                 System.out.println("-------2---------");
                 HttpEntity entity = response.getEntity();               

                 // print responce
                 outPut = EntityUtils.toString(entity);
                 Log.i("GET RESPONSE—-", outPut);
                  
                 //is = entity.getContent();
                 Log.e("log_tag ******", "good connection");
                  
                 bitmapOrg.recycle();
                  
             } catch (Exception e) {
                 Log.e("log_tag ******", "Error in http connection " + e.toString());
             }
     //    }
         return outPut;
     }}   
 


	
	