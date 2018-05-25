package at.fhj.swd.k_uber;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import at.fhj.swd.k_uber.database.StockItemDataBase;
import at.fhj.swd.k_uber.helper.ErrorHelper;
import at.fhj.swd.k_uber.models.StockItemDO;

public class ItemActivity extends AppCompatActivity {

    private final String LOGTAG = ItemActivity.class.getSimpleName();

    private Spinner itemSpinner;
    private EditText name;
    private EditText amount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((KUberApplication) getApplicationContext()).applyTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_activity);
        ((KUberApplication) getApplicationContext()).applyBackground(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViews();

        setupSpinner();
    }

    private void findViews() {
        itemSpinner = findViewById(R.id.item_label_sp);
        amount = findViewById(R.id.amount_et);
        name = findViewById(R.id.name_et);
    }

    private void setupSpinner() {
        // spinner == drop down
        // get the item label localized from sting.xml
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                ItemActivity.this,
                R.array.item_label,
                R.layout.spinner_layout
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemSpinner.setAdapter(adapter);
    }

    public void saveItem(View view) {
        Log.d(LOGTAG, "Trying to save item");
        Log.d(LOGTAG, "Name " + name.getText().toString());
        Log.d(LOGTAG, "Amount " + amount.getText().toString());
        Log.d(LOGTAG, "Lable " + itemSpinner.getSelectedItem().toString());

        new DBSaveTask().execute(new StockItemDO(
                name.getText().toString(),
                amount.getText().toString(),
                itemSpinner.getSelectedItem().toString()
        ));


    }

    class DBSaveTask extends AsyncTask<StockItemDO,Void,Void> {

        @Override
        protected Void doInBackground(StockItemDO... stockItemDOS) {
            StockItemDataBase db = Room.databaseBuilder(
                    getApplicationContext(),
                    StockItemDataBase.class,
                    "pre-alpha"
            ).build();
            try {
                db.dao().insert(stockItemDOS);
            } catch (Exception ex) {
                ex.printStackTrace();
                ErrorHelper.makeError(ItemActivity.this, "DatabaseError", getResources().getString(R.string.insert_error));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(ItemActivity.this, "Saved", Toast.LENGTH_SHORT).show();
            ItemActivity.this.finish();
        }
    }
}
