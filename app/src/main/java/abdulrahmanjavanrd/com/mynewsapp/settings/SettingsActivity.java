package abdulrahmanjavanrd.com.mynewsapp.settings;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import abdulrahmanjavanrd.com.mynewsapp.R;
import abdulrahmanjavanrd.com.mynewsapp.fragment.DateFragment;

/**
 * Created by nfs05 on 07/01/2018.
 */

public class SettingsActivity extends AppCompatActivity {

    Toolbar toolbar ;
    FragmentManager manager ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        toolbar = findViewById(R.id.toolBar_settings);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle(getResources().getString(R.string.user_preferences));
        ActionBar ac = getSupportActionBar();
        if (ac != null){
            ac.setDisplayHomeAsUpEnabled(true);
        }
        manager = getFragmentManager() ;
        manager.beginTransaction().add(R.id.pref_fragment,new SettingsFragment(),getResources().getString(R.string.setting_fragment_tag)).commit();
//        manager = getFragmentManager() ;
        manager.beginTransaction().add(R.id.date_fragment,new DateFragment(),"DateFragment").commit();
    }
}
