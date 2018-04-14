package pract.es.deusto.wake_up;

import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MySettings extends  PreferenceFragment {

    @Override
   public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        getFragmentManager().beginTransaction().replace(android.R.id.content,new MySettings()).commit();
    }
}
