package at.fhj.swd.k_uber;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;

import at.fhj.swd.k_uber.helper.ErrorHelper;

public class MainActivity extends AppCompatActivity {

    private final static String LOGTAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((KUberApplication) getApplicationContext()).applyTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((KUberApplication) getApplicationContext()).applyBackground(this);

        connectAWSCognito();
    }

    private void connectAWSCognito() {
        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                Log.i(LOGTAG, "AWSMobileClient is instantiated and you are connected to AWS!");
            }
        }).execute();
    }


    /*
     * Menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // get to settings
        if (item.getItemId() == R.id.menu_settings_item)
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        return super.onOptionsItemSelected(item);
    }

    // Button1
    public void gotoStock(View view) {
        startActivity(new Intent(MainActivity.this, StockActivity.class));
    }

    // Button2
    public void gotoRecipes(View view) {
        startActivity(new Intent(MainActivity.this, RecipesActivity.class));
    }

    public void gotoSettings(View view) {
        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
    }

    public void gotoShoppingList(View view) {
        Intent i = new Intent(MainActivity.this, StockActivity.class);
        i.putExtra(StockActivity.ISBOUGHT, false);
        startActivity(i);
    }
}
