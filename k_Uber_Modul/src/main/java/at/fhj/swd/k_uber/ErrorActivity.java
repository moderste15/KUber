package at.fhj.swd.k_uber;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import at.fhj.swd.k_uber.helper.ErrorHelper;

public class ErrorActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((KUberApplication) getApplicationContext()).applyTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        ((KUberApplication) getApplicationContext()).applyBackground(this);

        // get extras from bundle
        String title = getIntent().getExtras().getString(ErrorHelper.TITLE_TAG, "Missing Error Title");
        String message = getIntent().getExtras().getString(ErrorHelper.MESSAGE_TAG, "Missing Error Text");

        // Set title
        ((TextView) findViewById(R.id.error_header_tv)).setText(title);
        // Set title
        ((TextView) findViewById(R.id.error_info_tv)).setText(message);
    }


    /*
     * Called from xml
     */
    public void navigateToMain(View view) {
        navigateToMain();
    }

    /*
     * Allows to go from the Error back to MainActivity
     */
    public void navigateToMain() {
        startActivity(new Intent(ErrorActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        navigateToMain();
    }
}
