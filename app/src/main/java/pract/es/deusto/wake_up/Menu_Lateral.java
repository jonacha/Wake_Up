package pract.es.deusto.wake_up;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class Menu_Lateral extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView Nombre;
    TextView Email;
    static int profresionalId;
    static  String Nombre_medico;
    static  String Email_medico;
    public  ConexionSQLiteHelper conn;
    public SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__lateral);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Nombre_medico=getIntent().getExtras().getSerializable("nombre_pro").toString();
        Email_medico=getIntent().getExtras().getSerializable("email").toString();
        profresionalId=Integer.parseInt(getIntent().getExtras().getSerializable("cod_profesional").toString());
        Toast.makeText(this,"Pasos cosas: "+Nombre_medico+" /" +Email_medico+"/"+profresionalId, Toast.LENGTH_SHORT).show();
        Nombre=findViewById(R.id.nombre_Menu);
       // Nombre.getText();
        PreferenceManager.setDefaultValues(this,R.xml.preferences,false);



/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

/*
        Nombre=findViewById(R.id.nombre_Menu);
        Nombre.setText(getIntent().getExtras().getSerializable("nombre_pro").toString());
        Email=findViewById(R.id.email_Menu);
        Email.setText(getIntent().getExtras().getSerializable("nombre").toString());*/
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

            Intent intent=new Intent(this, MySettings.class);
            startActivity(intent);
            return true;
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
            Intent intentMenu=new Intent(Menu_Lateral.this,Menu_Lateral.class);
            intentMenu.putExtra("email",Email_medico);
            intentMenu.putExtra("cod_profesional",profresionalId);
            intentMenu.putExtra("nombre_pro",Nombre_medico);

            Menu_Lateral.this.startActivity(intentMenu);
        } else if (id == R.id.nav_medicion) {
            Intent intentReg=new Intent(Menu_Lateral.this,Medicion.class);
            Menu_Lateral.this.startActivity(intentReg);

        } else if (id == R.id.nav_logoff) {
            Intent intentReg=new Intent(Menu_Lateral.this,LoginActivity.class);
            Menu_Lateral.this.startActivity(intentReg);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void InicializarLista() {
        conn=new ConexionSQLiteHelper(getApplicationContext(),"Wake_Up",null,1);
        db=conn.getReadableDatabase();
        String login= "select *from usuario where cod_enf="+profresionalId+";";

        Cursor info=db.rawQuery(login,null);
        info.moveToFirst();
        try{


        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"El usuario no tiene pacientes ",Toast.LENGTH_LONG).show();

        }

    }
    /*
    public static class SettingScrenn extends PreferenceFragment{
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            Fragment f=new SettingScrenn();
            getFragmentManager().beginTransaction().replace(android.R.id.content,f).commit();
        }
    }*/
}
