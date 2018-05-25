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



    class DBTask extends AsyncTask<Void, Void, Void> {

        private List<StockItemDO> items = new ArrayList<>(); // Fails safe if none are found

        @Override
        protected Void doInBackground(Void... voids) {

            StockItemDataBase db = Room.databaseBuilder(
                    getApplicationContext(),
                    StockItemDataBase.class,
                    "pre-alpha"
            ).build();

            items = db.dao().getAllUsers();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // Fill recycler view
            adapter = new ItemAdapter(items);
            recyclerView.setAdapter(adapter);
        }
    }

}
