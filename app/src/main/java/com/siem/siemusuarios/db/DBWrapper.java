package com.siem.siemusuarios.db;

import android.content.Context;

/**
 * Created by Lucas on 21/8/17.
 */

public class DBWrapper {

    public static void cleanAllDB(Context context){
        cleanPerfiles(context);
    }

    /**
     * Perfiles
     */
    public static void cleanPerfiles(Context context) {
        context.getContentResolver().delete(
                DBContract.Perfiles.CONTENT_URI,
                null,
                null
        );
    }

}
