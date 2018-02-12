package cl.gestiona.estimotebluesep2017.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roman on 27-09-17.
 */

public class HistorialCrud {
    private HistorialHelperDB helperDB;
    private SQLiteDatabase db;

    public HistorialCrud(Context context) {
        helperDB = new HistorialHelperDB(context);
    }

    public void insertar(Historial historial){
        db = helperDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HistorialHelperDB.COLUMN_TITULAR, historial.getTitular());
        values.put(HistorialHelperDB.COLUMN_FECHA, historial.getFecha());
        values.put(HistorialHelperDB.COLUMN_HORA, historial.getHora());
        values.put(HistorialHelperDB.COLUMN_DESCRIPCION, historial.getDescripcion());
        db.insert(HistorialHelperDB.TABLA, null, values);
        db.close();
    }

    public void  deleteAllHistorial(){
        db = helperDB.getWritableDatabase();
        db.delete(HistorialHelperDB.TABLA,"ID > 0",null);
        db.close();
    }


    public List<Historial> getHistorialList(){
        List<Historial> list = new ArrayList<>();
        db = helperDB.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+HistorialHelperDB.TABLA, null);

        while (cursor.moveToNext()){
            Historial h = new Historial();
            h.setId(cursor.getInt(0));
            h.setTitular(cursor.getString(1));
            h.setFecha(cursor.getString(2));
            h.setHora(cursor.getString(3));
            h.setDescripcion(cursor.getString(4));
            list.add(h);
        }
        db.close();
        return list;

    }




}
