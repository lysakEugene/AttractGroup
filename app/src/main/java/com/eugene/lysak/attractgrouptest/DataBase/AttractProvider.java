package com.eugene.lysak.attractgrouptest.DataBase;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by zeka on 05.04.16.
 */
public class AttractProvider extends ContentProvider {

    private static final String TABLE_HEROES = "heroes";

    private static final String DB_NAME = TABLE_HEROES + ".db";
    private static final int DB_VERSION = 1;

    private static final UriMatcher sUriMatcher;

    private static final int PATH_ROOT = 0;
    private static final int PATH_HEROES = 1;

    static {
        sUriMatcher = new UriMatcher(PATH_ROOT);
        sUriMatcher.addURI(Contract.AUTHORITY, Contract.Heroes.CONTENT_PATH, PATH_HEROES);
    }

    private DatabaseHelper mDatabaseHelper;


    class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql =
                    "create table " + TABLE_HEROES + " (" +
                            Contract.Heroes._ID + " integer primary key, " +
                            Contract.Heroes.NAME + " text, " +
                            Contract.Heroes.DESCRIPTION + " text, " +
                            Contract.Heroes.IMAGE + " text," +
                            Contract.Heroes.TIME + " text" +
                            ")";
            db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }

    }

    @Override
    public boolean onCreate() {
        mDatabaseHelper = new DatabaseHelper(getContext(), DB_NAME, null, DB_VERSION);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (sUriMatcher.match(uri)) {
            case PATH_HEROES: {
                Cursor cursor = mDatabaseHelper.getReadableDatabase().query(TABLE_HEROES, projection, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), Contract.Heroes.CONTENT_URI);
                return cursor;
            }
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case PATH_HEROES:
                return Contract.Heroes.CONTENT_TYPE;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (sUriMatcher.match(uri)) {
            case PATH_HEROES: {
                mDatabaseHelper.getWritableDatabase().insert(TABLE_HEROES, null, values);
                getContext().getContentResolver().notifyChange(Contract.Heroes.CONTENT_URI, null);
            }
            default:
                return null;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (sUriMatcher.match(uri)) {
            case PATH_HEROES:
                return mDatabaseHelper.getWritableDatabase().delete(TABLE_HEROES, selection, selectionArgs);
            default:
                return 0;
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        switch (sUriMatcher.match(uri)) {
            case PATH_HEROES:
                return mDatabaseHelper.getWritableDatabase().update(TABLE_HEROES, values, selection, selectionArgs);
            default:
                return 0;
        }
    }
}
