package pract.es.deusto.wake_up;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import pract.es.deusto.wake_up.utilidades.ConexionSQLiteHelper;
import pract.es.deusto.wake_up.utilidades.usuario;

public class Maps_activiti extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marcador;
    //private ArrayList<Marker> Pacientes=new ArrayList<>();
    double lat = 0.0;
    double lng = 0.0;
    Button volver;
    public ConexionSQLiteHelper conn;
    public SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        conn=new ConexionSQLiteHelper(getApplicationContext(),"Wake_Up",null,1);
        setContentView(R.layout.activity_maps_activiti);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }
    public void sacarPacientes() {
        db = conn.getReadableDatabase();
        String Query = "select nombre,X,Y from usuario where cod_enf=" + Menu_Lateral.profresionalId + ";";
        Cursor info = db.rawQuery(Query, null);
        String nombre;
        double x;
        double y;
        try {
            info.moveToFirst();
            if (info != null) {
                Log.e("Cordenadas", "Y:" + info.getString(2) + " X:" + info.getString(1) + "nombre" + info.getString(0));
                nombre = info.getString(0);
                x = Double.parseDouble(info.getString(1));
                y = Double.parseDouble(info.getString(2));
                LatLng coordenadas = new LatLng(y, x);
                //Log.e("Cordenadas","Y:"+ y+" X:"+x);
                CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
                mMap.addMarker(new MarkerOptions().position(coordenadas).title(nombre)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.anciano)));
                mMap.animateCamera(miUbicacion);
                while (info.moveToNext()) {

                    Log.e("Cordenadas", "Y:" + info.getString(2) + " X:" + info.getString(1) + "nombre" + info.getString(0));
                    nombre = info.getString(0);
                    x = Double.parseDouble(info.getString(1));
                    y = Double.parseDouble(info.getString(2));
                    coordenadas = new LatLng(y, x);
                    //Log.e("Cordenadas","Y:"+ y+" X:"+x);
                    miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
                    mMap.addMarker(new MarkerOptions().position(coordenadas).title(nombre)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.anciano)));
                    mMap.animateCamera(miUbicacion);

                }
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Añada usuarios a la aplicacion ",Toast.LENGTH_LONG).show();
        }
        info.close();
        db.close();
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

      /*  // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
        miUbicacion();
      sacarPacientes();


    }

    private void agregarMarcador(double lat, double lng) {
         LatLng coordenadas = new LatLng(lat, lng);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
        if (marcador != null) marcador.remove();
            marcador = mMap.addMarker(new MarkerOptions().position(coordenadas).title("Mi posicion actual")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.enfermera_icon)));
                     mMap.animateCamera(miUbicacion);

        volver=findViewById(R.id.btn_maps_volver);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMenu=new Intent(Maps_activiti.this,Menu_Lateral.class);
                intentMenu.putExtra("email",Menu_Lateral.Email_medico);
                intentMenu.putExtra("cod_profesional",Menu_Lateral.profresionalId);
                intentMenu.putExtra("nombre_pro",Menu_Lateral.Nombre_medico);
                Maps_activiti.this.startActivity(intentMenu);
            }
        });
    }

    private void actualzarUbicacion(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            agregarMarcador(lat, lng);
        }

    }

    LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            sacarPacientes();
            actualzarUbicacion(location);

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private void miUbicacion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,15000,0,locListener);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualzarUbicacion(location);
    }
}
