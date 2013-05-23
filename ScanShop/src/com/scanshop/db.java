package com.scanshop;

import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteQuery;
import java.io.FileNotFoundException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.Hashtable;
import java.util.Locale;
import java.util.Random;
import java.util.Stack;



public class db {

	private static final String DATABASE_NAME = "shopscan_799";
	  private static final int DATABASE_VERSION = 1;
	  private String DB_PATH = "/data/data/com.scanshop/databases/";
	  private String TABLE_NAME_PDDB = "pddb";
	  private String TABLE_NAME_SYNCDB = "syncdb";
	  private String TABLE_NAME_COUNTRY="country_table";
	  private String TABLE_NAME_STATE="state_table";
	  private String TABLE_NAME_SECURITY_QUESTION="securityQuestionTable";
	  private String TABLE_NAME_SHOP_ITEMS_HISTORY="shopItemsHistory";
	  private String TABLE_NAME_SHOP_ITEMS="shopItems";
	  
	  private String DATABASE_CREATE_PDDB = "CREATE TABLE " + this.TABLE_NAME_PDDB + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, key TEXT, username TEXT, password TEXT" + ", firstName TEXT, lastName TEXT, city TEXT, state TEXT, country TEXT, email TEXT, mobile TEXT, postCode TEXT, addressLine1 TEXT, addressLine2 TEXT" +
	  		", nhsNumber TEXT, securityQuestion TEXT, securityQuestionAnswer TEXT)";
	  private String DATABASE_CREATE_SYNCDB = "CREATE TABLE " + this.TABLE_NAME_SYNCDB + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, register_date TEXT, " + "syncdb_date TEXT)";
	  private String DATABASE_CREATE_SHOP_ITEMS = "CREATE TABLE " + this.TABLE_NAME_SHOP_ITEMS + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, uniqueId TEXT NOT NULL UNIQUE, qr_code TEXT, shopItemName TEXT, shopItemType TEXT, amount TEXT, shortDescription TEXT, soldYes TEXT)";
	  private String DATABASE_CREATE_SHOP_ITEMS_LOG = "CREATE TABLE " + this.TABLE_NAME_SHOP_ITEMS_HISTORY + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, shopItemId TEXT NOT NULL UNIQUE, timeLogged TEXT, amountSoldFor TEXT, uniqueId TEXT)";
	  private String DATABASE_COUNTRY = "CREATE TABLE " + this.TABLE_NAME_COUNTRY + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, countryName TEXT, iso_code TEXT)";
	  private String DATABASE_STATE = "CREATE TABLE " + this.TABLE_NAME_STATE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, countryId TEXT, name TEXT)";
	  private String DATABASE_SECURITY = "CREATE TABLE " + this.TABLE_NAME_SECURITY_QUESTION + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, question TEXT)";
	  
    private Cursor c;
    private Context cont;
    public OpenHelper openHelper;
    //=new OpenHelper(cont);


    class Row extends Object {
        public String name;
        public String address;
        public String mobile;
        public String home;
        public long rowId;
    }


    
   

    private SQLiteDatabase db;
    Context ctx_;


    public db(Context ctx) {
    		this.ctx_=ctx;
            openHelper = new OpenHelper(this.ctx_);
            this.db = openHelper.getWritableDatabase();

        
    }

    public void close() {
        db.close();
        //this
    }


    public void openDB()
    {
        db = this.ctx_.openOrCreateDatabase(DB_PATH + DATABASE_NAME, DATABASE_VERSION, null);
        db.setLocale(Locale.ENGLISH);
    }


    public void play()
    {
    	/*this.db = this.ctx_.openOrCreateDatabase(this.DB_PATH + "shopscan_799", 1, null);
    	db.execSQL("DROP TABLE IF EXISTS 'shopItems'");
        String aas = "CREATE TABLE shopItems(_id INTEGER PRIMARY KEY AUTOINCREMENT, uniqueId TEXT NOT NULL UNIQUE, qr_code BLOB, shopItemName TEXT, shopItemType TEXT, amount TEXT, shortDescription TEXT, soldYes TEXT)";
        db.execSQL(aas);*/
    }

    public void createDBTables()
    {
    	this.db = this.ctx_.openOrCreateDatabase(this.DB_PATH + "shopscan_799", 1, null);
    	 
         
        this.db.execSQL(this.DATABASE_CREATE_PDDB);
        this.db.execSQL(this.DATABASE_CREATE_SYNCDB);
        Log.i("menu table create", "yes");
         Log.i("menu table create", "yes1");
         Log.i("menu table create", "yes1");
        this.db.execSQL(this.DATABASE_CREATE_SHOP_ITEMS);
        this.db.execSQL(this.DATABASE_CREATE_SHOP_ITEMS_LOG);
        this.db.execSQL(this.DATABASE_COUNTRY);
        Log.i("menu table create", "yes1");
        this.db.execSQL(this.DATABASE_STATE);
        Log.i("menu table create", "yes1");
        this.db.execSQL(this.DATABASE_SECURITY);
        Log.i("menu table create", "yes2");
        /*12-07 13:54:42.133: V/Track3(653): Clicked2122211*/
    }

    public boolean checkDataBase()
    {

        SQLiteDatabase checkDB = null;
        boolean check=false;

        try
        {
                String myPath = DB_PATH + DATABASE_NAME;
                Log.i("check the database", myPath);
                checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
                checkDB.setLocale(Locale.ENGLISH);
                
                Log.i("check the database", "1");
        }
        catch(SQLiteException e)
        {

            Log.i("Cannot Open the database>>>Under checkDatabase() procedure", "1");
                //database does't exist yet.

        }

        if(checkDB != null)
        {
        	Log.i("CheckDB", "true");
                check=true;
                //checkDB.close();

        }

        return check;
    }


    public Cursor GetAllRows(String tableName, String[] columns) {
        try {
            //Cursor c =
            return db.query(tableName, columns, null, null, null, null, null);
            //return c;
        } catch (SQLException e) {
            Log.e("Exception on query", e.toString());
            return null;
        }
    }




    public class OpenHelper extends SQLiteOpenHelper {

    	private String DATABASE_CREATE_PDDB = "CREATE TABLE " + db.this.TABLE_NAME_PDDB + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, key TEXT, username TEXT, password TEXT" + ", firstName TEXT, lastName TEXT, city TEXT, state TEXT, country TEXT, email TEXT, mobile TEXT, postCode TEXT, addressLine1 TEXT, addressLine2 TEXT" +
    	  		", nhsNumber TEXT, securityQuestion TEXT, securityQuestionAnswer TEXT)";
    	private String DATABASE_CREATE_SYNCDB = "CREATE TABLE " + db.this.TABLE_NAME_SYNCDB + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, register_date TEXT, " + "syncdb_date TEXT)";
    	private String DATABASE_CREATE_SHOP_ITEMS = "CREATE TABLE " + db.this.TABLE_NAME_SHOP_ITEMS + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, uniqueId TEXT NOT NULL UNIQUE, shopItemName TEXT, shopItemType TEXT, amount TEXT, shortDescription TEXT)";
    	private String DATABASE_CREATE_SHOP_ITEMS_LOG = "CREATE TABLE " + db.this.TABLE_NAME_SHOP_ITEMS_HISTORY + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, shopItemId TEXT NOT NULL UNIQUE, timeLogged TEXT)";
    	private String DATABASE_COUNTRY = "CREATE TABLE " + db.this.TABLE_NAME_COUNTRY + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, countryName TEXT, iso_code TEXT)";
    	private String DATABASE_STATE = "CREATE TABLE " + db.this.TABLE_NAME_STATE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, countryId TEXT, name TEXT)";
    	private String DATABASE_SECURITY = "CREATE TABLE " + db.this.TABLE_NAME_SECURITY_QUESTION + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, question TEXT)";
        public long insertId;


        public OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        	if (db.this.checkDataBase())
            {
              Log.i("Bad db", "Not created but open");
            }
            else
            {
              Log.i("Db path", db.this.DB_PATH);
              db.execSQL(this.DATABASE_CREATE_PDDB);
              Log.i("Db path", db.this.DB_PATH);
              db.execSQL(this.DATABASE_CREATE_SYNCDB);
              Log.i("Db path", db.this.DB_PATH);
              db.execSQL(this.DATABASE_CREATE_SHOP_ITEMS);
              Log.i("Db path2", db.this.DB_PATH);
              db.execSQL(this.DATABASE_CREATE_SHOP_ITEMS_LOG);
              db.execSQL(this.DATABASE_COUNTRY);
              Log.i("menu table create", "yes1");
              db.execSQL(this.DATABASE_STATE);
              Log.i("menu table create", "yes1");
              db.execSQL(this.DATABASE_SECURITY);
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        	Log.w("Database", "Upgrading database, this will drop tables and recreate.");
            db.execSQL("DROP TABLE IF EXISTS " + this.DATABASE_CREATE_PDDB);
            db.execSQL("DROP TABLE IF EXISTS " + this.DATABASE_CREATE_SYNCDB);
            db.execSQL("DROP TABLE IF EXISTS " + this.DATABASE_CREATE_SHOP_ITEMS);
            db.execSQL("DROP TABLE IF EXISTS " + this.DATABASE_CREATE_SHOP_ITEMS_LOG);
            db.execSQL("DROP TABLE IF EXISTS " + this.DATABASE_COUNTRY);
            db.execSQL("DROP TABLE IF EXISTS " + this.DATABASE_STATE);
            db.execSQL("DROP TABLE IF EXISTS " + this.DATABASE_SECURITY);
            
            onCreate(db);
        }

        public int getRowsCount(String tableName, String[] columns, String where, String[] args, String grpBy, String having, String orderBy ) {
            try {
                Log.i("tableName", tableName);
                Cursor c = db.query(tableName, columns, where, args, grpBy, having, orderBy);
                int count=c.getCount();
                c.close();
                return count;
            } catch (SQLException e) {
                Log.e("Exception on query", e.toString());
                return 0;
            }

        }

        public long createRow(String tableName, String[] columns, String[] entries)
        {
            ContentValues initialValues = new ContentValues();
            for(int count_=0;count_<columns.length; count_++)
            {
                initialValues.put(columns[count_], entries[count_]);
                System.out.println("Column name = " + columns[count_] + " & value = " + entries[count_]);
            }
            //this.insertId=db.insert(tableName, null, initialValues);
            this.insertId=db.insertWithOnConflict(tableName, null, initialValues, db.CONFLICT_IGNORE);
            return this.insertId;
        }

        public int deleteRow(long rowId, String tableName, String where) {
            return db.delete(tableName, where + "=" + rowId, null);
        }

        public Cursor fetchRow(String rowId, String tableName, String[] columns, String where, String[] stringArg) {
            //where="_id=?"
            //?=new String[]{rowId}

            Row row = new Row();
            Cursor c =
                db.query(tableName, columns, where, stringArg , null, null, null);
                    //db.query(true, tableName, new String[] {
                    //"_id", "name", "address", "mobile","home"}, "_id=" + rowId, null, null,
                    //null, null);
            return c;

        }
        
        
        public Cursor fetchRowType2(String paramString1, String paramString2, String[] paramArrayOfString1, String paramString3, String[] paramArrayOfString2, String paramString4, String paramString5)
        {
        	Row row = new Row();
        	Cursor c =
                    db.query(paramString2, paramArrayOfString1, paramString3, paramArrayOfString2, null, null, paramString4, paramString5);
        	return c;
        }
        
        
        public Cursor fetchRowType2(String tableName, String[] columns, String where, String[] whereArgs, String orderBy, String limit, String groupBy, String having)
        {
        	Row row = new Row();
        	Cursor c =
                    db.query(tableName, columns, where, whereArgs, groupBy, having, orderBy, limit);
        	return c;
        }


        public Stack getData(int column, String tablename)
        {
            Cursor c=fetchRow(null, tablename, null, null, null);
            Stack s=new Stack();

            while((c.moveToNext()))
            {
                s.addElement(c.getString(column));
            }
            return s;

        }

        public int updateRow(long rowId, ContentValues args, String where, String tableName) {
            //ContentValues args = new ContentValues();
            /*args.put("name", name);
            args.put("address", address);
            args.put("mobile", mobile);
            args.put("home", home);*/
            return db.update(tableName, args, "_id=" + rowId, null);
        }
        
        
        public int updateRow(ContentValues args, String where, String[] whereValues, String tableName) {
            //ContentValues args = new ContentValues();
            /*args.put("name", name);
            args.put("address", address);
            args.put("mobile", mobile);
            args.put("home", home);*/
            return db.update(tableName, args, where, whereValues);
        }

        public boolean tableExist(String tablename)
        {
        	
            try {
            	
                Cursor c=db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = ?", new String[]{tablename});
                if(c!=null && (c.getCount()>0))
                	{
                	Log.v("find here", "FIND");
                	c.close();
                	return true;
                	}
                else
                {
                	Log.v("find here", "FIND NO");
                	c.close();
                	return false;
                }
            }
            catch (SQLException Re) {
                return false;
            }

        }


        public void beginTransact()
        {
            db.beginTransaction();
        }

        public void setTransactSuc()
        {
            Log.i("setTransactSuc", "1");
            db.setTransactionSuccessful();
        }

        public void endTransact()
        {
            db.endTransaction();
        }
    }

}

