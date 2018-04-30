package pract.es.deusto.wake_up.utilidades;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import pract.es.deusto.wake_up.utilidades.Utilidades;

/**
 * Created by Jon_Acha on 30/03/2018.
 */

public class ConexionSQLiteHelper extends SQLiteOpenHelper{
   // String CREAR_TABLA_PACIENTE="CREATE TABLE Paciende (id INTEGER,NOMBRE,ID_DOCTOR TEXT ,TEL)";
    String CREATE_TABLE_ENFERMERA="CREATE TABLE IF NOT EXISTS profesional_salud ( cod_profesional_salud INTEGER primary key " +
            "  AUTOINCREMENT NOT NULL, nombre TEXT not null UNIQUE, pass TEXT not null ," +
            "  email TEXT not null UNIQUE);" ;
    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(CREATE_TABLE_ENFERMERA);
    db.execSQL(Utilidades.CREATE_TABLE_USER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int VersionNueva) {
         db.execSQL("DROP TABLE IF EXISTS usuario");
         db.execSQL("DROP TABLE IF EXISTS profesional_salud");
             onCreate(db);
    }

}
