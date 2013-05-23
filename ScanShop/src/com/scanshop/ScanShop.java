package com.scanshop;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ScanShop extends Activity implements OnClickListener {
    private CheckBox iAgree;
	private Button btn;
	private boolean iAgreeValue;
	private db d;
	private SQLiteDatabase db;
	private Context context;
	private int clickCheck=0;
	
	public void onBackPressed() {
        // do something on back.
		AlertDialog.Builder adb_4=new AlertDialog.Builder(ScanShop.this);
        Log.v("Track21", "Clicked1");
        adb_4.setTitle("Sending Option");
        Log.v("Track31", "Clicked2");
        adb_4.setMessage("Exit Application?");
        adb_4.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramDialogInterface, int paramInt)
          {
        	  onDestroy();
      		  System.exit(0);
              finish();
          }
        });
        adb_4.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramDialogInterface, int paramInt)
          {
        	  /*Intent myIntent = new Intent(SetUp.this, SetUp.class);
              SetUp.this.startActivity(myIntent);
              finish();*/
              paramDialogInterface.dismiss();
          }
        });
        
        adb_4.show();
    }
	
	
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
        	onDestroy();
    		System.exit(0);
            finish();
            return true;
        }
        else
        {
        	return super.onKeyDown(keyCode, event);
        }
    }
	
	
	
	//featureId=application&action=initialize
	private void sendPostRequest(String appId, String feature,
	  		String action) {

			class SendPostReqAsyncTask extends AsyncTask<String, Void, String>{

				ProgressDialog pDialog; 
				
				@Override
				protected void onPreExecute() {
			        pDialog = ProgressDialog.show(ScanShop.this,"Please wait...", "Setting Up Application ...", true);
			    }
				
				
				@Override
				protected String doInBackground(String... params) {

					String paramAppId = params[0];
					String paramFeature = params[1];
					String paramAction = params[2];

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
					// We add the content that we want to pass with the POST request to as name-value pairs
					//Now we put those sending details to an ArrayList with type safe of NameValuePair
					List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
					nameValuePairList.add(appIdBasicNameValuePair);
					nameValuePairList.add(featureBasicNameValuePair);
					nameValuePairList.add(actionBasicNameValuePair);
					
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
						  String[] as1;//country
						  String[] as2;//state
						  String[] as3;//security question
						  String[] as4;//shop items
						  boolean proceedNow=false;
		        		  
		        		  as1 = superString[0].split(":::");
		        		  as2 = superString[1].split(":::");
		        		  as3 = superString[2].split(":::");
		        		  as4 = superString[3].split(":::");
		        		  System.out.println("as size=" + as1.length);
		        		  System.out.println("as2 size=" + as2.length);
		        		  System.out.println("as3 size=" + as3.length);
		        		  System.out.println("as4 size=" + as4.length);
		        		  

		        		  
		        		  if ((as1.length % 3)!=0)
		        		  {
		        			  Log.i("DB CLOSED34 = ", "YES6");
		        			  proceedNow= false;
		        		  }
		        		  if ((as2.length % 3)!=0)
		        		  {
		        			  Log.i("DB CLOSED35 = ", "YES6");
		        			  proceedNow= false;
		        		  }
		        		  if ((as3.length % 2)!=0)
		        		  {
		        			  Log.i("DB CLOSED36 = ", "YES6");
		        			  proceedNow= false;
		        		  }
		        		  if ((as4.length % 7)!=0)
		        		  {
		        			  Log.i("DB CLOSED36 = ", "YES6");
		        			  proceedNow= false;
		        		  }
		        		  
		        		  
		        		  else
		        		  {
		        			  String str = getString(R.string.country_table);
		        			  Log.i("tablename = ", str);
		        			  String[] fields = new String[3];
		        			  fields[0] = "_id";
		        			  fields[1] = "countryName";
		        			  fields[2] = "iso_code";
		        			  
		        			  String[] entries = new String[3];
		        			  int counter=0;
		        			  Log.i("(as1.length/3) = ", ((Integer)(as1.length/3)).toString());
		        			  for(int count1=0;count1<(as1.length/3);count1++)
		        			  {
		        				  for(int count=0;count<3;count++)
		        				  {
		        					  entries[count]=as1[counter];
		        					  Log.i("entries[" + count + "] = ", as1[counter++]);
		        				  }
		        				  Log.i("test me?", "No o");
		        				  d.openHelper.createRow(str, fields, entries);
		        				  Log.i("d.openHelper.insertId", ((Long)(d.openHelper.insertId)).toString());
		        			  }
		        			  
		        			  
		        			  
		        			  String tableName = getString(R.string.state_table);
		        			  fields = new String[3];
		        			  fields[0] = "_id";
		        			  fields[1] = "name";
		        			  fields[2] = "countryId";
		        			  
		        			  Log.i("NEXT TABLE 1", "YES");
		        			  entries = new String[3];
		        			  counter=0;
		        			  
		        			  for(int count1=0;count1<(as2.length/3);count1++)
		        			  {
		        				  for(int count=0;count<3;count++)
		        				  {
		        					  entries[count]=as2[counter];
		        					  Log.i("entries[" + count + "] = ", as2[counter++]);
		        				  }
		        				  Log.i("test me?", "No o");
		        				  d.openHelper.createRow(tableName, fields, entries);
		        				  Log.i("d.openHelper.insertId", ((Long)(d.openHelper.insertId)).toString());
		        			  }
		        			  
		        			  Log.i("NEXT TABLE 3", "YES");
		        			  
		        			  
		        			  tableName = getString(R.string.securityQuestionTable);
		        			  fields = new String[2];
		        			  fields[0] = "_id";
		        			  fields[1] = "question";
		        			  
		        			  Log.i("NEXT TABLE 1", "YES");
		        			  entries = new String[2];
		        			  counter=0;
		        			  
		        			  for(int count1=0;count1<(as3.length/2);count1++)
		        			  {
		        				  for(int count=0;count<2;count++)
		        				  {
		        					  entries[count]=as3[counter];
		        					  Log.i("entries[" + count + "] = ", as3[counter++]);
		        				  }
					        	  d.openHelper.createRow(tableName, fields, entries);
					        	  Log.i("d.openHelper.insertId", ((Long)(d.openHelper.insertId)).toString());
				        	  }
				        	  Log.i("test me?", "No o");
				        	  
				        	  
				        	  tableName = getString(R.string.shopItems);
					          fields = new String[7];
					          fields[0] = "_id";
					          fields[1] = "qr_code";
					          fields[2] = "shopItemName";
					          fields[3] = "amount";
					          fields[4] = "shortDescription";
					          fields[5] = "uniqueId";
					          fields[6] = "shopItemType";
					          
					          
					          
					          
					          Log.i("NEXT TABLE 1", "YES");
					          entries = new String[7];
					          counter=0;
					          
					          for(int count1=0;count1<(as4.length/7);count1++)
					          {
					        	  for(int count=0;count<7;count++)
					        	  {
					        		  entries[count]=as4[counter];
					        		  Log.i("entries[" + count + "] = ", as4[counter++]);
					        	  }
					        	  Log.i("test me?", "No o");
					        	  d.openHelper.createRow(tableName, fields, entries);
					        	  Log.i("d.openHelper.insertId", ((Long)(d.openHelper.insertId)).toString());
					          }
					          
					          Log.i("NEXT TABLE 4", "YES");
					          
					          
					          
					          
					          Log.i("NEXT TABLE 5", "YES5");
					          d.openHelper.close();
					          Log.i("DB CLOSED38 = ", "YES6");
					          d.close();

					          proceedNow= true;
	                    	  Intent myIntent = new Intent(ScanShop.this, SetUp.class);
	                    	  ScanShop.this.startActivity((Intent)myIntent);
				                     
				                 
				          }
				          
				          if(proceedNow==false)
				          {
				        	  	//Toast.makeText(getApplicationContext(), "HTTP POST is working...", Toast.LENGTH_LONG).show();
				        	  	ScanShop.this.deleteDatabase(getString(R.string.CurrentDBName));
				        	  	AlertDialog.Builder adb_51 = new AlertDialog.Builder(ScanShop.this);
				                Log.v("Track2", "Clicked1");
				                (adb_51).setTitle("Sending Option");
				                Log.v("Track3", "Clicked2122211");
				                ((AlertDialog.Builder)adb_51).setMessage("Setting Up your application failed. Try Again");
				                ((AlertDialog.Builder)adb_51).setPositiveButton("Ok", new DialogInterface.OnClickListener()
				                {
				                  public void onClick(DialogInterface paramDialogInterface, int paramInt)
				                  {
				                	  clickCheck=0;
				                	  Enumeration localEnumeration;
				                	  
				                      
			                    	  Log.v("test", "8");
			                    	  ScanShop.this.d.openHelper.close();
			                    	  ScanShop.this.d.close();
			                    	  ScanShop.this.finish();
			                    	  Intent myIntent = new Intent(ScanShop.this, Exit.class);
			                    	  ScanShop.this.startActivity((Intent)myIntent);
				                     
				                  }
				                });	
				    	        ((AlertDialog.Builder)adb_51).show();
				          }
				          
						
					}else{
						Toast.makeText(getApplicationContext(), "Processing Errors occured.", Toast.LENGTH_LONG).show();
						
							ScanShop.this.deleteDatabase(getString(R.string.CurrentDBName));
	                    	Log.v("test", "9");
	                    	//context.deleteDatabase(getString(R.string.CurrentDBName));
	                    	AlertDialog.Builder adb_51 = new AlertDialog.Builder(ScanShop.this);
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
			sendPostReqAsyncTask.execute(appId, feature, action);		
		}
	  
	  
	  	

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntent().getExtras();
        String str = getString(R.string.personalDb);
		String[] arrayOfString = new String[2];
		arrayOfString[0] = "_id";
		arrayOfString[1] = "key";
		d=new db(this);
		if(this.d.openHelper.tableExist(getString(R.string.personalDb)))
		{
			int count1=this.d.openHelper.getRowsCount(str, arrayOfString, null, null, null, null, null);
			d.openHelper.close();
			d.close();
			System.out.println("count1=" + count1);
			if (count1 == 0)
			{
				Intent myIntent=new Intent(this, SetUp.class);
				this.startActivity(myIntent);
				finish();
				
			}
			else
			{
				Intent myIntent=new Intent(this, SignIn.class);
				this.startActivity(myIntent);
				finish();
			}
		}
		else
		{
			setContentView(R.layout.licenseagreement);
			this.setTitle("ShopScan - Welcome");
			
	        //context.deleteDatabase(getString(R.string.CurrentDBName));
	        new Thread();
	        try
	        {
	          Thread.sleep(1000L);
	          this.iAgree=(CheckBox)findViewById(R.id.iAgree);
	          this.iAgree.setOnClickListener(this);
	          this.btn=(Button)findViewById(R.id.licenseAgree);
	          this.btn.setOnClickListener(this);
	          TextView t2=((TextView)findViewById(R.id.urlValue));
	          
	          t2.setText(getString(R.string.posturl));
	          return;
	        }
	        catch (InterruptedException localInterruptedException)
	        {
	      	  
	        }
		}
		
        
    }

	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		try
	    {
		      if (arg0 == findViewById(R.id.iAgree))
		      {
			        if (!((CheckBox)arg0).isChecked())
			        {
			        	Log.i("agreeCHeckbox", "false");
			          	this.iAgreeValue = false;
			        }
			        else
			        {
				        this.iAgreeValue = true;
				        Log.i("agreeCHeckbox", "true");
			        }
		      }
		      
		      if (arg0 == findViewById(R.id.licenseAgree) && (this.clickCheck==0))
		      {
		    	  this.clickCheck=1;
		    	  	Log.i("reset agreeCHeckbox", "false");
					this.iAgreeValue = false;
		          
					
					Log.i("licenseAgree", "iAgreeValue=true");
					
			     		this.d = new db(this);
			     		
			     	if(this.d.checkDataBase())
			     	{
			     		System.out.println("Database exists");
			     		if(this.d.openHelper.getWritableDatabase().isOpen()!=true)
			     		{
			     			System.out.println("Database Not Open");
			     			this.db = this.d.openHelper.getWritableDatabase();
			     			this.db.setLocale((Locale.ENGLISH));
			     		}
			     		else
			     		{
			     			if(this.d.openHelper.tableExist(getString(R.string.personalDb)))
			     			{
			     				System.out.println("PersonalDb Table Exists");
			     			}
			     			else
			     			{
			     				System.out.println("PersonalDb Table Does not Exist so create");
			     				this.d.createDBTables();

								/*proReq=new ProcessRequest();
			     				if(proReq.syncMenuDB("featureId=application&action=initialize", "", d, this))
			     				{
			     					if(this.d.openHelper.tableExist(getString(R.string.personalDb)))
			    			     	{
			    						String str = getString(R.string.personalDb);
			    						String[] arrayOfString = new String[2];
			    						arrayOfString[0] = "_id";
			    						arrayOfString[1] = "key";
			    						int count1=this.d.openHelper.getRowsCount(str, arrayOfString, null, null, null, null, null);
			    						System.out.println("count1=" + count1);
			    						if (count1 > 0)
			    						{
			    							Log.i("starting SignIn activity", "yes");
			    							//this.d.openHelper.close();
			    							//this.d.close();
			    							//startActivity(new Intent(this, SignIn.class));
			    							d.openHelper.close();
			    							d.close();
			    							Intent myIntent=new Intent(this, SignIn.class);
			    							this.startActivity(myIntent);
			    							//finish();
			    							Log.i("starting setup activity", "SignIn start");
			    						}
			    						else
			    						{
			    							Log.i("starting setup activity", "yes");
			    						    //this.d.openHelper.close();
			    						    Log.i("starting setup activity", "yes1");
			    						   // this.d.close();
			    						    Log.i("starting setup activity", "SetUp.class");
			    						    d.openHelper.close();
			    							d.close();
			    							//11-29 03:15:27.596: I/url final=(459): http://10.0.2.2/:8080/PillPodServer/PillPodServlet1?fid%3Djsdjsdsd
			    							//pull data from server and install in database on phone
			    							
			    						    Intent myIntent=new Intent(this, SetUp.class);
			    						    Log.i("starting setup activity", "yes3");
			    						    this.startActivity(myIntent);
			    							Log.i("starting setup activity", "should start");
			    							
			    							
			    						}
			    						this.iAgreeValue = true;
			    		      		}
			    		      		else
			    		      		{
			    		      			AlertDialog.Builder adb_4=new AlertDialog.Builder(ScanShop.this);
			         			        Log.v("Track21", "Clicked1");
			         			        adb_4.setTitle("Setup Issues Experienced");
			         			        Log.v("Track31", "Clicked2");
			         			        adb_4.setMessage("Issues were experienced while setting up your application. Make Sure your internet settings are setup correctly then try again later");
			         			       Log.v("Track31", "Clicked3");
			         			        adb_4.setPositiveButton("Ok", new DialogInterface.OnClickListener()
			         			        {
			         			        	
			         			          public void onClick(DialogInterface paramDialogInterface, int paramInt)
			         			          {
			         			        	 Log.v("Track31", "Clicked4");
			         			        	  onDestroy();
			         			      		  System.exit(0);
			         			              finish();
			         			          }
			         			        });
			         			        
			         			        adb_4.show();
			    		      		}
			     				}
			     				else
			     				{
			     					//drop database and exit
			     					//boris 07714654845
			     					//chidi 07859937322
			     					if(this.deleteDatabase(getString(R.string.CurrentDBName)))
			     						{
			     						Log.v("Track621", "Clicked61");
			     						}
			     					AlertDialog.Builder adb_4=new AlertDialog.Builder(ScanShop.this);
			     			        Log.v("Track21", "Clicked1");
			     			        adb_4.setTitle("Setup Issues Experienced");
			     			        Log.v("Track31", "Clicked2");
			     			        adb_4.setMessage("Issues were experienced while setting up your application. Make Sure your internet settings are setup correctly then try again later");
			     			       Log.v("Track31", "Clicked3");
			     			        adb_4.setPositiveButton("Ok", new DialogInterface.OnClickListener()
			     			        {
			     			        	
			     			          public void onClick(DialogInterface paramDialogInterface, int paramInt)
			     			          {
			     			        	 Log.v("Track31", "Clicked4");
			     			        	  onDestroy();
			     			      		  System.exit(0);
			     			              finish();
			     			          }
			     			        });
			     			        
			     			        adb_4.show();
			     				}*/
			     				sendPostRequest(UUID.randomUUID().toString(),"FP", "INITIALIZE");
			     			}
			     			
			     			
			     		}
			     	}
			     	else
			     	{
			     		System.out.println("Database does not exist");
			     		System.out.println("Create Database now");
			     		this.d.createDBTables();
			     	}
			     	
			     	
					
		      }
		      else
		      {
		    	  this.iAgreeValue = false;
		      }
	    }
	    catch (UnsupportedOperationException e)
	    {
	    	StringWriter w = new StringWriter();
    	  	e.printStackTrace(new java.io.PrintWriter(w));
    	  	
	      	sendPostRequest(w.toString(), "message",
	      	  		"log error");
	    }
		catch (Exception e)
	    {
	    	StringWriter w = new StringWriter();
    	  	e.printStackTrace(new java.io.PrintWriter(w));
    	  	
	      	sendPostRequest(w.toString(), "message",
	      	  		"log error");
	    }
	}
}
