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
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


public class SellItem extends Activity
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
	  private TextView shopItemTypeEdit;
	private TextView amountEdit;
	private TextView shopItemDescription;
	private EditText shopItemQuantity;
	private EditText discountField;
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
	
	static final int DATE_DIALOG_ID = 0;


public SellItem()
{
	  	
		//this.timeList = arrayOfString;
		this.clickCheck = 0;
		this.h1_reverse = new Hashtable();
		
}


public void onBackPressed()
{
	    Intent intent_=new Intent(SellItem.this, Home.class);
	    intent_.putExtra("returnHome", 1);
	    intent_.putExtra("keyy", SellItem.this.keyy);
	    intent_.putExtra("ScreenType", "MAINMENU");
	    startActivity(intent_);
	    finish();
	    
}

public void onClick(View paramView)
{
	  try
	  {

		   if ((paramView == findViewById(R.id.shopItemQuantity)))
		    {
		  		Log.v("hehehehehe=", "as");
		  		registerForContextMenu(((EditText)findViewById(R.id.shopItemQuantity)));
		  		openContextMenu(this.shopItemQuantity);
		  		//unregisterForContextMenu(this.securityQuestion);
		  		
		    }
		    else
		    {
			  	this.clickCheck = (1 + this.clickCheck);
			  	System.out.println("this.clickCheck" + (this.clickCheck));
			  	
			  	if ((paramView == findViewById(R.id.cancelButton)) && (this.clickCheck == 1))
			    {
			  		this.clickCheck=0;
			  		SellItem.this.myIntent = new Intent(SellItem.this, Home.class);
		            myIntent.putExtra("keyy", SellItem.this.keyy);
		            Log.i("Inside Medication","Cancel Sale");
		            SellItem.this.startActivity(myIntent);
		            finish();
			    }
			  	else if ((paramView == findViewById(R.id.sellButton)) && (this.clickCheck == 1))
			    {
			      Object localObject1;
			      if (((TextView)findViewById(R.id.shopItemTitleEdit)).getText().toString().length()==0)
			      {
			        processFlow=false; processFlowMsg="No medication name provided";
			        
			      }
			      else if (((TextView)findViewById(R.id.shopItemQuantity)).getText().toString().equals("-Select One-"))
			      {
				        processFlow=false; processFlowMsg="No quantity specified";
			      }
			      else if ((((TextView)findViewById(R.id.shopItemTypeEdit)).getText().toString().length()==0))
			      {
				       	processFlow=false; processFlowMsg="The Type of Shop Item cannot be found";
				        
			      }
			      else if (((TextView)findViewById(R.id.amountEdit)).getText().toString().length()==0)
			      {
				        processFlow=false; processFlowMsg="No Pricing Found for this Item!";
				        
			      }
			      else
			      {
			    	  processFlow=true;
			      }
			      if(processFlow==false)
			      {
			    	  AlertDialog.Builder adb_2 = new AlertDialog.Builder(SellItem.this);
			            (adb_2).setTitle("Error Message");
			            (adb_2).setMessage(processFlowMsg);
			            (adb_2).setPositiveButton("Ok", new DialogInterface.OnClickListener()
			            {
			              public void onClick(DialogInterface paramDialogInterface, int paramInt)
			              {
			              		clickCheck=0;
			              		paramDialogInterface.dismiss();
		
			              		/*Bundle b = new Bundle();
			                	b.putString("keyy", SellItem.this.keyy);
			                	Intent intent = new Intent(SellItem.this, SellItem.class);
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
				        if(this.applyDiscountSelected==true)
				        {
				        	discountApplied=((EditText)discountField).getText().toString();
				        	if(Long.valueOf(discountApplied)>0)
				        	{
				        		amountValue=discountApplied;
				        	}
				        	else
				        	{
				        		amountValue=this.amountEdit.getText().toString();
				        	}
				        }
				        
				        
				        DateFormat df=new SimpleDateFormat("yyyy/MM/DD HH:mm:ss");
	            	  	Calendar cal=Calendar.getInstance();
	            	  	String currentdate=df.format(cal.getTime());
				        
				            Log.i("proceed", "Clicked");
				            
					            
			            uniqueId=UUID.randomUUID().toString();
			            System.out.println("2...shop_item_position = " + shop_item_position);
			            sendPostRequest( 
		                		Integer.toString(this.shop_item_position), 
		                		currentdate,
		                		amountValue,
		                		((EditText)SellItem.this.findViewById(R.id.shopItemQuantity)).getText().toString(),  
		                		keyy, 
		                		"shop",
		                		"sell item",
		                		uniqueId); 
				          
			      }
			    }
		    }
	  	}
		catch (Exception e)
		{
			SellItem.this.e = e;
			AlertDialog.Builder adb_51 = new AlertDialog.Builder(SellItem.this);
		    Log.v("Track2", "Clicked1");
		    (adb_51).setTitle("Sending Option");
		    Log.v("Track3", "Clicked2122211");
		    ((AlertDialog.Builder)adb_51).setMessage("Error occured while processing your request. Submit error to our team");
		    ((AlertDialog.Builder)adb_51).setPositiveButton("Ok", new DialogInterface.OnClickListener()
		    {
			      public void onClick(DialogInterface paramDialogInterface, int paramInt)
			      {
			      	//SellItem.this.e.;
				      	SellItem.this.w = new StringWriter();
				      	SellItem.this.e.printStackTrace(new java.io.PrintWriter(SellItem.this.w));
				      	
				      	sendPostRequest(w.toString(),
				      			"", "", "",
				      			SellItem.this.keyy,
				      	  		"message",
				      	  		"log error",
				      	  		"");
			      }
		    });	
		    ((AlertDialog.Builder)adb_51).show();
		    
		}
}


private void sendPostRequest(String shopItemId, 
	  		String timeLogged,
	  		String amountSoldFor,
	  		String quantity, 
	  		String keyy, 
	  		String feature,
	  		String action,
	  		String uniqueId) {
	  try
	  {
			class SendPostReqAsyncTask extends AsyncTask<String, Void, String>{

				ProgressDialog pDialog; 
				String shopItemId; 
				String timeLogged; 
				String amountSoldFor;
				String quantity;
				String keyy; 
				String feature; 
				String action; 
				String uniqueId;
				
				@Override
				protected void onPreExecute() {
			        pDialog = ProgressDialog.show(SellItem.this,"Please wait...", "Processing trasaction ...", true);
			    }
				
				
				@Override
				protected String doInBackground(String... params) {

					shopItemId = params[0];
					timeLogged = params[1];
					amountSoldFor = params[2];
					quantity = params[3];
					keyy = params[4];
					feature = params[5];
					action = params[6];
					uniqueId = params[7];

							
					d=new db(SellItem.this);
		            Object localObject3 = d.openHelper;
		            String str = SellItem.this.getString(R.string.shopItemsHistory);
		            String[] arrayOfString1 = new String[5];
		            arrayOfString1[0] = "_id";
		            arrayOfString1[1] = "shopItemId";
		            arrayOfString1[2] = "timeLogged";
		            arrayOfString1[3] = "amountSoldFor";
		            arrayOfString1[4] = "uniqueId";
		            String[] localObject1 = new String[5];
		            localObject1[0] = null;
		            localObject1[1] = shopItemId;
		            localObject1[2] = timeLogged;
		            localObject1[3] = amountSoldFor;
		            localObject1[4] = uniqueId;
	
		            System.out.println("2...shop_item_position = " + shopItemId);
		            System.out.println("2...shop_item_position = " + timeLogged);
		            System.out.println("2...shop_item_position = " + amountSoldFor);
		            System.out.println("2...shop_item_position = " + uniqueId);
		            
		            
		            System.out.println("Long.valueOf(shop_item_position)=" + todo);
		            

	            	long i=((d.openHelper)).createRow(str, arrayOfString1, localObject1);
	            	if(i>0)
	            	{
	            		ContentValues args=new ContentValues();
	            		args.put("soldYes", "1");
	            		
	            		if(d.openHelper.updateRow(args, "_id=?", new String[]{shopItemId}, getString(R.string.shopItems))==0)
	            		{
	            			d.openHelper.deleteRow(i, str, "_id");
	            			((d.openHelper)).close();
	            			d.close();
	            			return "FAIL::::Transaction Failed!";
	            		}
	            		else
	            		{
	            			((d.openHelper)).close();
	            			d.close();
							return "SUCCESS::::Transaction Successful!";
	            		}
	            	}
	            	else
	            	{
	            		((d.openHelper)).close();
			            d.close();
			            return "FAIL::::Transaction Failed!";
	            	}
		            

						
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
						
						
						Toast.makeText(getApplicationContext(), "Processing...", Toast.LENGTH_LONG).show();
						//Log.v("return value=", valuer);
		    			AlertDialog.Builder adb_51 = new AlertDialog.Builder(SellItem.this);
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
		                	  
		                	  	AlertDialog.Builder adb_52 = new AlertDialog.Builder(SellItem.this);
				                Log.v("Track2", "Clicked1");
				                (adb_52).setTitle("Sending Option");
				                Log.v("Track3", "Clicked2122211");
				                
				                
				                ((AlertDialog.Builder)adb_52).setMessage("Sell Another Shop Item?");
				                ((AlertDialog.Builder)adb_52).setPositiveButton("Yes", new DialogInterface.OnClickListener()
				                {
				                  public void onClick(DialogInterface paramDialogInterface, int paramInt)
				                  {
				                	  clickCheck=0;
				                	  SellItem.this.myIntent = new Intent(SellItem.this, Scanner.class);
				                      myIntent.putExtra("ScreenType", new menus().all_services()[0]);
				                      myIntent.putExtra("keyy", SellItem.this.keyy);
				                      Log.i("Inside Medication","Add New Medication After Adding First");
				                      SellItem.this.startActivity(myIntent);
				                      finish();
				                  }
				                });
				                ((AlertDialog.Builder)adb_52).setNegativeButton("No", new DialogInterface.OnClickListener()
				                {
				                	public void onClick(DialogInterface paramDialogInterface, int paramInt)
					                  {
					                	  clickCheck=0;
					                	  SellItem.this.myIntent = new Intent(SellItem.this, Home.class);
					                      myIntent.putExtra("keyy", SellItem.this.keyy);
					                      Log.i("Inside Medication","Going Back Home");
					                      SellItem.this.startActivity(myIntent);
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
	                      SellItem.this.clickCheck = 0;
	                      Log.v("Insert syncdb_date ", "failure");
	                      SellItem.this.clickCheck = 0;
	    					Toast.makeText(getApplicationContext(), "Processing...", Toast.LENGTH_LONG).show();
	    					AlertDialog.Builder adb_4=new AlertDialog.Builder(SellItem.this);
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
	                      //Intent myIntent = new Intent(SellItem.this, SellItem.class);
	                      //SellItem.this.startActivity((Intent)myIntent);
	                    
					}
				}			
			}

			SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
			sendPostReqAsyncTask.execute(shopItemId, 
			  		 timeLogged,
			  		 amountSoldFor,
			  		 quantity, 
			  		 keyy, 
			  		 feature,
			  		 action,
			  		 uniqueId);
		}
		catch (Exception e)
		{
			SellItem.this.e = e;
			AlertDialog.Builder adb_51 = new AlertDialog.Builder(SellItem.this);
		    Log.v("Track2", "Clicked1");
		    (adb_51).setTitle("Sending Option");
		    Log.v("Track3", "Clicked2122211");
		    ((AlertDialog.Builder)adb_51).setMessage("Error occured while processing your request. Submit error to our team");
		    ((AlertDialog.Builder)adb_51).setPositiveButton("Ok", new DialogInterface.OnClickListener()
		    {
			      public void onClick(DialogInterface paramDialogInterface, int paramInt)
			      {
			      	//SellItem.this.e.;
				      	SellItem.this.w = new StringWriter();
				      	SellItem.this.e.printStackTrace(new java.io.PrintWriter(SellItem.this.w));
				      	
				      	sendPostRequest(w.toString(),
				      			"", "", "",
				      			SellItem.this.keyy,
				      	  		"message",
				      	  		"log error",
				      	  		"");
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
		    	  this.shopItemQuantity.setText(item.getTitle());
		    	  this.currentMenuList = "";
		      }
			  else
			  {
				  return false;
			  }
	  	}
		catch (Exception e)
		{
			SellItem.this.e = e;
			AlertDialog.Builder adb_51 = new AlertDialog.Builder(SellItem.this);
		    Log.v("Track2", "Clicked1");
		    (adb_51).setTitle("Sending Option");
		    Log.v("Track3", "Clicked2122211");
		    ((AlertDialog.Builder)adb_51).setMessage("Error occured while processing your request. Submit error to our team");
		    ((AlertDialog.Builder)adb_51).setPositiveButton("Ok", new DialogInterface.OnClickListener()
		    {
			      public void onClick(DialogInterface paramDialogInterface, int paramInt)
			      {
			      	//SellItem.this.e.;
				      	SellItem.this.w = new StringWriter();
				      	SellItem.this.e.printStackTrace(new java.io.PrintWriter(SellItem.this.w));
				      	
				      	sendPostRequest(w.toString(),
				      			"", "", "",
				      			SellItem.this.keyy,
				      	  		"message",
				      	  		"log error",
				      	  		"");
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
		  Log.i("just started SellItem activity", "yes");
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
		        	if(bundle.containsKey("shop_item_position"))
			        {
			        	this.shop_item_position = bundle.getInt("shop_item_position");
			        	System.out.println("shop_item_position = " +  this.shop_item_position);
			        }
		        	else
		        	{
		        		this.myIntent = new Intent(SellItem.this, SignIn.class);
		        		this.myIntent.putExtra("noKeyReDirect", 1);
		        		SellItem.this.startActivity(myIntent);
			            finish();
		        	}
		        }
		        else
		        {
		        	this.myIntent = new Intent(SellItem.this, SignIn.class);
	        		this.myIntent.putExtra("noKeyReDirect", 1);
	        		SellItem.this.startActivity(myIntent);
		            finish();
		        }
		        

		        
	      }
	      else
	      {
	      	Intent myIntent = new Intent(SellItem.this, SignIn.class);
	      	SellItem.this.startActivity((Intent)myIntent);
	      	finish();
	      }
	      
		  setContentView(R.layout.sell_item);
		  
		  
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
		    this.shopItemTypeEdit = ((TextView)findViewById(R.id.shopItemTypeEdit));
		    this.shopItemQuantity = ((EditText)findViewById(R.id.shopItemQuantity));
		    this.shopItemDescription = ((TextView)findViewById(R.id.shopItemDescription));
		    this.amountEdit = ((TextView)findViewById(R.id.amountEdit));
		    
		    this.shopItemQuantity.setOnClickListener(this);
		    
		    
		    this.applyDiscountYes=(RadioButton)findViewById(R.id.applyDiscountYes);
		    this.applyDiscountNo=(RadioButton)findViewById(R.id.applyDiscountNo);
		    applyDiscountYes.setOnCheckedChangeListener(this);
		    applyDiscountNo.setOnCheckedChangeListener(this);
		    
		    
	    	System.out.println("localBundle.getInt(shop_item_position)=" + localBundle.getInt("shop_item_position"));
	    	this.todo = localBundle.getString("todo");
	    	d = new db(this);
	    	Cursor c=d.openHelper.fetchRowType2(getString(R.string.shopItems), new String[]{"_id", "shopItemName", "shopItemType", "amount", "shortDescription"}, 
	    			"_id=?", new String[]{Integer.toString(this.shop_item_position)}, null, null, null, null);
	    	if(c.getCount()>0)
	    	{
	    		c.moveToFirst(); 	
	    		System.out.println("_id= " + c.getString(0));
  				this.shopItemTitleEdit.setText(c.getString(1));
  		    	this.shopItemTypeEdit.setText(c.getString(2));
  		    	this.amountEdit.setText(c.getString(3));
  		    	this.shopItemDescription.setText(c.getString(4));
  		    	this.shopItemQuantity.setText("1");
  		    	c.close();
	            d.close();
	    	}
	    	else
	    	{
	    		c.close();
	    		d.close();
	    		AlertDialog.Builder adb_4=new AlertDialog.Builder(SellItem.this);
      	        adb_4.setTitle("Edit Medication");
      	        adb_4.setMessage("Shop Item Not Found. Try Again");
      	        adb_4.setPositiveButton("Ok", new DialogInterface.OnClickListener()
      	        {
      	          public void onClick(DialogInterface paramDialogInterface, int paramInt)
      	          {
      	        	  SellItem.this.myIntent = new Intent(SellItem.this, Home.class);
                      myIntent.putExtra("ScreenType", "MAINMENU");
                      myIntent.putExtra("keyy", SellItem.this.keyy);
                      SellItem.this.startActivity(myIntent);
                      finish();
      	          }
      	        });
      	        
      	        adb_4.show();
	    	}
		    		
		    	
		    
		}
		catch (Exception e)
		{
			SellItem.this.e = e;
			AlertDialog.Builder adb_51 = new AlertDialog.Builder(SellItem.this);
		    Log.v("Track2", "Clicked1");
		    (adb_51).setTitle("Sending Option");
		    Log.v("Track3", "Clicked2122211");
		    ((AlertDialog.Builder)adb_51).setMessage("Error occured while processing your request. Submit error to our team");
		    ((AlertDialog.Builder)adb_51).setPositiveButton("Ok", new DialogInterface.OnClickListener()
		    {
			      public void onClick(DialogInterface paramDialogInterface, int paramInt)
			      {
			      	//SellItem.this.e.;
				      	SellItem.this.w = new StringWriter();
				      	SellItem.this.e.printStackTrace(new java.io.PrintWriter(SellItem.this.w));
				      	
				      	sendPostRequest(w.toString(),
				      			"", "", "",
				      			SellItem.this.keyy,
				      	  		"message",
				      	  		"log error",
				      	  		"");
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
			SellItem.this.e = e;
			AlertDialog.Builder adb_51 = new AlertDialog.Builder(SellItem.this);
		    Log.v("Track2", "Clicked1");
		    (adb_51).setTitle("Sending Option");
		    Log.v("Track3", "Clicked2122211");
		    ((AlertDialog.Builder)adb_51).setMessage("Error occured while processing your request. Submit error to our team");
		    ((AlertDialog.Builder)adb_51).setPositiveButton("Ok", new DialogInterface.OnClickListener()
		    {
			      public void onClick(DialogInterface paramDialogInterface, int paramInt)
			      {
			      	//SellItem.this.e.;
				      	SellItem.this.w = new StringWriter();
				      	SellItem.this.e.printStackTrace(new java.io.PrintWriter(SellItem.this.w));
				      	
				      	sendPostRequest(w.toString(),
				      			"", "", "",
				      			SellItem.this.keyy,
				      	  		"message",
				      	  		"log error",
				      	  		"");
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
		    	Intent intent_=new Intent(SellItem.this, Home.class);
		    	intent_.putExtra("returnHome", 1);
			    intent_.putExtra("keyy", SellItem.this.keyy);
		 	    startActivity(intent_);
		 	    finish();
		      bool = true;
		    }
		    
	  	}
	  	catch (Exception e)
	  	{
			SellItem.this.e = e;
			AlertDialog.Builder adb_51 = new AlertDialog.Builder(SellItem.this);
		    Log.v("Track2", "Clicked1");
		    (adb_51).setTitle("Sending Option");
		    Log.v("Track3", "Clicked2122211");
		    ((AlertDialog.Builder)adb_51).setMessage("Error occured while processing your request. Submit error to our team");
		    ((AlertDialog.Builder)adb_51).setPositiveButton("Ok", new DialogInterface.OnClickListener()
		    {
			      public void onClick(DialogInterface paramDialogInterface, int paramInt)
			      {
			      	//SellItem.this.e.;
				      	SellItem.this.w = new StringWriter();
				      	SellItem.this.e.printStackTrace(new java.io.PrintWriter(SellItem.this.w));
				      	
				      	sendPostRequest(w.toString(),
				      			"", "", "",
				      			SellItem.this.keyy,
				      	  		"message",
				      	  		"log error",
				      	  		"");
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
			if(buttonView == findViewById(R.id.applyDiscountYes))
			{
				this.applyDiscountSelected=true;
				((RadioButton)findViewById(R.id.applyDiscountNo)).setChecked(false);
				tv01=new TextView(this);
				//tv01.setLineSpacing(0, getResources().getDimensionPixelSize(R.dimen.padding_left));
				tv01.setLayoutParams(new LinearLayout.LayoutParams(
	                    LinearLayout.LayoutParams.FILL_PARENT,
	                    LinearLayout.LayoutParams.WRAP_CONTENT, 1));
				tv01.setText("Type the discounted price:");
				
				
				this.discountAmount=new EditText(this);
				//this.discountAmount.setLineSpacing(0, getResources().getDimensionPixelSize(R.dimen.padding_left));
				this.discountAmount.setLayoutParams(new LinearLayout.LayoutParams(
	                    LinearLayout.LayoutParams.FILL_PARENT,
	                    LinearLayout.LayoutParams.WRAP_CONTENT, 1));
				this.discountAmount.setInputType(InputType.TYPE_CLASS_NUMBER);

				
				LinearLayout linLay1=((LinearLayout)findViewById(R.id.discountHolder));
	            linLay1.addView(tv01, linLay1.getChildCount());
	            linLay1.addView(this.discountAmount, linLay1.getChildCount());
	            
	            
			}
			else if(buttonView == findViewById(R.id.endDateNo))
			{
				((RadioButton)findViewById(R.id.endDateYes)).setChecked(false);
				if(this.applyDiscountSelected==true)
				{
					LinearLayout linLay1=((LinearLayout)findViewById(R.id.discountHolder));
					linLay1.removeViewAt((linLay1.getChildCount() - 2));
				    linLay1.removeViewAt((linLay1.getChildCount() - 1));
				}
				this.applyDiscountSelected=false;
			}
		}
	}
}
