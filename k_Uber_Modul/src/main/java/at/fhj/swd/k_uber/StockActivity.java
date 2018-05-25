package at.fhj.swd.k_uber;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import at.fhj.swd.k_uber.adapters.ItemAdapter;
import at.fhj.swd.k_uber.models.StockItemDO;

public class StockActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<StockItemDO> items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((KUberApplication) getApplicationContext()).applyTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        ((KUberApplication) getApplicationContext()).applyBackground(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViews();
        setupRV();

    }

    private void setupRV() {
        recyclerView.setLayoutManager(new LinearLayoutManager(StockActivity.this));
        //adapter = new ItemAdapter();
        // Dummy
        items = new ArrayList<StockItemDO>();
        for (int i = 0; i < 12; i++) {
            items.add(new StockItemDO("Dummy", ""+i, "none"));
        }
        adapter = new ItemAdapter(items);
        recyclerView.setAdapter(adapter);
    }

    private void findViews() {
        recyclerView = findViewById(R.id.stock_rv);
    }


    public void addItem(View view) {
        startActivity(new Intent(StockActivity.this, ItemActivity.class));
    }
}
