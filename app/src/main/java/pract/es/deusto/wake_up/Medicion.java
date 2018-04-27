package pract.es.deusto.wake_up;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

public class Medicion extends AppCompatActivity {
    Button volver;
    LineGraphSeries<DataPoint> mediciones;
    int pulsaciones []=new int [20];
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        int i=80;
        int j=0;
        for (j=0;j<20;j++){
            pulsaciones[j]=i;
            i++;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicion);
        double medicion, tiempo=-5.0;
        volver=findViewById(R.id.btn_vas_a_funcionar);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMenu=new Intent(Medicion.this,Menu_Lateral.class);
                intentMenu.putExtra("email",Menu_Lateral.Email_medico);
                intentMenu.putExtra("cod_profesional",Menu_Lateral.profresionalId);
                intentMenu.putExtra("nombre_pro",Menu_Lateral.Nombre_medico);
                Medicion.this.startActivity(intentMenu);
            }
        });
        GraphView graph =findViewById(R.id.graph);
        mediciones =new LineGraphSeries<DataPoint>();
        Random rand=new Random();
        for(i=0;i<30;i++){
            tiempo=i;
            medicion=pulsaciones[rand.nextInt(20)];
            mediciones.appendData(new DataPoint(tiempo,medicion),true,30);
        }
        graph.addSeries(mediciones);
    }

}
