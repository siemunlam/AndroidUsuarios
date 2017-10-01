package com.siem.siemusuarios.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.siem.siemusuarios.model.app.Perfil;

import java.util.ArrayList;
import java.util.List;

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

    public static void savePerfil(Context context, Perfil perfil){
        ContentValues cv = new ContentValues();
        cv.put(DBContract.Perfiles.COLUMN_NAME_NOMBRE, perfil.getNombre());
        cv.put(DBContract.Perfiles.COLUMN_NAME_APELLIDO, perfil.getApellido());
        cv.put(DBContract.Perfiles.COLUMN_NAME_NRO_CONTACTO, perfil.getNroContacto());
        cv.put(DBContract.Perfiles.COLUMN_NAME_SEXO, perfil.getSexo());
        cv.put(DBContract.Perfiles.COLUMN_NAME_FECHA_NACIMIENTO, perfil.getFechaNacimiento());
        context.getContentResolver().insert(
                DBContract.Perfiles.CONTENT_URI,
                cv
        );
    }

    public static List<Perfil> getAllPerfiles(Context context){
        Cursor cursor = context.getContentResolver().query(
                DBContract.Perfiles.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        List<Perfil> listPerfiles = new ArrayList<>();
        if(cursor != null){
            while(cursor.moveToNext()){
                String nombre = cursor.getString(cursor.getColumnIndex(DBContract.Perfiles.COLUMN_NAME_NOMBRE));
                String apellido = cursor.getString(cursor.getColumnIndex(DBContract.Perfiles.COLUMN_NAME_APELLIDO));
                Integer nroContacto = cursor.getInt(cursor.getColumnIndex(DBContract.Perfiles.COLUMN_NAME_NRO_CONTACTO));
                String sexo = cursor.getString(cursor.getColumnIndex(DBContract.Perfiles.COLUMN_NAME_SEXO));
                String fechaNacimiento = cursor.getString(cursor.getColumnIndex(DBContract.Perfiles.COLUMN_NAME_FECHA_NACIMIENTO));
                Perfil perfil = new Perfil();

                perfil.setNombre(nombre);
                perfil.setApellido(apellido);
                perfil.setNroContacto(nroContacto);
                perfil.setSexo(sexo);
                perfil.setFechaNacimiento(fechaNacimiento);
                listPerfiles.add(perfil);
            }
            cursor.close();
        }

        return listPerfiles;
    }

}
