package edu.cosc4730.prog4db.prog5Neil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "edu.cs4730.prog4db";
    private static final int DATABASE_VERSION = 3;

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "Name";   //contactName
    public static final String KEY_DATE = "Date";   //contactDate
    public static final String KEY_TYPE = "CheckNum";  //contactType
    public static final String KEY_AMOUNT = "Amount";
    public static final String KEY_CAT = "Category";
    public static final String KEY_CATNAME = "CatName";
    public static final String KEY_CREATED_ON = "contactCreationTimeStamp";

    public static final String TABLE_CHECKING = "checking";     //contacts
    public static final String TABLE_ACCOUNTS = "accounts";
    public static final String TABLE_CAT = "category";

    public static final String[] ALL_COLUMNS = {KEY_ID,KEY_NAME,KEY_DATE,KEY_TYPE,KEY_AMOUNT,KEY_CREATED_ON};

    private static final String CREATE_TABLE =
            " CREATE TABLE " + TABLE_CHECKING + " ( " +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_NAME + " TEXT, " +
                    KEY_DATE + " TEXT, " +
                    KEY_TYPE + " TEXT, " +
                    KEY_AMOUNT + " TEXT, " +
                    KEY_CREATED_ON + " TEXT default CURRENT_TIMESTAMP" +
                    ")";
    public DBOpenHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_CHECKING);
        onCreate(sqLiteDatabase);
    }
}
