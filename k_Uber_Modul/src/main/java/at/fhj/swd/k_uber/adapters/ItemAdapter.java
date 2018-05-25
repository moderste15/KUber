package at.fhj.swd.k_uber.adapters;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.List;

import at.fhj.swd.k_uber.R;
import at.fhj.swd.k_uber.database.StockItemDataBase;
import at.fhj.swd.k_uber.helper.ErrorHelper;
import at.fhj.swd.k_uber.helper.RecipeHelper;
import at.fhj.swd.k_uber.models.StockItemDO;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {


    public final List<StockItemDO> items;
    private final boolean isBought;
    private final Activity activity;


    public ItemAdapter(List<StockItemDO> items, boolean isBought, Activity activity) {
        this.items = items;
        this.isBought = isBought;
        this.activity = activity;
    }


    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        holder.itemName.setText(items.get(position).getItemName());
        holder.itemAmount.setText(items.get(position).getItemAmount());
        holder.itemLable.setText(items.get(position).getItemLable());
        if (!isBought)
            holder.itemCheck.setVisibility(View.VISIBLE);
    }

    public void persistIfNeeded(StockItemDO item) {
        new DBInsertOrUpdateIfNeededTask().execute(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CheckBox itemCheck;
        public TextView itemName;
        public TextView itemAmount;
        public TextView itemLable;

        public ViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.row_name_tv);
            itemAmount = itemView.findViewById(R.id.row_amount_tv);
            itemLable = itemView.findViewById(R.id.row_lable_tv);
            itemCheck = itemView.findViewById(R.id.row_check);
            itemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    StockItemDO item = items.get(getAdapterPosition());
                    item.setBought(isChecked);
                    if (isChecked) {
                        persistIfNeeded(item);
                        itemCheck.setOnCheckedChangeListener(null);
                    }
                }
            });
        }
    }

    class DBInsertOrUpdateIfNeededTask extends AsyncTask<StockItemDO, Void, Void> {


        @Override
        protected Void doInBackground(StockItemDO... stockItemDOS) {
            StockItemDataBase db = Room.databaseBuilder(
                    activity.getApplicationContext(),
                    StockItemDataBase.class,
                    "pre-alpha"
            ).build();

            List<StockItemDO> result = db.dao().getAllItems();
            boolean found = false;
            for (StockItemDO item : result) {
                if (item.getItemName().equals(stockItemDOS[0].getItemName()) && !found) {
                    if (item.getItemLable().equals(stockItemDOS[0].getItemLable())) {
                        found = true;
                        double isAmount;
                        try {
                            isAmount = Double.parseDouble(item.getItemAmount());
                        } catch (NumberFormatException nfex) {
                            nfex.printStackTrace();
                            try {
                                int isIntAmount = Integer.parseInt(item.getItemAmount());
                                isAmount = (double) isIntAmount;
                            } catch (NumberFormatException nfex2) {
                                nfex2.printStackTrace();
                                continue; //
                            }

                        }
                        double boughtAmount;
                        try {
                            boughtAmount = Double.parseDouble(stockItemDOS[0].getItemAmount());
                        } catch (NumberFormatException nfex) {
                            nfex.printStackTrace();
                            try {
                                int needIntAmount = Integer.parseInt(stockItemDOS[0].getItemAmount());
                                boughtAmount = (double) needIntAmount;
                            } catch (NumberFormatException nfex2) {
                                ErrorHelper.makeError(activity,
                                        "Corrupted Values",
                                        stockItemDOS[0].getItemAmount() + " != " + stockItemDOS[0].getItemAmount());
                                return null;
                            }
                        }
                        item.setItemAmount(Double.toString(boughtAmount+isAmount));

                    }

                }
            }
            if (!found) // was not in there == safe
                db.dao().insert(stockItemDOS);

            return null;
        }
    }

}
