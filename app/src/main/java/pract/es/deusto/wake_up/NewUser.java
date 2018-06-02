package pract.es.deusto.wake_up;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import android.provider.MediaStore;
import android.widget.Toast;

import pract.es.deusto.wake_up.utilidades.ConexionSQLiteHelper;
import pract.es.deusto.wake_up.utilidades.Utility;

public class NewUser extends AppCompatActivity {
    private int  SELECT_FILE = 1;
    Button registrarse;
    Button volver;
    Button imagen;
    private ImageView ivImage;
    EditText nombre_user,altura,peso,descripcion,telefono;
    public ConexionSQLiteHelper conn;
    public SQLiteDatabase db;
    private String userChoosenTask;
    String direccionIMagen="Vacio";
    double[] xArray=new double[20];
    double[] yArray=new double[20];
    double x;
    double y;
    private static final int REQUEST_WRITE_PERMISSION = 786;
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
        ivImage = (ImageView) findViewById(R.id.imagen_user);
        double suma=0.0001;
        x=-2.9366;
        y=43.2702;
        for(int i=0;i<xArray.length;i++){
            xArray[i]=x;
            yArray[i]=y;
            x=x+suma;
            y=y+suma;
        }
        Random r=new Random(System.currentTimeMillis());
        x=xArray[r.nextInt(20)];
        y=yArray[r.nextInt(20)];
        Log.e("X:",""+x);
        Log.e("Y:",""+y);
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
        imagen=findViewById(R.id.btn_imagen);
        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
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
        String insertExaple="Insert Into usuario (nombre,  descripcion ,altura,peso,telefono,residencia,cod_enf,image,X,Y) " +
                "    values     ('"+ nombre_user.getText().toString()+"','"+descripcion.getText().toString()+"" +
                "',"+alt+","+pes+","+tel+",'"+Residencia+"'" +
                ","+Menu_Lateral.profresionalId+",'"+direccionIMagen+"',"+x+","+y+");";
        //Toast.makeText(getApplicationContext(),direccionIMagen,Toast.LENGTH_LONG).show();
        db.execSQL(insertExaple);
        db.close();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals(" Galeria Imagenes"))
                        galleriaIntent();
                }
                break;
        }
    }


    private void selectImage() {
        final CharSequence[] items = {  " Galeria Imagenes",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(NewUser.this);
        builder.setTitle("Añadir Foto!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(NewUser.this);

             if (items[item].equals(" Galeria Imagenes")) {
                    userChoosenTask =" Galeria Imagenes";
                    if(result)
                        galleriaIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleriaIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);

        }
    }


    private void onSelectFromGalleryResult(Intent data) {
        Log.e("direccion",data.getDataString());
       direccionIMagen=data.getDataString();
        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());

                requestPermission(bm);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ivImage.setImageBitmap(bm);
    }
    private void requestPermission(Bitmap bm) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
            GuardarImagen(bm);
        }
    }
    private void GuardarImagen(Bitmap image){

        //Guardar imagen

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(Environment.getExternalStorageDirectory() + nombre_user.getText().toString());



            image.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
          //  Toast.makeText(getApplicationContext(), e.toString(),Toast.LENGTH_LONG).show();
        }
    }


}
