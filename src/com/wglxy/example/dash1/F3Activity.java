package com.wglxy.example.dash1;

import java.io.File;
import android.view.View;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class F3Activity extends DashboardActivity implements View.OnClickListener
{
	ImageButton ib;
	ImageButton b;
	Intent i;
	private static final int VIDEO_CAPTURE = 101;
	static Uri currVideoURI;

	private boolean hasCamera() {
	    if (getPackageManager().hasSystemFeature(
                       PackageManager.FEATURE_CAMERA_ANY)){
	        return true;
	    } else {
	        return false;
	    }
	}


protected void onCreate(Bundle savedInstanceState) 
{
    super.onCreate(savedInstanceState);
    setContentView (R.layout.activity_f3);
    setTitleFromActivityLabel (R.id.title_text);
    if (!hasCamera())
		ib.setEnabled(false);
		initialize();
}

private void initialize() {
	// TODO Auto-generated method stub
	ib = (ImageButton)  findViewById(R.id.ibTakePic);
	b = (ImageButton)  findViewById(R.id.upload);
	b.setOnClickListener(this);
    ib.setOnClickListener(this);
}


@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	switch (v.getId()){	
	case R.id.upload:
		ImageButton upload_btn = (ImageButton) this.findViewById(R.id.upload);
		upload_btn.setOnClickListener( new View.OnClickListener(){
		@Override
		public void onClick(View view) {
		// To open up a gallery browser
		  Intent intent = new Intent();
		  intent.setType("video/*");
		  intent.setAction(Intent.ACTION_GET_CONTENT);
		  startActivityForResult(Intent.createChooser(intent, "Select Video"),1);
		}});
		
		
	break;
	
	case R.id.ibTakePic:
	File mediaFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() 
			       + "/myvideo.mp4");
	i= new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
	Uri videoUri = Uri.fromFile(mediaFile);
	i.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
	i.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 500000);
	startActivityForResult(i, VIDEO_CAPTURE);
	break;
}}

protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1 && resultCode == RESULT_OK) {
    	
    	currVideoURI = data.getData();
        System.out.println("Current video Path is ----->" +  getRealPathFromURI(currVideoURI));
       // TextView tv_path = (TextView) findViewById(R.id.path);
        //tv_path.setText(getRealPathFromURI(currVideoURI));
       
    }}
public String getRealPathFromURI(Uri contentUri) {
	String res = null;
	String[] proj = { MediaStore.Video.Media.DATA };
	Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
	if(cursor.moveToFirst()){;
	int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
	res = cursor.getString(column_index);
	}
	cursor.close();
	return res;
	}
	} // end class
