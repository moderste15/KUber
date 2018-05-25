package at.fhj.swd.k_uber.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import at.fhj.swd.k_uber.R;
import at.fhj.swd.k_uber.models.StockItemDO;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {


    public final List<StockItemDO> items;
    private final boolean isBought;


    public ItemAdapter(List<StockItemDO> items, boolean isBought) {
        this.items = items;
        this.isBought = isBought;
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

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        public CheckBox itemCheck;
        public TextView itemName;
        public TextView itemAmount;
        public TextView itemLable;

        public ViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.row_name_tv);
            itemAmount = itemView.findViewById(R.id.row_amount_tv);
            itemLable = itemView.findViewById(R.id.row_lable_tv);
            itemCheck= itemView.findViewById(R.id.row_check);
            itemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    items.get(getAdapterPosition()).setBought(isChecked);
                }
            });
        }
    }

}
