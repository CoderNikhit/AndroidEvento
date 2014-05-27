package com.wglxy.example.dash1;

import java.util.concurrent.ExecutionException;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
//import android.View.OnClickListener;
//import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
//import android.widget.TextView;
import android.view.View;


public class F1Activity extends DashboardActivity implements View.OnClickListener
{
	static Uri currImageURI;
	ImageButton ib;
	ImageButton b;
	ImageView iv;
	Intent i;
	final static int cameraData = 0;


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
	    		if (requestCode == 1) {
			        // currImageURI is the global variable I’m using to hold the content:
			            Uri currImageURI = data.getData();
			            System.out.println("Current image Path is ----->" +  getRealPathFromURI(currImageURI));
			          //  TextView tv_path = (TextView) findViewById(R.id.path);
			           // tv_path.setText(getRealPathFromURI(currImageURI));
			            // are you looking at this?
			           //no.... this main1 will be called....and which indeed calls the httpuploader
			            // so how coe this problem?
			            // as I am sure that the highlighted two lines were able to print out the path in string form.
			            // lets loook at one diff example to show you.
			            // 2 min
			            // did you see? the upload called getrealpathfromuri in tha
			          
			            main1(getRealPathFromURI(currImageURI));
			            System.out.println("working..");
			        } else{
			        	Bundle extras = data.getExtras();
						Bitmap bmp = (Bitmap) extras.get("data");
						iv.setImageBitmap(bmp);       
			        }
			        }
	

	//Convert the image URI to the direct file system path of the image file
	public String getRealPathFromURI(Uri contentUri) {
		String res = null;
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
		if(cursor.moveToFirst()){;
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		res = cursor.getString(column_index);
		}
		cursor.close();
		return res;
		}
	
	public void main1(String s1){
//		System.out.println("Main1 entering.."+currImageURI);
//		String s1 = getRealPathFromURI(currImageURI);
//		System.out.println("Path: "+s1);
		HttpUploader uploader = new HttpUploader(s1);
		// there ? hmm
		//now entering in doInBackground method. ab aage dekh le...maybe upload ho gaya ho
//		try {
			// even this is is not called ? Just a sec... main method in android application ? u there ?
			System.out.println("-------3---------");
		  @SuppressWarnings("unused")
		Object image_name = uploader.execute();// this is not working as we are expecting...so here is the bug...ise call karne pe doInBackground ke ander ka code execute hona chahiye... jo nahi ho raha ....ye dekh le...mujhe bhi idea nahi hai ki y this is happening...but maybe google can help.
		  // but tune to changes kiye hain usse status quo change hua h na?
		  // error aa raha tha abhi kuch?. haan. wohi to bol raha hun.. ki this code is never entering the httploader... uski wajah se null pointer expection aa raha h.
		  // uploader.execute m ye execute kya h? define to nai kiya kahi??
		  // wo aysnc task me hai.. and this is the subclass... so... btw.. execute should execute 
		  // but this is returning an object. So are we using this object?? Not yet.. but maybe in future.
		  //sun...yeh jo argument paas kar rahen hain ham log execute me... iska aur httploader wali class ke jo arguments hain.. unme kuch panga hai shayad...
		  // bhai one thing you know ki getrealpathfromuri is returning because it is returning the path in string form which we could read.
		  //So uploader.execute is definitely getting a string
		  //can we print out the string? wohi to kar raha tha us class me... 
		  
		  System.out.println("-------4---------");
/*		} catch (InterruptedException e) {
		  e.printStackTrace();
		} catch (ExecutionException e) {
		  e.printStackTrace();
		}
*/

	}}
	    		
	
	    	// hope u got some hint ?.. Ur uploader is not called at all. So ?
//so??

	
	
	
	

 // end class
