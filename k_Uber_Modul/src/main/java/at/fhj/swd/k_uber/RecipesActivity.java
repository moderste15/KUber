package at.fhj.swd.k_uber;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.amazonaws.models.nosql.RecipeDO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.fhj.swd.k_uber.database.RecipeProvider;
import at.fhj.swd.k_uber.helper.ErrorHelper;
import at.fhj.swd.k_uber.helper.RecipeHelper;

public class RecipesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((KUberApplication) getApplicationContext()).applyTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        ((KUberApplication) getApplicationContext()).applyBackground(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (!isOnline(RecipesActivity.this))
            ErrorHelper.makeError(RecipesActivity.this, "No Connection", getResources().getString(R.string.error_no_internet));


    }



    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo networkinfo = cm.getActiveNetworkInfo();
        if (networkinfo != null && networkinfo.isConnected()) {
            return true;
        }
        return false;
    }

    public void filter(View view) {
        Intent i = new Intent(RecipesActivity.this, RecipeRVActivity.class);
        if (view.getId() == R.id.special_btn) {
            i.putExtra(RecipeHelper.KEY,RecipeHelper.SPECIAL);
            startActivity(i);
        }
        if (view.getId() == R.id.vegi_btn) {
            i.putExtra(RecipeHelper.KEY,RecipeHelper.VEGETARIAN);
            startActivity(i);
        }
        if (view.getId() == R.id.vegan_btn) {
            i.putExtra(RecipeHelper.KEY,RecipeHelper.VEGAN);
            startActivity(i);
        }
    }
}
