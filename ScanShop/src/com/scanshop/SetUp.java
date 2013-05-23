package com.scanshop;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;






public class SetUp extends Activity
  implements View.OnClickListener
{
  private Context a;
  private Button btn;
  private int clickCheck;
  private Context context;
  
  int runValue=0;

  
  private db d;
  private SQLiteDatabase db;
  private EditText email;
  private String emailValue;
  private EditText firstName;
  Hashtable h1_reverse;
  private String keyy;
  
  boolean procChoice = true;
  private String token;
  private String itemClicked;
  private int itemClickedValue;
  private String url;
  String valuer="";
  private boolean processFlow=false;
  private String processFlowMsg="";
  private Bundle bundle;
  private ContextMenu pContextMenu;
  private Button btnCancel;
  

  public SetUp()
  {
	  	
		//this.countryList = arrayOfString;
		this.clickCheck = 0;
		this.h1_reverse = new Hashtable();
  }

  

  

  public void onBackPressed()
  {
	  	finish();
	  	startActivity(new Intent(this, Exit.class));
  }

  public void onClick(View paramView)
  {
	  	
	  	this.clickCheck = (1 + this.clickCheck);
	  	System.out.println("clickCheck1=" + this.clickCheck);
	  	
	  	System.out.println("clickCheck2=" + this.clickCheck);
	  	
	  	if ((paramView == findViewById(R.id.setupCancel)) && (this.clickCheck == 1))
	    {
	  		AlertDialog.Builder adb_2 = new AlertDialog.Builder(SetUp.this);
            (adb_2).setTitle("Error Message");
            (adb_2).setMessage("You can not use this application without signing up. Do you still want to exit?");
            
            (adb_2).setPositiveButton("Yes", new DialogInterface.OnClickListener()
            {
              public void onClick(DialogInterface paramDialogInterface, int paramInt)
              {
              		clickCheck=0;
              		paramDialogInterface.dismiss();
              		finish();
              		System.exit(0);
              		
              }
            });
            (adb_2).setNegativeButton("No", new DialogInterface.OnClickListener()
            {
              public void onClick(DialogInterface paramDialogInterface, int paramInt)
              {
              		clickCheck=0;
              		paramDialogInterface.dismiss();
              		
              }
            });
            Log.v("Insert pddb ", "failure");
            adb_2.show();
	    }
	  	if ((paramView == findViewById(R.id.setupOk)) && (this.clickCheck == 1))
	    {
	  		
	      Object localObject1;
	      
    	  if (((EditText)findViewById(R.id.password)).getText().toString().length() == 0)
	      {
    		  processFlow=false;
    		  processFlowMsg="No Password provided";
	      }
    	  else
    	  {
    		  
			  if (((EditText)findViewById(R.id.firstName)).getText().toString().length() == 0)
		      {
				  processFlow=false;
	    		  processFlowMsg="No Confirmation code provided";
		      }
			  else
			  {
	  
				  if (((EditText)findViewById(R.id.email)).getText().toString().length() == 0)
			      {
					  processFlow=false;
		    		  processFlowMsg="No email address provided";
			      }
				  else
				  {
					  processFlow=true;
    			  }
    		  }
    	  }
	    
    	  
    	  
    	  
	      	if(processFlow==true)
		  	{
		  		
		        
		        String localObject12 = "Confirm your entries before proceeding. Press the Back Button to go back to make changes. \nEmail: " + 
		        ((EditText)findViewById(R.id.email)).getText().toString() + "\nPassword: " + ((EditText)findViewById(R.id.password)).getText().toString() + 
		        "\nConfirmation Code: " + ((EditText)findViewById(R.id.firstName)).getText().toString() +
		        "\n\n Click and wait for processing";
		        
		        Log.v("password:", ((EditText)findViewById(R.id.password)).getText().toString().substring(0, 1));
		        AlertDialog.Builder adb_4 = new AlertDialog.Builder(this);
		        (adb_4).setTitle("Confirmation");
		        (adb_4).setMessage((CharSequence)localObject12);
		        (adb_4).setPositiveButton("Proceed", new DialogInterface.OnClickListener()
		        {
		        	public void onClick(DialogInterface paramDialogInterface, int paramInt)
		          	{
		        		sendPostRequest(
		                  		((EditText)SetUp.this.findViewById(R.id.password)).getText().toString(),
		                  		((EditText)SetUp.this.findViewById(R.id.firstName)).getText().toString(),
		                  		((EditText)SetUp.this.findViewById(R.id.email)).getText().toString(), 
		                  		"user",
		                  		"register"); 
		          	}
		        });
		        (adb_4).setNegativeButton("Go Back", new DialogInterface.OnClickListener()
	            {
	        		public void onClick(DialogInterface paramDialogInterface, int paramInt)
	        		{
	        			paramDialogInterface.dismiss();
	        		}
	            });
		        ((AlertDialog.Builder)adb_4).show();
		  	}
		  	else
		  	{
		  		AlertDialog.Builder adb_2 = new AlertDialog.Builder(SetUp.this);
	            (adb_2).setTitle("Error Message");
	            (adb_2).setMessage(processFlowMsg);
	            (adb_2).setPositiveButton("Ok", new DialogInterface.OnClickListener()
	            {
	              public void onClick(DialogInterface paramDialogInterface, int paramInt)
	              {
	              		clickCheck=0;
	              		paramDialogInterface.dismiss();
	              		Intent setupIntent = new Intent(SetUp.this, SetUp.class);
	              		SetUp.this.startActivity(setupIntent);
	              }
	            });
	            Log.v("Insert pddb ", "failure12");
	            adb_2.show();
		  	}
	    }
	  	
	  	
	  	
  }
  
  
  private void sendPostRequest( 
  		String password,
  		String firstName,
  		String email, 
  		String user,
  		String register) {

		class SendPostReqAsyncTask extends AsyncTask<String, Void, String>{

			ProgressDialog pDialog; 
			
			@Override
			protected void onPreExecute() {
		        pDialog = ProgressDialog.show(SetUp.this,"Please wait...", "Processing trasaction ...", true);
		    }
			
			
			@Override
			protected String doInBackground(String... params) {

				String paramPassword = params[0];
				String firstName = params[1];
				String email = params[2];
				String paramFeature = params[3];
				String paramAction = params[4];


				System.out.println("*** doInBackground ** confirmcode " + firstName + " paramPassword :" + paramPassword);

				HttpClient httpClient = new DefaultHttpClient();

				// In a POST request, we don't pass the values in the URL.
				//Therefore we use only the web page URL as the parameter of the HttpPost argument
				HttpPost httpPost = new HttpPost(getString(R.string.posturl));

				// Because we are not passing values over the URL, we should have a mechanism to pass the values that can be
				//uniquely separate by the other end.
				//To achieve that we use BasicNameValuePair				
				//Things we need to pass with the POST request
				BasicNameValuePair passwordBasicNameValuePAir = new BasicNameValuePair("password", paramPassword);
				BasicNameValuePair featureBasicNameValuePair = new BasicNameValuePair("feature", paramFeature);
				BasicNameValuePair actionBasicNameValuePAir = new BasicNameValuePair("action", paramAction);
				BasicNameValuePair firstNameValuePAir = new BasicNameValuePair("firstName", firstName);
				BasicNameValuePair emailValuePAir = new BasicNameValuePair("email", email);
				// We add the content that we want to pass with the POST request to as name-value pairs
				//Now we put those sending details to an ArrayList with type safe of NameValuePair
				List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
				nameValuePairList.add(passwordBasicNameValuePAir);
				nameValuePairList.add(featureBasicNameValuePair);
				nameValuePairList.add(actionBasicNameValuePAir);
				nameValuePairList.add(firstNameValuePAir);
				nameValuePairList.add(emailValuePAir);
				
				System.out.println("*** doInBackground1 ** paramFeature " + paramFeature + " paramPassword :" + paramPassword);
				try {
					// UrlEncodedFormEntity is an entity composed of a list of url-encoded pairs. 
					//This is typically useful while sending an HTTP POST request. 
					UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);
					System.out.println("*** doInBackground2 ** paramFeature " + paramFeature + " paramPassword :" + paramPassword);
					// setEntity() hands the entity (here it is urlEncodedFormEntity) to the request.
					httpPost.setEntity(urlEncodedFormEntity);

					try {
						// HttpResponse is an interface just like HttpPost.
						//Therefore we can't initialize them
						HttpResponse httpResponse = httpClient.execute(httpPost);
						System.out.println("*** doInBackground3 ** paramFeature " + paramFeature + " paramPassword :" + paramPassword);
						// According to the JAVA API, InputStream constructor do nothing. 
						//So we can't initialize InputStream although it is not an interface
						InputStream inputStream = httpResponse.getEntity().getContent();
						System.out.println("*** doInBackground4 ** paramFeature " + paramFeature + " paramPassword :" + paramPassword);
						InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
						System.out.println("*** doInBackground5 ** paramFeature " + paramFeature + " paramPassword :" + paramPassword);
						BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
						System.out.println("*** doInBackground6 ** paramFeature " + paramFeature + " paramPassword :" + paramPassword);
						StringBuilder stringBuilder = new StringBuilder();

						String bufferedStrChunk = null;

						while((bufferedStrChunk = bufferedReader.readLine()) != null){

							stringBuilder.append(bufferedStrChunk);
						}
						System.out.println("*** doInBackground7 ** paramFeature " + paramFeature + " paramPassword :" + paramPassword);
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
				
				String str=result.toString().replaceAll("_white_", "_");
			    System.out.println(str);
			    Object obj;
			    JSONObject jObj=null;
			    JSONObject jObj1=null;
				try {
					obj = JSONValue.parseWithException(str);
					jObj = (JSONObject)obj;
				    System.out.println(jObj);
				    
				    
				    String str1=jObj.get("logs").toString().replace("\\", "").replace("[", "").replace("]", "").replaceAll("_white_", "_");
				    if(!str1.equals("null") && str1!=null)
				    {
			            System.out.println("str1....." + str1);
			            Object obj1 = JSONValue.parseWithException(str1);
			            jObj1 = (JSONObject)obj1;
			            System.out.println(jObj1);
				    }
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    
			    
			    
			    
			    
				if((result!=null) && (result.length()>0) && (jObj!=null) && ((Boolean)jObj.get("success")==true || (Boolean)jObj.get("success")==false))
				{
				}
				else
				{
					result = "FAIL::::We encountered an error while processing your request. Please try again";
				}
				if((jObj!=null) && (Boolean)jObj.get("success")==true){
					Toast.makeText(getApplicationContext(), "HTTP POST is working...", Toast.LENGTH_LONG).show();
					Log.v("return value=", valuer);
	    			AlertDialog.Builder adb_51 = new AlertDialog.Builder(SetUp.this);
	                Log.v("Track2", "Clicked1");
	                (adb_51).setTitle("Sending Option");
	                Log.v("Track3", "Clicked2122211");
	                ((AlertDialog.Builder)adb_51).setMessage(jObj.get("message").toString());
	                ((AlertDialog.Builder)adb_51).setPositiveButton("Ok", new OnPositiveClick(jObj1));	
	    	        ((AlertDialog.Builder)adb_51).show();
				}else{
					
					if((jObj!=null) && (Boolean)jObj.get("success")==false){
					
						Toast.makeText(getApplicationContext(), "Processing Errors occured.", Toast.LENGTH_LONG).show();
						
	                	Log.v("test", "9");
	                	SetUp.this.clickCheck = 0;
	                	//context.deleteDatabase(getString(R.string.CurrentDBName));
	                	AlertDialog.Builder adb_51 = new AlertDialog.Builder(SetUp.this);
	  	                Log.v("Track2", "Clicked1");
	  	                (adb_51).setTitle("Sending Option");
	  	                Log.v("Track3", "Clicked2122211");
	  	                ((AlertDialog.Builder)adb_51).setMessage(jObj.get("message").toString());
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
					else
					{
Toast.makeText(getApplicationContext(), "Processing Errors occured.", Toast.LENGTH_LONG).show();
						
	                	Log.v("test", "9");
	                	SetUp.this.clickCheck = 0;
	                	//context.deleteDatabase(getString(R.string.CurrentDBName));
	                	AlertDialog.Builder adb_51 = new AlertDialog.Builder(SetUp.this);
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
		}

		SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
		sendPostReqAsyncTask.execute(
		  		password,
		  		firstName, 
		  		email,
		  		user,
		  		register);		
	}
  
  
  	
  	
  class OnPositiveClick implements DialogInterface.OnClickListener
  {

	  JSONObject jObj;
	  
	  public OnPositiveClick(JSONObject jObj1)
	  {
		  jObj=jObj1;
	  }
	  
		public void onClick(DialogInterface dialog, int which) 
		{
			// TODO Auto-generated method stub
			if(jObj!=null)
			{
				System.out.println("We are in onclick for the dialog interface");
	      	  	clickCheck=0;
	      	  	Enumeration localEnumeration;
	      	  	Object localObject;
	      	  	Log.v("proceed", "Clicked");
                SetUp.this.keyy=jObj.get("keyy").toString();
	          	  
	      	  	  d=new db(SetUp.this);
	          	  Object localObject3 = d.openHelper;
	          	  String str = SetUp.this.getString(R.string.personalDb);
	          	  String[] arrayOfString1 = new String[17];
	          	  arrayOfString1[0] = "_id";
	          	  arrayOfString1[1] = "key";
	          	  arrayOfString1[2] = "username";
	          	  arrayOfString1[3] = "password";
	          	  arrayOfString1[4] = "firstName";
	          	  arrayOfString1[5] = "lastName";
	          	  arrayOfString1[6] = "city";
	          	  arrayOfString1[7] = "state";
	          	  arrayOfString1[8] = "country";
	          	  arrayOfString1[9] = "email";
	          	  arrayOfString1[10] = "mobile";
	          	  arrayOfString1[11] = "postCode";
	          	  arrayOfString1[12] = "addressLine1";
	          	  arrayOfString1[13] = "addressLine2";
	          	  arrayOfString1[14] = "nhsNumber";
	          	  arrayOfString1[15] = "securityQuestion";
	          	  arrayOfString1[16] = "securityQuestionAnswer";
	          	  String[] localObject1 = new String[17];
	          	  localObject1[0] = null;
	          	  localObject1[1] = jObj.get("keyy").toString();
	          	  localObject1[2] = "";
	          	  localObject1[3] = jObj.get("password").toString();;
	          	  localObject1[4] = jObj.get("firstName").toString();;
	          	  localObject1[5] = jObj.get("lastName").toString();;
	          	  localObject1[6] = jObj.get("city").toString();;
	          	  localObject1[7] = jObj.get("state").toString();;
	          	  localObject1[8] = jObj.get("country").toString();;
	          	  localObject1[9] = jObj.get("email").toString();;
	          	  localObject1[10] = jObj.get("mobile").toString();;
	          	  localObject1[11] = "";
	          	  localObject1[12] = jObj.get("addressLine1").toString();;
	          	  localObject1[13] = jObj.get("addressLine2").toString();;
	          	  localObject1[14] = jObj.get("nhsNumber").toString();;
	          	  localObject1[15] = jObj.get("securityQuestion").toString();;
	          	  localObject1[16] = jObj.get("securityQuestionAnswer").toString();
		            
		            
	          	  long idEntry=((db.OpenHelper)localObject3).createRow(str, arrayOfString1, localObject1);
		            
		            
		            
	          	  Log.v("key____ ::> ", SetUp.this.keyy);
	          	  if ((SetUp.this.d.openHelper.insertId == -1L))
	          	  {
	          		  Log.v("test", "1");
	          		  SetUp.this.clickCheck = 0;
	          		  Log.v("value=", "we are here2" + idEntry);  
	          		  d.openHelper.deleteRow(idEntry, str, "_id");
	          		  d.openHelper.close();
	          		  d.close();
	          		  AlertDialog.Builder adb_2 = new AlertDialog.Builder(SetUp.this);
	          		  (adb_2).setTitle("Error Message");
	          		  (adb_2).setMessage("Error Setting Up. \nTry Again");
	          		  (adb_2).setPositiveButton("Ok", new DialogInterface.OnClickListener()
	          		  {
	          			  public void onClick(DialogInterface paramDialogInterface, int paramInt)
	          			  {
	          				  clickCheck=0;
	          				  paramDialogInterface.dismiss();
	          				  Intent setupIntent = new Intent(SetUp.this, SetUp.class);
	          				  SetUp.this.startActivity(setupIntent);
	          			  }
	          		  });
	          		  Log.v("Insert pddb ", "failure");
	          		  adb_2.show();
	          	  }
	          	  
	          	  else
	          	  {
	          		  Cursor c1=d.openHelper.fetchRow(null, getString(R.string.shopItems), new String[]{"_id"}, null, null);
	          		  if (c1.getCount() == 0)
	          		  {
		          		  Log.v("test", "7");
		          		  SetUp.this.d.openHelper.close();
		          		  SetUp.this.d.close();
		          		  c1.close();
		          		  Intent myIntent = new Intent(SetUp.this, Synchronize.class);
		          		  myIntent.putExtra("keyy", SetUp.this.keyy);
		          		  SetUp.this.startActivity((Intent)myIntent);
	          		  }
	          		  else
	          		  {
	          			  Log.v("test", "8");
	                	  SetUp.this.d.openHelper.close();
	                      SetUp.this.d.close();
	                      c1.close();
	                      Bundle b = new Bundle();
				    	  b.putString("keyy", SetUp.this.keyy);
				    	  Intent intent = new Intent(SetUp.this, Home.class);
				    	  intent.putExtras(b);
				    	  startActivity(intent);
				    	  finish();
	          		  }
	          	  }
			}
			else
			{
				AlertDialog.Builder adb_2 = new AlertDialog.Builder(SetUp.this);
        		  (adb_2).setTitle("Error Message");
        		  (adb_2).setMessage("Error Setting Up. \nTry Again");
        		  (adb_2).setPositiveButton("Ok", new DialogInterface.OnClickListener()
        		  {
        			  public void onClick(DialogInterface paramDialogInterface, int paramInt)
        			  {
        				  clickCheck=0;
        				  paramDialogInterface.dismiss();
        				  Intent setupIntent = new Intent(SetUp.this, SetUp.class);
        				  SetUp.this.startActivity(setupIntent);
        			  }
        		  });
        		  Log.v("Insert pddb ", "failure");
        		  adb_2.show();
			}
		}
	  
  }
  	
  	
  	
  public boolean onContextItemSelected(MenuItem item)
  {
	  AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
	  	    
	    return true;
  }
  

  public void onCreate(Bundle paramBundle)
  {
	  Log.v("just started setup activity", "yes");
	  this.clickCheck =0;
	  
	  	this.d = new db(this);
	  	
	    this.db = this.d.openHelper.getWritableDatabase();
	    
	    if (!this.d.checkDataBase())
	    {
	    	Log.v("there is no database", "yes");
	      this.d.openHelper.close();
	      this.d.close();
	      startActivity(new Intent(this, ScanShop.class));
	      finish();
	    }
	    else
	    {
	    	if(this.d.openHelper.tableExist(getString(R.string.personalDb)))
			{
		    	Cursor c1=d.openHelper.fetchRow(null, getString(R.string.personalDb), new String[]{"_id"}, null, null);
	            if (c1.getCount() != 0) 
	            {
	          	  	Log.v("test1", "7");
	                SetUp.this.d.openHelper.close();
	                SetUp.this.d.close();
	                c1.close();
	          	  	Intent myIntent = new Intent(SetUp.this, SignIn.class);
	                SetUp.this.startActivity((Intent)myIntent);
	            }
	            else
	            {
	            	SetUp.this.d.openHelper.close();
	                SetUp.this.d.close();
	                c1.close();
			    	this.context = this;
				    Log.v("finish creation", "yes");
	            }
			}
	    	else
	    	{
	    		Log.v("test2", "7");
			    Log.v("finish creation", "yes");
	    	}
	    }




	  	System.out.println("btn.toString=");
	    super.onCreate(paramBundle);
        
	    setContentView(R.layout.createsetuplogin);
	    this.setTitle("Setup My Account!");
	    this.a = this;
	    this.btn = ((Button)findViewById(R.id.setupOk));
	    this.btn.setOnClickListener(this);
	    this.btnCancel = ((Button)findViewById(R.id.setupCancel));
	    this.btnCancel.setOnClickListener(this);
	    this.firstName = ((EditText)findViewById(R.id.firstName));
	    this.email = ((EditText)findViewById(R.id.email));
	    
	    
  }

  public void onCreateContextMenu(ContextMenu paramContextMenu, View paramView, ContextMenu.ContextMenuInfo paramContextMenuInfo)
  {
	  Log.i("asas", "asas");
	  d=new db(this);
	  SetUp.this.pContextMenu=paramContextMenu;
	  super.onCreateContextMenu(paramContextMenu, paramView, paramContextMenuInfo);
	  
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
	  	boolean bool;
	    if ((paramInt != 4) || (paramKeyEvent.getRepeatCount() != 0))
	    {
	      bool = super.onKeyDown(paramInt, paramKeyEvent);
	    }
	    else
	    {
	    	if (paramInt == KeyEvent.KEYCODE_BACK && paramKeyEvent.getRepeatCount() == 0) {
	    		this.clickCheck = 0;
	    		finish();
	  	      	startActivity(new Intent(this, Exit.class));
	  	      	bool = true;
	    	}
	    	else
	    	{
	    		bool=false;
	    	}
	      
	    }
	    return bool;
  }
}
