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
    public static final int PRECATEGORIZACION = 2;
    public static final int OPCION_PRECATEGORIZACION = 3;

    private DataBaseHandler dbHandler;
    private SQLiteDatabase db;
    public static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final String cAuthority = DBContract.CONTENT_AUTHORITY;

    static {
        sUriMatcher.addURI(cAuthority, DBContract.PERFILES, PERFILES);
        sUriMatcher.addURI(cAuthority, DBContract.PRECATEGORIZACION, PRECATEGORIZACION);
        sUriMatcher.addURI(cAuthority, DBContract.OPCION_PRECATEGORIZACION, OPCION_PRECATEGORIZACION);
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

            case PRECATEGORIZACION:
                return DBContract.MIME_DIR + "/" + DBContract.PRECATEGORIZACION;

            case OPCION_PRECATEGORIZACION:
                return DBContract.MIME_DIR + "/" + DBContract.OPCION_PRECATEGORIZACION;

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

            case PRECATEGORIZACION:
                return db.query(
                        DBContract.Precategorizacion.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );

            case OPCION_PRECATEGORIZACION:
                return db.query(
                        DBContract.OpcionPrecategorizacion.TABLE_NAME,
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

            case PRECATEGORIZACION:
                id = db.insert(
                        DBContract.Precategorizacion.TABLE_NAME,
                        null,
                        contentValues
                );
                return Uri.parse(DBContract.Precategorizacion.CONTENT_URI + "/" + id);

            case OPCION_PRECATEGORIZACION:
                id = db.insert(
                        DBContract.OpcionPrecategorizacion.TABLE_NAME,
                        null,
                        contentValues
                );
                return Uri.parse(DBContract.OpcionPrecategorizacion.CONTENT_URI + "/" + id);

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

            case PRECATEGORIZACION:
                deleted = db.delete(
                        DBContract.Precategorizacion.TABLE_NAME,
                        selection,
                        selectionArgs
                );
                return deleted;

            case OPCION_PRECATEGORIZACION:
                deleted = db.delete(
                        DBContract.OpcionPrecategorizacion.TABLE_NAME,
                        selection,
                        selectionArgs
                );
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

            case PRECATEGORIZACION:
                return db.update(
                        DBContract.Precategorizacion.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs
                );

            case OPCION_PRECATEGORIZACION:
                return db.update(
                        DBContract.OpcionPrecategorizacion.TABLE_NAME,
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
