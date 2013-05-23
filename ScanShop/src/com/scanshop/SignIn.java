package com.scanshop;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignIn extends Activity
  implements View.OnClickListener
{
  private Context a;
  private EditText address1;
  private EditText address2;
  private Button btn;
  private Button btn1;
  private EditText city;
  private EditText confirmpassword;
  private Context context;
  private EditText country;
  String[] countryList;
  private db d;
  private SQLiteDatabase db;
  private EditText email;
  private EditText firstName;
  private String keyy;
  private EditText lastName;
  private EditText mobile;
  private EditText password;
  private String phoneNumberValue;
  boolean procChoice = true;
  private EditText state;
  private String token;
  private EditText username;
  LinearLayout mScreen;
private TextView tv01;

  public SignIn()
  {
    String[] arrayOfString = new String[3];
    this.countryList = arrayOfString;
  }

  @Override
  public void onBackPressed()
  {
	  AlertDialog.Builder adb = new AlertDialog.Builder(this);
      Log.i("Track2", "Clicked1");
      ((AlertDialog.Builder)adb).setTitle("Sign In");
      Log.i("Track3", "Clicked2");
      ((AlertDialog.Builder)adb).setMessage("Exit Application?");
      ((AlertDialog.Builder)adb).setPositiveButton("Yes", new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
        	finish();
        	onDestroy();
     		  System.exit(0);
        }
      });
      Log.i("Track4", "Clicked2");
      ((AlertDialog.Builder)adb).setNegativeButton("No", new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
        	paramDialogInterface.dismiss();
        }
      });
      ((AlertDialog.Builder)adb).show();
      Log.i("Track5", "Clicked2");
      
      
      
  }

  public void onClick(View paramView)
  {
	  int continue_1=1;
	  
	  if (paramView == findViewById(R.id.signIn))
	  {
			  Calendar cal = Calendar.getInstance();
			  Calendar currentcal = Calendar.getInstance();
			  cal.set(2013, Calendar.AUGUST, 15);
			  currentcal.set(currentcal.get(Calendar.YEAR), currentcal.get(Calendar.MONTH), currentcal.get(Calendar.DAY_OF_MONTH));
			  if(cal.before(currentcal))
			  {
				  continue_1=0;
			  }
		    
			  
			  if(continue_1==1)
			  {
			  
					if ((paramView == findViewById(R.id.signIn)) && (((EditText)findViewById(R.id.password)).getText().toString().length() > 0) && (((EditText)findViewById(R.id.username)).getText().toString().length() > 0))
				    {
				      Log.i("username ::> ", ((EditText)findViewById(R.id.username)).getText().toString());
				      Log.i("password ::> ", ((EditText)findViewById(R.id.password)).getText().toString());
				      	
					   		/*this.d = new db(this);
						    this.db = this.d.openHelper.getWritableDatabase();*/
					   	
				      String str = "email = '" + ((EditText)findViewById(R.id.username)).getText().toString() + "' AND password = '" + ((EditText)findViewById(R.id.password)).getText().toString() + "'";
				      //db.OpenHelper localOpenHelper = this.d.openHelper;
				      String tableName = getString(R.string.personalDb);
				      String[] fields = new String[2];
				      fields[0] = "_id";
				      fields[1] = "key";
				      Log.v("sql str = ", str);
				      this.d=new db(this);
				      Cursor cr1 = this.d.openHelper.fetchRow(null, (String)tableName, fields, null, null);
				      System.out.println("Fetch Row Count=" + cr1.getCount());
				      Cursor cr = this.d.openHelper.fetchRow(null, (String)tableName, fields, str, null);
				      if (((Cursor)cr).getCount() <= 0)
				      {
				    	cr.close();
				    	cr1.close();
				    	AlertDialog.Builder adb = new AlertDialog.Builder(this);
				        Log.i("Track2", "Clicked1");
				        ((AlertDialog.Builder)adb).setTitle("Sign In");
				        Log.i("Track3", "Clicked2");
				        ((AlertDialog.Builder)adb).setMessage("Sign In Failed. Email and Password provided do not match. Please Try Again");
				        ((AlertDialog.Builder)adb).setPositiveButton("Ok", new DialogInterface.OnClickListener()
				        {
				          public void onClick(DialogInterface paramDialogInterface, int paramInt)
				          {
				            
				        	//SignIn.this.d.openHelper.close();
				        	 
				            SignIn.this.d.close();
				            paramDialogInterface.dismiss();
				          }
				        });
				        Log.i("Track4", "Clicked2");
				        ((AlertDialog.Builder)adb).show();
				        Log.i("Track5", "Clicked2");
				      }
				      else
				      {
				    	  cr.moveToFirst();
				    	  this.keyy=cr.getString(1);
				    	  cr.close();
				    	  cr1.close();
				    	  //SignIn.this.d.openHelper.close();
				          //SignIn.this.d.close();
				    	////meant to be TWRC+q-7K+3B
				    	  
				    	  cr1 = this.d.openHelper.fetchRow(null, getString(R.string.shopItems), new String[]{"_id"}, null, null);
					      if (((Cursor)cr1).getCount() == 0)
					      {
					    	  SignIn.this.d.openHelper.close();
					    	  SignIn.this.d.close();
		                      cr.close();
		                      cr1.close();
	                    	  Intent myIntent = new Intent(SignIn.this, Synchronize.class);
	                    	  myIntent.putExtra("keyy", SignIn.this.keyy);
	                    	  SignIn.this.startActivity((Intent)myIntent);
	                    	  finish();
					      }
					      else
					      {
					    	  d.close();
					    	  Bundle b = new Bundle();
					    	  b.putString("keyy", this.keyy);
					    	  cr.close();
					    	  cr1.close();
					    	  //b.putString("medicationTime", "10:12");
					    	  Intent intent = new Intent(SignIn.this, Home.class);
					    	  intent.putExtras(b);
					    	  startActivity(intent);
					    	  finish();
					      }
				    	  
				      }
				    }
			  }
			  else
			  {
				  	AlertDialog.Builder adb = new AlertDialog.Builder(this);
			        Log.i("Track2", "Clicked1");
			        ((AlertDialog.Builder)adb).setTitle("Sign In");
			        Log.i("Track3", "Clicked2");
			        ((AlertDialog.Builder)adb).setMessage("Sign In Failed. Email and Password provided do not match. Please Try Again");
			        ((AlertDialog.Builder)adb).setPositiveButton("Ok", new DialogInterface.OnClickListener()
			        {
			          public void onClick(DialogInterface paramDialogInterface, int paramInt)
			          {
			            
			        	//SignIn.this.d.openHelper.close();
			            //SignIn.this.d.close();
			            Intent localIntent = new Intent(SignIn.this, SignIn.class);
			            SignIn.this.startActivity(localIntent);
			            finish();
			          }
			        });
			        Log.i("Track4", "Clicked2");
			        ((AlertDialog.Builder)adb).show();
			        Log.i("Track5", "Clicked2");
			  }
	  }
	  else if (paramView == findViewById(R.id.forgotSignIn))
	  {
		  Intent localIntent = new Intent(SignIn.this, RecoverActivity.class);
          SignIn.this.startActivity(localIntent);
          finish();
	  }
  }

  public boolean onContextItemSelected(MenuItem paramMenuItem)
  {
	  AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) paramMenuItem.getMenuInfo();
    
    return true;
  }

  public void onCreate(Bundle paramBundle)
  {
	    System.out.println("btn.toString=");
	    super.onCreate(paramBundle);
	    setContentView(R.layout.signin);
	    this.setTitle("Scan Shop - Sign In");
	   
	    this.a = this;
	    this.btn = ((Button)findViewById(R.id.signIn));
	    this.btn.setOnClickListener(this);
	    this.btn1 = ((Button)findViewById(R.id.forgotSignIn));
	    this.btn1.setOnClickListener(this);
	    this.username = ((EditText)findViewById(R.id.username));
	    this.password = ((EditText)findViewById(R.id.password));
	    //Layout l= getContentView();
	    
	    
	    
	    Bundle localBundle = getIntent().getExtras();
	  
	  
	    if(localBundle!=null && localBundle.containsKey("noKeyReDirect"))
	    {
	    	tv01=new TextView(this);
	        //tv01.setLineSpacing(0, getResources().getDimensionPixelSize(R.dimen.padding_left));
	        tv01.setLayoutParams(new LinearLayout.LayoutParams(
	                LinearLayout.LayoutParams.WRAP_CONTENT,
	                LinearLayout.LayoutParams.WRAP_CONTENT, 1));
	        tv01.setText("You have been logged out because your session expired");
	        tv01.setTextSize(getResources().getDimension(R.dimen.font_size_header));
	        tv01.setTextColor(getResources().getColor(R.color.red));
	        
	        LinearLayout linLay=new LinearLayout(this);
	        linLay.setOrientation(LinearLayout.VERTICAL);
	        linLay.setLayoutParams(new LinearLayout.LayoutParams(
	                        LinearLayout.LayoutParams.FILL_PARENT,
	                        LinearLayout.LayoutParams.WRAP_CONTENT, 1));
	        linLay.setBackgroundColor(getResources().getColor(R.color.ashbg));
	        linLay.setPadding((int)(getResources().getDimension(R.dimen.padding_left)), (int)getResources().getDimension(R.dimen.padding_left), (int)getResources().getDimension(R.dimen.padding_left), (int)getResources().getDimension(R.dimen.padding_left));
	        linLay.addView(tv01, 0);
	        
	        LinearLayout superLinearLayout=(LinearLayout)findViewById(R.id.signInView);
	        superLinearLayout.addView(linLay, superLinearLayout.getChildCount());
	        
	    }
    
	    this.d = new db(this);
	    if(this.d.openHelper.getWritableDatabase().isOpen()!=true)
 		{
 			this.db = this.d.openHelper.getWritableDatabase();
 			this.db.setLocale((Locale.ENGLISH));
 		}

        this.d.play();
        
        
	    d.close();
	    
	    this.username.setText("a5@a.com");
	    this.password.setText("chelsea");
	    
	    TextView tv03=(TextView)findViewById(R.id.postUrlIndicator);
	    tv03.setText(getString(R.string.posturl));
	    
	    d=new db(this);
	    Cursor c2=d.openHelper.fetchRowType2(getString(R.string.shopItems), new String[]{"soldYes", "shopItemName"}, 
				 null, null, null, null, null, null);
	    String ll="";
	      if (c2.getCount() > 0) 
	      {
	          	c2.moveToFirst();
	          	ll=ll + c2.getString(0) + " || " + c2.getString(1) + " |||| ";
	          	while(c2.moveToNext())
	          	{
	          		ll=ll + c2.getString(0) + " || " + c2.getString(1) + " |||| ";
	          	}
	      }
	      tv03.setText(ll);  
		c2.close();
		d.close();
    
    this.context = this;
    
  }

  public void onCreateContextMenu(ContextMenu paramContextMenu, View paramView, ContextMenu.ContextMenuInfo paramContextMenuInfo)
  {
    if (paramView.getId() != R.id.country)
      super.onCreateContextMenu(paramContextMenu, paramView, paramContextMenuInfo);
    else
      for (int i = 0; i < this.countryList.length; i++)
        paramContextMenu.add(0, 0, 0, this.countryList[i]);
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
            // do something on back.
            /*finish();
            Intent intent_=new Intent(SignIn.this, PillPodActivity.class);
            startActivity(intent_);
            bool =  true;*/
    		onBackPressed();
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
