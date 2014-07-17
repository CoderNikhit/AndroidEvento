package com.wglxy.example.dash1;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.annotation.SuppressLint;
import android.util.Log;

public class HttpVideoUploader{
 String Name;
@SuppressLint("SimpleDateFormat")
public int upLoad2Server(String sourceFileUri) throws MalformedURLException, IOException {
  String upLoadServerUri = Constants.SERVER_ADDRESS+"images/api.videoupload.php";
  String fileName = sourceFileUri;
  String employee = sourceFileUri;
  String delims = "[.]";
  String[] tokens = employee.split(delims);
  int i = tokens.length;
  String VideoName = tokens[i-1];
  long timestamp = (new java.util.Date()).getTime();
  String Name = "vid_"+timestamp+"."+VideoName;
  System.out.println("---------------"+Name);
  HttpURLConnection conn = null;
  DataOutputStream dos = null;
  String lineEnd = "\r\n";
  String twoHyphens = "--";
  String boundary = "*****";
  int bytesRead, bytesAvailable, bufferSize;
  byte[] buffer;
  int maxBufferSize = 1024 * 1024 * 1024;
  @SuppressWarnings("unused")
String responseFromServer = "";
  
  File sourceFile = new File(sourceFileUri);
  if (!sourceFile.isFile()) {
   Log.e("Huzza", "Source File Does not exist");
   return 0;
  }
  int serverResponseCode = 0;
  
  System.out.println(Name+"----------------------------------");
  F1Activity.Name = Name;
  
try { // open a URL connection to the Servlet
   FileInputStream fileInputStream = new FileInputStream(sourceFile);
   upLoadServerUri += ("?Name="+Name); 
   URL url = new URL(upLoadServerUri);
   conn = (HttpURLConnection) url.openConnection(); // Open a HTTP  connection to  the URL
   conn.setDoOutput(true); // Allow Outputs
   conn.setUseCaches(false); // Don't use a Cached Copy
   conn.setRequestMethod("POST");
   conn.setRequestProperty("Connection", "Keep-Alive");
   conn.setRequestProperty("ENCTYPE", "multipart/form-data");
   conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
   conn.setRequestProperty("uploaded_file", fileName);
   
   dos = new DataOutputStream(conn.getOutputStream());
   //System.out.println(fileName);
   dos.writeBytes(twoHyphens + boundary + lineEnd);
   dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""+ fileName + "\"" + lineEnd);
  
   dos.writeBytes(lineEnd); 
   bytesAvailable = fileInputStream.available(); // create a buffer of  maximum size
   Log.i("Huzza", "Initial .available : " + bytesAvailable);
   bufferSize = Math.min(bytesAvailable, maxBufferSize);
   buffer = new byte[bufferSize];
  
   // ho to gaya!!
   // office // nai hua tha :( :D jadu. 
   // read file and write it into form...
   bytesRead = fileInputStream.read(buffer, 0, bufferSize);
   
   while (bytesRead > 0) {
    dos.write(buffer, 0, bufferSize);
     bytesAvailable = fileInputStream.available();
     bufferSize = Math.min(bytesAvailable, maxBufferSize);
     bytesRead = fileInputStream.read(buffer, 0, bufferSize);
}//
   
   // send multipart form data necesssary after file data...
   dos.writeBytes(lineEnd);
   dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
   // Responses from the server (code and message)
   serverResponseCode = conn.getResponseCode();
   String serverResponseMessage = conn.getResponseMessage();
   
   Log.i("Upload file to server", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);
   // close streams
   Log.i("Upload file to server", fileName + " File is written");
   fileInputStream.close();
   dos.flush();
   dos.close();
  } catch (MalformedURLException ex) {
   ex.printStackTrace();
   Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
  } catch (Exception e) {
   e.printStackTrace();
  }
//this block will give the response of upload link 
// karna kya hai ?
// chala k dekh
  try {
   BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
   String line;
   while ((line = rd.readLine()) != null) {
    Log.i("Huzza", "RES Message: " + line);
   }
   rd.close();
  } catch (IOException ioex) {
   Log.e("Huzza", "error: " + ioex.getMessage(), ioex);
  }
 
  return serverResponseCode;  // like 200 (Ok)

 }} // end upLoad2Server