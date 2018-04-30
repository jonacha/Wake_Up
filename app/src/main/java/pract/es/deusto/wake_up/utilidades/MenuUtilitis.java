package pract.es.deusto.wake_up.utilidades;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import pract.es.deusto.wake_up.Foto_perfil;
import pract.es.deusto.wake_up.List_users;
import pract.es.deusto.wake_up.LoginActivity;
import pract.es.deusto.wake_up.Maps_activiti;
import pract.es.deusto.wake_up.Medicion;
import pract.es.deusto.wake_up.MySettings;
import pract.es.deusto.wake_up.NewUser;
import pract.es.deusto.wake_up.R;

/**
 * Created by Jon_Acha on 30/04/2018.
 */

public class MenuUtilitis extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView Nombre;
    TextView Email;
    static int profresionalId;
    static  String Nombre_medico;
    static  String Email_medico;
    public ConexionSQLiteHelper conn;
    public SQLiteDatabase db;
    Button ListaPacientes;
    Button AddUser;
    Button Mediciones;
    Button Widget;
    Button FotoPerfil;
    private ImageView ivImage;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu__lateral, menu);
        Nombre=findViewById(R.id.nombre_Menu);
        Nombre.setText(Nombre_medico);
        Email=findViewById(R.id.email_Menu);
        Email.setText( Email_medico);
        SharedPreferences SP= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        ivImage=findViewById(R.id.image_menu);

        String image=SP.getString("image","NA");
        Log.d("STATE","image: "+ image);
        if(image.equalsIgnoreCase("NA")) {

        }
        else{
            ivImage.setImageBitmap(decodificar(image));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent=new Intent(MenuUtilitis.this, MySettings.class);
            startActivity(intent);

            return true;
        }
        if(id==R.id.geo){
            Intent intentGeo=new Intent(MenuUtilitis.this,Maps_activiti.class);
            MenuUtilitis.this.startActivity(intentGeo);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add_User) {
            Intent intentReg=new Intent(MenuUtilitis.this,NewUser.class);


            MenuUtilitis.this.startActivity(intentReg);
            // Handle the camera action
        } else if (id == R.id.nav_lista) {
            Intent intentMenu=new Intent(MenuUtilitis.this,List_users.class);
            MenuUtilitis.this.startActivity(intentMenu);
        } else if (id == R.id.nav_medicion) {
            Intent intentReg=new Intent(MenuUtilitis.this,Medicion.class);
            MenuUtilitis.this.startActivity(intentReg);

        }else if (id == R.id.nav_perfil) {
            Intent intentReg=new Intent(MenuUtilitis.this,Foto_perfil.class);
            MenuUtilitis.this.startActivity(intentReg);
        } else if (id == R.id.nav_logoff) {
            Intent intentReg=new Intent(MenuUtilitis.this,LoginActivity.class);
            MenuUtilitis.this.startActivity(intentReg);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public static Bitmap decodificar(String imput) {
        byte[] decodedByte = Base64.decode(imput, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
