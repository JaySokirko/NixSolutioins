package com.jay.nixsolutionstest.model.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.jay.nixsolutionstest.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.constraint.Constraints.TAG;

public class PurchasesAdapter extends RecyclerView.Adapter<PurchasesAdapter.ViewHolder> {

    private List<Drawable> drawablesList;
    private List<String> descriptionsList;
    private List<String> pricesList;

    private Context context;

    private LayoutInflater layoutInflater;

    private OnItemClickListener callback;

    //Use an array to hold the state of the checkbox.
    private SparseBooleanArray itemStateArray = new SparseBooleanArray();

    private boolean isAllItemsSelected;

    public PurchasesAdapter(Context context, List<Drawable> drawablesList, List<String> descriptionsList,
                            List<String> pricesList, OnItemClickListener callback) {

        this.drawablesList = drawablesList;
        this.descriptionsList = descriptionsList;
        this.pricesList = pricesList;

        this.context = context;

        layoutInflater = LayoutInflater.from(context);

        this.callback = callback;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycler_view_purchases, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PurchasesAdapter.ViewHolder holder, int position) {

        holder.purchaseImageView.setImageDrawable(drawablesList.get(position));

        holder.descriptionTextView.setText(descriptionsList.get(position));

        holder.priceTextView.setText(context.getResources().getString(R.string.price)
                .concat(pricesList.get(position)));

        //Use the sparse boolean array to set the checked state when the view is bound
        holder.bindCheckBoxState(position);
    }


    @Override
    public int getItemCount() {

        return descriptionsList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.purchases_image)
        ImageView purchaseImageView;

        @BindView(R.id.purchases_description)
        TextView descriptionTextView;

        @BindView(R.id.purchases_price)
        TextView priceTextView;

        @BindView(R.id.purchases_checkbox)
        CheckBox checkBox;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {

            //In the item click handler onClick() use the state of the items
            //in the itemStateArray to check before toggling
            if (!itemStateArray.get(getAdapterPosition(), false)) {

                checkBox.setChecked(true);
                itemStateArray.put(getAdapterPosition(), true);

            } else {
                checkBox.setChecked(false);
                itemStateArray.put(getAdapterPosition(), false);
            }
            onItemClickCallback();
        }


        void bindCheckBoxState(int position) {

            // use the sparse boolean array to check
            if (!itemStateArray.get(position, false)) {

                checkBox.setChecked(false);
            } else {
                checkBox.setChecked(true);
            }
        }
    }


    private void onItemClickCallback(){

        boolean isEnyItemSelected = false;
        ArrayList<Integer> selectedPositions = new ArrayList<>();

        //Check if at least one item is selected.
        for (int i = 0; i < itemStateArray.size(); i++) {
            if (itemStateArray.valueAt(i)) {

                isEnyItemSelected = true;
                break;
            }
        }

        for (int i = 0; i < getItemCount(); i++){

            if (itemStateArray.valueAt(i)){
                selectedPositions.add(i);
            }
        }

        callback.onItemClick(selectedPositions, isEnyItemSelected);
    }


    public void setAllChecked(){

        for (int i = 0; i < getItemCount(); i++){

            if (isAllItemsSelected) {

                itemStateArray.put(i, false);

            }else {
                itemStateArray.put(i, true);
            }
        }
        notifyDataSetChanged();

        isAllItemsSelected = !isAllItemsSelected;
    }


    public void disableItemCheckedAtPosition(int position){

        itemStateArray.put(position, false);
    }


    public interface OnItemClickListener {
        void onItemClick(ArrayList<Integer> position, boolean isSelectedEnyOne);
    }
}
