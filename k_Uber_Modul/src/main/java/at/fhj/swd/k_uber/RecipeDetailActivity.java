package at.fhj.swd.k_uber;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import at.fhj.swd.k_uber.helper.ErrorHelper;
import at.fhj.swd.k_uber.helper.RecipeHelper;
import at.fhj.swd.k_uber.models.StockItemDO;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView name;
    private TextView ingredients;
    private TextView text;

    private List<StockItemDO> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((KUberApplication) getApplicationContext()).applyTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ((KUberApplication) getApplicationContext()).applyBackground(this);

        findViews();
        setupViews();
        setupImage();
    }

    private void setupImage() {
        String url = getIntent().getStringExtra(RecipeHelper.URL);
        ImageView imageView = findViewById(R.id.recipe_detail_header_iv);
        if (url != null)
            Glide.with(RecipeDetailActivity.this)
                    .load(url)
                    .apply(
                            new RequestOptions()
                                    .placeholder(new ColorDrawable(0xFF3872a4))
                                    .error(new ColorDrawable(0xFFFF0000))
                    )
                    .into(imageView);
    }

    private void setupViews() {
        items = new ArrayList<>();
        setTitle(getIntent().getStringExtra(RecipeHelper.NAME));
        name.setText(getIntent().getStringExtra(RecipeHelper.NAME));
        text.setText(getIntent().getStringExtra(RecipeHelper.TEXT));
        StringBuilder builder = new StringBuilder();
        try {
            String[] lines = getIntent().getStringExtra(RecipeHelper.INGREDIENTS).split(RecipeHelper.NEWLINE);
            for (String line : lines) {
                String[] words = line.split(RecipeHelper.SEPERATOR);
                builder.append(words[0] + " " + words[1] + "\n");
                String[] unit = words[1].split("\\s+");
                if (unit.length == 1) {
                    items.add(new StockItemDO(words[0], words[1], "", false));
                } else {
                    items.add(new StockItemDO(words[0], unit[0], unit[1], false));
                }
            }
        } catch (IndexOutOfBoundsException iobex) {
            iobex.printStackTrace();
            ErrorHelper.makeError(RecipeDetailActivity.this, "Unit Error", getResources().getString(R.string.error_unit_fault));
        }
        ingredients.setText(builder.toString());
    }

    private void findViews() {
        name = findViewById(R.id.detail_name_tv);
        ingredients = findViewById(R.id.detail_ingredients_tv);
        text = findViewById(R.id.detail_text_tv);
    }
}
