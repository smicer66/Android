package com.scanshop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
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
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


public class AddItem extends Activity
implements View.OnClickListener, CompoundButton.OnCheckedChangeListener
{
	  private Context a;
	  private Button btn;
	  private int clickCheck=0;
	  private Context context;
	  private db d;
	  private SQLiteDatabase db;
	  Hashtable h1_reverse;
	  private String keyy;
	  boolean procChoice = true;
	  private TextView shopItemTitleEdit;
	  private EditText shopItemTypeEdit;
	private EditText amountEdit;
	private EditText shopItemDescription;
	private String scannedContents="";
	private Button sellButton;
	private Button cancelButton;
	public String uniqueId="";
	
	
	//private Button mPickDate1;
	private String token;
	//private Button mPickDate2;
	final Calendar cal = Calendar.getInstance(); 
	
	private int shop_item_position;
	private boolean processFlow=false;
	private String processFlowMsg;
	private Bundle bundle;
	private Intent myIntent;
	protected String presentFeatureCode;
	public String todo;
	protected StringWriter w;
	protected Exception e;
	private String action;
	private RadioButton applyDiscountYes;
	private RadioButton applyDiscountNo;
	private boolean applyDiscountSelected;
	String jsonMedicationDetail =null;
	private String currentMenuList="";
	private String discountApplied;
	private String amountValue;
	private TextView tv01;
	private EditText discountAmount;
	private String item;
	private CheckBox sinkLive;
	private String sinkLiveSelected="0";
	private String shopItemNameValue;
	private String shopItemTypeValue;
	private String shortDescriptionValue;
	
	static final int DATE_DIALOG_ID = 0;


public AddItem()
{
	  	
		//this.timeList = arrayOfString;
		this.clickCheck = 0;
		this.h1_reverse = new Hashtable();
		
}


public void onBackPressed()
{
	    Intent intent_=new Intent(AddItem.this, Home.class);
	    intent_.putExtra("returnHome", 1);
	    intent_.putExtra("keyy", AddItem.this.keyy);
	    intent_.putExtra("ScreenType", "MAINMENU");
	    startActivity(intent_);
	    finish();
	    
}

public void onClick(View paramView)
{
	  try
	  {

		   
			  	this.clickCheck = (1 + this.clickCheck);
			  	System.out.println("this.clickCheck" + (this.clickCheck));
			  	
			  	if ((paramView == findViewById(R.id.cancelButton)) && (this.clickCheck == 1))
			    {
			  		this.clickCheck=0;
			  		AddItem.this.myIntent = new Intent(AddItem.this, Home.class);
		            myIntent.putExtra("keyy", AddItem.this.keyy);
		            Log.i("Inside Medication","Cancel Sale");
		            AddItem.this.startActivity(myIntent);
		            finish();
			    }
			  	else if ((paramView == findViewById(R.id.sellButton)) && (this.clickCheck == 1))
			    {
			      Object localObject1;
			      if (((TextView)findViewById(R.id.shopItemTitleEdit)).getText().toString().length()==0)
			      {
			        processFlow=false; processFlowMsg="No medication name provided";
			        
			      }
			      /*else if ((((EditText)findViewById(R.id.shopItemTypeEdit)).getText().toString().length()==0))
			      {
				       	processFlow=false; processFlowMsg="The Type of Shop Item cannot be found";
				        
			      }*/
			      else if (((EditText)findViewById(R.id.amountEdit)).getText().toString().length()==0)
			      {
				        processFlow=false; processFlowMsg="No Pricing Found for this Item!";
				        
			      }
			      else
			      {
			    	  processFlow=true;
			      }
			      if(processFlow==false)
			      {
			    	  AlertDialog.Builder adb_2 = new AlertDialog.Builder(AddItem.this);
			            (adb_2).setTitle("Error Message");
			            (adb_2).setMessage(processFlowMsg);
			            (adb_2).setPositiveButton("Ok", new DialogInterface.OnClickListener()
			            {
			              public void onClick(DialogInterface paramDialogInterface, int paramInt)
			              {
			              		clickCheck=0;
			              		paramDialogInterface.dismiss();
		
			              		/*Bundle b = new Bundle();
			                	b.putString("keyy", AddItem.this.keyy);
			                	Intent intent = new Intent(AddItem.this, AddItem.class);
			                	intent.putExtras(b);
			                	startActivity(intent);
			                	finish();*/
			              }
			            });
			            Log.v("Insert pddb ", "failure12");
			            adb_2.show();
			      }
			      else
			      {
				        String endDateValue="";
				        amountValue=this.amountEdit.getText().toString();
				        
				        
				        DateFormat df=new SimpleDateFormat("yyyy/MM/DD HH:mm:ss");
	            	  	Calendar cal=Calendar.getInstance();
	            	  	String currentdate=df.format(cal.getTime());
				        
				            Log.i("proceed", "Clicked");
				            
					            
			            uniqueId=UUID.randomUUID().toString();
			            System.out.println("2...shop_item_position = " + shop_item_position);
			            sendPostRequest( 
		                		this.item, 
		                		this.shopItemTitleEdit.getText().toString(),
		                		this.shopItemTypeEdit.getText().toString(),
		                		uniqueId,  
		                		amountValue, 
		                		this.shopItemDescription.getText().toString(),
		                		keyy,
		                		this.sinkLiveSelected, 
		                		"shop",
		                		"add new item"); 
			            

				          
			      }
			    }
		    
	  	}
		catch (Exception e)
		{
			AddItem.this.e = e;
			AlertDialog.Builder adb_51 = new AlertDialog.Builder(AddItem.this);
		    Log.v("Track2", "Clicked1");
		    (adb_51).setTitle("Sending Option");
		    Log.v("Track3", "Clicked2122211");
		    ((AlertDialog.Builder)adb_51).setMessage("Error occured while processing your request. Submit error to our team");
		    ((AlertDialog.Builder)adb_51).setPositiveButton("Ok", new DialogInterface.OnClickListener()
		    {
			      public void onClick(DialogInterface paramDialogInterface, int paramInt)
			      {
			      	//AddItem.this.e.;
				      	AddItem.this.w = new StringWriter();
				      	AddItem.this.e.printStackTrace(new java.io.PrintWriter(AddItem.this.w));
				      	
				      	sendPostRequest(w.toString(),
				      			"", "", "",
				      	  		"",
				      	  		"",
				      			AddItem.this.keyy,
				      	  		"",
				      	  		"message",
				      	  		"log error");
			      }
		    });	
		    ((AlertDialog.Builder)adb_51).show();
		    
		}
}


private void sendPostRequest(String qrCode, 
	  		String shopItemNameValue,
	  		String shopItemTypeValue,
	  		String uniqueId, 
	  		String amountValue, 
	  		String shortDescriptionValue,
	  		String keyy,
	  		String sinkLive,
	  		String feature,
	  		String action) {
	  try
	  {
			class SendPostReqAsyncTask extends AsyncTask<String, Void, String>{

				ProgressDialog pDialog; 
				String qrCode; 
				String shopItemNameValue; 
				String shopItemTypeValue;
				String uniqueId;
				String amountValue; 
				String shortDescriptionValue; 
				String keyy; 
				String sinkLive;
				String feature; 
				String action;
				
				@Override
				protected void onPreExecute() {
			        pDialog = ProgressDialog.show(AddItem.this,"Please wait...", "Processing trasaction ...", true);
			    }
				
				
				@Override
				protected String doInBackground(String... params) {

					try
					{
					
						qrCode = params[0];
						shopItemNameValue = params[1];
						shopItemTypeValue = params[2];
						uniqueId = params[3];
						amountValue = params[4];
						shortDescriptionValue = params[5];
						keyy = params[6];
						sinkLive = params[7];
						feature = params[8];
						action = params[9];
	
								
						d=new db(AddItem.this);
			            Object localObject3 = d.openHelper;
			            String str = AddItem.this.getString(R.string.shopItems);
			            String[] arrayOfString1 = new String[8];
			            arrayOfString1[0] = "_id";
			            arrayOfString1[1] = "qr_code";
			            arrayOfString1[2] = "shopItemName";
			            arrayOfString1[3] = "shopItemType";
			            arrayOfString1[4] = "uniqueId";
			            arrayOfString1[5] = "amount";
			            arrayOfString1[6] = "shortDescription";
			            arrayOfString1[7] = "soldYes";
			            //_id, uniqueId, qr_code, shopItemName, shopItemType, amount, shortDescription, soldYes
			            String[] localObject1 = new String[8];
			            localObject1[0] = null;
			            localObject1[1] = qrCode;
			            localObject1[2] = shopItemNameValue;
			            localObject1[3] = shopItemTypeValue;
			            localObject1[4] = uniqueId;
			            localObject1[5] = amountValue;
			            localObject1[6] = shortDescriptionValue;
			            localObject1[7] = "0";
	
			            
			            System.out.println("Long.valueOf(shop_item_position)=" + todo);
			            
	
		            	long i=((d.openHelper)).createRow(str, arrayOfString1, localObject1);/**/
		            	//return ("SUCCESS::::Transaction Successful!" + str + "|" + i  + " | " + qrCode + " | " + shopItemNameValue + " | " + action + " | " + feature + " | " + 
		            		//	keyy + " | " + shortDescriptionValue + " | " + amountValue + " | " + uniqueId);
		            	/**/
		            	if(i>0)
		            	{
		            		if(sinkLive.equals("1"))
		            		{
		            			HttpClient httpClient = new DefaultHttpClient();
		            			
		    					HttpPost httpPost = new HttpPost(getString(R.string.posturl));
	
		    					BasicNameValuePair featureValuePair = new BasicNameValuePair("feature", feature);
		    					BasicNameValuePair actionValuePair = new BasicNameValuePair("action", action);
		    					BasicNameValuePair qrCodeValuePair = new BasicNameValuePair("qrCode", qrCode);
		    					BasicNameValuePair shopItemNameValuePair = new BasicNameValuePair("shopItemNameValue", shopItemNameValue);
		    					BasicNameValuePair shopItemTypeValuePair = new BasicNameValuePair("shopItemTypeValue", shopItemTypeValue);
		    					BasicNameValuePair uniqueIdValuePair = new BasicNameValuePair("uniqueId", uniqueId);
		    					BasicNameValuePair amountValuePair = new BasicNameValuePair("amountValue", amountValue);
		    					BasicNameValuePair shortDescriptionValuePair = new BasicNameValuePair("shortDescriptionValue", shortDescriptionValue);
		    					System.out.println("*** doInBackground ** postValue " );
	
		    					// We add the content that we want to pass with the POST request to as name-value pairs
		    					//Now we put those sending details to an ArrayList with type safe of NameValuePair
		    					List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
		    					nameValuePairList.add(featureValuePair);
		    					nameValuePairList.add(actionValuePair);
		    					nameValuePairList.add(qrCodeValuePair);
		    					nameValuePairList.add(shopItemNameValuePair);
		    					nameValuePairList.add(shopItemTypeValuePair);
		    					nameValuePairList.add(uniqueIdValuePair);
		    					nameValuePairList.add(amountValuePair);
		    					nameValuePairList.add(shortDescriptionValuePair);
		    					
		    					System.out.println("*** doInBackground1 ** postValue " + qrCode);
		    					try {
		    						// UrlEncodedFormEntity is an entity composed of a list of url-encoded pairs. 
		    						//This is typically useful while sending an HTTP POST request. 
		    						UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);
		    						System.out.println("*** doInBackground2 ** postValue " + qrCode);
		    						// setEntity() hands the entity (here it is urlEncodedFormEntity) to the request.
		    						httpPost.setEntity(urlEncodedFormEntity);
	
		    						try {
		    							// HttpResponse is an interface just like HttpPost.
		    							//Therefore we can't initialize them
		    							HttpResponse httpResponse = httpClient.execute(httpPost);
		    							System.out.println("*** doInBackground3 ** postValue " + qrCode);
		    							// According to the JAVA API, InputStream constructor do nothing. 
		    							//So we can't initialize InputStream although it is not an interface
		    							InputStream inputStream = httpResponse.getEntity().getContent();
		    							System.out.println("*** doInBackground4 ** postValue " + qrCode);
		    							InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		    							System.out.println("*** doInBackground5 ** postValue " + qrCode);
		    							BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		    							System.out.println("*** doInBackground6 ** postValue " + qrCode);
		    							StringBuilder stringBuilder = new StringBuilder();
	
		    							String bufferedStrChunk = null;
	
		    							while((bufferedStrChunk = bufferedReader.readLine()) != null){
	
		    								stringBuilder.append(bufferedStrChunk);
		    							}
		    							System.out.println("*** doInBackground7 ** postValue " + qrCode);
		    							//Toast.makeText(getApplicationContext(), stringBuilder.toString(), Toast.LENGTH_LONG).show();
		    							return stringBuilder.toString();
	
		    						} catch (ClientProtocolException cpe) {
		    							AddItem.this.w = new StringWriter();
		    					      	cpe.printStackTrace(new java.io.PrintWriter(AddItem.this.w));
		    					      	
		    					      	sendPostRequest(w.toString(),
		    					      			"", "", "",
		    					      	  		"",
		    					      	  		"",
		    					      			AddItem.this.keyy,
		    					      	  		"",
		    					      	  		"message",
		    					      	  		"log error");
		    					      	return null;
		    						} catch (IOException ioe) {
		    							AddItem.this.w = new StringWriter();
		    					      	ioe.printStackTrace(new java.io.PrintWriter(AddItem.this.w));
		    					      	
		    					      	sendPostRequest(w.toString(),
		    					      			"", "", "",
		    					      	  		"",
		    					      	  		"",
		    					      			AddItem.this.keyy,
		    					      	  		"",
		    					      	  		"message",
		    					      	  		"log error");
		    					      	return null;
		    						}
	
		    					} catch (UnsupportedEncodingException uee) {
		    						AddItem.this.w = new StringWriter();
		    				      	uee.printStackTrace(new java.io.PrintWriter(AddItem.this.w));
		    				      	
		    				      	sendPostRequest(w.toString(),
		    				      			"", "", "",
		    				      	  		"",
		    				      	  		"",
		    				      			AddItem.this.keyy,
		    				      	  		"",
		    				      	  		"message",
		    				      	  		"log error");
		    				      	return null;
		    					}
	
		    					
		            		}
		            		
		            		else
		            		{
		            			return "SUCCESS::::Transaction Successful!";
		            		}
		            	}
		            	else
		            	{
		            		((d.openHelper)).close();
				            d.close();
				            return "FAIL::::Transaction Failed!";
		            	}/**/
					}
					catch (Exception uee) {
						AddItem.this.w = new StringWriter();
				      	uee.printStackTrace(new java.io.PrintWriter(AddItem.this.w));
				      	
				      	sendPostRequest(w.toString(),
				      			"", "", "",
				      	  		"",
				      	  		"",
				      			AddItem.this.keyy,
				      	  		"",
				      	  		"message",
				      	  		"log error");
				      	return null;
					}

						
				}

				@Override
				protected void onPostExecute(String result) {
					try
					{
						super.onPostExecute(result);
						/*AddItem.this.myIntent = new Intent(AddItem.this, Home.class);
	                      myIntent.putExtra("keyy", AddItem.this.keyy);
	                      Log.i("Inside Medication","Going Back Home");
	                      AddItem.this.startActivity(myIntent);
	                      finish();*/
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
						AlertDialog.Builder adb_52 = new AlertDialog.Builder(AddItem.this);
		                Log.v("Track2", "Clicked1");
		                (adb_52).setTitle("Sending Option");
		                Log.v("Track3", "Clicked2122211");
		                
		                
		                /*((AlertDialog.Builder)adb_52).setMessage(result);
		                ((AlertDialog.Builder)adb_52).setPositiveButton("Yes", new DialogInterface.OnClickListener()
		                {
		                  public void onClick(DialogInterface paramDialogInterface, int paramInt)
		                  {
		                	 
		                  }
		                });
		                ((AlertDialog.Builder)adb_52).setNegativeButton("No", new DialogInterface.OnClickListener()
		                {
		                	public void onClick(DialogInterface paramDialogInterface, int paramInt)
			                  {
			                	  
			                  }
		                });	
		                ((AlertDialog.Builder)adb_52).show();*/
						if(result.startsWith("SUCCESS")){
							
							
							Toast.makeText(getApplicationContext(), "Processing...", Toast.LENGTH_LONG).show();
							//Log.v("return value=", valuer);
			    			AlertDialog.Builder adb_51 = new AlertDialog.Builder(AddItem.this);
			                //Log.v("Track2", "Clicked1");
			                (adb_51).setTitle("Sending Option");
			                //Log.v("Track3", "Clicked2122211");
			                ((AlertDialog.Builder)adb_51).setMessage(result.substring("SUCCESS::::".length()));
			                ((AlertDialog.Builder)adb_51).setPositiveButton("Ok", new DialogInterface.OnClickListener()
			                {
			                  public void onClick(DialogInterface paramDialogInterface, int paramInt)
			                  {
			                	  clickCheck=0;
			                	  Enumeration localEnumeration;
			                	  Object localObject;
			                	  
			                	  	AlertDialog.Builder adb_52 = new AlertDialog.Builder(AddItem.this);
					                //Log.v("Track2", "Clicked1");
					                (adb_52).setTitle("Sending Option");
					               // Log.v("Track3", "Clicked2122211");
					                
					                
					                ((AlertDialog.Builder)adb_52).setMessage("Add Another Shop Item?");
					                ((AlertDialog.Builder)adb_52).setPositiveButton("Yes", new DialogInterface.OnClickListener()
					                {
					                  public void onClick(DialogInterface paramDialogInterface, int paramInt)
					                  {
					                	  clickCheck=0;
					                	  AddItem.this.myIntent = new Intent(AddItem.this, Scanner.class);
					                      myIntent.putExtra("ScreenType", new menus().all_services()[4]);
					                      myIntent.putExtra("keyy", AddItem.this.keyy);
					                      Log.i("Inside Medication","Add New Medication After Adding First");
					                      AddItem.this.startActivity(myIntent);
					                      finish();
					                  }
					                });
					                ((AlertDialog.Builder)adb_52).setNegativeButton("No", new DialogInterface.OnClickListener()
					                {
					                	public void onClick(DialogInterface paramDialogInterface, int paramInt)
						                  {
						                	  clickCheck=0;
						                	  AddItem.this.myIntent = new Intent(AddItem.this, Home.class);
						                      myIntent.putExtra("keyy", AddItem.this.keyy);
						                      Log.i("Inside Medication","Going Back Home");
						                      AddItem.this.startActivity(myIntent);
						                      finish();
						                  }
					                });	
					                ((AlertDialog.Builder)adb_52).show();
			                  }
			                });	
			    	        ((AlertDialog.Builder)adb_51).show();
						}else{
							Toast.makeText(getApplicationContext(), "Processing Errors occured.", Toast.LENGTH_LONG).show();
							
		                    	Log.v("test", "9");
		                      AddItem.this.clickCheck = 0;
		                      Log.v("Insert syncdb_date ", "failure");
		                      AddItem.this.clickCheck = 0;
		    					Toast.makeText(getApplicationContext(), "Processing...", Toast.LENGTH_LONG).show();
		    					AlertDialog.Builder adb_4=new AlertDialog.Builder(AddItem.this);
				      	        adb_4.setTitle("Billing Cards");
				      	        adb_4.setMessage(result.substring("FAIL::::".length()));
				      	        adb_4.setPositiveButton("Ok", new DialogInterface.OnClickListener()
				      	        {
				      	          public void onClick(DialogInterface paramDialogInterface, int paramInt)
				      	          {
				      	        	  paramDialogInterface.dismiss();
				      	          }
				      	        });
				      	        
				      	        adb_4.show();
		                      //Intent myIntent = new Intent(AddItem.this, AddItem.class);
		                      //AddItem.this.startActivity((Intent)myIntent);
		                    
						}/**/
					}
					catch (Exception uee) {
						AddItem.this.w = new StringWriter();
				      	uee.printStackTrace(new java.io.PrintWriter(AddItem.this.w));
				      	
				      	sendPostRequest(w.toString(),
				      			"", "", "",
				      	  		"",
				      	  		"",
				      			AddItem.this.keyy,
				      	  		"",
				      	  		"message",
				      	  		"log error");
					}
				}			
			}

			SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
			sendPostReqAsyncTask.execute(qrCode, 
			  		shopItemNameValue,
			  		shopItemTypeValue,
			  		uniqueId, 
			  		amountValue, 
			  		shortDescriptionValue,
			  		keyy,
			  		sinkLive,
			  		feature,
			  		action);
		}
		catch (Exception e)
		{
			AddItem.this.e = e;
			AlertDialog.Builder adb_51 = new AlertDialog.Builder(AddItem.this);
		    Log.v("Track2", "Clicked1");
		    (adb_51).setTitle("Sending Option");
		    Log.v("Track3", "Clicked2122211");
		    ((AlertDialog.Builder)adb_51).setMessage("Error occured while processing your request. Submit error to our team");
		    ((AlertDialog.Builder)adb_51).setPositiveButton("Ok", new DialogInterface.OnClickListener()
		    {
			      public void onClick(DialogInterface paramDialogInterface, int paramInt)
			      {
			      	//AddItem.this.e.;
				      	AddItem.this.w = new StringWriter();
				      	AddItem.this.e.printStackTrace(new java.io.PrintWriter(AddItem.this.w));
				      	
				      	sendPostRequest(w.toString(),
				      			"", "", "",
				      	  		"",
				      	  		"",
				      			AddItem.this.keyy,
				      	  		"",
				      	  		"message",
				      	  		"log error");
			      }
		    });	
		    ((AlertDialog.Builder)adb_51).show();
		}
}
	  
	  

public boolean onContextItemSelected(MenuItem item)
{
	  try
	  {
			  AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
			  if (this.currentMenuList.equals("Quantity"))
		      {
		    	  //this.shopItemQuantity.setText(item.getTitle());
		    	  this.currentMenuList = "";
		      }
			  else
			  {
				  return false;
			  }
	  	}
		catch (Exception e)
		{
			AddItem.this.e = e;
			AlertDialog.Builder adb_51 = new AlertDialog.Builder(AddItem.this);
		    Log.v("Track2", "Clicked1");
		    (adb_51).setTitle("Sending Option");
		    Log.v("Track3", "Clicked2122211");
		    ((AlertDialog.Builder)adb_51).setMessage("Error occured while processing your request. Submit error to our team");
		    ((AlertDialog.Builder)adb_51).setPositiveButton("Ok", new DialogInterface.OnClickListener()
		    {
			      public void onClick(DialogInterface paramDialogInterface, int paramInt)
			      {
			      	//AddItem.this.e.;
				      	AddItem.this.w = new StringWriter();
				      	AddItem.this.e.printStackTrace(new java.io.PrintWriter(AddItem.this.w));
				      	
				      	sendPostRequest(w.toString(),
				      			"", "", "",
				      	  		"",
				      	  		"",
				      			AddItem.this.keyy,
				      	  		"",
				      	  		"message",
				      	  		"log error");
			      }
		    });	
		    ((AlertDialog.Builder)adb_51).show();
		}
	  	finally
	  	{
		  return true;
	  	}
	   
}




public void onCreate(Bundle paramBundle)
{
	  try
	  {
		  Log.i("just started AddItem activity", "yes");
		  System.out.println("btn.toString=");
		  super.onCreate(paramBundle);
		  bundle=getIntent().getExtras();
	      
		  
	      //Log.v("bundle.size=", "" + bundle.size());
	      if(bundle!=null)
	      {
		        if(bundle.containsKey("keyy"))
		        {
		        	this.keyy=bundle.getString("keyy");
		        	Log.v("keyy when entering home =", "" + this.keyy);
		        	if(bundle.containsKey("item"))
			        {
			        	this.item = bundle.getString("item");
			        	System.out.println("shop_item_position = " +  this.item);
			        }
		        	else
		        	{
		        		this.myIntent = new Intent(AddItem.this, Home.class);
		        		this.myIntent.putExtra("noKeyReDirect", 1);
	                    myIntent.putExtra("keyy", AddItem.this.keyy);
		        		
		        		AddItem.this.startActivity(myIntent);
			            finish();
		        	}
		        }
		        else
		        {
		        	this.myIntent = new Intent(AddItem.this, SignIn.class);
	        		this.myIntent.putExtra("noKeyReDirect", 1);
	        		AddItem.this.startActivity(myIntent);
		            finish();
		        }
		        

		        
	      }
	      else
	      {
	      	Intent myIntent = new Intent(AddItem.this, SignIn.class);
	      	AddItem.this.startActivity((Intent)myIntent);
	      	finish();
	      }
	      
		  setContentView(R.layout.add_item);
		  
		  
		  this.setTitle("Sell My Shop Item");
		  
		  //Cursor c=d.openHelper.fetchRowType2(null, getString(R.string.drugs_table), new String[]{"_id"}, null, null, null, null);
	      //System.out.println("asdasdss==" + c.getCount());
	      
		  this.clickCheck =0;
		  	//this.d = new db(this);
		    
	    	this.context = this;
		    
		    Bundle localBundle = getIntent().getExtras();
		    this.a = this;
		    this.sellButton = ((Button)findViewById(R.id.sellButton));
		    this.sellButton.setOnClickListener(this);
		    this.cancelButton = ((Button)findViewById(R.id.cancelButton));
		    this.cancelButton.setOnClickListener(this);
		    this.shopItemTitleEdit = ((TextView)findViewById(R.id.shopItemTitleEdit));
		    this.shopItemTypeEdit = ((EditText)findViewById(R.id.shopItemTypeEdit));
		    this.shopItemDescription = ((EditText)findViewById(R.id.shopItemDescription));
		    this.amountEdit = ((EditText)findViewById(R.id.amountEdit));
		    this.sinkLive = ((CheckBox)findViewById(R.id.sinkLive));
		    this.sinkLive.setOnCheckedChangeListener(this);
		    this.shopItemTitleEdit.setText(this.item);
		    
		    
		    
		    
		    
		    	
		    
		}
		catch (Exception e)
		{
			AddItem.this.e = e;
			AlertDialog.Builder adb_51 = new AlertDialog.Builder(AddItem.this);
		    Log.v("Track2", "Clicked1");
		    (adb_51).setTitle("Sending Option");
		    Log.v("Track3", "Clicked2122211");
		    ((AlertDialog.Builder)adb_51).setMessage("Error occured while processing your request. Submit error to our team");
		    ((AlertDialog.Builder)adb_51).setPositiveButton("Ok", new DialogInterface.OnClickListener()
		    {
			      public void onClick(DialogInterface paramDialogInterface, int paramInt)
			      {
			      	//AddItem.this.e.;
				      	AddItem.this.w = new StringWriter();
				      	AddItem.this.e.printStackTrace(new java.io.PrintWriter(AddItem.this.w));
				      	
				      	sendPostRequest(w.toString(),
				      			"", "", "",
				      	  		"",
				      	  		"",
				      			AddItem.this.keyy,
				      	  		"",
				      	  		"message",
				      	  		"log error");
			      }
		    });	
		    ((AlertDialog.Builder)adb_51).show();
		}
	    
}

public void onCreateContextMenu(ContextMenu paramContextMenu, View paramView, ContextMenu.ContextMenuInfo paramContextMenuInfo)
{
	  try
	  {
		  
		  //for(int count=0; count<paramContextMenu.size(); count++)
		  //{
			  paramContextMenu.clear();
		  //}
			  System.out.println("contextMenuId=" + paramView.getId());
		  
		  paramContextMenu.add(0, 0, 0, "-Select One-");
	    	
	    	if (paramView.getId() == R.id.shopItemQuantity)
	    	{
	    		  this.currentMenuList = "Quantity";
			      for (int i = 1; i < 100; i++)
			      {
			        paramContextMenu.add(0, i, 0, Integer.toString(i));
			      }
	    	}
	    	else
	    	{
	  	      super.onCreateContextMenu(paramContextMenu, paramView, paramContextMenuInfo);
	    	}
	  	}
		catch (Exception e)
		{
			AddItem.this.e = e;
			AlertDialog.Builder adb_51 = new AlertDialog.Builder(AddItem.this);
		    Log.v("Track2", "Clicked1");
		    (adb_51).setTitle("Sending Option");
		    Log.v("Track3", "Clicked2122211");
		    ((AlertDialog.Builder)adb_51).setMessage("Error occured while processing your request. Submit error to our team");
		    ((AlertDialog.Builder)adb_51).setPositiveButton("Ok", new DialogInterface.OnClickListener()
		    {
			      public void onClick(DialogInterface paramDialogInterface, int paramInt)
			      {
			      	//AddItem.this.e.;
				      	AddItem.this.w = new StringWriter();
				      	AddItem.this.e.printStackTrace(new java.io.PrintWriter(AddItem.this.w));
				      	
				      	sendPostRequest(w.toString(),
				      			"", "", "",
				      	  		"",
				      	  		"",
				      			AddItem.this.keyy,
				      	  		"",
				      	  		"message",
				      	  		"log error");
			      }
		    });	
		    ((AlertDialog.Builder)adb_51).show();
		}	
	    	
}



public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
{
	  	boolean bool=false;
	  	try
	  	{
		  	
		    if ((paramInt != 4) || (paramKeyEvent.getRepeatCount() != 0))
		    {
		      bool = super.onKeyDown(paramInt, paramKeyEvent);
		    }
		    else
		    {
		    	this.clickCheck = 0;
		    	Intent intent_=new Intent(AddItem.this, Home.class);
		    	intent_.putExtra("returnHome", 1);
			    intent_.putExtra("keyy", AddItem.this.keyy);
		 	    startActivity(intent_);
		 	    finish();
		      bool = true;
		    }
		    
	  	}
	  	catch (Exception e)
	  	{
			AddItem.this.e = e;
			AlertDialog.Builder adb_51 = new AlertDialog.Builder(AddItem.this);
		    Log.v("Track2", "Clicked1");
		    (adb_51).setTitle("Sending Option");
		    Log.v("Track3", "Clicked2122211");
		    ((AlertDialog.Builder)adb_51).setMessage("Error occured while processing your request. Submit error to our team");
		    ((AlertDialog.Builder)adb_51).setPositiveButton("Ok", new DialogInterface.OnClickListener()
		    {
			      public void onClick(DialogInterface paramDialogInterface, int paramInt)
			      {
			      	//AddItem.this.e.;
				      	AddItem.this.w = new StringWriter();
				      	AddItem.this.e.printStackTrace(new java.io.PrintWriter(AddItem.this.w));
				      	
				      	sendPostRequest(w.toString(),
				      			"", "", "",
				      	  		"",
				      	  		"",
				      			AddItem.this.keyy,
				      	  		"",
				      	  		"message",
				      	  		"log error");
			      }
		    });	
		    ((AlertDialog.Builder)adb_51).show();
	  	}
	  	finally
	  	{
	  		return bool;
	  	}
}


static String intToString(int num, int digits) {
	    assert digits > 0 : "Invalid number of digits";

	    // create variable length array of zeros
	    char[] zeros = new char[digits];
	    Arrays.fill(zeros, '0');
	    // format number as String
	    DecimalFormat df = new DecimalFormat(String.valueOf(zeros));

	    return df.format(num);
	}

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if(isChecked)
		{
			if(buttonView == findViewById(R.id.sinkLive))
			{
				this.sinkLiveSelected="1";
				
			}
		}
		else
		{
			if(buttonView == findViewById(R.id.sinkLive))
			{
				this.sinkLiveSelected="0";
			}
		}
	}
}
