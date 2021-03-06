package pract.es.deusto.wake_up;

import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import pract.es.deusto.wake_up.utilidades.Utility;

public class Foto_perfil extends AppCompatActivity {

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Button btnSelect;
    Button volver;
    private ImageView ivImage;
    private String userChoosenTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto_perfil);
        btnSelect = (Button) findViewById(R.id.btnSelectPhoto);
        btnSelect.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        ivImage = (ImageView) findViewById(R.id.ivImage);
        SharedPreferences SP= PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        String image=SP.getString("image","NA");
        // Log.d("STATE","image: "+ image);
        if(image.equalsIgnoreCase("NA")) {

        }
        else{
            ivImage.setImageBitmap(decodificar(image));
        }
        volver=findViewById(R.id.btn_foto_perfil_volver);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMenu=new Intent(Foto_perfil.this,Menu_Lateral.class);
                intentMenu.putExtra("email",Menu_Lateral.Email_medico);
                intentMenu.putExtra("cod_profesional",Menu_Lateral.profresionalId);
                intentMenu.putExtra("nombre_pro",Menu_Lateral.Nombre_medico);
                Foto_perfil.this.startActivity(intentMenu);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Tomar Foto"))
                        cameraIntent();
                    else if(userChoosenTask.equals(" Galeria Imagenes"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Tomar Foto", " Galeria Imagenes",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(Foto_perfil.this);
        builder.setTitle("Añadir Foto!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(Foto_perfil.this);

                if (items[item].equals("Tomar Foto")) {
                    userChoosenTask ="Tomar Foto";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals(" Galeria Imagenes")) {
                    userChoosenTask =" Galeria Imagenes";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);

    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");


        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ivImage.setImageBitmap(thumbnail);
        String imagen_Codificada= CodificarLaIMagen(thumbnail);
        guardarIMagen(imagen_Codificada);
        Log.e("Direccion",destination.toString());
     /*   SharedPreferences SP= PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        String Residencia=SP.getString("image","NA");*/
    }

    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ivImage.setImageBitmap(bm);
        String imagen_Codificada= CodificarLaIMagen(bm);
        guardarIMagen(imagen_Codificada);

    }
    private static String CodificarLaIMagen(Bitmap bm){
        ByteArrayOutputStream imageByte=new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG,100,imageByte);
        byte [] b=imageByte.toByteArray();
        String imageEncored= android.util.Base64.encodeToString(b, android.util.Base64.DEFAULT);
        return imageEncored;
    }
    private void guardarIMagen(String imagen_Codificada){
        SharedPreferences editor= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor editorImage=editor.edit();
        editorImage.putString("image",imagen_Codificada);
        editorImage.commit();
    }
    public static Bitmap decodificar(String imput){
        byte []decodedByte= android.util.Base64.decode(imput,0);
        return BitmapFactory.decodeByteArray(decodedByte,0,decodedByte.length);
    }


}
