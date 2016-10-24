package edu.cosc4730.prog4db.prog5Neil;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.ULocale;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import android.net.Uri;

import edu.cosc4730.prog4db.prog5Neil.DBOpenHelper;

public class ContactsProvider extends ContentProvider
{

    private static final String PROVIDER_NAME = "edu.cosc4730.prog4db.prog5Neil";
    //private static final String BASE_PATH = "contacts";
    public static final Uri CONTENT_URI1 = Uri.parse("content://" + PROVIDER_NAME + "/Category");
    public static final Uri CONTENT_URI2 = Uri.parse("content://" + PROVIDER_NAME + "/Accounts/transactions/1");

    private static final int CATEGORIES = 1;
    private static final int CATEGORIES_ID = 2;
    private static final int ACCOUNTS = 3;
    private static final int ACCOUNTS_ID = 4;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(PROVIDER_NAME,"Category", CATEGORIES);
        uriMatcher.addURI(PROVIDER_NAME,"Category/#",CATEGORIES_ID);
        uriMatcher.addURI(PROVIDER_NAME,"Accounts/transactions/1",ACCOUNTS);
        uriMatcher.addURI(PROVIDER_NAME,"Accounts/transactions/1/#",ACCOUNTS_ID);
    }

    private SQLiteDatabase database;


    static final String TAG = "ContactsProvider";

    public boolean onCreate()
    {
        DBOpenHelper helper = new DBOpenHelper(getContext());
        database = helper.getWritableDatabase();
        return true;
    }

    @Override
    public String getType(Uri uri) {

        switch (uriMatcher.match(uri)) {
            case CATEGORIES:
                return "vnd.android.cursor.dir/checking";
            default:
                throw new IllegalArgumentException("This is an Unknown URI " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case CATEGORIES:
                cursor =  database.query(DBOpenHelper.TABLE_CHECKING,DBOpenHelper.ALL_COLUMNS,
                        s,null,null,null,DBOpenHelper.KEY_NAME +" ASC");
                break;
            default:
                throw new IllegalArgumentException("This is an Unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        int updCount = 0;
        switch (uriMatcher.match(uri)) {
            case CATEGORIES:
                updCount =  database.update(DBOpenHelper.TABLE_CHECKING,contentValues,s,strings);
                break;
            default:
                throw new IllegalArgumentException("This is an Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return updCount;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        long id = database.insert(DBOpenHelper.TABLE_CHECKING,null,contentValues);

        if (id > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI1, id);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Insertion Failed for URI :" + uri);

    }

    @Override
    public int delete(Uri uri, String s, String[] strings)
    {
        int delCount = 0;
        switch (uriMatcher.match(uri)) {
            case CATEGORIES:
                delCount =  database.delete(DBOpenHelper.TABLE_CHECKING,s,strings);
                break;
            default:
                throw new IllegalArgumentException("This is an Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return delCount;
    }

    /*@Override
    public boolean onCreate() {
        DBOpenHelper helper = new DBOpenHelper(getContext());
        database = helper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case CATEGORIES:
                cursor =  database.query(DBOpenHelper.TABLE_CHECKING,DBOpenHelper.ALL_COLUMNS,
                        s,null,null,null,DBOpenHelper.CONTACT_NAME +" ASC");
                break;
            default:
                throw new IllegalArgumentException("This is an Unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        switch (uriMatcher.match(uri)) {
            case CATEGORIES:
                return "vnd.android.cursor.dir/contacts";
            default:
                throw new IllegalArgumentException("This is an Unknown URI " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        long id = database.insert(DBOpenHelper.TABLE_CHECKING,null,contentValues);

        if (id > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI1, id);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Insertion Failed for URI :" + uri);

    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        int delCount = 0;
        switch (uriMatcher.match(uri)) {
            case CATEGORIES:
                delCount =  database.delete(DBOpenHelper.TABLE_CHECKING,s,strings);
                break;
            default:
                throw new IllegalArgumentException("This is an Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return delCount;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        int updCount = 0;
        switch (uriMatcher.match(uri)) {
            case CATEGORIES:
                updCount =  database.update(DBOpenHelper.TABLE_CHECKING,contentValues,s,strings);
                break;
            default:
                throw new IllegalArgumentException("This is an Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return updCount;
    }*/
}
