package pract.es.deusto.wake_up;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.hardware.SensorEventListener;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;


public class User_data extends AppCompatActivity implements SensorEventListener {
    EditText nombre_EdT,telefono_EdT,peso_EdT,altura_EdT,residencia_Edt,decripcion_EdT,acelerometro_EdT;
    Button volver_btn,llamar_btn;
    String tel,nombre,peso,altura,resi,decri,aceler;
    private SensorManager Acelerometro;
    private Sensor senAccelerometer;
    private long UltimaLectura = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;




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
        llamar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialContactPhone(tel);
            }
        });
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
        Acelerometro = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = Acelerometro.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Acelerometro.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);

    }
    private void dialContactPhone(final String phoneNumber){
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",phoneNumber,null)));
    }
    protected void onPause() {
        super.onPause();
        Acelerometro.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        Acelerometro.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent SensorEvent) {
        Sensor mySensor = SensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = SensorEvent.values[0];
            float y = SensorEvent.values[1];
            float z = SensorEvent.values[2];

            long TiempoActual = System.currentTimeMillis();

            if ((TiempoActual - UltimaLectura) > 100) {
                long diffTime = (TiempoActual - UltimaLectura);
                UltimaLectura = TiempoActual;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z)/ diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {

                }
                last_x = x;
                last_y = y;
                last_z = z;
            }
            aceler="("+last_x+","+last_y+","+last_z+")";
            acelerometro_EdT.setText(aceler);
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
