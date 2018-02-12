package cl.gestiona.estimotebluesep2017.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.NotificationCompat;

/**
 * Created by roman on 27-09-17.
 */

public class HistorialHelperDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="historial";
    private static final int VERSION = 1;


    public static final String TABLA = "HISTORIAL";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_TITULAR = "TITULAR";
    public static final String COLUMN_FECHA = "FECHA";
    public static final String COLUMN_HORA = "HORA";
    public static final String COLUMN_DESCRIPCION = "DESCRIPCION";



    public HistorialHelperDB(Context context){
        super(context, DATABASE_NAME, null , VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE ");
        builder.append(TABLA);
        builder.append("(");
        builder.append(COLUMN_ID);
        builder.append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        builder.append(COLUMN_TITULAR);
        builder.append(" TEXT, ");
        builder.append(COLUMN_FECHA);
        builder.append(" TEXT, ");
        builder.append(COLUMN_HORA);
        builder.append(" TEXT, ");
        builder.append(COLUMN_DESCRIPCION);
        builder.append(" TEXT)");

        db.execSQL(builder.toString());
        db.execSQL("INSERT INTO HISTORIAL VALUES(NULL,'Roman','02/10/2017','09:50:12', 'Entrada')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
