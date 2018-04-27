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

                registrarEnfermeraSQL();
            }
        });
    }
    private void registrarEnfermeraSQL() {

        db=conn.getWritableDatabase();
        String insertExaple="INSERT INTO profesional_salud (nombre,email,pass) VALUES ('" +
                nombre.getText().toString()+ "','"+email.getText().toString() +"','"+ pass.getText().toString()+"');";
        db.execSQL(insertExaple);
        Intent intentReg=new Intent(Registro.this,LoginActivity.class);
        Registro.this.startActivity(intentReg);
        db.close();
    }

}
