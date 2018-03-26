import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

import com.example.android.sunshine.R;

import java.util.List;

/**
 * Created by dell on 3/26/2018.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String key) {
    addPreferencesFromResource(R.xml.pref_general);
        SharedPreferences sharedPreferences=getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen =getPreferenceScreen();
        int count =preferenceScreen.getPreferenceCount();
        for(int i = 0; i < count;i++){
            Preference p = preferenceScreen.getPreference(i);
            if(!(p instanceof CheckBoxPreference)){
                String value= sharedPreferences.getString(p.getKey(),"");
                setPreferenceSummary(p,value);
            }
        }

    }

    private void setPreferenceSummary(Preference preference,String value){
        if(preference instanceof ListPreference){
            ListPreference listPreference=(ListPreference)preference;
            int prefIndex=listPreference.findIndexOfValue(value);
            if(prefIndex>=0){
                listPreference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        }else if(preference instanceof EditTextPreference) {
            preference.setSummary(value);
        }
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    Preference p =findPreference(key);
    if(null != p){
        if(!(p instanceof CheckBoxPreference)){
            String value =sharedPreferences.getString(p.getKey(),"");
            setPreferenceSummary(p,value);
        }
    }
    }

    @Override
    public void onStart() {
        super.onStart();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
