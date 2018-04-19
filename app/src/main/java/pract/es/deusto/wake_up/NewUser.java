package pract.es.deusto.wake_up;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewUser extends AppCompatActivity {
    Button registrarse;
    Button volver;
    EditText nombre_user,altura,peso,descripcion,telefono;
    public  ConexionSQLiteHelper conn;
    public SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        volver=findViewById(R.id.btn_back_reg_user);
        registrarse=findViewById(R.id.btn_registro_user);
        nombre_user=findViewById(R.id.nombre_reg_user);
        altura=findViewById(R.id.altura_user);
        peso=findViewById(R.id.peso_reg_user);
        descripcion=findViewById(R.id.descripcion_reg_user);
        telefono=findViewById(R.id.telephhono_user);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentMenu=new Intent(NewUser.this,Menu_Lateral.class);
                intentMenu.putExtra("email",Menu_Lateral.Email_medico);
                intentMenu.putExtra("cod_profesional",Menu_Lateral.profresionalId);
                intentMenu.putExtra("nombre_pro",Menu_Lateral.Nombre_medico);
                NewUser.this.startActivity(intentMenu);
            }
        });
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegistrarPacienteSQL();
                Intent intentMenu=new Intent(NewUser.this,Menu_Lateral.class);
                intentMenu.putExtra("email",Menu_Lateral.Email_medico);
                intentMenu.putExtra("cod_profesional",Menu_Lateral.profresionalId);
                intentMenu.putExtra("nombre_pro",Menu_Lateral.Nombre_medico);
                NewUser.this.startActivity(intentMenu);
            }
        });
        //btn_back_reg_user
    }

    private void RegistrarPacienteSQL() {
        conn=new ConexionSQLiteHelper(getApplicationContext(),"Wake_Up",null,1);
        db=conn.getWritableDatabase();
        ContentValues registro=new ContentValues();
        Integer tel=Integer.parseInt(telefono.getText().toString());
        Integer pes=Integer.parseInt(peso.getText().toString());
        Integer alt=Integer.parseInt(altura.getText().toString());
        SharedPreferences SP= PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        String Residencia=SP.getString("residencia","NA");
        Log.d("STATE",Residencia);
        String insertExaple="Insert Into usuario (nombre,  descripcion ,altura,peso,telefono,residencia,cod_enf) " +
                "    values     ('"+ nombre_user.getText().toString()+"','"+descripcion.getText().toString()+"',"+alt+","+pes+","+tel+",'"+Residencia+"',"+Menu_Lateral.profresionalId+");";

        db.execSQL(insertExaple);
        db.close();
    }
}
