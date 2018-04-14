package pract.es.deusto.wake_up;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pract.es.deusto.wake_up.utilidades.Utilidades;

public class Registro extends AppCompatActivity {
    Button volver, registrar;
    EditText nombre,email,pass;
    public  ConexionSQLiteHelper conn;
    public  SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        volver=findViewById(R.id.back);
        nombre=(EditText) findViewById(R.id.nick) ;
        email=(EditText) findViewById(R.id.email) ;
        pass=(EditText) findViewById(R.id.pass) ;
        conn=new ConexionSQLiteHelper(getApplicationContext(),"Wake_Up",null,1);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentReg=new Intent(Registro.this,LoginActivity.class);
                Registro.this.startActivity(intentReg);
            }
        });
        registrar=findViewById(R.id.re_registro);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   registrarEnfermera();
                registrarEnfermeraSQL();
            }
        });
    }

    private void registrarEnfermeraSQL() {
      //  ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"Wake_Up",null,1);
        //SQLiteDatabase db=conn.getWritableDatabase();
      /*ContentValues registro=new ContentValues();
      registro.put("nombre",nombre.getText().toString());
        registro.put("email",email.getText().toString());
        registro.put("pass",pass.getText().toString());*/
        db=conn.getWritableDatabase();
        String insertExaple="INSERT INTO profesional_salud (nombre,email,pass) VALUES ('" +
                nombre.getText().toString()+ "','"+email.getText().toString() +"','"+ pass.getText().toString()+"');";
        db.execSQL(insertExaple);

        Intent intentReg=new Intent(Registro.this,LoginActivity.class);
        Registro.this.startActivity(intentReg);
        /*Long solucion=LoginActivity.db.insert("profesional_salud",null,registro);
        Toast.makeText(this,"Pass o email o nick incorrectos"+solucion, Toast.LENGTH_SHORT).show();
        if(solucion!=-1){
            Intent intentReg=new Intent(Registro.this,LoginActivity.class);
            Registro.this.startActivity(intentReg);
        }*/
      /* String insert_profesional= "insert into profesional_salud (nombre,pass,email) " +
                "  values('"+ nombre.getText().toString()+"','"+ pass.getText().toString()+"',   '"+ email.getText().toString()+"');";
    */
       //db.execSQL(insert_profesional);
       //LoginActivity.db.execSQL(insert_profesional);
       //Toast.makeText(getApplicationContext(),"Id registrado: "+,Toast.LENGTH_SHORT).show();



        db.close();
    }
/*
    public void onClick(View view){
        registrarEnfermera();
    }

    private void registrarEnfermera() {
        Toast.makeText(getApplicationContext(),"llego",Toast.LENGTH_SHORT).show();
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"Wake_Up",null,1);

        SQLiteDatabase db=conn.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Utilidades.CAMPO_NOMBRE,nombre.getText().toString());
        values.put(Utilidades.CAMPO_EMAIL,email.getText().toString());
        values.put(Utilidades.CAMPO_PASS,pass.getText().toString());
        Log.d("STATE","registrando enfermera");
        Long idResultado=db.insert(Utilidades.TABLA_ENFERMERA,Utilidades.CAMPO_COD_PRO,values);
        Toast.makeText(getApplicationContext(),"Id registrado: "+idResultado,Toast.LENGTH_SHORT).show();
        db.close();
    }*/

}
