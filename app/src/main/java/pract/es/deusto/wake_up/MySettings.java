package pract.es.deusto.wake_up;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MySettings extends PreferenceActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content,new MyPreferenceFragment()).commit();
    }

    public static class MyPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {

            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            PreferenceManager preferenceManager=getPreferenceManager();
            if(preferenceManager.getSharedPreferences().getBoolean("Bluethood",true)){
                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapter == null) {
                    // Device does not support Bluetooth
                } else {
                    if (!mBluetoothAdapter.isEnabled()) {
                        startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE),1);

                    }
                }
            }else{

            }
            if(preferenceManager.getSharedPreferences().getBoolean("Geolocalizacion",true)){
                Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.putExtra("enabled", true);
                startActivity(intent);

            }else{
                startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
            }
            if(preferenceManager.getSharedPreferences().getBoolean("WI_FI",true)){
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }else{

            }

        }
    }
}
