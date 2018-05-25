package at.fhj.swd.k_uber;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.amazonaws.models.nosql.RecipeDO;

import java.util.ArrayList;
import java.util.List;

import at.fhj.swd.k_uber.database.RecipeProvider;
import at.fhj.swd.k_uber.helper.ErrorHelper;
import at.fhj.swd.k_uber.helper.RecipeHelper;
import at.fhj.swd.k_uber.adapters.RecipeAdapter;

public class RecipeRVActivity extends AppCompatActivity {

    private String filter;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<RecipeDO> recipes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((KUberApplication) getApplicationContext()).applyTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_rv);
        ((KUberApplication) getApplicationContext()).applyBackground(this);

        try {
            filter = getIntent().getStringExtra(RecipeHelper.KEY);
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorHelper.makeError(RecipeRVActivity.this, "No Filter", getResources().getString(R.string.error_no_filter));
        }
        setTitle(filter); // Title equals filter, feedback from hci

        findViews();
        setupRV();
    }

    private void findViews() {
        recyclerView = findViewById(R.id.recipe_rv);
    }

    private void setupRV() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(RecipeRVActivity.this);

        recyclerView.setLayoutManager(layoutManager);


        // Class for DB connection (see end of file)
        new AWSDBTask().execute();

        // Add divider
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);


    }

    private class AWSDBTask extends AsyncTask<Void,Void,Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            List<RecipeDO> result = RecipeProvider.getInstance(RecipeRVActivity.this).getRecipes();
            recipes = new ArrayList<>();
            for (RecipeDO rdo : result) {
                Log.d("Help", rdo.getName());
                if (rdo.getType().contains(filter))
                    recipes.add(rdo);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            findViewById(R.id.recipes_pb).setVisibility(View.GONE);
            if (recipes.isEmpty())
                ErrorHelper.makeError(RecipeRVActivity.this, "No recipes", getResources().getString(R.string.error_no_recipe));
            adapter = new RecipeAdapter(recipes);
            recyclerView.setAdapter(adapter);
        }
    }
}
