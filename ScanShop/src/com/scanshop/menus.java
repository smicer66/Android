/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scanshop;

/**
 *
 * @author Kachi
 */


import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import java.util.*;

public class menus {

    //Keeper keepz = null;
    //Records recs = null;
	
	Hashtable<String, String> h1;

    public menus() {

    	
    	
    }


    public String[] all_services() {
        String as[] = null;
        as = new String[]{"Scan An Item", "Synchronize", "History/Log", "Get Help", "Add Items Here", "Exit"};
        return as;
    }
    
    public String[] all_services_describe() {
        String as[] = null;
        as = new String[]{"Scan a shop item for purchase", 
        		"Batch Processing & Logging details", 
        		"View History of Todays Purchases", 
        		"Help on how to use the application", 
        		"Add New SHop items here",
        		"Exit this application"};
        return as;
    }
    
    
    public String getDescription(String key) {
    	
        String describe=h1.get(key);
        if((describe!=null) && (describe.length()>0))
        {
        	return describe;
        }
        else
        {
        	return "";
        }
    }
    
    public String[] _settings_describe() {
        String as[] = null;
        as = new String[]{"Change how I get alerts, etc", "Help and explanations on the settings"};
        return as;
    }
    
    
    public String[] _settings() {
        String as[] = null;
        as = new String[]{"Application Settings", "Synchronize My Data", "Get Help"};
        return as;
    }
    
    
    public String[] _bills_describe() {
        String as[] = null;
        as = new String[]{"All charges relating using this application", 
        		"Add a new billing credit/debit card", 
        		"Make changes to your billing cards", 
        		"Help on billing issues"};
        return as;
    }
    
    public String[] _bills() {
        String as[] = null;
        as = new String[]{"View Bills", "Add New Debit/Credit Card", "Manage Debit/Credit Card", "Get Help"};
        return as;
    }
    
    
    public String[] _medications_saved() {
        String as[] = null;
        as = new String[]{"Aspirin", "Paracetamol", "Codeine", "Quinine"};
        return as;
    }
    
    
    public String[] getMonths()
    {
    	String as[] = null;
        as = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
        return as;
    }
    
    public int[] _medications_saved_qty() {
        int as[] = null;
        as = new int[]{3, 30, 1, 10};
        return as;
    }
    
    public String[] _medication(){
    	String as[] = null;
        as = new String[]{"Add New Medication", "Manage My Medications", "Refill Medication"};
        return as;
    }
    
    public String[] _medication_describe(){
    	String as[] = null;
        as = new String[]{"Add your new medications", "Pause, Stop taking medications", "Refill my medications quantity"};
        return as;
    }
    
    public String[] _history_compliance(){
    	String as[] = null;
        as = new String[]{"View All History", "View By Date Range"};
        return as;
    }
    
    public String[] _history_compliance_describe(){
    	String as[] = null;
        as = new String[]{"Log of all medications taken", "View log of medications within date ranges"};
        return as;
    }
    
    public String[] _my_account(){
    	String as[] = null;
        as = new String[]{"My Details", "Edit My Details", "Change My Password"};
        return as;
    }
    

    
    public String[] _my_account_describe(){
    	String as[] = null;
        as = new String[]{"About Me", "Make changes to my details", "Change your passwords here"};
        return as;
    }

    
    
    public String[] helpPanel() {
        String as[] = {
            "Help about Scanning Shop Items", "Help on Synchronization", "Help on Shop Item History", "Other Help Info"
        };
        return as;
    }
    
    
    public String[] helpPanel_describe() {
        String as[] = {
            "FAQs about managing your medication", "FAQs about your medication history", "FAQs on your PillPod User Account", "FAQs on your bills", "FAQs on your settings"
        };
        return as;
    }

    




    //END OF NAIGATION CONTROLS
    private static Hashtable bank_codes,  backPageTo,  backPageToIndex,  backPageToTopupIndex,  accType,  mssgTokens,  backPageToBankingIndex,  backPageToBillPaymentIndex,  backPageToServicesIndex,  backPageToSecurityIndex,  backPageToESAIndex,  backPageToESACodeIndex,  backPageToHelpIndex,  holdinTab;
    private String holdaa;
}