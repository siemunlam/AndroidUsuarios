package com.siem.siemusuarios.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.siem.siemusuarios.model.api.MotivoPrecategorizacion;
import com.siem.siemusuarios.model.app.Perfil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucas on 21/8/17.
 */

public class DBWrapper {

    public static void cleanAllDB(Context context){
        cleanPerfiles(context);
        cleanPrecategorizaciones(context);
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
        ContentValues cv = getPerfilesContentValues(perfil);
        context.getContentResolver().insert(
                DBContract.Perfiles.CONTENT_URI,
                cv
        );
    }

    public static void updatePerfil(Context context, Perfil perfil){
        deletePerfil(context, perfil);
        ContentValues cv = getPerfilesContentValues(perfil);
        cv.put(DBContract.Perfiles._ID, perfil.getId());
        context.getContentResolver().insert(
                DBContract.Perfiles.CONTENT_URI,
                cv
        );
    }

    public static void deletePerfil(Context context, Perfil perfil){
        context.getContentResolver().delete(
                DBContract.Perfiles.CONTENT_URI,
                DBContract.Perfiles._ID + " = ? ",
                new String[]{ String.valueOf(perfil.getId()) }
        );
    }

    private static ContentValues getPerfilesContentValues(Perfil perfil){
        ContentValues cv = new ContentValues();
        cv.put(DBContract.Perfiles.COLUMN_NAME_NOMBRE, perfil.getNombre());
        cv.put(DBContract.Perfiles.COLUMN_NAME_APELLIDO, perfil.getApellido());
        if(perfil.getNroContacto() != null)
            cv.put(DBContract.Perfiles.COLUMN_NAME_NRO_CONTACTO, perfil.getNroContacto());
        else
            cv.putNull(DBContract.Perfiles.COLUMN_NAME_NRO_CONTACTO);
        cv.put(DBContract.Perfiles.COLUMN_NAME_SEXO, perfil.getSexo());
        cv.put(DBContract.Perfiles.COLUMN_NAME_FECHA_NACIMIENTO, perfil.getFechaNacimiento());

        return cv;
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
                int id = cursor.getInt(cursor.getColumnIndex(DBContract.Perfiles._ID));
                String nombre = cursor.getString(cursor.getColumnIndex(DBContract.Perfiles.COLUMN_NAME_NOMBRE));
                String apellido = cursor.getString(cursor.getColumnIndex(DBContract.Perfiles.COLUMN_NAME_APELLIDO));
                String sexo = cursor.getString(cursor.getColumnIndex(DBContract.Perfiles.COLUMN_NAME_SEXO));
                String fechaNacimiento = cursor.getString(cursor.getColumnIndex(DBContract.Perfiles.COLUMN_NAME_FECHA_NACIMIENTO));

                Perfil perfil = new Perfil();
                perfil.setId(id);
                perfil.setNombre(nombre);
                perfil.setApellido(apellido);
                if(!cursor.isNull(cursor.getColumnIndex(DBContract.Perfiles.COLUMN_NAME_NRO_CONTACTO)))
                    perfil.setNroContacto(cursor.getInt(cursor.getColumnIndex(DBContract.Perfiles.COLUMN_NAME_NRO_CONTACTO)));
                perfil.setSexo(sexo);
                perfil.setFechaNacimiento(fechaNacimiento);
                listPerfiles.add(perfil);
            }
            cursor.close();
        }

        return listPerfiles;
    }

    /**
     * Precategorizacion
     */
    public static void cleanPrecategorizaciones(Context context) {
        context.getContentResolver().delete(
                DBContract.Precategorizacion.CONTENT_URI,
                null,
                null
        );
    }

    public static void savePrecategorizacion(Context context, MotivoPrecategorizacion motivo){
        ContentValues cv = new ContentValues();
        cv.put(DBContract.Precategorizacion.COLUMN_NAME_DESCRIPCION, motivo.getDescripcion());
        context.getContentResolver().insert(
                DBContract.Precategorizacion.CONTENT_URI,
                cv
        );
    }

    public static List<MotivoPrecategorizacion> getAllPrecategorizaciones(Context context){
        Cursor cursor = context.getContentResolver().query(
                DBContract.Precategorizacion.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        List<MotivoPrecategorizacion> listPrecategorizaciones = new ArrayList<>();
        if(cursor != null){
            while(cursor.moveToNext()){
                String descripcion = cursor.getString(cursor.getColumnIndex(DBContract.Precategorizacion.COLUMN_NAME_DESCRIPCION));

                MotivoPrecategorizacion precategorizacion = new MotivoPrecategorizacion(descripcion);
                listPrecategorizaciones.add(precategorizacion);
            }
            cursor.close();
        }

        return listPrecategorizaciones;
    }

}
