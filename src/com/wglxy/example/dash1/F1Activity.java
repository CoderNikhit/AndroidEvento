package com.wglxy.example.dash1;


import java.util.concurrent.ExecutionException;
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
	
	public static void main(String[] args) {
		HttpUploader uploader = new HttpUploader();
		F1Activity C= new F1Activity();
		try {
		  @SuppressWarnings("unused")
		Object image_name = uploader.execute(C.getRealPathFromURI(currImageURI)).get();       
		} catch (InterruptedException e) {
		  e.printStackTrace();
		} catch (ExecutionException e) {
		  e.printStackTrace();
		}


	}}
	    		
	
	    		
	
	
	
	
	

 // end class
