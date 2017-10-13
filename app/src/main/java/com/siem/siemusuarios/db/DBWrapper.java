package com.siem.siemusuarios.db;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.siem.siemusuarios.model.api.MotivoAjuste;
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
        cleanAjuste(context);
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
        cleanOpcionPrecategorizacion(context);
    }

    private static void cleanOpcionPrecategorizacion(Context context){
        context.getContentResolver().delete(
                DBContract.OpcionPrecategorizacion.CONTENT_URI,
                null,
                null
        );
    }

    public static void savePrecategorizacion(Context context, MotivoPrecategorizacion motivo){
        ContentValues cv = new ContentValues();
        cv.put(DBContract.Precategorizacion.COLUMN_NAME_DESCRIPCION, motivo.getDescripcion());
        Uri uri = context.getContentResolver().insert(
                DBContract.Precategorizacion.CONTENT_URI,
                cv
        );
        saveOpcionPrecategorizacion(context, motivo, ContentUris.parseId(uri));
    }

    private static void saveOpcionPrecategorizacion(Context context, MotivoPrecategorizacion motivo, long idPrecategorizacion) {
        for (String descripcion : motivo.getListOptions()) {
            ContentValues cv = new ContentValues();
            cv.put(DBContract.OpcionPrecategorizacion.COLUMN_NAME_DESCRIPCION, descripcion);
            cv.put(DBContract.OpcionPrecategorizacion.COLUMN_NAME_ID_PRECATEGORIZACION, idPrecategorizacion);
            context.getContentResolver().insert(
                    DBContract.OpcionPrecategorizacion.CONTENT_URI,
                    cv
            );
        }
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
                int id = cursor.getInt(cursor.getColumnIndex(DBContract.Precategorizacion._ID));
                String descripcion = cursor.getString(cursor.getColumnIndex(DBContract.Precategorizacion.COLUMN_NAME_DESCRIPCION));

                MotivoPrecategorizacion precategorizacion = new MotivoPrecategorizacion(descripcion);
                precategorizacion.setListOptions(getAllOpcionesPrecategorizacion(context, id));
                listPrecategorizaciones.add(precategorizacion);
            }
            cursor.close();
        }

        return listPrecategorizaciones;
    }

    public static List<String> getAllOpcionesPrecategorizacion(Context context, int idPrecategorizacion){
        Cursor cursor = context.getContentResolver().query(
                DBContract.OpcionPrecategorizacion.CONTENT_URI,
                null,
                DBContract.OpcionPrecategorizacion.COLUMN_NAME_ID_PRECATEGORIZACION + " = ? ",
                new String[]{ String.valueOf(idPrecategorizacion) },
                null
        );

        List<String> listOpcionesPrecategorizacion = new ArrayList<>();
        if(cursor != null){
            while(cursor.moveToNext()){
                String descripcion = cursor.getString(cursor.getColumnIndex(DBContract.OpcionPrecategorizacion.COLUMN_NAME_DESCRIPCION));

                listOpcionesPrecategorizacion.add(descripcion);
            }
            cursor.close();
        }

        return listOpcionesPrecategorizacion;
    }

    /**
     * Motivos de Ajuste
     */
    public static void cleanAjuste(Context context) {
        context.getContentResolver().delete(
                DBContract.Ajuste.CONTENT_URI,
                null,
                null
        );
    }

    public static void saveAjuste(Context context, MotivoAjuste motivo){
        ContentValues cv = new ContentValues();
        cv.put(DBContract.Ajuste.COLUMN_NAME_DESCRIPCION, motivo.getDescripcion());
        Uri uri = context.getContentResolver().insert(
                DBContract.Ajuste.CONTENT_URI,
                cv
        );
    }

    public static List<MotivoAjuste> getAllAjuste(Context context){
        Cursor cursor = context.getContentResolver().query(
                DBContract.Ajuste.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        List<MotivoAjuste> listMotivosAjuste = new ArrayList<>();
        if(cursor != null){
            while(cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex(DBContract.Ajuste._ID));
                String descripcion = cursor.getString(cursor.getColumnIndex(DBContract.Ajuste.COLUMN_NAME_DESCRIPCION));

                MotivoAjuste ajuste = new MotivoAjuste(descripcion);
                //precategorizacion.setListOptions(getAllOpcionesPrecategorizacion(context, id));
                listMotivosAjuste.add(ajuste);
            }
            cursor.close();
        }

        return listMotivosAjuste;
    }

}
