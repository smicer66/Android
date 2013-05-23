/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scanshop;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import java.io.*;
import java.text.ParseException;
import java.util.*;


/**
 *
 * @author kachi
 */
public class Home extends ListActivity {

    public menus menu=new menus();
    private String presentFeatureCode;
    private String[] menuName;
    private String currentItemClicked="";

    private String carrier=null, receipientPhone=null;
    private String amount, quantity;
   
    private boolean pinYes;
    
    String[] voucherAmount;
    Intent myIntent;
    private String confirmMessage;
    private String voucherValue_;
    private db d;
    private Context context;
    private int returnTimes=0;
	private String temp_carrier;
	private int backIndex;
	private Bundle bundle;
	private String keyy;
	private Integer[] imgid;
	private LayoutInflater mInflater;
	private RowData rd;
	private Vector<RowData> data;
	private String[] menuDescribe;
	

    public Home()
    {

    }
    
    @Override
    public void onBackPressed() {
        // do something on back.
    	
    	if(this.backIndex==0)
    	{
    		if(this.presentFeatureCode.equals("MAINMENU"))
    		{
    			AlertDialog.Builder adb_4=new AlertDialog.Builder(Home.this);
    	        Log.v("Track21", "Clicked1");
    	        adb_4.setTitle("Exit");
    	        Log.v("Track31", "Clicked2");
    	        adb_4.setMessage("Log Out?");
    	        adb_4.setPositiveButton("Yes", new DialogInterface.OnClickListener()
    	        {
    	          public void onClick(DialogInterface paramDialogInterface, int paramInt)
    	          {
    	        	  Intent myIntent = new Intent(Home.this, SignIn.class);
    	              startActivity(myIntent);
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
    	              Log.v("backIndex in Home cancelled Going Back =", "" + backIndex);
    	          }
    	        });
    	        
    	        adb_4.show();
    		}
    		else
    		{
	    		Intent intent_=new Intent(Home.this, Home.class);
	    	    intent_.putExtra("keyy", Home.this.keyy);
		        startActivity(intent_);
		        finish();
    		}
    	}
    	else
    	{
    		Intent intent_=new Intent(Home.this, Home.class);
    	    intent_.putExtra("keyy", Home.this.keyy);
    		
	        startActivity(intent_);
	        finish();
    	}
    }

    
    
    public Integer selectIcon(String name)
    {
    	if (null!=name)
    	{
	  	  if (name.equals(new menus().all_services()[0]))
	        {
	          return Integer.valueOf(R.drawable.scanitem);
	          
	        }
	  	  else if (name.equals(new menus().all_services()[1]))
	        {
	  		  return Integer.valueOf(R.drawable.synch);
	          
	        }
	  	  else if (name.equals(new menus().all_services()[2]))
	        {
	  		  return  Integer.valueOf(R.drawable.history);
	          
	        }
	  	  else if (name.equals(new menus().all_services()[3]))
	        {
	  		  return Integer.valueOf(R.drawable.help);
	          
	        }
	  	  else if(name.equals(new menus().all_services()[4]))
	        {
	  		  return  Integer.valueOf(R.drawable.additems);
	          
	        }
	  	  else if(name.equals(new menus().all_services()[5]))
	        {
	  		  return  Integer.valueOf(R.drawable.exit);
	          
	        }
	        else
	        {
	      	  return Integer.valueOf(R.drawable.defaulticon);
	        }
    	
    	}
    	else
    	{
    		return Integer.valueOf(R.drawable.defaulticon);
    	}
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	
    	
        this.presentFeatureCode="MAINMENU";
        this.menuName=menu.all_services();
        super.onCreate(savedInstanceState);
        bundle=getIntent().getExtras();
        
        //Log.v("bundle.size=", "" + bundle.size());
        if(bundle!=null)
        {
	       
	        if(bundle.containsKey("keyy") && (bundle.getString("keyy").length()>0))
	        {
	        	this.keyy=bundle.getString("keyy");
	        	Log.v("keyy when entering home =", "" + this.keyy);
	        	if(bundle.containsKey("backIndex"))
	 	        {
	 	        	this.backIndex=bundle.getInt("backIndex");
	 	        	Log.v("backIndex when entering home =", "" + this.backIndex);
	 	        }
	        }
	        else
	        {
	        	Intent myIntent = new Intent(Home.this, SignIn.class);
	        	Home.this.startActivity((Intent)myIntent);
	            finish();
	        }
        }
        else
        {
        	this.backIndex=0;
        	Intent myIntent = new Intent(Home.this, SignIn.class);
        	Home.this.startActivity((Intent)myIntent);
            finish();
        }
        
        
        this.imgid = new Integer[this.menuName.length];
        System.out.println("---dsds9--size of imid" + this.imgid.length);
        
        this.mInflater = ((LayoutInflater)getSystemService("layout_inflater"));
        this.data = new Vector();
        int j=0;
        this.menuDescribe=new menus().all_services_describe();
        while(j<this.menuName.length)
        {
            
              String menuItemName = this.menuName[j];
              String menuItemDescribe = this.menuDescribe[j];
              //String menuItemDescribe = new menus().getDescription(this.menuName[j]);
              this.rd = new RowData(j, (String)menuItemName, menuItemDescribe);
              this.data.add(this.rd);
              System.out.println("this.menuName[j] = " + this.menuName[j]);
              this.imgid[j]=selectIcon(this.menuName[j]);
              j++;
        }
        /*old list displayer
         * 
         * setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.menuName));
         * getListView().setTextFilterEnabled(true);
         */
        getListView().setId(0);
        getListView().setBackgroundColor(getResources().getColor(R.color.grey));
		setListAdapter(new CustomAdapter(this, R.layout.list, R.id.title, this.data, this.mInflater, this.imgid));
        getListView().setTextFilterEnabled(true);
        this.setTitle("Scan Shop!");
        context =this;
        
        
        //finish();
        //Intent intent_=new Intent(Home.this, LicenseAgreement.class);
        //startActivity(intent_);

    }


    public boolean onKeyDown(int keyCode, KeyEvent event)  {
    	
    	Intent intent_;
    	if ((keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) && (this.backIndex==0)) {
        	onBackPressed();
	        return true;
        }
        else
        {
        	return super.onKeyDown(keyCode, event);
        }

        
    }



    protected void onListItemClick(ListView l, View v, int position, long id) {

    	super.onListItemClick(l, v, position, id);
    	Object o = this.getListAdapter().getItem(position);
    	String pen = o.toString();
    	//Toast.makeText(this, "presentFeatureCode" + this.presentFeatureCode, Toast.LENGTH_SHORT).show();

        Log.i("Home", this.presentFeatureCode);
        currentItemClicked=l.getItemAtPosition(position).toString();
        Log.i("currentItemClicked=", currentItemClicked);
        this.menuName=this.getMenuToGoTo(position);
        /*if(this.menuName!=null)
        {
            setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, this.menuName));
        }
        getListView().setTextFilterEnabled(true);*/
        if(this.menuName!=null)
        {
	        this.imgid = new Integer[this.menuName.length];
	        System.out.println("---dsds9--size of imid" + this.imgid.length);
	        
	        this.mInflater = ((LayoutInflater)getSystemService("layout_inflater"));
	        this.data = new Vector();
	        int j=0;
	        while(j<this.menuName.length)
	        {
	            
	              String menuItemName = this.menuName[j];
	              String menuItemDescribe = menuDescribe[j];
	              //String menuItemDescribe = new menus().getDescription(this.menuName[j]);
	              this.rd = new RowData(j, (String)menuItemName, menuItemDescribe);
	              this.data.add(this.rd);
	              this.imgid[j]=selectIcon(this.menuName[j]);
	              j++;
	        }
        }
        /*old list displayer
         * 
         * setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.menuName));
         * getListView().setTextFilterEnabled(true);
         */
        getListView().setId(0);
		setListAdapter(new CustomAdapter(this, R.layout.list, R.id.title, this.data, this.mInflater, this.imgid));
        getListView().setTextFilterEnabled(true);
        
        
    }


    String getCountry() {
        return "NIGERIA";
    }



    public String[] getMenuToGoTo(int position)
    {
    	String teleco="";
    	this.backIndex=1;
    	
        
        
        
    	Log.i("Inside Medication",presentFeatureCode);
    	
        if(this.presentFeatureCode.equals("MAINMENU"))
        {
//        	
            //"Medication", "History & Compliance", "My Account", "Bills", "Settings", "Get Help", "Exit"
            switch(position)
            {

                case 0:		
                    this.presentFeatureCode=this.menu.all_services()[0];
                    this.myIntent = new Intent(Home.this, Scanner.class);
                    myIntent.putExtra("ScreenType", this.presentFeatureCode);
                    System.out.println("keyy bad=" + this.keyy);
                    myIntent.putExtra("keyy", this.keyy);
                    Log.i("Inside Medication","Add New Medication exactly2");
                    Home.this.startActivity(myIntent);
                    finish();
                    return null;
                case 1:
                	Log.i("Inside Medication","Add New Medication exactly3");
                	this.presentFeatureCode=this.menu.all_services()[1];
                    this.myIntent = new Intent(Home.this, Synchronize.class);
                    myIntent.putExtra("ScreenType", this.presentFeatureCode);
                    System.out.println("keyy bad=" + this.keyy);
                    myIntent.putExtra("keyy", this.keyy);
                    Log.i("Inside Medication","Add New Medication exactly2");
                    Home.this.startActivity(myIntent);
                    finish();
                    return null;
                case 2:
                    presentFeatureCode=this.menu.all_services()[2];
                    db d = new db(this);
                	Cursor c=d.openHelper.fetchRowType2(null, getString(R.string.shopItemsHistory), new String[]{"_id", "shopItemId", "timeLogged"}, null, null, null, null);
                    System.out.println("asdasdss==" + c.getCount());
                    String[] menuContents=new String[c.getCount()];
                    String[] menuContentsDescribe=new String[c.getCount()];
                    
                    String units="";
                    
                    
                    
                    
                      if(c.getCount()>0)	//if there is an entry within 
	      	  		  {
	      	  			  Log.i("1.c.getCount()>0", ((Integer)(c.getCount())).toString());
	      	  			  
	      	  			  int i=0;
	      	  			  c.moveToFirst();
	      	  			   
	      	  			  Cursor c1=d.openHelper.fetchRowType2(getString(R.string.shopItems), new String[]{"_id", "shopItemName", "amount", "uniqueId", "shopItemType"}, 
	      	  					"_id=?", new String[]{Integer.toString(c.getInt(1))}, null, null, null, null);
	      	  			  System.out.println("c1.getCount()=" + c1.getCount() + "c.getInt(1) = " + c.getInt(1));
	      	  			  if(c1.getCount()>0)
	      	  			  {
		      	  			  c1.moveToFirst();
		      	  			  menuContents[i] = c1.getString(1);
		      	  			
		      	  			  menuContentsDescribe[i++] = c1.getString(2) + "Naira [" + c.getString(2) + "]";
		      	  			  
		      	  			  
		      	              c.close();
		      	              d.close();
	      	  			  }
	      	  			  while(c.moveToNext())
	      	  			  {
	      	  				  c1=d.openHelper.fetchRowType2(null, getString(R.string.shopItems), new String[]{"_id", "shopItemName", "amount", "uniqueId", "shopItemType"}, "_id=?", new String[]{Integer.toString(c.getInt(0))}, null, null);
	      	  				  if(c1.getCount()>0)
		      	  			  {
	      	  					  c1.moveToFirst();
	      	  					  menuContents[i] = c1.getString(1);
		      	  			
	      	  					  menuContentsDescribe[i++] = c1.getString(2) + "Naira [" + c.getString(2) + "]";
		      	  			  }
	      	  			  }
	      	              Log.i("DB CLOSED54 = ", "YES4");
	      	  		  }
	      	  		  else
	      	  		  {
	      	  			c.close();
	      	  			d.close();
	      	  		  }
                      
                      if(menuContents.length >0)
                      {
                    	  this.menuDescribe=menuContentsDescribe;
                    	  return menuContents;
                      }
                      else
                      {
                    	  	AlertDialog.Builder adb_4=new AlertDialog.Builder(Home.this);
	              	        adb_4.setTitle("Medications");
	              	        adb_4.setMessage("You do not have any shop items sold yet! Sell One?");
	              	        adb_4.setPositiveButton("Yes", new DialogInterface.OnClickListener()
	              	        {
	              	          public void onClick(DialogInterface paramDialogInterface, int paramInt)
	              	          {
	              	        	  Log.v("Home.this.keyy===", Home.this.keyy);
	              	        	  Home.this.myIntent = new Intent(Home.this, Scanner.class);
	              	        	  Home.this.presentFeatureCode=Home.this.menu.all_services()[0];
			                      myIntent.putExtra("ScreenType", Home.this.presentFeatureCode);
			                      myIntent.putExtra("keyy", Home.this.keyy);
			                      Home.this.startActivity(myIntent);
			                      finish();
	              	          }
	              	        });
	              	        adb_4.setNegativeButton("No", new DialogInterface.OnClickListener()
	              	        {
	              	          public void onClick(DialogInterface paramDialogInterface, int paramInt)
	              	          {
	              	        	  Log.v("Home.this.keyy===", Home.this.keyy);
	              	        	  Home.this.myIntent = new Intent(Home.this, Home.class);
	              	        	  Home.this.presentFeatureCode="MAINMENU";
	              	        	  Home.this.myIntent.putExtra("keyy", Home.this.keyy);
			                      myIntent.putExtra("ScreenType", Home.this.presentFeatureCode);
			                      myIntent.putExtra("keyy", Home.this.keyy);
			                      Home.this.startActivity(myIntent);
			                      finish();
	                            
	              	          }
	              	        });
	              	        
	              	        adb_4.show();
		              	      Home.this.presentFeatureCode=Home.this.menu.all_services()[0];
	                          Log.v("TRACER", "Level 1 Medication");
	                          Log.v("TRACER VALUE", Home.this.presentFeatureCode);
	                          return null;
                      }
                case 3:
                    presentFeatureCode=this.menu.all_services()[3];
                    this.menuDescribe=new menus().helpPanel_describe();
                    return this.menu.helpPanel();
                case 4:
                    presentFeatureCode=this.menu.all_services()[4];
                    this.myIntent = new Intent(Home.this, Scanner.class);
                    myIntent.putExtra("ScreenType", this.presentFeatureCode);
                    System.out.println("keyy bad=" + this.keyy);
                    myIntent.putExtra("keyy", this.keyy);
                    Log.i("Inside Medication","Add New Medication exactly2");
                    Home.this.startActivity(myIntent);
                    finish();
                    return null;
                case 5:
                    presentFeatureCode=this.menu.all_services()[5];
                    this.myIntent = new Intent(Home.this, Exit.class);

                    myIntent.putExtra("keyy", this.keyy);
                    myIntent.putExtra("ScreenType", this.presentFeatureCode);
                    Home.this.startActivity(myIntent);
                    finish();
                    
                    return null;
            }
        }
        

        


        else if(this.presentFeatureCode.equals(this.menu.all_services()[2]))
        {
        	Bundle b = new Bundle();
        	b.putString("shop_item_position", Integer.toString(position));
        	b.putString("keyy", this.keyy);
        	System.out.println("shop_item Saved keyy=" + keyy);
        	Intent intent = new Intent(Home.this, ShopItemHistory.class);
        	intent.putExtras(b);
        	startActivity(intent);
        	finish();
        	return null;

        }

       /* else if(this.presentFeatureCode.equals(this.menu.all_services()[1]))
        {
            switch(position)
            {
                //"Utilities", "Pay Merchant", "Subscribed Payments", "Check Bill"
                case 0:
                    this.presentFeatureCode=this.menu._history_compliance()[0];
                    this.myIntent = new Intent(Home.this, History.class);
                    myIntent.putExtra("ScreenType", this.presentFeatureCode);
                    myIntent.putExtra("currentPage", 0);
                    myIntent.putExtra("keyy", this.keyy);
                    Home.this.startActivity(myIntent);
                    finish();
                    return null;
                case 1:
                    this.presentFeatureCode=this.menu._history_compliance()[1];
                    this.myIntent = new Intent(Home.this, History.class);
                    myIntent.putExtra("ScreenType", this.presentFeatureCode);
                    myIntent.putExtra("currentPage", 0);
                    myIntent.putExtra("keyy", this.keyy);
                    Home.this.startActivity(myIntent);
                    finish();
                    return null;
                /*default:
                    this.presentFeatureCode="MAINMENU";
                    return this.menu.all_services();
            }
        }*/



        
        
        
        
        /*get Help*/
        else if(this.presentFeatureCode.equals(this.menu.all_services()[3]))
        {
            switch(position)
            {
                //"Utilities", "Pay Merchant", "Subscribed Payments", "Check Bill"
                case 0:
                    this.presentFeatureCode=this.menu.helpPanel()[0];
                    this.myIntent = new Intent(Home.this, GetHelp.class);
                    myIntent.putExtra("ScreenType", this.presentFeatureCode);
                    myIntent.putExtra("keyy", this.keyy);
                    Home.this.startActivity(myIntent);
                    finish();
                    return null;
                case 1:
                    this.presentFeatureCode=this.menu.helpPanel()[1];
                    this.myIntent = new Intent(Home.this, GetHelp.class);
                    myIntent.putExtra("ScreenType", this.presentFeatureCode);
                    myIntent.putExtra("keyy", this.keyy);
                    Home.this.startActivity(myIntent);
                    finish();
                    return null;
                case 2:
                    this.presentFeatureCode=this.menu.helpPanel()[2];
                    this.myIntent = new Intent(Home.this, GetHelp.class);
                    myIntent.putExtra("ScreenType", this.presentFeatureCode);
                    myIntent.putExtra("keyy", this.keyy);
                    Home.this.startActivity(myIntent);
                    finish();
                    return null;
                case 3:
                    this.presentFeatureCode=this.menu.helpPanel()[3];
                    this.myIntent = new Intent(Home.this, GetHelp.class);
                    myIntent.putExtra("ScreenType", this.presentFeatureCode);
                    myIntent.putExtra("keyy", this.keyy);
                    Home.this.startActivity(myIntent);
                    finish();
                    return null;
                /*default:
                    this.presentFeatureCode="MAINMENU";
                    return this.menu.all_services();*/
            }
        }


        
        else
        {
        	Intent intent = new Intent(Home.this, Home.class);
            myIntent.putExtra("keyy", this.keyy);
        	startActivity(intent);
        	finish();
        	return null;

        }
		return null;
		
    }

 }

