package at.fhj.swd.k_uber;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

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
    }
}
