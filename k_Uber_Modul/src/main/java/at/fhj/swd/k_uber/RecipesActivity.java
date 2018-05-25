package at.fhj.swd.k_uber;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RecipesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((KUberApplication) getApplicationContext()).applyTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        ((KUberApplication) getApplicationContext()).applyBackground(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
