package pract.es.deusto.wake_up;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Medicion extends AppCompatActivity {

    private static final String TAG = "UsingThingspeakAPI";
    private static final String THINGSPEAK_CHANNEL_ID = "500106";
    private static final String THINGSPEAK_API_KEY = "T9S7BOWQL8RGMGHG"; //GARBAGE KEY
    private static final String THINGSPEAK_API_KEY_STRING = "api_key";
    /* Be sure to use the correct fields for your own app*/
    private static final String THINGSPEAK_FIELD1 = "field1";
    private static final String THINGSPEAK_FIELD2 = "field2";
    private static final String THINGSPEAK_UPDATE_URL = "https://api.thingspeak.com/update?";
    private static final String THINGSPEAK_CHANNEL_URL = "https://api.thingspeak.com/channels/";
    private static final String THINGSPEAK_FEEDS_LAST = "/feeds/1.json?";
    Button volver;
    TextView t1,t2;
    Button actualizar;
    LineGraphSeries<DataPoint> mediciones;
    int pulsaciones []=new int [10];
    GraphView graph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new FetchThingspeakTask().execute();
        int i=80;
        int j=0;
/*        for (j=0;j<10;j++){
            pulsaciones[j]=i;
            i++;
        }*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicion);
        double medicion, tiempo=-5.0;
        volver=findViewById(R.id.btn_vas_a_funcionar);
        actualizar=findViewById(R.id.btn_recargar);
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
        new FetchThingspeakTask().execute();
        graph =findViewById(R.id.graph);
        mediciones =new LineGraphSeries<DataPoint>();
        Random rand=new Random();

      //  new FetchThingspeakTask().execute();
       // graph.addSeries(mediciones);
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    new FetchThingspeakTask().execute();
                }
                catch(Exception e){
                    Log.e("ERROR", e.getMessage(), e);
                }
            }
        });
/*
        setContentView(R.layout.Medicion);
        t1=(TextView)findViewById(R.id.textView2);
        t2=(TextView)findViewById(R.id.textView3);
        b1=(Button) findViewById(R.id.button);
        t2.setText("");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new ThinkSpeak.FetchThingspeakTask().execute();
                }
                catch(Exception e){
                    Log.e("ERROR", e.getMessage(), e);
                }
            }
        });*/

    }

    class FetchThingspeakTask extends AsyncTask<Void, Void, String> {
        protected void onPreExecute() {
         //   t2.setText("Fetching Data from Server.Please Wait...");
        }
        protected String doInBackground(Void... urls) {
            try {
                URL url = new URL("https://api.thingspeak.com/channels/500106/fields/1.json?api_key=T9S7BOWQL8RGMGHG&results=10");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }
        protected void onPostExecute(String response) {
            if(response == null) {
                Toast.makeText(Medicion.this, "There was an error", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONObject channel = (JSONObject) new JSONTokener(response).nextValue();
                Log.e("pelos", channel.toString());
                JSONArray jArray = channel.getJSONArray("feeds");
                for(int i=0;i<jArray.length();i++){
                    pulsaciones[i]=jArray.getJSONObject(i).getInt("field1");
                }
                double medicion, tiempo=-5.0;
                mediciones =new LineGraphSeries<DataPoint>();
                for(int i=0;i<10;i++){
                    tiempo=i;
                    medicion=pulsaciones[i];
                    mediciones.appendData(new DataPoint(tiempo,medicion),true,10);
                }
                graph.addSeries(mediciones);
                Log.e("pelo2", pulsaciones[1]+"");
                /*double v1 = channel.getDouble(THINGSPEAK_FIELD1);
                if(v1>=90)
                    t1.setText("HI ALL  ");
                else
                    t1.setText("NO VALUES");*/
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
