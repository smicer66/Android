package com.scanshop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class Synchronize extends Activity implements OnClickListener {
    private CheckBox iAgree;
	private Button synchronizeButton;
	private boolean iAgreeValue;
	private db d;
	private SQLiteDatabase db;
	private Context context;
	private int clickCheck=0;
	private String presentFeatureCode;
	private String keyy;
	private Intent myIntent;
	private boolean backPressedProceed=true;
	
	public void onBackPressed() {
		if(backPressedProceed==true)
		{
			Bundle b = new Bundle();
			  b.putString("keyy", this.keyy);
			  Intent intent = new Intent(Synchronize.this, Home.class);
			  intent.putExtras(b);
		      startActivity(intent);
		      finish();
		}
    }
	
	
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
        	onBackPressed();
            return true;
        }
        else
        {
        	return super.onKeyDown(keyCode, event);
        }
    }
	
	
	
	//featureId=application&action=initialize
	private void sendPostRequest(String appId, String feature,
	  		String action, String key) {

			class SendPostReqAsyncTask extends AsyncTask<String, Void, String>{

				ProgressDialog pDialog; 
				
				@Override
				protected void onPreExecute() {
			        pDialog = ProgressDialog.show(Synchronize.this,"Please wait...", "Setting Up Application ...", true);
			    }
				
				
				@Override
				protected String doInBackground(String... params) {

					String paramAppId = params[0];
					String paramFeature = params[1];
					String paramAction = params[2];
					String paramKey = params[3];

					System.out.println("*** doInBackground ** paramAppId " + paramAppId);

					HttpClient httpClient = new DefaultHttpClient();

					// In a POST request, we don't pass the values in the URL.
					//Therefore we use only the web page URL as the parameter of the HttpPost argument
					HttpPost httpPost = new HttpPost(getString(R.string.posturl));
					
					System.out.println("URL TO CONNECT TO: " + getString(R.string.posturl));

					// Because we are not passing values over the URL, we should have a mechanism to pass the values that can be
					//uniquely separate by the other end.
					//To achieve that we use BasicNameValuePair				
					//Things we need to pass with the POST request
					BasicNameValuePair appIdBasicNameValuePair = new BasicNameValuePair("appId", paramAppId);
					BasicNameValuePair featureBasicNameValuePair = new BasicNameValuePair("feature", paramFeature);
					BasicNameValuePair actionBasicNameValuePair = new BasicNameValuePair("action", paramAction);
					BasicNameValuePair keyBasicNameValuePair = new BasicNameValuePair("keyy", paramKey);
					// We add the content that we want to pass with the POST request to as name-value pairs
					//Now we put those sending details to an ArrayList with type safe of NameValuePair
					List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
					nameValuePairList.add(appIdBasicNameValuePair);
					nameValuePairList.add(featureBasicNameValuePair);
					nameValuePairList.add(actionBasicNameValuePair);
					nameValuePairList.add(keyBasicNameValuePair);
					
					System.out.println("*** doInBackground1 ** paramAppId " + paramAppId);
					try {
						// UrlEncodedFormEntity is an entity composed of a list of url-encoded pairs. 
						//This is typically useful while sending an HTTP POST request. 
						UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);
						System.out.println("*** doInBackground2 ** paramAppId " + paramAppId);
						// setEntity() hands the entity (here it is urlEncodedFormEntity) to the request.
						httpPost.setEntity(urlEncodedFormEntity);

						try {
							// HttpResponse is an interface just like HttpPost.
							//Therefore we can't initialize them
							HttpResponse httpResponse = httpClient.execute(httpPost);
							System.out.println("*** doInBackground3 ** paramAppId " + paramAppId);
							// According to the JAVA API, InputStream constructor do nothing. 
							//So we can't initialize InputStream although it is not an interface
							InputStream inputStream = httpResponse.getEntity().getContent();
							System.out.println("*** doInBackground4 ** paramAppId " + paramAppId);
							InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
							System.out.println("*** doInBackground5 ** paramAppId " + paramAppId);
							BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
							System.out.println("*** doInBackground6 ** paramAppId " + paramAppId);
							StringBuilder stringBuilder = new StringBuilder();

							String bufferedStrChunk = null;

							while((bufferedStrChunk = bufferedReader.readLine()) != null){

								stringBuilder.append(bufferedStrChunk);
							}
							System.out.println("*** doInBackground7 ** paramAppId " + paramAppId);
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
					Synchronize.this.clickCheck=0;

					Log.v("original result=", result);

					if((result!=null) && (result.length()>0) && (result.startsWith("SUCCESS::::") || result.startsWith("FAIL::::")))
					{
					}
					else
					{
						result = "FAIL::::We encountered an error while processing your request. Please try again";
					}
					if(result.startsWith("SUCCESS")){
						
						
						  result=result.substring("SUCCESS::::".length());
						  String[] superString = result.split("########");
						  String[] as4;//shop items
						  boolean proceedNow=false;
		        		  
		        		  as4 = superString[0].split(":::");
		        		  System.out.println("as4 size=" + as4.length);
		        		  
		        		  
		        		  if ((as4.length % 7)!=0)
		        		  {
		        			  Log.i("DB CLOSED36 = ", "YES7");
		        			  proceedNow= false;
		        		  }
		        		  
		        		  
		        		  else
		        		  {
		        			  String tableName = getString(R.string.shopItems);
					          String[] fields = new String[8];
					          fields[0] = "_id";
					          fields[1] = "qr_code";
					          fields[2] = "shopItemName";
					          fields[3] = "amount";
					          fields[4] = "shortDescription";
					          fields[5] = "uniqueId";
					          fields[6] = "shopItemType";
					          fields[7] = "soldYes";
					          

					          
					          Log.i("NEXT TABLE 1", "YES");
					          String[] entries = new String[8];
					          int counter=0;
					          
					          d=new db(Synchronize.this);
					          Cursor c=d.openHelper.fetchRowType2(getString(R.string.shopItems), new String[]{"_id"}, null, null, null, null, null, null);
					    	  if(c.getCount()>0)
					    	  {
					    		  c.moveToFirst();
					    		  Synchronize.this.d.openHelper.deleteRow(c.getInt(0), getString(R.string.shopItems), "_id");
					    		  
					    		  while(c.moveToNext())
					    		  {
					    			  Synchronize.this.d.openHelper.deleteRow(c.getInt(0), getString(R.string.shopItems), "_id");
					    		  }
					    		  
					    	  }
					    	  c.close();
					          
					          for(int count1=0;count1<(as4.length/7);count1++)
					          {
					        	  for(int count=0;count<7;count++)
					        	  {
					        		  entries[count]=as4[counter];
					        		  Log.i("entries[" + count + "] = ", as4[counter++]);
					        	  }
					        	  entries[7]="0";
					        	  Log.i("test me?", "No o");
					        	  d.openHelper.createRow(tableName, fields, entries);
					        	  Log.i("d.openHelper.insertId", ((Long)(d.openHelper.insertId)).toString());
					          }
					          
					          Log.i("NEXT TABLE 4", "YES");
					          
					          c=d.openHelper.fetchRowType2(getString(R.string.shopItemsHistory), new String[]{"_id"}, null, null, null, null, null, null);
					    	  if(c.getCount()>0)
					    	  {
					    		  c.moveToFirst();
					    		  Synchronize.this.d.openHelper.deleteRow(c.getInt(0), getString(R.string.shopItemsHistory), "_id");
					    		  
					    		  while(c.moveToNext())
					    		  {
					    			  Synchronize.this.d.openHelper.deleteRow(c.getInt(0), getString(R.string.shopItemsHistory), "_id");
					    		  }
					    		  
					    	  }
					    	  c.close();
					          
					          
					          Log.i("NEXT TABLE 5", "YES5");
					          d.openHelper.close();
					          Log.i("DB CLOSED38 = ", "YES6");
					          d.close();

					          proceedNow= true;
					          backPressedProceed=false;
	                	      AlertDialog.Builder adb_51 = new AlertDialog.Builder(Synchronize.this);
				                Log.v("Track2", "Clicked1");
				                (adb_51).setTitle("Synchronization");
				                Log.v("Track3", "Clicked2122211");
				                ((AlertDialog.Builder)adb_51).setMessage("Synchronization Finished!");
				                ((AlertDialog.Builder)adb_51).setPositiveButton("Ok", new DialogInterface.OnClickListener()
				                {
				                  public void onClick(DialogInterface paramDialogInterface, int paramInt)
				                  {
				                	  clickCheck=0;

			                    	  Bundle b = new Bundle();
			                		  b.putString("keyy", Synchronize.this.keyy);
			                		  Intent intent = new Intent(Synchronize.this, Home.class);
			                		  intent.putExtras(b);
			                	      startActivity(intent);
			                	      Synchronize.this.finish();
				                     
				                  }
				                });	
				    	        ((AlertDialog.Builder)adb_51).show();
				                     
				                 
				          }
				          
				          if(proceedNow==false)
				          {
				        	  	//Toast.makeText(getApplicationContext(), "HTTP POST is working...", Toast.LENGTH_LONG).show();
				        	  backPressedProceed=false;
				        	  	//Synchronize.this.deleteDatabase(getString(R.string.CurrentDBName));
				        	  	AlertDialog.Builder adb_51 = new AlertDialog.Builder(Synchronize.this);
				                Log.v("Track2", "Clicked1");
				                (adb_51).setTitle("Sending Option");
				                Log.v("Track3", "Clicked2122211");
				                ((AlertDialog.Builder)adb_51).setMessage("Synchronizing Failed. Try Again");
				                ((AlertDialog.Builder)adb_51).setPositiveButton("Ok", new DialogInterface.OnClickListener()
				                {
				                  public void onClick(DialogInterface paramDialogInterface, int paramInt)
				                  {
				                	  clickCheck=0;
				                	  Enumeration localEnumeration;
				                	  
				                      
			                    	  Log.v("test", "8");
			                    	  Synchronize.this.d.openHelper.close();
			                    	  Synchronize.this.d.close();
			                    	  finish();
			                    	  Bundle b = new Bundle();
			                		  b.putString("keyy", Synchronize.this.keyy);
			                		  Intent intent = new Intent(Synchronize.this, Home.class);
			                		  intent.putExtras(b);
			                	      startActivity(intent);
			                	      Synchronize.this.finish();
				                     
				                  }
				                });	
				    	        ((AlertDialog.Builder)adb_51).show();
				          }
				          
						
					}else{
						Toast.makeText(getApplicationContext(), "Processing Errors occured.", Toast.LENGTH_LONG).show();
						
							backPressedProceed=false;
							Synchronize.this.deleteDatabase(getString(R.string.CurrentDBName));
	                    	Log.v("test", "9");
	                    	//context.deleteDatabase(getString(R.string.CurrentDBName));
	                    	AlertDialog.Builder adb_51 = new AlertDialog.Builder(Synchronize.this);
		  	                Log.v("Track2", "Clicked1");
		  	                (adb_51).setTitle("Sending Option");
		  	                Log.v("Track3", "Clicked2122211");
		  	                ((AlertDialog.Builder)adb_51).setMessage(result.substring("FAIL::::".length()));
		  	                ((AlertDialog.Builder)adb_51).setPositiveButton("Ok", new DialogInterface.OnClickListener()
		  	                {
		  	                  public void onClick(DialogInterface paramDialogInterface, int paramInt)
		  	                  {
		  	                	  clickCheck=0;
		  	                	  paramDialogInterface.dismiss();
		  	                  }
		  	                });	
		  	    	        ((AlertDialog.Builder)adb_51).show();
	                    
					}
				}			
			}

			SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
			sendPostReqAsyncTask.execute(appId, feature, action, key);		
		}
	  
	  
	  	

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	    setContentView(R.layout.synchronize);
        getIntent().getExtras();
        
		
        Bundle bundle=getIntent().getExtras();
	      
		  
	      //Log.v("bundle.size=", "" + bundle.size());
	      if(bundle!=null)
	      {
		        if(bundle.containsKey("keyy"))
		        {
		        	this.keyy=bundle.getString("keyy");
		        	Log.v("|||||||||||||||keyy when entering home =", "" + this.keyy);
		        }
		        else
		        {
		        	Synchronize.this.myIntent = new Intent(Synchronize.this, SignIn.class);
		        	myIntent.putExtra("noKeyReDirect", 1);
		        	myIntent.putExtra("keyy", Synchronize.this.keyy);
		        	Synchronize.this.startActivity(myIntent);
		        	finish();
		        }
		        
		        
		        if(bundle.containsKey("ScreenType"))
		        {
		        	this.presentFeatureCode=bundle.getString("ScreenType");
		        	Log.v("presentFeatureCode when entering AddDrug =", "" + this.presentFeatureCode);
		        }
		        
		        
		        
	      }
	      else
	      {
		      	Intent myIntent = new Intent(Synchronize.this, SignIn.class);
		      	Synchronize.this.startActivity((Intent)myIntent);
		      	finish();
	      }
	      
	      

	        this.synchronizeButton = (Button)findViewById(R.id.synchronizeButton);
	        this.synchronizeButton.setOnClickListener(this);
        
    }

	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		try
	    {
		      if (arg0 == findViewById(R.id.synchronizeButton) && (this.clickCheck==0))
		      {
		    	  this.clickCheck=1;
		    	  this.d=new db(this);
		    	  
		    	  String shopItemHistoryList="";
		    	  
		    	  Cursor c=d.openHelper.fetchRowType2(getString(R.string.shopItemsHistory), new String[]{"shopItemId", "timeLogged", "uniqueId", "amountSoldFor"}, null, null, null, null, null, null);
		    	  if(c.getCount()>0)
		    	  {
		    		  c.moveToFirst();
		    		  shopItemHistoryList=shopItemHistoryList + c.getInt(0) + "######" + c.getString(1) + "######" + c.getString(2) + "######" + c.getString(3) + "######";
		    		  
		    		  while(c.moveToNext())
		    		  {
		    			  
		    			  shopItemHistoryList=shopItemHistoryList + c.getInt(0) + "######" + c.getString(1) + "######" + c.getString(2) + "######" + c.getString(3) + "######";
		    		  }
		    		  
		    	  }
		    	  
                  System.out.println("asdasdss==" + shopItemHistoryList);
                  c.close();
                  d.close();
                  this.clickCheck=0;
		    	  sendPostRequest(shopItemHistoryList,"shop", "SYNCHRONIZE", this.keyy);
					
		      }
	    }
	    catch (UnsupportedOperationException localUnsupportedOperationException)
	    {
	    	Log.i("starting setup activity", "yes4");
	    }
	}
}
