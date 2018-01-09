package abdulrahmanjavanrd.com.mynewsapp.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import abdulrahmanjavanrd.com.mynewsapp.R;

/**
 * @author Abdulrahman.A on 07/01/2018.
 */

public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        (getActivity()).getActionBar();
        // Here Add resource xml File .
        addPreferencesFromResource(R.xml.pref_settings);
        /**  Here create method  {@link #bindSummaryText(Preference)}to appeared the user choice and  call onPreferenceChange */
        Preference pageSize = findPreference(getString(R.string.news_page_size_key));
        bindSummaryText(pageSize);
        // show order-by choice ..
        Preference orderBy = findPreference(getString(R.string.news_order_by_key));
        bindSummaryText(orderBy);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String summaryNewValue = newValue.toString();
        if (preference instanceof ListPreference) {
            // create new ListPreferences .
            ListPreference listPreference = (ListPreference) preference;
            // get index
            int prefIndex = listPreference.findIndexOfValue(summaryNewValue);
            if (prefIndex >= 0) {
                // create Array of  CharSequence .
                CharSequence[] allLabels = listPreference.getEntries();
                preference.setSummary(allLabels[prefIndex]);
            } else {
                preference.setSummary(summaryNewValue);
            }

        }
        preference.setSummary(summaryNewValue);
        return true;
    }

    private void bindSummaryText(Preference preference) {
        preference.setOnPreferenceChangeListener(this);
        SharedPreferences mShared = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
        String pageSize = mShared.getString(preference.getKey(), "");
        onPreferenceChange(preference, pageSize);
    }
}
