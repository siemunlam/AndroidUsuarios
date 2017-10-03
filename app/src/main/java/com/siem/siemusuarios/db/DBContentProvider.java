package com.siem.siemusuarios.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class DBContentProvider extends ContentProvider {

    public static final int PERFILES = 1;

    private DataBaseHandler dbHandler;
    private SQLiteDatabase db;
    public static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final String cAuthority = DBContract.CONTENT_AUTHORITY;

    static {
        sUriMatcher.addURI(cAuthority, DBContract.PERFILES, PERFILES);
    }

    public DBContentProvider() {
    }

    @Override
    public boolean onCreate() {
        dbHandler = DataBaseHandler.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int match = sUriMatcher.match(uri);
        switch (match) {
            case PERFILES:
                return DBContract.MIME_DIR + "/" + DBContract.PERFILES;

            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int match = sUriMatcher.match(uri);
        db = dbHandler.getWritableDatabase();

        switch (match) {
            case PERFILES:
                return db.query(
                        DBContract.Perfiles.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );

            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        int match = sUriMatcher.match(uri);
        db = dbHandler.getWritableDatabase();

        switch (match) {
            case PERFILES:
                long id = db.insert(
                        DBContract.Perfiles.TABLE_NAME,
                        null,
                        contentValues
                );
                notifyChange(DBContract.Perfiles.CONTENT_URI, null);
                return Uri.parse(DBContract.Perfiles.CONTENT_URI + "/" + id);

            default:
                return uri;
        }
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        int match = sUriMatcher.match(uri);
        db = dbHandler.getWritableDatabase();

        switch (match){
            case PERFILES:
                int deleted = db.delete(
                        DBContract.Perfiles.TABLE_NAME,
                        selection,
                        selectionArgs
                );
                notifyChange(DBContract.Perfiles.CONTENT_URI, null);
                return deleted;

            default:
                return 0;
        }
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int match = sUriMatcher.match(uri);
        db = dbHandler.getWritableDatabase();

        switch (match) {
            case PERFILES:
                return db.update(
                        DBContract.Perfiles.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs
                );

            default:
                return 0;
        }
    }

    private void notifyChange(@NonNull Uri uri, @Nullable ContentObserver observer) {
        if(getContext() != null)
            getContext().getContentResolver().notifyChange(uri, observer);
    }

}
