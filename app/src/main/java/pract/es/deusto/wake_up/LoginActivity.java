package pract.es.deusto.wake_up;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pract.es.deusto.wake_up.utilidades.Utilidades;

public class LoginActivity extends AppCompatActivity {
    Button registrarse;
    Button login;
    EditText nombre,pass;
    int cod_profesional;
    String email_pro;
    String nombre_pro;

    public  ConexionSQLiteHelper conn;
    public SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        email_pro="jonacha@deusto";
        nombre_pro="Jon";
        cod_profesional=0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        conn=new ConexionSQLiteHelper(getApplicationContext(),"Wake_Up",null,1);
        db=conn.getReadableDatabase();
        //db.execSQL(Utilidades.CREATE_TABLE_USER);
        nombre=(EditText) findViewById(R.id.nick_email);
        pass=(EditText) findViewById(R.id.password) ;
        login=findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          loginEnfermeraSQL();
            }
        });
        registrarse=findViewById(R.id.btn_registrarse);
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentReg=new Intent(LoginActivity.this,Registro.class);
                LoginActivity.this.startActivity(intentReg);
            }
        });

    }

    private void loginEnfermeraSQL() {
       // db=conn.getReadableDatabase();
        String login= "select  cod_profesional_salud,email, nombre  from profesional_salud where( email='"+ nombre.getText().toString()+
                "' OR nombre='"+nombre.getText().toString()+"') AND pass='"+pass.getText().toString()+"';";

        Cursor info=db.rawQuery(login,null);
        info.moveToFirst();
     try{

         cod_profesional=Integer.parseInt(info.getString(0));
         email_pro=info.getString(1);
         nombre_pro=info.getString(2);

         Intent intentMenu=new Intent(LoginActivity.this,Menu_Lateral.class);
         intentMenu.putExtra("email",email_pro);
         intentMenu.putExtra("cod_profesional",cod_profesional);
         intentMenu.putExtra("nombre_pro",nombre_pro);
         LoginActivity.this.startActivity(intentMenu);
         info.close();
     }catch (Exception e){
         Toast.makeText(getApplicationContext(),"El usuario no existe ",Toast.LENGTH_LONG).show();
         limpiar();
     }
       /*  if(info.moveToFirst()){
            cod_profesional=Integer.parseInt(info.getString(0));
            email_pro=info.getString(1);
            nombre_pro=info.getString(2);
            Intent intentMenu=new Intent(LoginActivity.this,Menu_Lateral.class);
            intentMenu.putExtra("email",email_pro);
            intentMenu.putExtra("cod_profesional",cod_profesional);
            intentMenu.putExtra("nombre_pro",nombre_pro);
            LoginActivity.this.startActivity(intentMenu);
        }else{
            Toast.makeText(this,"Pass o email o nick incorrectos", Toast.LENGTH_SHORT).show();
        }
        *//*
        Intent intentMenu=new Intent(LoginActivity.this,Menu_Lateral.class);
        intentMenu.putExtra("email",email_pro);
        intentMenu.putExtra("cod_profesional",cod_profesional);
        intentMenu.putExtra("nombre_pro",nombre_pro);
        LoginActivity.this.startActivity(intentMenu);*/
       db.close();
    }

    private void limpiar() {
        nombre.setText("");
        pass.setText("");
    }
}
