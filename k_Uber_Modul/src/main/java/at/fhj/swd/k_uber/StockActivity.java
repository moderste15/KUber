package at.fhj.swd.k_uber;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((KUberApplication) getApplicationContext()).applyTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        ((KUberApplication) getApplicationContext()).applyBackground(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    public void addItem(View view) {
        startActivity(new Intent(StockActivity.this, ItemActivity.class));
    }
}
