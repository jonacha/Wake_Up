package pract.es.deusto.wake_up;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView;


import java.util.ArrayList;

import pract.es.deusto.wake_up.utilidades.ConexionSQLiteHelper;
import pract.es.deusto.wake_up.utilidades.Utility;
import pract.es.deusto.wake_up.utilidades.usuario;

public class List_users extends AppCompatActivity {
    public ConexionSQLiteHelper conn;
    public SQLiteDatabase db;
    private ArrayList<usuario> listaruser;
    ArrayList<String> listanombres;
    ListView listViewUsuarios;
    Button volver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_users);
        conn=new ConexionSQLiteHelper(getApplicationContext(),"Wake_Up",null,1);
        listViewUsuarios=(ListView)findViewById(R.id.lista_pacientes);
        listaruser=new ArrayList<usuario>();
        listanombres=new ArrayList<String>();
        volver=findViewById(R.id.btn_volver_lista);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMenu=new Intent(List_users.this,Menu_Lateral.class);
                intentMenu.putExtra("email",Menu_Lateral.Email_medico);
                intentMenu.putExtra("cod_profesional",Menu_Lateral.profresionalId);
                intentMenu.putExtra("nombre_pro",Menu_Lateral.Nombre_medico);
                List_users.this.startActivity(intentMenu);
            }
        });
        sacarPacientes();
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,listanombres);
        listViewUsuarios.setAdapter(adapter);
        listViewUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("STATE","Usuarios lista"+ i +" Usuario " +listaruser.get(i).getNombre() );
              Intent intentMenu=new Intent(List_users.this,User_data.class);
                intentMenu.putExtra("nombre",listaruser.get(i).getNombre());
                intentMenu.putExtra("tel",listaruser.get(i).getTelefono());
                intentMenu.putExtra("peso",listaruser.get(i).getPeso());
                intentMenu.putExtra("descrip",listaruser.get(i).getDescripcion());
                intentMenu.putExtra("altura",listaruser.get(i).getAltura());
                intentMenu.putExtra("residencia",listaruser.get(i).getResidencia());
                intentMenu.putExtra("image",listaruser.get(i).getImage());

                List_users.this.startActivity(intentMenu);
                boolean result= Utility.checkPermission(List_users.this);
                Log.e("Permisos",""+result);
                //metodos para llamar a la clase ver datos paciente
            }
        });
    }
    public void sacarPacientes(){
        db=conn.getReadableDatabase();
        String Query="select * from usuario where cod_enf="+Menu_Lateral.profresionalId+";";
        Cursor info=db.rawQuery(Query,null);
        usuario u= null;
        String nombre;
         String descripcion;
        int altura;
        int peso;
        String residencia;
        int telefono;
        String image;
        double x;
        double y;

        if(info!=null){
        while (info.moveToNext()){
            Log.d("STATE",info.getString(1));
            nombre=info.getString(1);
            descripcion=info.getString(1);
            altura=Integer.parseInt(info.getString(3));
            peso=Integer.parseInt(info.getString(4));
            residencia =info.getString(6);
            telefono=Integer.parseInt(info.getString(5));
            Log.e("image",info.getString(8));
            Log.e("x",info.getString(9));
            Log.e("y",info.getString(10));
           image=info.getString(8);
            x=Double.parseDouble(info.getString(9));
            y=Double.parseDouble(info.getString(10));
            u=new usuario(nombre,descripcion,altura,peso,residencia,telefono,image,x,y);
            listaruser.add(u);
        }}
        info.close();
        db.close();
        obtenerListaUsuarios();
    }
    private void obtenerListaUsuarios() {
        listanombres=new ArrayList<String>();
        for(int i=0;i<listaruser.size();i++){
            listanombres.add("\t User name:"+listaruser.get(i).getNombre()+" \n  \t TELF: " +listaruser.get(i).getTelefono() );
        }
    }
}
