package at.fhj.swd.k_uber;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import at.fhj.swd.k_uber.adapters.ItemAdapter;
import at.fhj.swd.k_uber.database.StockItemDataBase;
import at.fhj.swd.k_uber.helper.RecipeHelper;
import at.fhj.swd.k_uber.models.StockItemDO;

public class StockActivity extends AppCompatActivity {


    public static final String ISBOUGHT = "ISBOUGHT";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<StockItemDO> items;

    private static boolean isBought = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((KUberApplication) getApplicationContext()).applyTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        ((KUberApplication) getApplicationContext()).applyBackground(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        isBought = getIntent().getBooleanExtra(ISBOUGHT, true);
        if (!isBought) {
            setTitle(getResources().getString(R.string.shoppingtitle));
            findViewById(R.id.add_btn).setVisibility(View.GONE);
            findViewById(R.id.bought_btn).setVisibility(View.VISIBLE);
        }

        findViews();
        setupRV();

    }

    @Override
    protected void onResume() {
        super.onResume();
        new DBTask().execute();
    }

    private void setupRV() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(StockActivity.this);

        recyclerView.setLayoutManager(layoutManager);


        // Class for DB connection (see end of file)
        new DBTask().execute();

        // Add divider
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);


    }

    private void findViews() {
        recyclerView = findViewById(R.id.stock_rv);
    }


    public void addItem(View view) {
        startActivity(new Intent(StockActivity.this, ItemActivity.class));
    }

    public void boughtItems(View view) {

    }


    class DBTask extends AsyncTask<Void, Void, Void> {

        private List<StockItemDO> items = new ArrayList<>(); // Fails safe if none are found

        @Override
        protected Void doInBackground(Void... voids) {

            StockItemDataBase db = Room.databaseBuilder(
                    getApplicationContext(),
                    StockItemDataBase.class,
                    "pre-alpha"
            ).build();

            List<StockItemDO> result = db.dao().getAllItems();
            for (StockItemDO item : result)
                if (item.isBought() == StockActivity.isBought) // only add to stock if bought
                    items.add(item);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // Fill recycler view
            adapter = new ItemAdapter(items, isBought);
            recyclerView.setAdapter(adapter);
        }
    }

    class DBUpdateTask extends AsyncTask<StockItemDO, Void, Void> {


        @Override
        protected Void doInBackground(StockItemDO... stockItemDOS) {
            StockItemDataBase db = Room.databaseBuilder(
                    getApplicationContext(),
                    StockItemDataBase.class,
                    "pre-alpha"
            ).build();

            db.dao().update(stockItemDOS);
            return null;
        }
    }

}
