package at.fhj.swd.k_uber;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toolbar;

import at.fhj.swd.k_uber.helper.PrefUtility;

public class SettingsActivity extends AppCompatActivity {

    private static final String LOGTAG = SettingsActivity.class.getSimpleName();

    // Members
    private Switch darkSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((KUberApplication) getApplicationContext()).applyTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ((KUberApplication) getApplicationContext()).applyBackground(this);

        setup();
    }

    /**
     * Save current settings to shared prefences
     * @param view
     */
    public void save(View view) {
        Log.i(LOGTAG, "Saving settings to SharedPreferences");
        SharedPreferences.Editor editor = ((KUberApplication) getApplicationContext()).getSettings().edit();
        boolean hasDarkTheme = ((KUberApplication) getApplicationContext()).isUsingDarkTheme(); // get current theme

        Log.d(LOGTAG, "Is using dark theme? = " + hasDarkTheme);
        editor.putBoolean(PrefUtility.DARKMODE, hasDarkTheme);
        editor.commit();

        // go back to main and remove history stack
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    /*
     * Small setup
     */
    private void setup() {
        darkSwitch = findViewById(R.id.switch1);
        darkSwitch.setChecked(((KUberApplication) getApplicationContext()).isUsingDarkTheme());
        darkSwitch.setOnCheckedChangeListener(new DarkSwitch(this));
    }


    /*
     * Listener for dark mode switch
     */
    class DarkSwitch implements CompoundButton.OnCheckedChangeListener {

        private SettingsActivity settingsActivity;

        public DarkSwitch(SettingsActivity settingsActivity) {
            this.settingsActivity = settingsActivity;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Log.i(LOGTAG, "Using dark mode = " + isChecked);
            ((KUberApplication) getApplicationContext()).setUsingDarkTheme(isChecked);
            settingsActivity.recreate();
        }
    }
}
