package pract.es.deusto.wake_up.utilidades;

/**
 * Created by Jon_Acha on 30/03/2018.
 */

public class Utilidades {

    //Constates tabla enfermera
    public static final   String TABLA_ENFERMERA="profesional_salud";
    public  static final   String CAMPO_COD_PRO="cod_profesional_salud ";
    public static final String CAMPO_NOMBRE="nombre";
    public static final String CAMPO_EMAIL="email";
    public  static final  String CAMPO_PASS="pass";



   public static final String CREATE_TABLE_ENFERMERA="CREATE TABLE IF NOT EXISTS profesional_salud ( cod_profesional_salud INTEGER primary key \\\n" +
           "  AUTOINCREMENT NOT NULL, nombre TEXT not null UNIQUE, pass TEXT not null , \\\n" +
           "  email TEXT not null UNIQUE" ;
   public static final String CREATE_TABLE_USER="CREATE TABLE IF NOT EXISTS usuario (cod_usuario " +
           "INTEGER primary key  AUTOINCREMENT NOT NULL, nombre TEXT not null UNIQUE, " +
           "descripcion TEXT not null ,altura INTEGER, peso INTEGER,telefono INTEGER , " +
           "residencia TEXT,  cod_enf INTEGER not null,image TEXT,X NUMERIC ,Y NUMERIC ); ";

}
