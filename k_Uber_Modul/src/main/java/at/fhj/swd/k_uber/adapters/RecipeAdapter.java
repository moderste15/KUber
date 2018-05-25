package at.fhj.swd.k_uber.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amazonaws.models.nosql.RecipeDO;

import java.util.List;

import at.fhj.swd.k_uber.R;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {


    private final List<RecipeDO> recipes;

    public RecipeAdapter(List<RecipeDO> recipes) {
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_row, parent, false);
        return new RecipeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(recipes.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener{

        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.rec_name_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            RecipeDO clicked = recipes.get(getAdapterPosition());
            Log.e("NotError", clicked.getName());
        }
    }
}
