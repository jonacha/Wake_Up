package pract.es.deusto.wake_up;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class User_data extends AppCompatActivity {
    EditText nombre_EdT,telefono_EdT,peso_EdT,altura_EdT,residencia_Edt,decripcion_EdT,acelerometro_EdT;
    Button volver_btn,acelerometro_btn,llamar_btn;
    String tel,nombre,peso,altura,resi,decri,aceler;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        tel=getIntent().getExtras().getSerializable("tel").toString();
        nombre=getIntent().getExtras().getSerializable("nombre").toString();
        peso=getIntent().getExtras().getSerializable("peso").toString();
        altura=getIntent().getExtras().getSerializable("altura").toString();
        resi=getIntent().getExtras().getSerializable("residencia").toString();
        decri=getIntent().getExtras().getSerializable("descrip").toString();



        nombre_EdT=findViewById(R.id.nombre_user);
        telefono_EdT=findViewById(R.id.telephhono_user);
        peso_EdT=findViewById(R.id.peso_user);
        altura_EdT=findViewById(R.id.altura_user);
        residencia_Edt=findViewById(R.id.residencia_ed);
        decripcion_EdT=findViewById(R.id.descripcion_user);
        acelerometro_EdT=findViewById(R.id.acelerometro_usuer);

        nombre_EdT.setText("Nombre: "+nombre);
        telefono_EdT.setText("Telefono: "+tel);
        peso_EdT.setText("Peso: "+peso);
        altura_EdT.setText("Altura:"+altura);
        residencia_Edt.setText("Residencia: "+resi);
        decripcion_EdT.setText("Description: "+decri);


        llamar_btn=findViewById(R.id.btn_llamar);
        acelerometro_btn=findViewById(R.id.btn_acelerometro);
        volver_btn=findViewById(R.id.btn_back__user);

        volver_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMenu=new Intent(User_data.this,Menu_Lateral.class);
                intentMenu.putExtra("email",Menu_Lateral.Email_medico);
                intentMenu.putExtra("cod_profesional",Menu_Lateral.profresionalId);
                intentMenu.putExtra("nombre_pro",Menu_Lateral.Nombre_medico);
                User_data.this.startActivity(intentMenu);
            }
        });

    }
}
