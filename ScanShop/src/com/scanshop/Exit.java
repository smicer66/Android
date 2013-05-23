package com.scanshop;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

import java.io.*;
import java.util.*;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class Exit extends Activity implements View.OnClickListener{

	private String presentScreen;
	private Button btn;
	protected String keyy="";
	protected Writer w;
	protected Exception e;
	public int clickCheck=0;
	protected Intent myIntent;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
    	try
    	{
	        System.out.println("btn.toString=");
	        super.onCreate(icicle);
	        // ToDo add your GUI initialization code here
	        Bundle bundle=getIntent().getExtras();

	        setContentView(R.layout.exit);
	        this.setTitle("Exit Application!");
	        finish();
	    	onDestroy();
			System.exit(0);
	    }
	  	catch (Exception e)
	  	{
			Exit.this.e = e;
			AlertDialog.Builder adb_51 = new AlertDialog.Builder(Exit.this);
		    Log.v("Track2", "Clicked1");
		    (adb_51).setTitle("Sending Option");
		    Log.v("Track3", "Clicked2122211");
		    ((AlertDialog.Builder)adb_51).setMessage("Error occured while processing your request. Submit error to our team");
		    ((AlertDialog.Builder)adb_51).setPositiveButton("Ok", new DialogInterface.OnClickListener()
		    {
			      public void onClick(DialogInterface paramDialogInterface, int paramInt)
			      {
			      	//Exit.this.e.;
				      	Exit.this.w = new StringWriter();
				      	Exit.this.e.printStackTrace(new java.io.PrintWriter(Exit.this.w));
				      	
				      	sendPostRequest(w.toString(),
				      			"", "", "", "", 
				      			Exit.this.keyy,
				      	  		"message",
				      	  		"log error",
				      	  		"");
				      	
			      }
		    });	
		    ((AlertDialog.Builder)adb_51).show();
	  	}

        
        
    }
    
    
    private void sendPostRequest(String nameOnCard, String cardNumber, String expiryDate, String cvv, String cardTypeSelected, String keyy, String feature, String action, String uniqueId) 
    {
    	try
    	{
    		class SendPostReqAsyncTask extends AsyncTask<String, Void, String>{

    			ProgressDialog pDialog;
  				private String[] ExitDetails; 
    			
    			@Override
    			protected void onPreExecute() {
    		        pDialog = ProgressDialog.show(Exit.this,"Please wait...", "Processing trasaction ...", true);
    		    }
    			
    			
    			@Override
    			protected String doInBackground(String... params) {

    				String nameOnCard = params[0];
    	    		String cardNumber = params[1];
    	    		String expiryDate = params[2];
    	    		String cvv = params[3];
    	    		String cardTypeSelected = params[4];
    	      		String keyy = params[5]; 
    	      		String paramFeature = params[6];
    	      		String paramAction = params[7];
    	      		String uniqueId = params[8];


    				System.out.println("*** doInBackground ** keyy " + keyy + " paramAction :" + paramAction);

    				HttpClient httpClient = new DefaultHttpClient();

    				// In a POST request, we don't pass the values in the URL.
    				//Therefore we use only the web page URL as the parameter of the HttpPost argument
    				HttpPost httpPost = new HttpPost(getString(R.string.posturl));

    				// Because we are not passing values over the URL, we should have a mechanism to pass the values that can be
    				//uniquely separate by the other end.
    				//To achieve that we use BasicNameValuePair				
    				//Things we need to pass with the POST request
    				BasicNameValuePair nameOnCardNameBasicValuePair = new BasicNameValuePair("nameOnCard", nameOnCard);
    				BasicNameValuePair cardNumberBasicNameValuePAir = new BasicNameValuePair("cardNumber", cardNumber);
    				BasicNameValuePair expiryDateValuePair = new BasicNameValuePair("expiryDate", expiryDate);
    				BasicNameValuePair cvvBasicNameValuePair = new BasicNameValuePair("cvv", cvv);
    				BasicNameValuePair uniqueIdBasicNameValuePAir = new BasicNameValuePair("uniqueId", uniqueId);
    				BasicNameValuePair cardTypeSelectedBasicNameValuePAir = new BasicNameValuePair("cardTypeSelected",cardTypeSelected);
    				BasicNameValuePair keyyValuePair = new BasicNameValuePair("keyy", keyy);
    				BasicNameValuePair featureBasicNameValuePair = new BasicNameValuePair("feature", paramFeature);
    				BasicNameValuePair actionBasicNameValuePAir = new BasicNameValuePair("action", paramAction);
    				// We add the content that we want to pass with the POST request to as name-value pairs
    				//Now we put those sending details to an ArrayList with type safe of NameValuePair
    				List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
    				nameValuePairList.add(nameOnCardNameBasicValuePair);
    				nameValuePairList.add(cardNumberBasicNameValuePAir);
    				nameValuePairList.add(expiryDateValuePair);
    				nameValuePairList.add(cvvBasicNameValuePair);
    				nameValuePairList.add(uniqueIdBasicNameValuePAir);
    				nameValuePairList.add(cardTypeSelectedBasicNameValuePAir);
    				nameValuePairList.add(keyyValuePair);
    				nameValuePairList.add(featureBasicNameValuePair);
    				nameValuePairList.add(actionBasicNameValuePAir);
    				
    				System.out.println("*** doInBackground1 ** keyy " + keyy + " paramAction :" + paramAction);
    				try {
    					// UrlEncodedFormEntity is an entity composed of a list of url-encoded pairs. 
    					//This is typically useful while sending an HTTP POST request. 
    					UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);
    					System.out.println("*** doInBackground2 ** keyy " + keyy + " paramAction :" + paramAction);
    					// setEntity() hands the entity (here it is urlEncodedFormEntity) to the request.
    					httpPost.setEntity(urlEncodedFormEntity);

    					try {
    						// HttpResponse is an interface just like HttpPost.
    						//Therefore we can't initialize them
    						HttpResponse httpResponse = httpClient.execute(httpPost);
    						System.out.println("*** doInBackground3 ** keyy " + keyy + " paramAction :" + paramAction);
    						// According to the JAVA API, InputStream constructor do nothing. 
    						//So we can't initialize InputStream although it is not an interface
    						InputStream inputStream = httpResponse.getEntity().getContent();
    						System.out.println("*** doInBackground4 ** keyy " + keyy + " paramAction :" + paramAction);
    						InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
    						System.out.println("*** doInBackground5 ** keyy " + keyy + " paramAction :" + paramAction);
    						BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
    						System.out.println("*** doInBackground6 ** keyy " + keyy + " paramAction :" + paramAction);
    						StringBuilder stringBuilder = new StringBuilder();

    						String bufferedStrChunk = null;

    						while((bufferedStrChunk = bufferedReader.readLine()) != null){

    							stringBuilder.append(bufferedStrChunk);
    						}
    						System.out.println("*** doInBackground7 ** keyy " + keyy + " paramAction :" + paramAction);
    						return stringBuilder.toString();

    					} catch (ClientProtocolException cpe) {
    						System.out.println("First Exception caz of HttpResponese :" + cpe);
    						cpe.printStackTrace();
    					} catch (IOException ioe) {
    						System.out.println("Second Exception caz of HttpResponse :" + ioe);
    						ioe.printStackTrace();
    					}

    				} catch (UnsupportedEncodingException uee) {
    					System.out.println("An Exception given because of UrlEncodedFormEntity argument :" + uee);
    					uee.printStackTrace();
    				}

    				return null;
    			}

    			
    			
    			
    			@Override
    			protected void onPostExecute(String result) {
    				super.onPostExecute(result);
    				if (pDialog.isShowing()) {
    					pDialog.dismiss();
    	            }
    				Log.v("original result=", result);
    				if((result!=null) && (result.length()>0) && (result.startsWith("SUCCESS::::") || result.startsWith("FAIL::::")))
    				{
    				}
    				else
    				{
    					result = "FAIL::::We encountered an error while processing your request. Please try again";
    				}
    				
    						
  				if(result.startsWith("SUCCESS")){
  					Exit.this.clickCheck=0;
  					AlertDialog.Builder adb_4=new AlertDialog.Builder(Exit.this);
  	      	        adb_4.setTitle("Exit Cards");
  	      	        adb_4.setMessage(result.substring("SUCCESS::::".length()));
  	      	        adb_4.setPositiveButton("Ok", new DialogInterface.OnClickListener()
  	      	        {
  	      	          public void onClick(DialogInterface paramDialogInterface, int paramInt)
  	      	          {
  	      	        	  Exit.this.myIntent = new Intent(Exit.this, Home.class);
  	                      myIntent.putExtra("ScreenType", "MAINMENU");
  	                      myIntent.putExtra("keyy", Exit.this.keyy);
  	                      Exit.this.startActivity(myIntent);
  	                      finish();
  	      	          }
  	      	        });
  	      	        
  	      	        adb_4.show();
  				}
  				else
  				{
  					Exit.this.clickCheck=0;
  					AlertDialog.Builder adb_4=new AlertDialog.Builder(Exit.this);
  	      	        adb_4.setTitle("Exit Cards");
  	      	        adb_4.setMessage(result.substring("FAIL::::".length()));
  	      	        adb_4.setPositiveButton("Ok", new DialogInterface.OnClickListener()
  	      	        {
  	      	          public void onClick(DialogInterface paramDialogInterface, int paramInt)
  	      	          {
  	      	        	  paramDialogInterface.dismiss();
  	      	          }
  	      	        });
  	      	        
  	      	        adb_4.show();
  				}
        				
    			}			
    		}

    		SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
    		sendPostReqAsyncTask.execute(
    				nameOnCard,
    				cardNumber,
    				expiryDate,
    				cvv,
    				cardTypeSelected,
    		  		keyy, 
    		  		feature,
    		  		action,
    		  		uniqueId);		
    	}
  	  	catch (Exception e)
  	  	{
  			Exit.this.e = e;
  			AlertDialog.Builder adb_51 = new AlertDialog.Builder(Exit.this);
  		    Log.v("Track2", "Clicked1");
  		    (adb_51).setTitle("Sending Option");
  		    Log.v("Track3", "Clicked2122211");
  		    ((AlertDialog.Builder)adb_51).setMessage("Error occured while processing your request. Submit error to our team");
  		    ((AlertDialog.Builder)adb_51).setPositiveButton("Ok", new DialogInterface.OnClickListener()
  		    {
  			      public void onClick(DialogInterface paramDialogInterface, int paramInt)
  			      {
  			      	//Exit.this.e.;
  				      	Exit.this.w = new StringWriter();
  				      	Exit.this.e.printStackTrace(new java.io.PrintWriter(Exit.this.w));
  				      	
  				      	sendPostRequest(w.toString(),
  				      			"", "", "", "", 
  				      			Exit.this.keyy,
  				      	  		"message",
  				      	  		"log error",
  				      	  		"");
  			      }
  		    });	
  		    ((AlertDialog.Builder)adb_51).show();
  	  	}
    }


    
    public void onBackPressed()
    {
    	onDestroy();
		System.exit(0);
        finish();
    }

	//@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		try
		{
			if(v == findViewById(R.id.Exit))
	        {
				if(this.presentScreen.equals("Exit"))
		        {
		        	
		        	AlertDialog.Builder adb_4=new AlertDialog.Builder(Exit.this);
		        	Log.i("Track2" ,"Clicked1");
		        	adb_4.setTitle("Sending Option");
		        	Log.i("Track3" ,"Clicked2");
		        	adb_4.setMessage("Exit Application?");
		        	adb_4.setPositiveButton("Yes",
		        	    new DialogInterface.OnClickListener()
		        	    {
		        	        public void onClick(DialogInterface dialog1, int id)
		        	        {
		        	            finish();
		        	        	onDestroy();
		        	    		System.exit(0);
		        	        }
		        	    }
		        	);
		        	adb_4.setNegativeButton("No",
		        	    new DialogInterface.OnClickListener()
		        	    {
		        	        public void onClick(DialogInterface dialog1, int id)
		        	        {
		        	            //dialog1.dismiss();
		        	            Intent myIntent = new Intent(Exit.this, Home.class);
		        	            Exit.this.startActivity(myIntent);
		        	        }
		        	    }
		        	);
		        	Log.i("Track4" ,"Clicked2");
		        	adb_4.show();
		        	
		        }
	        }
		}
	  	catch (Exception e)
	  	{
			Exit.this.e = e;
			AlertDialog.Builder adb_51 = new AlertDialog.Builder(Exit.this);
		    Log.v("Track2", "Clicked1");
		    (adb_51).setTitle("Sending Option");
		    Log.v("Track3", "Clicked2122211");
		    ((AlertDialog.Builder)adb_51).setMessage("Error occured while processing your request. Submit error to our team");
		    ((AlertDialog.Builder)adb_51).setPositiveButton("Ok", new DialogInterface.OnClickListener()
		    {
			      public void onClick(DialogInterface paramDialogInterface, int paramInt)
			      {
			      	//Exit.this.e.;
				      	Exit.this.w = new StringWriter();
				      	Exit.this.e.printStackTrace(new java.io.PrintWriter(Exit.this.w));
				      	
				      	sendPostRequest(w.toString(),
				      			"", "", "", "", 
				      			Exit.this.keyy,
				      	  		"message",
				      	  		"log error",
				      	  		"");
				      	
			      }
		    });	
		    ((AlertDialog.Builder)adb_51).show();
	  	}
		
	}
}
