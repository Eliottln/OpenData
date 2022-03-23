package com.example.opendata;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

public class SettingsActivity extends AppCompatActivity {
    private static ListPreference pTheme;
    private static ListPreference pSort;
    private static SwitchPreference pOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            Preference pTheme = findPreference("theme");
            Preference pSort = findPreference("sort");
            Preference pOrder = findPreference("order");
            SettingsActivity.pTheme = (ListPreference) pTheme;
            SettingsActivity.pSort = (ListPreference) pSort;
            SettingsActivity.pOrder = (SwitchPreference) pOrder;

            assert pTheme != null;
            pTheme.setOnPreferenceChangeListener((preference, newValue) -> {
                if ("auto".equals(newValue)) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                } else if ("light".equals(newValue)) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else if ("dark".equals(newValue)) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                return true;
            });
        }
    }

    private void saveTheme(){
        SharedPreferences sharedPreferences = getSharedPreferences("Preferences",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("theme", pTheme.getValue());
        myEdit.apply();
    }

    private void saveFilter(){
        SharedPreferences sharedPreferences = getSharedPreferences("Preferences",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putString("sort", pSort.getValue());
        if (pSort.getValue().equals("measurements_parameter"))
            myEdit.putBoolean("order", !pOrder.isChecked());
        else
            myEdit.putBoolean("order", pOrder.isChecked());

        myEdit.apply();
    }

    @Override
    protected void onDestroy() {
        saveTheme();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        saveFilter();
        super.onBackPressed();
    }
}