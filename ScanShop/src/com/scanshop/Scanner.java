package com.scanshop;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;




public class Scanner extends Activity
  implements View.OnClickListener
{
  private Context a;
  private Button btn;
  private EditText city;
  private int clickCheck=0;
  private EditText confirmpassword;
  private Context context;
  private EditText country;
  private menus menu;
  private Intent myIntent;
  static String[] countryList={"Nigeria", "Zambia", "Ghana", "USA", "Canada", "United Kingdom", 
		"Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", 
		"Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", 
		"Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", 
		"Bosnia and Herzegovina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory", 
		"British Virgin Islands", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", 
		"Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", 
		"Cocos Islands", "Colombia", "Comoros", "Congo", "Cook Islands", "Costa Rica", "Croatia", "Cuba", "Cyprus", 
		"Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", 
		"El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands", "Faroe Islands", 
		"Fiji", "Finland", "France", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", 
		"Germany", "Gibraltar", "Great Britain", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", 
		"Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and McDonald Islands", "Honduras", "Hong Kong", "Hungary", "Iceland", 
		"India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Ivory Coast", "Jamaica", "Japan", "Jordan", "Kazakhstan", 
		"Kenya", "Kiribati", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", 
		"Luxembourg", "Macau", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", 
		"Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", 
		"Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", 
		"New Zealand", "Nicaragua", "Niger", "Niue", "Norfolk Island", "North Korea", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", 
		"Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn Island", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", 
		"Russia", "Rwanda", "S. Georgia and S. Sandwich Isls.", "Saint Kitts &amp; Nevis", "Saint Lucia", "Saint Vincent and The Grenadines", "Samoa", 
		"San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Serbia and Montenegro", "Seychelles", "Sierra Leone", "Singapore", 
		"Slovakia", "Slovenia", "Somalia", "South Africa", "South Korea", "Spain", "Sri Lanka", "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname", 
		"Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Togo", "Tokelau", 
		"Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "U.S. Minor Outlying Islands", "Uganda", 
		"Ukraine", "United Arab Emirates", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Virgin Islands", "Wallis and Futuna Islands", 
		"Western Sahara", "Yemen", "Zaire", "Zimbabwe"};
  private db d;
  private SQLiteDatabase db;
  private EditText email;
  private String emailValue;
  private EditText firstName;
  Hashtable h1_reverse;
  private String keyy;
  private EditText lastName;
  private EditText mobile;
  private EditText password;
  private String phoneNumberValue;
  boolean procChoice = true;
  private EditText state;
  private String token;
  private EditText username;
protected StringWriter w;
protected Exception e;
protected String presentFeatureCode;
protected String action;
private String contents1_;
private String contents1;
private String format1;
private boolean backPressedProceed;
  

  public Scanner()
  {
	  	
		//this.countryList = arrayOfString;
		this.clickCheck = 0;
		this.h1_reverse = new Hashtable();
		menu=new menus();
  }



  
  public void onBackPressed()
  {
	  if(backPressedProceed==true)
	  {
		  Bundle b = new Bundle();
		  b.putString("keyy", this.keyy);
		  Intent intent = new Intent(Scanner.this, Home.class);
		  intent.putExtras(b);
	      startActivity(intent);
	      finish();
	  }
  }

  public void onClick(View paramView)
  {
	  	this.clickCheck = (1 + this.clickCheck);
	    if ((paramView == findViewById(R.id.ScannerOk)) && (this.clickCheck == 1))
	    {
	      Object localObject1;
	      
	    }
  }

  public boolean onContextItemSelected(MenuItem item)
  {
    
	  AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
	    this.country.setText(item.getTitle());
	    return true;
  }
  
  
  

  public void onCreate(Bundle paramBundle)
  {
	  super.onCreate(paramBundle);
	  Log.i("just started Scanner activity", "yes");
	  if(this.getString(R.string.demo).equals("demo1"))
	  {
		  Log.i("just started demo", "yes1");
		  Log.i("this.menu._medication()[0]==", this.menu._medication()[0].toString());
		  
		  Bundle bundle=getIntent().getExtras();
		  if(bundle!=null)
	      {
			  
			  if(bundle.containsKey("keyy"))
			  {
				  Scanner.this.keyy=bundle.get("keyy").toString();
				  if(bundle.containsKey("ScreenType") && (bundle.get("ScreenType").toString().equals(this.menu.all_services()[0]) || bundle.get("ScreenType").toString().equals(this.menu.all_services()[4])))
				  {
					  this.presentFeatureCode=bundle.get("ScreenType").toString();

					  
		        	  Boolean callUpScannerApplication=false;
		        	  
		        	  
		        	  /**/Intent intent1 = new Intent("com.google.zxing.client.android.SCAN");
		        	  List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent1, PackageManager.MATCH_DEFAULT_ONLY);    
        	          if(list.size() > 0)
        	          {
        	        	 callUpScannerApplication=true;
        	        	 Toast.makeText(Scanner.this, "Starting Scanner Application", Toast.LENGTH_LONG );
        	          }
		        	        	 
		        	        	 
		        	  final PackageManager pm = getPackageManager();

		        	  List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
		        	  for(int i=0;i<packages.size(); i++)
		        	  {
		        		  ApplicationInfo ai=(ApplicationInfo)packages.get(i);
		        		  System.out.println("App installed" + ai.packageName);
		        		  if(ai.packageName.toLowerCase().equals("com.google.zxing.client.android.scan"))
		        		  {
		        			  callUpScannerApplication=true;
		        		  }
		        	  }
		        	  if(callUpScannerApplication==true)
		        	  {
			        	  Intent intent = new Intent("com.google.zxing.client.android.SCAN"); 
			        	  startActivityForResult(intent, 0);
			        	  //finish();
		        		  //sendPostRequest("shop", "identify medication", "010181141805", "010181141805");
		        		  /*d=new db(this);
						  Cursor c=d.openHelper.fetchRowType2(getString(R.string.shopItems), new String[]{"_id", "uniqueId", "shopItemName", "shopItemType", "amount", "shortDescription"}, "qr_code=? AND soldYes!= ?", new String[]{"010181141805", "1"}, null, null, null, null);
						  
				    	  if(c.getCount()>0)
				    	  {
				    		  c.moveToFirst();
				    		  //Synchronize.this.d.openHelper.deleteRow(c.getInt(0), getString(R.string.shopItems), "_id");
				    		   	Intent result = new Intent(Scanner.this, SellItem.class);
				            	result.putExtra("returnHome", 1);
				            	result.putExtra("keyy", Scanner.this.keyy);
				            	result.putExtra("shop_item_position", c.getInt(0));
				            	c.close();
				            	d.close();
				            	Scanner.this.startActivity(result);
				            	finish();
				    		  
				    	  }
				    	  else
				    	  {

				    		  AlertDialog.Builder adb_4=new AlertDialog.Builder(Scanner.this);
						        adb_4.setTitle("Scan Shop Item");
						        adb_4.setMessage("No Shop items match the item you scanned");
						        adb_4.setPositiveButton("Ok", new DialogInterface.OnClickListener()
						        {
							          public void onClick(DialogInterface paramDialogInterface, int paramInt)
							          {
							        	  //paramDialogInterface.dismiss();
							        	  
						        		  Scanner.this.myIntent = new Intent(Scanner.this, Home.class);
						        		  myIntent.putExtra("returnHome", 1);
					                      myIntent.putExtra("keyy", Scanner.this.keyy);
					                      Log.i("Inside Medication","No Shop items match the item you scanned");
					                      Scanner.this.startActivity(myIntent);
					                      finish();
							        	  
							          }
						        });
						        
						        adb_4.show();
						        c.close();
				            	d.close();
				    	  }*/
		        		  //(String feature, String action, String format, String contents)
		        	  }
		        	  else
		        	  {
		        		  	backPressedProceed=false;
		        		  	AlertDialog.Builder adb_4=new AlertDialog.Builder(Scanner.this);
					        adb_4.setTitle("Scan Shop Item");
					        adb_4.setMessage("Hey, you've do not have a QR Scanner. Download one from the Play Store");
					        adb_4.setPositiveButton("Ok", new DialogInterface.OnClickListener()
					        {
						          public void onClick(DialogInterface paramDialogInterface, int paramInt)
						          {
						        	  //paramDialogInterface.dismiss();
						        	  
					        		  Scanner.this.myIntent = new Intent(Scanner.this, Home.class);
					        		  myIntent.putExtra("returnHome", 1);
				                      myIntent.putExtra("keyy", Scanner.this.keyy);
				                      Log.i("Inside Medication","Scanner not installed");
				                      Scanner.this.startActivity(myIntent);
				                      finish();
						        	  
						          }
					        });
					        
					        adb_4.show();
		        	  }
				          
				  }
				  else
				  {
					  this.myIntent = new Intent(Scanner.this, Home.class);
		              Scanner.this.startActivity(myIntent);
		              finish();
				  }
			  }
			  else
			  {
				  this.myIntent = new Intent(Scanner.this, SignIn.class);
				  this.myIntent.putExtra("noKeyReDirect", 1);
	              Scanner.this.startActivity(myIntent);
	              
	              finish();
			  }
	      }
		  else
		  {
			  this.myIntent = new Intent(Scanner.this, SignIn.class);
			  this.myIntent.putExtra("noKeyReDirect", 1);
              Scanner.this.startActivity(myIntent);
              
              finish();
		  }
	  }
	  else
	  {
		  
	  }
  }
  
  public void onActivityResult(int requestCode, int resultCode, Intent intent)
  {
	  try
	  {
		  if(intent.hasExtra("SCAN_RESULT"))
		  {
			  Toast.makeText(this, Integer.toString(RESULT_OK) + " ||| " + Integer.toString(requestCode) + " ||| " + Integer.toString(resultCode), Toast.LENGTH_LONG);
			  Intent result = new Intent(this, ShopItem.class);
			  contents1 = intent.getStringExtra("SCAN_RESULT");
			  this.contents1_=contents1;
			  format1 = intent.getStringExtra("SCAN_RESULT_FORMAT");
			  if((requestCode == 0) && (resultCode == RESULT_OK))
			  {
				  
				  //IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
				  /*String scanResult="";
				  String contents="";
				  String format="";*/
				  result = new Intent(this, ShopItem.class);
				  if(contents1 !=null)
				  {
					  
					  if(Scanner.this.getString(R.string.demo).equals("demo"))
		              {
						  
		              }
					  else
					  {
						  d=new db(this);
						  Toast.makeText(Scanner.this, contents1, Toast.LENGTH_LONG);
						  
						  ContentValues cv=new ContentValues();
						  cv.put("qr_code", contents1);
						  d.openHelper.updateRow(cv, "_id=?", new String[]{"4"}, getString(R.string.shopItems));
						  
						  contents1=contents1.replaceAll("\\n", "");
						  contents1=contents1.replaceAll("\\r", "");
						  String str_22="";
						  int chr;
						  //Toast.makeText(Scanner.this, contents1, Toast.LENGTH_LONG);
						  /*Cursor c43=d.openHelper.fetchRowType2(getString(R.string.shopItems), new String[]{"_id", "uniqueId", "shopItemName", "shopItemType", "qr_code"}, "_id=?", new String[]{"4"}, null, null, null, null);
						  c43.moveToFirst();
						  
						  try {
							  
								String sCurrentLine;
					 
								BufferedReader br = new BufferedReader(new StringReader(contents1));
					 
								
								while(((chr = br.read()) != -1))
				  				{
				  					str_22=str_22 + chr;
				  				}
								System.out.println(str_22);
								
								/*if(c43.getString(4).equals(str_22))
								{
									//sendPostRequest("shop", "test error", str_22, contents1);
								}*/
								
					 
								/*if (br != null)br.close();
							} catch (IOException e) {
								e.printStackTrace();
							} finally {
								
							}
						  c43.close();*/
						  /*AlertDialog.Builder adb_4=new AlertDialog.Builder(Scanner.this);
					        adb_4.setTitle("Scan Shop Item");
					        adb_4.setMessage("45");
					        adb_4.setPositiveButton("Ok", new DialogInterface.OnClickListener()
					        {
						          public void onClick(DialogInterface paramDialogInterface, int paramInt)
						          {
						        	  //paramDialogInterface.dismiss();
						        	  
					        		  Scanner.this.myIntent = new Intent(Scanner.this, Home.class);
					        		  myIntent.putExtra("returnHome", 1);
				                      myIntent.putExtra("keyy", Scanner.this.keyy);
				                      Log.i("Inside Medication","No Shop items match the item you scanned 12=" + Scanner.this.contents1 + " &format1 = " + Scanner.this.format1);
				                      Scanner.this.startActivity(myIntent);
				                      finish();
						        	  
						          }
					        });
					        adb_4.show();*/
						  Cursor c=d.openHelper.fetchRowType2(getString(R.string.shopItems), new String[]{"_id", "uniqueId", "shopItemName", "shopItemType", "amount", "shortDescription"}, "qr_code=? AND soldYes != ?", new String[]{contents1, "1"}, null, null, null, null);
						  //, " AND soldYes!= ?1"
				    	  if(c.getCount()>0)
				    	  {
				    		  c.moveToFirst();
				    		  if(this.presentFeatureCode.equals(new menus().all_services()[4]))
				    		  {
				    			  backPressedProceed=false;
				    			  AlertDialog.Builder adb_4=new AlertDialog.Builder(Scanner.this);
							        adb_4.setTitle("Scan Shop Item");
							        adb_4.setMessage("This item is already in the inventory");
							        adb_4.setPositiveButton("Ok", new DialogInterface.OnClickListener()
							        {
								          public void onClick(DialogInterface paramDialogInterface, int paramInt)
								          {
								        	  //paramDialogInterface.dismiss();
								        	  
							        		  /*Scanner.this.myIntent = new Intent(Scanner.this, Home.class);
							        		  myIntent.putExtra("returnHome", 1);
						                      myIntent.putExtra("keyy", Scanner.this.keyy);
						                      Log.i("Inside Medication","No Shop items match the item you scanned 12=" + Scanner.this.contents1 + " &format1 = " + Scanner.this.format1);
						                      Scanner.this.startActivity(myIntent);
						                      finish();*/
								        	  AlertDialog.Builder adb_4=new AlertDialog.Builder(Scanner.this);
										        adb_4.setTitle("Scan Shop Item");
										        adb_4.setMessage("1");
										        adb_4.setPositiveButton("Ok", new DialogInterface.OnClickListener()
										        {
											          public void onClick(DialogInterface paramDialogInterface, int paramInt)
											          {
											        	  //paramDialogInterface.dismiss();
											        	  
										        		  Scanner.this.myIntent = new Intent(Scanner.this, Home.class);
										        		  myIntent.putExtra("returnHome", 1);
									                      myIntent.putExtra("keyy", Scanner.this.keyy);
									                      Log.i("Inside Medication","No Shop items match the item you scanned 12=" + Scanner.this.contents1 + " &format1 = " + Scanner.this.format1);
									                      Scanner.this.startActivity(myIntent);
									                      finish();
											        	  
											          }
										        });
										        adb_4.show();
								        	  
								          }
							        });

				    		  }
				    		  else
				    		  {
					    		  //Synchronize.this.d.openHelper.deleteRow(c.getInt(0), getString(R.string.shopItems), "_id");
					    		   	Intent intent_1 = new Intent(Scanner.this, SellItem.class);
					    		   	intent_1.putExtra("returnHome", 1);
					    		   	intent_1.putExtra("keyy", Scanner.this.keyy);
					    		   	intent_1.putExtra("shop_item_position", c.getInt(0));
					            	c.close();
					            	d.close();
					            	Scanner.this.startActivity(intent_1);
					            	finish();
				    		  }
				    		  
				    	  }
				    	  else
				    	  {
				    		  if(this.presentFeatureCode.equals(new menus().all_services()[4]))
				    		  {
				    			  
				    			  	Intent intent_1 = new Intent(Scanner.this, AddItem.class);
					    		   	intent_1.putExtra("returnHome", 1);
					    		   	intent_1.putExtra("keyy", Scanner.this.keyy);
					    		   	intent_1.putExtra("item", contents1);
					            	c.close();
					            	d.close();
					            	Scanner.this.startActivity(intent_1);
					            	finish();
				    		  }
				    		  else
				    		  {
					    		  
				    			  	backPressedProceed=false;
				    			  	AlertDialog.Builder adb_4=new AlertDialog.Builder(Scanner.this);
							        adb_4.setTitle("Scan Shop Item");
							        adb_4.setMessage("No Shop items match the item you scanned 12=" + Scanner.this.contents1 + " &format1 = " + Scanner.this.format1);
							        adb_4.setPositiveButton("Ok", new DialogInterface.OnClickListener()
							        {
								          public void onClick(DialogInterface paramDialogInterface, int paramInt)
								          {
								        	  //paramDialogInterface.dismiss();
								        	  
							        		  Scanner.this.myIntent = new Intent(Scanner.this, Home.class);
							        		  myIntent.putExtra("returnHome", 1);
						                      myIntent.putExtra("keyy", Scanner.this.keyy);
						                      Log.i("Inside Medication","No Shop items match the item you scanned 12=" + Scanner.this.contents1 + " &format1 = " + Scanner.this.format1);
						                      Scanner.this.startActivity(myIntent);
						                      finish();
								        	  
								          }
							        });


							        c.close();
					            	d.close();
							        adb_4.show();
				    		  }
				    	  }
				    	  
						  //live connection
						  //sendPostRequest("shop", "identify shop item", format1, contents1);
						  
					  }
					  
				  }
				  else
				  {
					  backPressedProceed=false;
					  AlertDialog.Builder adb_51 = new AlertDialog.Builder(Scanner.this);
		              Log.i("Track2", "Clicked1");
		              (adb_51).setTitle("Scan Status");
		              Log.i("Track3", "Clicked2122211");
		              ((AlertDialog.Builder)adb_51).setMessage("No Results Obtained For Scan. Try Again?");
		              ((AlertDialog.Builder)adb_51).setPositiveButton("Yes", new DialogInterface.OnClickListener()
		              {
		                public void onClick(DialogInterface paramDialogInterface, int paramInt)
		                {
		                	Intent result = new Intent(Scanner.this, Scanner.class);
		                	result.putExtra("returnHome", 1);
		                	result.putExtra("keyy", Scanner.this.keyy);
		                	result.putExtra("ScreenType", Scanner.this.presentFeatureCode);
		                	Scanner.this.startActivity(result);
		                	finish();
		                }
		              });
		              ((AlertDialog.Builder)adb_51).setNegativeButton("No", new DialogInterface.OnClickListener()
		              {
		                public void onClick(DialogInterface paramDialogInterface, int paramInt)
		                {
		                	Intent result = new Intent(Scanner.this, Home.class);
		                	result.putExtra("returnHome", 1);
		                	result.putExtra("keyy", Scanner.this.keyy); 
		                	Scanner.this.startActivity(result);
		                	finish();
		                }
		              });
		              adb_51.show();
				  }
			  }
			  else
			  {
				  backPressedProceed=false;
				  AlertDialog.Builder adb_51 = new AlertDialog.Builder(Scanner.this);
		          Log.i("Track2", "Clicked1");
		          (adb_51).setTitle("Scan Status");
		          Log.i("Track3", "Clicked2122211");
		          ((AlertDialog.Builder)adb_51).setMessage("No Results Obtained For Scan. Try Again?");
		          ((AlertDialog.Builder)adb_51).setPositiveButton("Yes", new DialogInterface.OnClickListener()
		          {
		            public void onClick(DialogInterface paramDialogInterface, int paramInt)
		            {
		            	Intent result = new Intent(Scanner.this, Scanner.class);
		            	result.putExtra("returnHome", 1);
		            	result.putExtra("keyy", Scanner.this.keyy);
		            	result.putExtra("ScreenType", Scanner.this.presentFeatureCode);
		            	Scanner.this.startActivity(result);
		            	finish();
		            }
		          });
		          ((AlertDialog.Builder)adb_51).setNegativeButton("No", new DialogInterface.OnClickListener()
		          {
		            public void onClick(DialogInterface paramDialogInterface, int paramInt)
		            {
		            	Intent result = new Intent(Scanner.this, Home.class);
		            	result.putExtra("returnHome", 1);
		            	result.putExtra("keyy", Scanner.this.keyy); 
		            	Scanner.this.startActivity(result);
		            	finish();
		            }
		          });
		          adb_51.show();
				  
			  }
		  }	  	
	  }
	  catch(Exception e)
	  {
		  StringWriter w = new StringWriter();
	      	e.printStackTrace(new java.io.PrintWriter(w));
		  sendPostRequest("shop", "identify shop item", w.toString(), contents1);
		  Intent result = new Intent(Scanner.this, Home.class);
		  result.putExtra("returnHome", 1);
		  result.putExtra("keyy", Scanner.this.keyy); 
		  Scanner.this.startActivity(result);
		  finish();
	  }
	  	
	  
  }
  
  
  
  
  private void sendPostRequest(String feature, String action, String format, String contents) {
	  try
	  {
			class SendPostReqAsyncTask extends AsyncTask<String, Void, String>{

				ProgressDialog pDialog; 
				String postValue; 
				
				@Override
				protected void onPreExecute() {
			        pDialog = ProgressDialog.show(Scanner.this,"Please wait...", "Processing trasaction ...", true);
			    }
				
				
				@Override
				protected String doInBackground(String... params) {

					String feature = params[0];
					String action = params[1];
					String format = params[2];
					String contents = params[3];


					System.out.println("*** doInBackground ** postValue " + postValue);

					HttpClient httpClient = new DefaultHttpClient();

					HttpPost httpPost = new HttpPost(getString(R.string.posturl));

					BasicNameValuePair featureValuePair = new BasicNameValuePair("feature", feature);
					BasicNameValuePair actionValuePair = new BasicNameValuePair("action", action);
					BasicNameValuePair contentsValuePair = new BasicNameValuePair("contents", contents);
					BasicNameValuePair formatValuePair = new BasicNameValuePair("format", contents);
					System.out.println("*** doInBackground ** postValue " );

					// We add the content that we want to pass with the POST request to as name-value pairs
					//Now we put those sending details to an ArrayList with type safe of NameValuePair
					List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
					nameValuePairList.add(featureValuePair);
					nameValuePairList.add(actionValuePair);
					nameValuePairList.add(contentsValuePair);
					nameValuePairList.add(formatValuePair);
					
					System.out.println("*** doInBackground1 ** postValue " + contents);
					try {
						// UrlEncodedFormEntity is an entity composed of a list of url-encoded pairs. 
						//This is typically useful while sending an HTTP POST request. 
						UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);
						System.out.println("*** doInBackground2 ** postValue " + contents);
						// setEntity() hands the entity (here it is urlEncodedFormEntity) to the request.
						httpPost.setEntity(urlEncodedFormEntity);

						try {
							// HttpResponse is an interface just like HttpPost.
							//Therefore we can't initialize them
							HttpResponse httpResponse = httpClient.execute(httpPost);
							System.out.println("*** doInBackground3 ** postValue " + contents);
							// According to the JAVA API, InputStream constructor do nothing. 
							//So we can't initialize InputStream although it is not an interface
							InputStream inputStream = httpResponse.getEntity().getContent();
							System.out.println("*** doInBackground4 ** postValue " + contents);
							InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
							System.out.println("*** doInBackground5 ** postValue " + contents);
							BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
							System.out.println("*** doInBackground6 ** postValue " + contents);
							StringBuilder stringBuilder = new StringBuilder();

							String bufferedStrChunk = null;

							while((bufferedStrChunk = bufferedReader.readLine()) != null){

								stringBuilder.append(bufferedStrChunk);
							}
							System.out.println("*** doInBackground7 ** postValue " + contents);
							//Toast.makeText(getApplicationContext(), stringBuilder.toString(), Toast.LENGTH_LONG).show();
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
					System.out.println("original result=" + result);
					
                    /*boolean stillLoggedIn = jsonResponse.isNull("Message");
                    if(stillLoggedIn==true){
                        String d = jsonResponse.getString("d");
                        if(d.contentEquals("null")){
                            return jsonObjectDefault;
                        }
                    } else {
                        return jsonObjectDefault;
                    }*/
					JSONObject jsonResponse = null;
					/*Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();*/
					if((result!=null) && (result.length()>0))
	  				{
						
						try {
							
								String string1= result.toString();
								JSONParser parser = new JSONParser();
								org.json.simple.parser.ContainerFactory containerFactory = new ContainerFactory(){
								public List creatArrayContainer() {
									return new LinkedList();
								}

								public Map createObjectContainer() {
									return new LinkedHashMap();
								}
						                        
							};
						                
						  
						    Map json;

						    Hashtable<String,Object> h1= new Hashtable();
						    ArrayList<Hashtable<String,Object>> a1=new ArrayList<Hashtable<String, Object>>();
							try {
								json = (Map)parser.parse(string1, containerFactory);
								Iterator iter = json.entrySet().iterator();
							    System.out.println("==iterate result==");
							    
							    while(iter.hasNext()){
								      Map.Entry entry = (Map.Entry)iter.next();
								      
								      if(entry.getValue()!=null)
								      {
								    	  System.out.println(entry.getKey() + "=>" + entry.getValue());
								    	  System.out.println(entry.getKey().toString() + "=>" + entry.getValue().toString());
								    	  h1.put(entry.getKey().toString(), entry.getValue());
								      }
							    }
							    a1.add(h1);
							                        
							    System.out.println("==toJSONString()==" + h1.toString());
							    System.out.println(json.toString());
							} catch (org.json.simple.parser.ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
								
							
							if(h1.get("success").equals(true))
							{
								Log.i("We are inside the success", "true"); 
				                if(Scanner.this.action!=null && Scanner.this.action.equals("edit"))//null
				                {
				                	  clickCheck=0;
				                	  Scanner.this.myIntent = new Intent(Scanner.this, SellItem.class);
				                	 
				                	  myIntent.putExtra("ScreenType", Scanner.this.presentFeatureCode);
				                      myIntent.putExtra("keyy", Scanner.this.keyy);
				                      myIntent.putExtra("shopItemId", h1.get("shopItemId").toString());
				                      myIntent.putExtra("shopItemName", h1.get("shopItemName").toString());
				                      myIntent.putExtra("shopItemType", h1.get("shopItemType").toString());
				                      myIntent.putExtra("amount", h1.get("amount").toString());
				                      myIntent.putExtra("uniqueId", h1.get("uniqueId").toString());
				                      myIntent.putExtra("details", h1.get("details").toString());
				                      Log.i("Inside Medication","Add New Medication After Adding First");
				                      Scanner.this.startActivity(myIntent);
				                      finish();
					                  
				                }
				                else
				                {
				                	clickCheck=0;
				                	  Scanner.this.myIntent = new Intent(Scanner.this, SellItem.class);
				                	 
				                	  myIntent.putExtra("ScreenType", Scanner.this.presentFeatureCode);
				                      myIntent.putExtra("keyy", Scanner.this.keyy);
				                      myIntent.putExtra("shopItemId", h1.get("shopItemId").toString());
				                      myIntent.putExtra("shopItemName", h1.get("shopItemName").toString());
				                      myIntent.putExtra("shopItemType", h1.get("shopItemType").toString());
				                      myIntent.putExtra("amount", h1.get("amount").toString());
				                      myIntent.putExtra("uniqueId", h1.get("uniqueId").toString());
				                      myIntent.putExtra("details", h1.get("details").toString());
				                      Log.i("Inside Medication","Add New Medication After Adding First");
				                      Scanner.this.startActivity(myIntent);
				                      finish();
				                }
				                  
							}
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	  				}
	  				else
	  				{
	  					result = "FAIL::::We could not find a match for the shop item scanned. Please try again";
	  					try {
							jsonResponse = new JSONObject(result);
							System.out.println(jsonResponse);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	  				}
					
					
				}			
			}

			SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
			sendPostReqAsyncTask.execute(feature, action, contents, format);
		}
		catch (Exception e)
		{
			Scanner.this.e = e;
			backPressedProceed=false;
			AlertDialog.Builder adb_51 = new AlertDialog.Builder(Scanner.this);
		    Log.v("Track2", "Clicked1");
		    (adb_51).setTitle("Sending Option");
		    Log.v("Track3", "Clicked2122211");
		    ((AlertDialog.Builder)adb_51).setMessage("Error occured while processing your request. Submit error to our team");
		    ((AlertDialog.Builder)adb_51).setPositiveButton("Ok", new DialogInterface.OnClickListener()
		    {
			      public void onClick(DialogInterface paramDialogInterface, int paramInt)
			      {
			      	//AddDrug.this.e.;
			    	  	Scanner.this.w = new StringWriter();
			    	  	Scanner.this.e.printStackTrace(new java.io.PrintWriter(Scanner.this.w));
				      	
				      	sendPostRequest("Error", "", w.toString(), "" );
			      }
		    });	
		    ((AlertDialog.Builder)adb_51).show();
		}
  }
	 
  
  
  private void sendPostRequest1(String postValue) {
	  try
	  {
			class SendPostReqAsyncTask extends AsyncTask<String, Void, String>{

				ProgressDialog pDialog; 
				String postValue; 
				
				@Override
				protected void onPreExecute() {
			        pDialog = ProgressDialog.show(Scanner.this,"Please wait...", "Processing trasaction ...", true);
			    }
				
				
				@Override
				protected String doInBackground(String... params) {

					postValue = params[0];


					System.out.println("*** doInBackground ** postValue " + postValue);

					HttpClient httpClient = new DefaultHttpClient();

					HttpPost httpPost = new HttpPost(getString(R.string.posturl));

					BasicNameValuePair postValuePair = new BasicNameValuePair("postValue", postValue);
					System.out.println("*** doInBackground ** postValue " );

					// We add the content that we want to pass with the POST request to as name-value pairs
					//Now we put those sending details to an ArrayList with type safe of NameValuePair
					List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
					nameValuePairList.add(postValuePair);
					
					System.out.println("*** doInBackground1 ** postValue " + postValue);
					try {
						// UrlEncodedFormEntity is an entity composed of a list of url-encoded pairs. 
						//This is typically useful while sending an HTTP POST request. 
						UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);
						System.out.println("*** doInBackground2 ** postValue " + postValue);
						// setEntity() hands the entity (here it is urlEncodedFormEntity) to the request.
						httpPost.setEntity(urlEncodedFormEntity);

						try {
							// HttpResponse is an interface just like HttpPost.
							//Therefore we can't initialize them
							HttpResponse httpResponse = httpClient.execute(httpPost);
							System.out.println("*** doInBackground3 ** postValue " + postValue);
							// According to the JAVA API, InputStream constructor do nothing. 
							//So we can't initialize InputStream although it is not an interface
							InputStream inputStream = httpResponse.getEntity().getContent();
							System.out.println("*** doInBackground4 ** postValue " + postValue);
							InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
							System.out.println("*** doInBackground5 ** postValue " + postValue);
							BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
							System.out.println("*** doInBackground6 ** postValue " + postValue);
							StringBuilder stringBuilder = new StringBuilder();

							String bufferedStrChunk = null;

							while((bufferedStrChunk = bufferedReader.readLine()) != null){

								stringBuilder.append(bufferedStrChunk);
							}
							System.out.println("*** doInBackground7 ** postValue " + postValue);
							
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
					
                    
					JSONObject jsonResponse = null;
					if((result!=null) && (result.length()>0))
	  				{
						
						try {
							
							String string1= result.toString();
							JSONParser parser = new JSONParser();
							org.json.simple.parser.ContainerFactory containerFactory = new ContainerFactory(){
								public List creatArrayContainer() {
									return new LinkedList();
								}

								public Map createObjectContainer() {
									return new LinkedHashMap();
								}
						                        
							};
						                
						  
						    Map json;

						    Hashtable<String,Object> h1= new Hashtable();
						    
							try {
								json = (Map)parser.parse(string1, containerFactory);
								Iterator iter = json.entrySet().iterator();
							    System.out.println("==iterate result==");
							    
							    while(iter.hasNext()){
								      Map.Entry entry = (Map.Entry)iter.next();
								      //System.out.println(entry.getKey() + "=>" + entry.getValue());
								      if(entry.getValue()!=null)
								      {
								    	  h1.put(entry.getKey().toString(), entry.getValue());
								      }
							    }
							                        
							    System.out.println("==toJSONString()==" + h1.toString());
							    System.out.println(JSONValue.toJSONString(json));
							} catch (org.json.simple.parser.ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
								
							if(h1.get("success").equals(true))
							{
								 
				                if(Scanner.this.action.equals("edit"))//null
				                {
				                	  clickCheck=0;
				                	  Scanner.this.myIntent = new Intent(Scanner.this, ShopItem.class);
				                      myIntent.putExtra("ScreenType", Scanner.this.presentFeatureCode);
				                      myIntent.putExtra("keyy", Scanner.this.keyy);
				                      myIntent.putExtra("MedicationDetails", jsonResponse.toString());
				                      Log.i("Inside Medication","Add New Medication After Adding First");
				                      Scanner.this.startActivity(myIntent);
				                      finish();
					                  
				                }
				                else
				                {
				                	  Scanner.this.myIntent = new Intent(Scanner.this, ShopItem.class);
				                      myIntent.putExtra("ScreenType", Scanner.this.presentFeatureCode);
				                      myIntent.putExtra("keyy", Scanner.this.keyy);
				                      myIntent.putExtra("scannedDetailsMedicationName", h1.get("medicationName").toString());
				                      myIntent.putExtra("scannedDetailsMedicationType", h1.get("medicationType").toString());
				                      myIntent.putExtra("scannedDetailsMedicationDosage", h1.get("medicationDosage").toString());
				                      myIntent.putExtra("scannedDetailsMedicationStrength", h1.get("medicationStrength").toString());
				                      myIntent.putExtra("scannedDetailsMedicationDirections", h1.get("medicationDirections").toString());
				                      myIntent.putExtra("scannedDetailsMedicationFrequency", h1.get("medicationFrequency").toString());
				                      myIntent.putExtra("scannedDetailsMedicationUnit", h1.get("medicationUnit").toString());
				                      myIntent.putExtra("ScannedMedicationDetails", "1");
				                      Log.i("Inside Medication","Add New Medication After Adding First");
				                      Scanner.this.startActivity(myIntent);
				                      finish();
				                }
				                  
							}
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	  				}
	  				else
	  				{
	  					result = "FAIL::::We could not find a match for the shop item scanned. Please try again";
	  					try {
							jsonResponse = new JSONObject(result);
							System.out.println(jsonResponse);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	  				}
					
					
				}			
			}

			SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
			sendPostReqAsyncTask.execute(postValue);
		}
		catch (Exception e)
		{
			Scanner.this.e = e;
			AlertDialog.Builder adb_51 = new AlertDialog.Builder(Scanner.this);
		    Log.v("Track2", "Clicked1");
		    (adb_51).setTitle("Sending Option");
		    Log.v("Track3", "Clicked2122211");
		    ((AlertDialog.Builder)adb_51).setMessage("Error occured while processing your request. Submit error to our team");
		    ((AlertDialog.Builder)adb_51).setPositiveButton("Ok", new DialogInterface.OnClickListener()
		    {
			      public void onClick(DialogInterface paramDialogInterface, int paramInt)
			      {
			      	//AddDrug.this.e.;
			    	  	Scanner.this.w = new StringWriter();
			    	  	Scanner.this.e.printStackTrace(new java.io.PrintWriter(Scanner.this.w));
				      	
				      	sendPostRequest("Error", "", w.toString(), "");
			      }
		    });	
		    ((AlertDialog.Builder)adb_51).show();
		}
  }
	 

  public void onCreateContextMenu(ContextMenu paramContextMenu, View paramView, ContextMenu.ContextMenuInfo paramContextMenuInfo)
  {
	  if (paramView.getId() != R.id.country)
	      super.onCreateContextMenu(paramContextMenu, paramView, paramContextMenuInfo);
	    else
	      for (int i = 0; i < this.countryList.length; i++)
	      {
	        paramContextMenu.add(0, 0, 0, this.countryList[i]);
	      }
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
	            onBackPressed();
	            bool = true;
	        }
	        else
	        {
	        	bool = super.onKeyDown(paramInt, paramKeyEvent);
	        }
	    }
	    return bool;
  }
}
