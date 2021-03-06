package pract.es.deusto.wake_up;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.ImageView;
import android.widget.Toast;

import pract.es.deusto.wake_up.utilidades.Utility;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;


public class User_data extends AppCompatActivity implements SensorEventListener {
    EditText nombre_EdT,telefono_EdT,peso_EdT,altura_EdT,residencia_Edt,decripcion_EdT,acelerometro_EdT;
    Button volver_btn,llamar_btn;
    String tel,nombre,peso,altura,resi,decri,aceler,image;
    private SensorManager Acelerometro;
    private Sensor senAccelerometer;
    private long UltimaLectura = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;
    private ImageView ivImage;
    Bitmap bm=null;
    private static final int PICK_FROM_GALLERY = 1;
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
        image=getIntent().getExtras().getSerializable("image").toString();



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

        ivImage=findViewById(R.id.image_perfil);

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
        boolean permisos_Galeria= Utility.checkPermission(User_data.this);
        if(permisos_Galeria){
            accederGalleria();
        }
    }
/*
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    accederGalleria();

                    break;
        }
    }
    }*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //accederGalleria();

                    break;
                }
            case PICK_FROM_GALLERY:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,Uri.parse(image));
                    startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                } else {
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                }
                break;

        }
    }

    private void accederGalleria()

    {  /*     FileInputStream in = null;
        try {
            in = new FileInputStream(Environment.getExternalStorageDirectory()+image);
            BufferedInputStream buf = new BufferedInputStream(in);
            byte[] bMapArray= new byte[buf.available()];
            buf.read(bMapArray);
            Bitmap bMap = BitmapFactory.decodeByteArray(bMapArray, 0, bMapArray.length);


            ivImage.setImageBitmap(bMap);

            Toast.makeText(getApplicationContext(),image+ " Funciono pero no",Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(),image+ "  "+e.toString(),Toast.LENGTH_LONG).show();;
            Log.d("No Hay foto","No hay foto de este usuario");
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),image + "  "+e.toString(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
            Utility.checkPermission(User_data.this);
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);

            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);

            startActivityForResult(Intent.createChooser(intent, "Select File"),1);
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), Uri.parse(image));
            }catch (IOException z){
                bm = null;
            }


        if (bm!=null){
            ivImage.setImageBitmap(bm);
        }*/
        try {
            if (ActivityCompat.checkSelfPermission(User_data.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(User_data.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
            } else {
                Utility.checkPermission(User_data.this);
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), Uri.parse(image));
                ivImage.setImageBitmap(bm);
            }
        } catch (Exception e) {
           // Toast.makeText(getApplicationContext(),image+ e ,Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

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
