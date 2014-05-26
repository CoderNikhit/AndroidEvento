package com.wglxy.example.dash1;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
//import android.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;


public class F1Activity extends DashboardActivity implements View.OnClickListener
{
	ImageButton ib;
	ImageButton b;
	ImageView iv;
	Intent i;
	final static int cameraData = 0;
/**
 * onCreate
 *
 * Called when the activity is first created. 
 * This is where you should do all of your normal static set up: create views, bind data to lists, etc. 
 * This method also provides you with a Bundle containing the activity's previously frozen state, if there was one.
 * 
 * Always followed by onStart().
 *
 * @param savedInstanceState Bundle
 */

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


public void onClick(View v) {
	// TODO Auto-generated method stub
	switch (v.getId()){
	
	case R.id.upload:
		//upload button
		ImageButton upload_btn = (ImageButton) this.findViewById(R.id.upload);
		upload_btn.setOnClickListener( new View.OnClickListener(){
		@Override
		public void onClick(View view) {
		// To open up a gallery browser
		  Intent intent = new Intent();
		  intent.setType("image/*");
		  intent.setAction(Intent.ACTION_GET_CONTENT);
		  startActivityForResult(Intent.createChooser(intent, "Select Picture"),1);
		}});
					
		
	break;
	
	case R.id.ibTakePic:
	i= new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
	startActivityForResult(i, cameraData);
	break;
}}


	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    		// TODO Auto-generated method stub
	    		super.onActivityResult(requestCode, resultCode, data);
	    		if (resultCode == RESULT_OK){
	    				
	    		        	Bundle extras = data.getExtras();
	    					Bitmap bmp = (Bitmap) extras.get("data");
	    					iv.setImageBitmap(bmp);       
	    		        }
	    		}}
	    		
	
	    		
	
	
	
	
	

 // end class
