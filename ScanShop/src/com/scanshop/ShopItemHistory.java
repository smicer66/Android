package com.scanshop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class ShopItemHistory extends Activity implements View.OnClickListener
{
	  private Context a;
	  private Button btn;
	  private int clickCheck;
	  private Context context;
	  private db d;
	  private SQLiteDatabase db;
	  Hashtable h1_reverse;
	  private String keyy;
	  boolean procChoice = true;
	  private String currentMenuList;
	  
	private Button btn_1;
	int status;
	String change_status="";

	
	private Intent myIntent;
	private String presentFeatureCode;
	private TextView quantityView;
	private String alertMessage;
	private String idToChange;
	private String uidToChange;
	private String shop_item_position;
	private TextView shopItemSoldFor;
	private TextView shopItemDateBought;
	private TextView shopItemPrice;
	private TextView shopItemTitle;
	private String emailStr="";

	static final int DATE_DIALOG_ID = 0;
	  

	  
	  
	  public void onBackPressed()
	  {
		  Intent intent_=new Intent(ShopItemHistory.this, Home.class);
		  intent_.putExtra("returnHome", 1);
		  intent_.putExtra("keyy", keyy);
	      startActivity(intent_);
	      finish();
	  }

	  public void onClick(View paramView)
	  {
		  	
		  	this.clickCheck = (1 + this.clickCheck);
		  	System.out.println("this.clickCheck" + (this.clickCheck));
		  	
		    if ((paramView == findViewById(R.id.printoutreceipt)) && (this.clickCheck == 1))
		    {
		    	this.clickCheck=0;
		    	AlertDialog.Builder adb_41=new AlertDialog.Builder(ShopItemHistory.this);
		    	 adb_41.setTitle("Change Medication Status");
		    	 adb_41.setMessage("No Bluetooth Printer Detected");
		    	 adb_41.setPositiveButton("ok", new DialogInterface.OnClickListener()
		    	 {
		    		 public void onClick(DialogInterface paramDialogInterface, int paramInt)
		    		 {
		    			 
		    			 paramDialogInterface.dismiss();
		    			 
		    		 }
		    	 });
		    	 adb_41.show();
		    }
		    else if ((paramView == findViewById(R.id.emailReceipt)) && (this.clickCheck == 1))
		    {
		    	this.clickCheck=0;
		    	sendPostRequest(emailStr, 
				  		this.keyy,
				  		"Shop",
				  		"EMAIL CONTENTS");
		    }
	  }

	  
	  
	  private void sendPostRequest(String emailStr, 
		  		String keyy,
		  		String feature,
		  		String action) {

				class SendPostReqAsyncTask extends AsyncTask<String, Void, String>{

					ProgressDialog pDialog; 
					
					@Override
					protected void onPreExecute() {
				        pDialog = ProgressDialog.show(ShopItemHistory.this,"Please wait...", "Processing trasaction ...", true);
				    }
					
					
					@Override
					protected String doInBackground(String... params) {

						String emailStr = params[0];
						String keyy = params[1];
						String feature = params[2];
						String action = params[3];

						System.out.println("*** doInBackground ** action " + action + " uniqueId :" + emailStr);

						HttpClient httpClient = new DefaultHttpClient();

						// In a POST request, we don't pass the values in the URL.
						//Therefore we use only the web page URL as the parameter of the HttpPost argument
						HttpPost httpPost = new HttpPost(getString(R.string.posturl));

						// Because we are not passing values over the URL, we should have a mechanism to pass the values that can be
						//uniquely separate by the other end.
						//To achieve that we use BasicNameValuePair				
						//Things we need to pass with the POST request
						BasicNameValuePair emailStrBasicNameValuePair = new BasicNameValuePair("emailStr", emailStr);
						BasicNameValuePair keyyBasicNameValuePAir = new BasicNameValuePair("keyy", keyy);
						BasicNameValuePair featureBasicNameValuePair = new BasicNameValuePair("feature", feature);
						BasicNameValuePair actionBasicNameValuePAir = new BasicNameValuePair("action", action);
						// We add the content that we want to pass with the POST request to as name-value pairs
						//Now we put those sending details to an ArrayList with type safe of NameValuePair
						List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
						nameValuePairList.add(emailStrBasicNameValuePair);
						nameValuePairList.add(keyyBasicNameValuePAir);
						nameValuePairList.add(featureBasicNameValuePair);
						nameValuePairList.add(actionBasicNameValuePAir);
						
						System.out.println("*** doInBackground1 ** feature " + feature + " keyy :" + keyy);
						try {
							// UrlEncodedFormEntity is an entity composed of a list of url-encoded pairs. 
							//This is typically useful while sending an HTTP POST request. 
							UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);
							System.out.println("*** doInBackground1 ** feature " + feature + " keyy :" + keyy);
							// setEntity() hands the entity (here it is urlEncodedFormEntity) to the request.
							httpPost.setEntity(urlEncodedFormEntity);

							try {
								// HttpResponse is an interface just like HttpPost.
								//Therefore we can't initialize them
								HttpResponse httpResponse = httpClient.execute(httpPost);
								System.out.println("*** doInBackground1 ** feature " + feature + " keyy :" + keyy);
								// According to the JAVA API, InputStream constructor do nothing. 
								//So we can't initialize InputStream although it is not an interface
								InputStream inputStream = httpResponse.getEntity().getContent();
								System.out.println("*** doInBackground2 ** feature " + feature + " keyy :" + keyy);
								InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
								System.out.println("*** doInBackground3 ** feature " + feature + " keyy :" + keyy);
								BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
								System.out.println("*** doInBackground4 ** feature " + feature + " keyy :" + keyy);
								StringBuilder stringBuilder = new StringBuilder();

								String bufferedStrChunk = null;

								while((bufferedStrChunk = bufferedReader.readLine()) != null){

									stringBuilder.append(bufferedStrChunk);
								}
								System.out.println("*** doInBackground5 ** feature " + feature + " keyy :" + keyy);
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
						Log.v("result=", result);
						Log.v("original result=", result);
	    				if((result!=null) && (result.length()>0) && (result.startsWith("SUCCESS::::") || result.startsWith("FAIL::::")))
	    				{
	    				}
	    				else
	    				{
	    					result = "FAIL::::We encountered an error while processing your request. Please try again";
	    				}
	    				
	    				
						if(result.startsWith("SUCCESS")){
							Toast.makeText(getApplicationContext(), "HTTP POST is working...", Toast.LENGTH_LONG).show();
							AlertDialog.Builder adb_51 = new AlertDialog.Builder(ShopItemHistory.this);
			                Log.v("Track2", "Clicked1");
			                (adb_51).setTitle("Sending Option");
			                Log.v("Track3", "Clicked2122211");
			                ((AlertDialog.Builder)adb_51).setMessage(result.substring("SUCCESS::::".length()));
			                ((AlertDialog.Builder)adb_51).setPositiveButton("Ok", new DialogInterface.OnClickListener()
			                {
			                  public void onClick(DialogInterface paramDialogInterface, int paramInt)
			                  {
			                	  clickCheck=0;
			                	  Enumeration localEnumeration;
			                	  Object localObject;
			                	  Bundle b = new Bundle();
			                	  b.putString("keyy", ShopItemHistory.this.keyy);
			                	  Intent intent = new Intent(ShopItemHistory.this, Home.class);
			                	  intent.putExtras(b);
			                	  startActivity(intent);
			                	  finish();
			                    
			                  }
			                });	
			    	        ((AlertDialog.Builder)adb_51).show();
						}else{
							Toast.makeText(getApplicationContext(), "Processing Errors occured.", Toast.LENGTH_LONG).show();
							
							Toast.makeText(getApplicationContext(), "HTTP POST is working...", Toast.LENGTH_LONG).show();
							AlertDialog.Builder adb_51 = new AlertDialog.Builder(ShopItemHistory.this);
			                Log.v("Track2", "Clicked1");
			                (adb_51).setTitle("Sending Option");
			                Log.v("Track3", "Clicked2122211");
			                ((AlertDialog.Builder)adb_51).setMessage(result.substring("FAIL::::".length()));
			                ((AlertDialog.Builder)adb_51).setPositiveButton("Ok", new DialogInterface.OnClickListener()
			                {
			                  public void onClick(DialogInterface paramDialogInterface, int paramInt)
			                  {
								  clickCheck=0;
			                	  Enumeration localEnumeration;
			                	  Object localObject;
			                	  Bundle b = new Bundle();
			                	  b.putString("keyy", ShopItemHistory.this.keyy);
			                	  Intent intent = new Intent(ShopItemHistory.this, Home.class);
			                	  intent.putExtras(b);
			                	  startActivity(intent);
			                	  finish();
			                  }
			                });	
			    	        ((AlertDialog.Builder)adb_51).show();
		                    
						}
					}			
				}

				SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
				sendPostReqAsyncTask.execute(emailStr, 
				  		keyy,
				  		feature,
				  		action);		
			}
		  
		  
	  
	  public void onCreate(Bundle paramBundle)
	  {
		  Log.i("just started ShopItemHistory activity", "yes");
		  System.out.println("btn.toString=");
		  super.onCreate(paramBundle);
		  Bundle bundle=getIntent().getExtras();
	      
		  
	      //Log.v("bundle.size=", "" + bundle.size());
	      if(bundle!=null)
	      {
		        if(bundle.containsKey("keyy"))
		        {
		        	this.keyy=bundle.getString("keyy");
		        	Log.v("keyy when entering home =", "" + this.keyy);
		        }
		        if(bundle.containsKey("ScreenType"))
		        {
		        	this.presentFeatureCode=bundle.getString("ScreenType");
		        	Log.v("presentFeatureCode when entering AddDrug =", "" + this.presentFeatureCode);
		        }
		        
		        if(bundle.containsKey("shop_item_position"))
		        {
		        	this.shop_item_position=bundle.getString("shop_item_position");
		        	Log.v("shop_item_position when entering Shop Item description =", "" + this.shop_item_position);
		        }
		        else
		        {
		        	ShopItemHistory.this.myIntent = new Intent(ShopItemHistory.this, Home.class);
                    myIntent.putExtra("ScreenType", "MAINMENU");
                    myIntent.putExtra("keyy", ShopItemHistory.this.keyy);
                    ShopItemHistory.this.startActivity(myIntent);
                    finish();
		        }
		        
	      }
	      else
	      {
		      	Intent myIntent = new Intent(ShopItemHistory.this, SignIn.class);
		      	ShopItemHistory.this.startActivity((Intent)myIntent);
		      	finish();
	      }
	      
	      
		  setContentView(R.layout.shopitemhistory);
		  this.setTitle("My Sold Items Detail!");
		  
		  this.clickCheck =0;
		  
		  shopItemSoldFor = ((TextView)findViewById(R.id.shopItemSoldFor));
		  shopItemDateBought = ((TextView)findViewById(R.id.shopItemDateBought));
		  shopItemPrice = ((TextView)findViewById(R.id.shopItemPrice));
		  shopItemSoldFor = ((TextView)findViewById(R.id.shopItemSoldFor));
		  shopItemTitle = ((TextView)findViewById(R.id.shopItemTitle));
		    
		  		  
		    
		    this.btn = ((Button)findViewById(R.id.printReceipt));
		    this.btn.setOnClickListener(this);

		    
		    
		    if(this.shop_item_position!=null)
		    {
		    	d=new db(this);
		    	System.out.println("shop item position " + shop_item_position);
		    	
		    	Cursor c=d.openHelper.fetchRowType2(getString(R.string.shopItemsHistory), new String[]{"_id", 
		    		"shopItemId", "timeLogged", "amountSoldFor", "uniqueId"}, null, null, null, this.shop_item_position + ", 1", null, null);
		    	if(c.getCount()>0)
		    	{
		    	
		    		c.moveToFirst();
		    	
			    	Cursor c1=d.openHelper.fetchRowType2(getString(R.string.shopItems), new String[]{"_id", 
			    		"shopItemName", "shopItemType", "amount", "shortDescription"}, "_id=?", new String[]{Integer.toString(c.getInt(1))}, null, null, null, null);
			    	
			    	
			    	if(c1.getCount()>0)
			    	{
			    		c1.moveToFirst(); 	
		  				this.shopItemSoldFor.setText(c1.getString(1));
		  		    	this.shopItemDateBought.setText(c.getString(2));
		  		    	this.shopItemPrice.setText(c1.getString(3));
		  		    	this.shopItemSoldFor.setText(c.getString(3));
		  		    	this.shopItemTitle.setText(c1.getString(4));
		  		    	
		  		    	emailStr="Hi,<br>Kindly find attached your receipt for your purchase.<br><br>" +
		  		    			"Transaction Id: " + c.getString(4) +
		  		    			"Shop item: " + c1.getString(4) +
		  		    			"Item Type: " + c1.getString(2) +
		  		    			"Amount: " + c.getString(3) +
		  		    			"Date Purchased: " + c.getString(2) +
		  		    			"Regards \n Administrator ";
		  		    	
		  		    	c1.close();
			              
			    	}
			    	else
			    	{
			    		AlertDialog.Builder adb_4=new AlertDialog.Builder(ShopItemHistory.this);
		      	        adb_4.setTitle("View Shop Item");
		      	        adb_4.setMessage("Shop Item Not Found Try Again");
		      	        adb_4.setPositiveButton("Ok", new DialogInterface.OnClickListener()
		      	        {
		      	          public void onClick(DialogInterface paramDialogInterface, int paramInt)
		      	          {
		      	        	  ShopItemHistory.this.myIntent = new Intent(ShopItemHistory.this, Home.class);
		                      myIntent.putExtra("ScreenType", "MAINMENU");
		                      myIntent.putExtra("keyy", ShopItemHistory.this.keyy);
		                      ShopItemHistory.this.startActivity(myIntent);
		                      finish();
		      	          }
		      	        });
		      	        
		      	        adb_4.show();
			    	}
		    	}
		    	else
		    	{
		    		AlertDialog.Builder adb_4=new AlertDialog.Builder(ShopItemHistory.this);
	      	        adb_4.setTitle("View Shop Item");
	      	        adb_4.setMessage("Shop Item Not Found Try Again");
	      	        adb_4.setPositiveButton("Ok", new DialogInterface.OnClickListener()
	      	        {
	      	          public void onClick(DialogInterface paramDialogInterface, int paramInt)
	      	          {
	      	        	  ShopItemHistory.this.myIntent = new Intent(ShopItemHistory.this, Home.class);
	                      myIntent.putExtra("ScreenType", "MAINMENU");
	                      myIntent.putExtra("keyy", ShopItemHistory.this.keyy);
	                      ShopItemHistory.this.startActivity(myIntent);
	                      finish();
	      	          }
	      	        });
	      	        
	      	        adb_4.show();
		    	}
		    	
		    }
		    else
		    {
		    	Intent myIntent = new Intent(ShopItemHistory.this, Home.class);
		    	myIntent.putExtra("returnHome", 1);
				myIntent.putExtra("keyy", ShopItemHistory.this.keyy);
	            ShopItemHistory.this.startActivity((Intent)myIntent);
		    }
		    
		    
	  }

	  public void onCreateContextMenu(ContextMenu paramContextMenu, View paramView, ContextMenu.ContextMenuInfo paramContextMenuInfo)
	  {
		  if ((paramView.getId() != R.id.directions) || (paramView.getId() != R.id.medicationType))
		      super.onCreateContextMenu(paramContextMenu, paramView, paramContextMenuInfo);
		    else
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
		    	this.clickCheck = 0;
		    	if (paramInt == KeyEvent.KEYCODE_BACK && paramKeyEvent.getRepeatCount() == 0) {
		            // do something on back.
		            finish();
		            Intent intent_=new Intent(ShopItemHistory.this, Home.class);
		      	  	intent_.putExtra("returnHome", 1);
		      	  	intent_.putExtra("keyy", keyy);
		            startActivity(intent_);
		            bool =  true;
		        }
		        else
		        {
		        	bool =  super.onKeyDown(paramInt, paramKeyEvent);
		        }
		    }
		    return bool;
	  }
	}
