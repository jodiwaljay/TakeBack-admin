package com.elegance.takeback;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;

/**
 * Created by Jay on 9/30/2015.
 */
public class sql_logo {

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
    public sql_logo(Context c){
        ourContext = c;
    }

    public sql_logo open() throws SQLException{
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
            db.execSQL("CREATE TABLE " + DATABASE_TABLE + " ( ID INTEGER PRIMARY KEY AUTOINCREMENT, IMAGE BLOB );"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }
    public long createEntry(byte[] IMG ){
        ContentValues cv = new ContentValues();
        cv.put("IMAGE",IMG);

        return ourDatabase.update(DATABASE_TABLE, cv, "ID=0", null);

    }

    public byte[] getData (){
            byte[] img = null;
            Cursor c;
            String[] col={"IMAGE"};
            c=ourDatabase.query(DATABASE_TABLE, col, null, null, null, null, null);

            if(c!=null){
                c.moveToFirst();
                do{
                    img=c.getBlob(c.getColumnIndex("IMAGE"));
                }while(c.moveToNext());
            }
            c.close();
            return img;


    }

    public void checkDefaultImage(){
        Cursor mCursor = ourDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE, null);

        if (!mCursor.moveToFirst())
        {
            ContentValues cv = new ContentValues();
            Bitmap bitmap = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.khadi_small);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100, stream);
            byte[] bitMapData = stream.toByteArray();
            cv.put("IMAGE",bitMapData);
            ourDatabase.insert(DATABASE_TABLE,null,cv);

        }
    }


}
