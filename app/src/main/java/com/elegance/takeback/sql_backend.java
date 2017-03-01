package com.elegance.takeback;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Jay on 9/30/2015.
 */
public class sql_backend {

    public static final String KEY_ROWID = "_id";
    public static final String NAME = "NAME";
    public static final String ADDRESS = "ADDRESS";
    public static final String PHONE = "PHONE";
    public static final String FAX = "FAX";
    public static final String EMAIL = "EMAIL";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String LOGO = "LOGO";


    private final Context ourContext;
    private static final String DATABASE_NAME = "BACKEND_DB";
    private static final String DATABASE_TABLE = "BACKEND_TABLE";
    private static final int DATABASE_VERSION = 1;

    private dbHelper ourHelper;
    private SQLiteDatabase ourDatabase;
    public sql_backend(Context c){
        ourContext = c;
    }

    public sql_backend open() throws SQLException{
        ourHelper = new dbHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        ourHelper.close();
    }

    private static class dbHelper extends SQLiteOpenHelper{

        public dbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
                            KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            NAME + " TEXT NOT NULL, " +
                            ADDRESS + " TEXT NOT NULL, " +
                            PHONE + " TEXT NOT NULL, " +
                            FAX + " TEXT NOT NULL, " +
                            EMAIL + " TEXT NOT NULL, " +
                            DESCRIPTION + " TEXT NOT NULL, " +
                            LOGO + " TEXT NOT NULL);"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }
    public long createEntry(String NAME, String ADDRESS, String PHONE, String FAX, String EMAIL, String DESCRIPTION, String LOGO ){
        ContentValues cv = new ContentValues();
        cv.put("NAME", NAME);
        cv.put("ADDRESS", ADDRESS);
        cv.put("PHONE", PHONE);
        cv.put("FAX", FAX);
        cv.put("EMAIL", EMAIL);
        cv.put("DESCRIPTION", DESCRIPTION);
        cv.put("LOGO", LOGO);

        return ourDatabase.insert(DATABASE_TABLE,null,cv);

    }

    public ArrayList<String> getData (){
        String columns[] = new String[]{NAME};
        Cursor c = ourDatabase.query(DATABASE_TABLE,columns,null,null,null,null,null);

        ArrayList<String> company_names = new ArrayList<String>();


        if(c==null||c.isAfterLast()){
            return null;
        }
        int iName = c.getColumnIndex(NAME);
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            company_names.add(c.getString(iName));
        }

        c.close();
        return company_names;
    }

    public ArrayList<String> getInfoCompany(String name_given){
        String columns[] = new String[]{ NAME, ADDRESS, PHONE, FAX, EMAIL, DESCRIPTION, LOGO };
        ArrayList<String> company_info = new ArrayList<String>();

        Cursor c = ourDatabase.query(DATABASE_TABLE,columns,NAME+"=?",new String[]{name_given},null,null,null);
        if(c!=null) {
            company_info.add(name_given);
            int i = 1;
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext(), i++){
                company_info.add(c.getString(1));
                company_info.add(c.getString(2));
                company_info.add(c.getString(3));
                company_info.add(c.getString(4));
                company_info.add(c.getString(5));
                company_info.add(c.getString(6));
            }
            c.close();
            return company_info;
        }
            return null;
    }

    public boolean isEmpty()  {
        Cursor mCursor = ourDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE, null);

        if (!mCursor.moveToFirst())
        {
            return true;

        }
    return false;
    }
}
