package pract.es.deusto.wake_up;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class List_Pacientes extends ListActivity {
    public  ConexionSQLiteHelper conn;
    public SQLiteDatabase db;
    private ArrayList<usuario> listarruser;
    ArrayList<String> listanombres;
    ListView listViewUsuarios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<usuario> arruser=new ArrayList<usuario>();
        setContentView(R.layout.activity_list__pacientes);
        conn=new ConexionSQLiteHelper(getApplicationContext(),"Wake_Up",null,1);
        listViewUsuarios=(ListView)findViewById(R.id.usuarios_lista);
        //db=conn.getReadableDatabase();
        sacarPacientes();
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,listanombres);
        listViewUsuarios.setAdapter(adapter);
        listViewUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //metodos para llamar a la clase ver datos paciente
            }
        });
    }
    public void sacarPacientes(){
        db=conn.getReadableDatabase();
        String Query="select * from usuario where cod_enf="+Menu_Lateral.profresionalId+";";
        Cursor info=db.rawQuery(Query,null);
         /* CREATE TABLE IF NOT EXISTS usuario (cod_usuario INTEGER primary key  AUTOINCREMENT NOT NULL,
             * nombre TEXT not null UNIQUE, descripcion TEXT not null ,altura INTEGER,
             * peso INTEGER,telefono INTEGER , residencia TEXT,  cod_enf INTEGER not null);
             */

        Integer.parseInt(info.getString(1));
        usuario u= null;
        //new usuario(info.getString(1),info.getString(2),Integer.parseInt(info.getString(3)),Integer.parseInt(info.getString(4)),info.getString(6),Integer.parseInt(info.getString(5)));
        //arruser.add(u);
        while (info.moveToNext()){
            Toast.makeText(getApplicationContext(),info.getString(1),Toast.LENGTH_LONG).show();
            u=new usuario(info.getString(1),info.getString(2),Integer.parseInt(info.getString(3)),Integer.parseInt(info.getString(4)),info.getString(6),Integer.parseInt(info.getString(5)));
            listarruser.add(u);
        }

        db.close();
        obtenerListaUsuarios();
    }

    private void obtenerListaUsuarios() {
        listanombres=new ArrayList<String>();
            for(int i=0;i<listarruser.size();i++){
                listanombres.add(listarruser.get(i).getNombre()+" TELF" +listarruser.get(i).getTelefono() );
            }
        Toast.makeText(getApplicationContext(),"llego 1 ",Toast.LENGTH_LONG).show();
    }

}
