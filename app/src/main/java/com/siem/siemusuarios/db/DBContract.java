package com.siem.siemusuarios.db;

import android.net.Uri;
import android.provider.BaseColumns;

import com.siem.siemusuarios.BuildConfig;

public class DBContract {

    /**
     * Database constants
     */
    public static final String COMMA_SEP = ",";
    public static final String TEXT_TYPE = " TEXT";
    public static final String INTEGER_TYPE = " INTEGER";
    public static final String PRIMARY_KEY = " PRIMARY KEY";
    public static final String AUTOINCREMENT = " AUTOINCREMENT";
    public static final String NOT_NULL = " NOT NULL";
    public static final String DEFAULT = " DEFAULT";
    public static final String NULL = " NULL";

    /**
     * Content constants
     */
    public static final String CONTENT_AUTHORITY = BuildConfig.APPLICATION_ID + ".provider";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Path constants
     */
    public static final String PERFILES = "perfiles";
    public static final String PRECATEGORIZACION = "precategorizacion";

    /**
     * Tipos MIME
     */
    public static final String MIME_DIR = "vnd.android.cursor.dir";
    public static final String MIME_ITEM = "vnd.android.cursor.item";

    public DBContract() {
    }

    public static abstract class Perfiles implements BaseColumns {
        public static final String TABLE_NAME = "perfiles";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendEncodedPath(PERFILES).build();

        public static final String COLUMN_NAME_NOMBRE = "nombre";
        public static final String COLUMN_NAME_APELLIDO = "apellido";
        public static final String COLUMN_NAME_NRO_CONTACTO = "nro_contacto";
        public static final String COLUMN_NAME_SEXO = "sexo";
        public static final String COLUMN_NAME_FECHA_NACIMIENTO = "fecha_nacimiento";
    }

    public static abstract class Precategorizacion implements BaseColumns {
        public static final String TABLE_NAME = "precategorizacion";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendEncodedPath(PRECATEGORIZACION).build();

        public static final String COLUMN_NAME_DESCRIPCION = "descripcion";
    }
}
