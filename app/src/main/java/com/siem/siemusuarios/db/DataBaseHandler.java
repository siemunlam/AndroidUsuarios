package com.siem.siemusuarios.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Field;

public class DataBaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DBNAME = "siemusuariosdb";
    private static DataBaseHandler mInstance = null;

    /**
     * Delete tables
     */
    public static final String SQL_DELETE_PERFILES =
            "DROP TABLE IF EXISTS " + DBContract.Perfiles.TABLE_NAME;
    public static final String SQL_DELETE_PRECATEGORIZACION =
            "DROP TABLE IF EXISTS " + DBContract.Precategorizacion.TABLE_NAME;
    public static final String SQL_DELETE_OPCION_PRECATEGORIZACION =
            "DROP TABLE IF EXISTS " + DBContract.OpcionPrecategorizacion.TABLE_NAME;
    public static final String SQL_DELETE_OPCION_AJUSTE =
            "DROP TABLE IF EXISTS " + DBContract.Ajuste.TABLE_NAME;

    /**
     * Create tables
     */
    private static final String SQL_CREATE_PERFILES =
            "CREATE TABLE " + DBContract.Perfiles.TABLE_NAME + "("
                    + DBContract.Perfiles._ID + DBContract.INTEGER_TYPE + DBContract.PRIMARY_KEY + DBContract.AUTOINCREMENT + DBContract.COMMA_SEP
                    + DBContract.Perfiles.COLUMN_NAME_NOMBRE + DBContract.TEXT_TYPE + DBContract.COMMA_SEP
                    + DBContract.Perfiles.COLUMN_NAME_NRO_CONTACTO + DBContract.INTEGER_TYPE + DBContract.COMMA_SEP
                    + DBContract.Perfiles.COLUMN_NAME_APELLIDO + DBContract.TEXT_TYPE + DBContract.COMMA_SEP
                    + DBContract.Perfiles.COLUMN_NAME_SEXO + DBContract.TEXT_TYPE + DBContract.COMMA_SEP
                    + DBContract.Perfiles.COLUMN_NAME_FECHA_NACIMIENTO + DBContract.TEXT_TYPE + ") ";

    private static final String SQL_CREATE_PRECATEGORIZACION =
            "CREATE TABLE " + DBContract.Precategorizacion.TABLE_NAME + "("
                    + DBContract.Precategorizacion._ID + DBContract.INTEGER_TYPE + DBContract.PRIMARY_KEY + DBContract.AUTOINCREMENT + DBContract.COMMA_SEP
                    + DBContract.Precategorizacion.COLUMN_NAME_DESCRIPCION + DBContract.TEXT_TYPE + ") ";

    private static final String SQL_CREATE_OPCION_PRECATEGORIZACION =
            "CREATE TABLE " + DBContract.OpcionPrecategorizacion.TABLE_NAME + "("
                    + DBContract.OpcionPrecategorizacion._ID + DBContract.INTEGER_TYPE + DBContract.PRIMARY_KEY + DBContract.AUTOINCREMENT + DBContract.COMMA_SEP
                    + DBContract.OpcionPrecategorizacion.COLUMN_NAME_ID_PRECATEGORIZACION + DBContract.INTEGER_TYPE + DBContract.COMMA_SEP
                    + DBContract.OpcionPrecategorizacion.COLUMN_NAME_DESCRIPCION + DBContract.TEXT_TYPE + ") ";

    private static final String SQL_CREATE_AJUSTE =
            "CREATE TABLE " + DBContract.Ajuste.TABLE_NAME + "("
                    + DBContract.Ajuste._ID + DBContract.INTEGER_TYPE + DBContract.PRIMARY_KEY + DBContract.AUTOINCREMENT + DBContract.COMMA_SEP
                    + DBContract.Ajuste.COLUMN_NAME_DESCRIPCION + DBContract.TEXT_TYPE + ") ";


    private DataBaseHandler(Context context) {
        super(context, DBNAME, null, DATABASE_VERSION);
    }

    public static DataBaseHandler getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new DataBaseHandler(ctx.getApplicationContext());
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            execSQLFields(sqLiteDatabase, "SQL_CREATE");
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        recreateDB(sqLiteDatabase);
    }

    public void recreateDB(SQLiteDatabase sqLiteDatabase){
        try {
            execSQLFields(sqLiteDatabase, "SQL_DELETE");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        onCreate(sqLiteDatabase);
    }

    private void execSQLFields(SQLiteDatabase db, String fieldsPrefix) {
        for(Field field : getClass().getDeclaredFields()) {
            if (field.getName().contains(fieldsPrefix)) {
                try {
                    db.execSQL((String) field.get(this));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
