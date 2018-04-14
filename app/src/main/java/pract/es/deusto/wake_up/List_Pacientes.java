package pract.es.deusto.wake_up;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class List_Pacientes extends AppCompatActivity {
    public  ConexionSQLiteHelper conn;
    public SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__pacientes);

    }

}
