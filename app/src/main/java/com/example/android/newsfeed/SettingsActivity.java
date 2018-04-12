package com.example.android.newsfeed;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }

    public static class SearchPreferenceFragment extends PreferenceFragment
    {
        /**
         * Creates a fragment for when the settings icon is clicked on
         * @param savedInstanceState
         */
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);

            // Add on the preferences created in the XML
            addPreferencesFromResource(R.xml.settings_main);
        }
    }
}
