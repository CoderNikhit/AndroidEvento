package com.wglxy.example.dash1;

import java.io.IOException;
import java.net.MalformedURLException;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.View;


@SuppressLint("NewApi")
public class F1Activity extends DashboardActivity implements View.OnClickListener
{
	static Uri currImageURI;
	static Uri currVideoURI;
	ImageButton ib;
	ImageButton b;
	ImageView iv;
	Intent i;
	ImageView j;
	final static int cameraData = 102;
	final static int videoData = 101;
	static String Name;


protected void onCreate(Bundle savedInstanceState) 
{
    super.onCreate(savedInstanceState);
    setContentView (R.layout.activity_f1);
    setTitleFromActivityLabel (R.id.title_text);
    initialize();
}

private void initialize() {
	// TODO Auto-generated method stub
	ib = (ImageButton)  findViewById(R.id.ibTakePic);
	b = (ImageButton)  findViewById(R.id.upload);
	b.setOnClickListener(this);
	ib.setOnClickListener(this);
	}


@SuppressLint("InlinedApi")
public void onClick(View v) {
	// TODO Auto-generated method stub
	switch (v.getId()){

	case R.id.upload:
		//upload button
		CharSequence colors[] = new CharSequence[] {"Image", "Video"};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Pick One");
		builder.setItems(colors, new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {

		    	switch (which) {
		        case 0:
		        	Intent intent = new Intent();
		  		    intent.setType("image/*");
		  		    intent.setAction(Intent.ACTION_GET_CONTENT);
		  		    startActivityForResult(Intent.createChooser(intent, "Select Image"),0);
		          break;
		        case 1:
		        	Intent intent1 = new Intent();
		  		    intent1.setType("video/*");
		  		    intent1.setAction(Intent.ACTION_GET_CONTENT);
		  		    startActivityForResult(Intent.createChooser(intent1, "Select Video"),1);
		  		  break;
		        default:
		          break;
		        }
		    }
		});
		builder.show();
		break;

	case R.id.ibTakePic:
		CharSequence colors1[] = new CharSequence[] {"Image", "Video"};
		AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
		builder1.setTitle("Pick One");
		builder1.setItems(colors1, new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		    	switch (which) {
		        case 0:
		        	i= new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		        	startActivityForResult(i, cameraData);

		          break;
		        case 1:
		        	i= new Intent(android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
		        	startActivityForResult(i, videoData);
		  		  break;
		        default:
		          break;
		        }
		    }
		});
		builder1.show();
		break;
}}


	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    		// TODO Auto-generated method stub
	    		super.onActivityResult(requestCode, resultCode, data);
	    		if (requestCode == 0) {
	    			if (resultCode == RESULT_OK) {
	    				Uri currImageURI = data.getData();
	    				// yahan error aa rahi hai!
	    				// wo to mujhe b pata tha
	    				// baat ye h ki f1 maiine naya dala tha
	    				// baaki sab wohi h.
	    				// error php k karan b ho sakti h
	    				// jab bada file upload kar rahe the uss din jis din ho gaya
	    				// same error aa rahi tho
	    		// php m maine change kiya
	    				// but thk nai hua.
	    				// aur yaha error nai ho sakta
	    				// ye code sadiyo se aisa hi h
	    				// issme hmne kabhi change nai kiya. yaha kuch khas h b nai.ok
			            System.out.println("Current graphic Path is ----->" +  getRealPathFromURI(currImageURI));
			            main1(getRealPathFromURI(currImageURI)); 
	    			    Toast.makeText(this, "image upload successful to:\n" + data.getData(), Toast.LENGTH_LONG).show();
	    			    Intent myIntent = new Intent(F1Activity.this, F2Activity.class);
	    		    	F1Activity.this.startActivity(myIntent);

	    			} else if (resultCode == RESULT_CANCELED) {
	    			    	Toast.makeText(this, "upload cancelled.", Toast.LENGTH_LONG).show();
	    			  } else {
	    			     Toast.makeText(this, "Failed to upload image", Toast.LENGTH_LONG).show();
	    		      }		


			          	} 
	    		if (requestCode == 1) {
	    			if (resultCode == RESULT_OK) {
	    			    Uri currVideoURI = data.getData();
			            System.out.println("Current graphic Path is ----->" +  getRealPathFromURI1(currVideoURI));
			            System.out.println(getRealPathFromURI1(currVideoURI));
			            HttpVideoUploader uploader = new HttpVideoUploader();
			           
							try {
								int response= uploader.upLoad2Server(""+ getRealPathFromURI1(currVideoURI));
							} catch (MalformedURLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
			    		Toast.makeText(this, "video upload successful to:\n" + data.getData(), Toast.LENGTH_LONG).show();
			    		Intent myIntent = new Intent(F1Activity.this, F2Activity.class);
				    	F1Activity.this.startActivity(myIntent);
	    			}
	    			else if (resultCode == RESULT_CANCELED) {
	    			    	Toast.makeText(this, "upload cancelled.", Toast.LENGTH_LONG).show();
	    			    } 
	    			else {
	    			     Toast.makeText(this, "Failed to upload video", Toast.LENGTH_LONG).show();
	    		      }}
	    		if (requestCode == 101){
	    			if (resultCode == RESULT_OK) {
	    			     Toast.makeText(this, "Video saved to:\n" + data.getData(), Toast.LENGTH_LONG).show();
	    			  } else if (resultCode == RESULT_CANCELED) {
	    			    	Toast.makeText(this, "Video recording cancelled.", Toast.LENGTH_LONG).show();
	    			  } else {
	    			     Toast.makeText(this, "Failed to record video", Toast.LENGTH_LONG).show();
	    		      }		
	    			  }
	    		if (requestCode == 102){
	    			if (resultCode == RESULT_OK) {
	    			     Toast.makeText(this, "Image saved to:\n" + data.getData(), Toast.LENGTH_LONG).show();
	    			  } else if (resultCode == RESULT_CANCELED) {
	    			    	Toast.makeText(this, "Image capture cancelled.", Toast.LENGTH_LONG).show();
	    			  } else {
	    			     Toast.makeText(this, "Failed to save Image", Toast.LENGTH_LONG).show();
	    		      }}}


	//Convert the image URI to the direct file system path of the image file
	public String getRealPathFromURI(Uri contentUri) {
		String res = null;
			System.out.println("........................image case1........................");
			String[] proj = { MediaStore.Images.Media.DATA } ;
			Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
			if(cursor.moveToFirst()){;
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			System.out.println("........................image case2........................");
			res = cursor.getString(column_index);
			}
			cursor.close();
			return res;
			}

	public String getRealPathFromURI1(Uri contentUri) {
		String res = null;
			System.out.println("........................video case........................");
			String[] proj = { MediaStore.Video.Media.DATA } ;
			Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
			if(cursor.moveToFirst()){;
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
			res = cursor.getString(column_index);
			}
			cursor.close();
			return res;
			}



	public void main1(String s1){
		HttpUploader uploader = new HttpUploader(s1);			
		System.out.println("-------3---------");
		  @SuppressWarnings("unused")
		Object image_name = uploader.execute();
		  System.out.println("-------4---------");
	}}