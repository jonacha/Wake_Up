package pract.es.deusto.wake_up;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import pract.es.deusto.wake_up.utilidades.ConexionSQLiteHelper;

public class Menu_Lateral extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView Nombre;
    TextView Email;
    static int profresionalId;
    static  String Nombre_medico;
    static  String Email_medico;
    static String image;
    public ConexionSQLiteHelper conn;
    public SQLiteDatabase db;
    Button ListaPacientes;
    Button AddUser;
    Button Mediciones;
    Button Widget;
    Button FotoPerfil;
    Button Ble;
    private ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__lateral);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Nombre_medico=getIntent().getExtras().getSerializable("nombre_pro").toString();
        Email_medico=getIntent().getExtras().getSerializable("email").toString();
        profresionalId=Integer.parseInt(getIntent().getExtras().getSerializable("cod_profesional").toString());
        Nombre=findViewById(R.id.nombre_Menu);
        PreferenceManager.setDefaultValues(this,R.xml.preferences,false);
        ListaPacientes=findViewById(R.id.btn_listaPacientes);
        ListaPacientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentReg=new Intent(Menu_Lateral.this,List_users.class);
                Menu_Lateral.this.startActivity(intentReg);
            }
        });

        AddUser=findViewById(R.id.btn_add_user);
        AddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentReg=new Intent(Menu_Lateral.this,NewUser.class);
                Menu_Lateral.this.startActivity(intentReg);
            }
        });
        Mediciones=findViewById(R.id.btn_medicion);
        Mediciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentReg=new Intent(Menu_Lateral.this,Medicion.class);
                Menu_Lateral.this.startActivity(intentReg);
            }
        });


        FotoPerfil=findViewById(R.id.btn_Foto_perfil);
        FotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentReg=new Intent(Menu_Lateral.this,Foto_perfil.class);
                Menu_Lateral.this.startActivity(intentReg);
            }
        });
        Ble=findViewById(R.id.btn_LLamada);
        Ble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentReg=new Intent(Menu_Lateral.this,Ble.class);
                Menu_Lateral.this.startActivity(intentReg);
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

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

        image=SP.getString("image","NA");
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

            Intent intent=new Intent(Menu_Lateral.this, MySettings.class);
            startActivity(intent);

            return true;
        }
        if(id==R.id.geo){
            Intent intentGeo=new Intent(Menu_Lateral.this,Maps_activiti.class);
            Menu_Lateral.this.startActivity(intentGeo);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add_User) {
            Intent intentReg=new Intent(Menu_Lateral.this,NewUser.class);


            Menu_Lateral.this.startActivity(intentReg);
            // Handle the camera action
        } else if (id == R.id.nav_lista) {
            Intent intentMenu=new Intent(Menu_Lateral.this,List_users.class);
            Menu_Lateral.this.startActivity(intentMenu);
        } else if (id == R.id.nav_medicion) {
            Intent intentReg=new Intent(Menu_Lateral.this,Medicion.class);
            Menu_Lateral.this.startActivity(intentReg);

        }else if (id == R.id.nav_perfil) {
            Intent intentReg=new Intent(Menu_Lateral.this,Foto_perfil.class);
            Menu_Lateral.this.startActivity(intentReg);
        }else if (id == R.id.nav_bluethood) {
            Intent intentReg=new Intent(Menu_Lateral.this,Ble.class);
            Menu_Lateral.this.startActivity(intentReg);
        }
        else if (id == R.id.nav_logoff) {
            Intent intentReg=new Intent(Menu_Lateral.this,LoginActivity.class);
            Menu_Lateral.this.startActivity(intentReg);
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
