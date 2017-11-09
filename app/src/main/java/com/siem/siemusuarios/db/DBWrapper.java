package com.siem.siemusuarios.db;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.siem.siemusuarios.model.api.Motivo;
import com.siem.siemusuarios.model.app.Auxilio;
import com.siem.siemusuarios.model.app.Perfil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Lucas on 21/8/17.
 */

public class DBWrapper {

    public static void cleanAllDB(Context context){
        cleanPerfiles(context);
        cleanPrecategorizaciones(context);
        cleanAjuste(context);
        cleanAuxilios(context);
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
        cv.put(DBContract.Perfiles.COLUMN_NAME_NRO_CONTACTO, perfil.getContacto());
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
                perfil.setContacto(cursor.getString(cursor.getColumnIndex(DBContract.Perfiles.COLUMN_NAME_NRO_CONTACTO)));
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

    public static void savePrecategorizacion(Context context, Motivo motivo){
        ContentValues cv = new ContentValues();
        cv.put(DBContract.Precategorizacion.COLUMN_NAME_DESCRIPCION, motivo.getDescripcion());
        Uri uri = context.getContentResolver().insert(
                DBContract.Precategorizacion.CONTENT_URI,
                cv
        );
        saveOpcionPrecategorizacion(context, motivo, ContentUris.parseId(uri));
    }

    private static void saveOpcionPrecategorizacion(Context context, Motivo motivo, long idPrecategorizacion) {
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

    public static List<Motivo> getAllPrecategorizaciones(Context context){
        Cursor cursor = context.getContentResolver().query(
                DBContract.Precategorizacion.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        List<Motivo> listPrecategorizaciones = new ArrayList<>();
        if(cursor != null){
            while(cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex(DBContract.Precategorizacion._ID));
                String descripcion = cursor.getString(cursor.getColumnIndex(DBContract.Precategorizacion.COLUMN_NAME_DESCRIPCION));

                Motivo precategorizacion = new Motivo(descripcion);
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
        cleanOpcionAjuste(context);
    }

    private static void cleanOpcionAjuste(Context context){
        context.getContentResolver().delete(
                DBContract.OpcionAjuste.CONTENT_URI,
                null,
                null
        );
    }

    public static void saveAjuste(Context context, Motivo motivo){
        ContentValues cv = new ContentValues();
        cv.put(DBContract.Ajuste.COLUMN_NAME_DESCRIPCION, motivo.getDescripcion());
        Uri uri = context.getContentResolver().insert(
                DBContract.Ajuste.CONTENT_URI,
                cv
        );
        saveOpcionAjuste(context, motivo, ContentUris.parseId(uri));
    }

    private static void saveOpcionAjuste(Context context, Motivo motivo, long idAjuste) {
        for (String descripcion : motivo.getListOptions()) {
            ContentValues cv = new ContentValues();
            cv.put(DBContract.OpcionAjuste.COLUMN_NAME_DESCRIPCION, descripcion);
            cv.put(DBContract.OpcionAjuste.COLUMN_NAME_ID_AJUSTE, idAjuste);
            context.getContentResolver().insert(
                    DBContract.OpcionAjuste.CONTENT_URI,
                    cv
            );
        }
    }

    public static List<Motivo> getAllAjuste(Context context){
        Cursor cursor = context.getContentResolver().query(
                DBContract.Ajuste.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        List<Motivo> listMotivosAjuste = new ArrayList<>();
        if(cursor != null){
            while(cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex(DBContract.Ajuste._ID));
                String descripcion = cursor.getString(cursor.getColumnIndex(DBContract.Ajuste.COLUMN_NAME_DESCRIPCION));

                Motivo ajuste = new Motivo(descripcion);
                ajuste.setListOptions(getAllOpcionesAjuste(context, id));
                listMotivosAjuste.add(ajuste);
            }
            cursor.close();
        }

        return listMotivosAjuste;
    }

    public static List<String> getAllOpcionesAjuste(Context context, int idAjuste){
        Cursor cursor = context.getContentResolver().query(
                DBContract.OpcionAjuste.CONTENT_URI,
                null,
                DBContract.OpcionAjuste.COLUMN_NAME_ID_AJUSTE + " = ? ",
                new String[]{ String.valueOf(idAjuste) },
                null
        );

        List<String> listOpcionesAjuste = new ArrayList<>();
        if(cursor != null){
            while(cursor.moveToNext()){
                String descripcion = cursor.getString(cursor.getColumnIndex(DBContract.OpcionAjuste.COLUMN_NAME_DESCRIPCION));

                listOpcionesAjuste.add(descripcion);
            }
            cursor.close();
        }

        return listOpcionesAjuste;
    }

    /**
     * Auxilios
     */
    public static void cleanAuxilios(Context context) {
        context.getContentResolver().delete(
                DBContract.Auxilios.CONTENT_URI,
                null,
                null
        );
    }

    public static void saveAuxilio(Context context, Auxilio auxilio){
        if(getAuxilio(context, auxilio.getCodigo()) == null){
            ContentValues cv = new ContentValues();
            cv.put(DBContract.Auxilios.COLUMN_NAME_CODIGO, auxilio.getCodigo());
            cv.put(DBContract.Auxilios.COLUMN_NAME_ESTADO, auxilio.getEstado());
            cv.put(DBContract.Auxilios.COLUMN_NAME_FECHA, new Date().getTime());
            context.getContentResolver().insert(
                    DBContract.Auxilios.CONTENT_URI,
                    cv
            );
        }else{
            updateAuxilio(context, auxilio);
        }
    }

    public static int updateAuxilio(Context context, Auxilio auxilio){
        ContentValues cv = new ContentValues();
        cv.put(DBContract.Auxilios.COLUMN_NAME_ESTADO, auxilio.getEstado());
        cv.put(DBContract.Auxilios.COLUMN_NAME_FECHA, new Date().getTime());
        return context.getContentResolver().update(
                DBContract.Auxilios.CONTENT_URI,
                cv,
                DBContract.Auxilios.COLUMN_NAME_CODIGO + " = ? ",
                new String[]{ auxilio.getCodigo() }
        );
    }

    public static void deleteAuxilio(Context context, String codigoAuxilio){
        context.getContentResolver().delete(
                DBContract.Auxilios.CONTENT_URI,
                DBContract.Auxilios.COLUMN_NAME_CODIGO + " = ? ",
                new String[]{ codigoAuxilio }
        );
    }

    public static List<Auxilio> getAuxilios(Context context){
        Cursor cursor = context.getContentResolver().query(
                DBContract.Auxilios.CONTENT_URI,
                null,
                null,
                null,
                DBContract.Auxilios.COLUMN_NAME_FECHA + " DESC"
        );

        List<Auxilio> listAuxilios = new ArrayList<>();
        if(cursor != null){
            while(cursor.moveToNext()){
                String codigo = cursor.getString(cursor.getColumnIndex(DBContract.Auxilios.COLUMN_NAME_CODIGO));
                String estado = cursor.getString(cursor.getColumnIndex(DBContract.Auxilios.COLUMN_NAME_ESTADO));
                String fecha = cursor.getString(cursor.getColumnIndex(DBContract.Auxilios.COLUMN_NAME_FECHA));

                Auxilio auxilio = new Auxilio();
                auxilio.setCodigo(codigo);
                auxilio.setEstado(estado);
                auxilio.setFecha(fecha);

                listAuxilios.add(auxilio);
            }
            cursor.close();
        }

        return listAuxilios;
    }

    public static Auxilio getAuxilio(Context context, String codigo){
        Cursor cursor = context.getContentResolver().query(
                DBContract.Auxilios.CONTENT_URI,
                null,
                DBContract.Auxilios.COLUMN_NAME_CODIGO + " = ? ",
                new String[]{ codigo },
                null
        );

        Auxilio auxilio = null;
        if(cursor != null){
            if(cursor.moveToNext()){
                String estado = cursor.getString(cursor.getColumnIndex(DBContract.Auxilios.COLUMN_NAME_ESTADO));
                String fecha = cursor.getString(cursor.getColumnIndex(DBContract.Auxilios.COLUMN_NAME_FECHA));

                auxilio = new Auxilio();
                auxilio.setCodigo(codigo);
                auxilio.setEstado(estado);
                auxilio.setFecha(fecha);
            }
            cursor.close();
        }

        return auxilio;
    }

}
