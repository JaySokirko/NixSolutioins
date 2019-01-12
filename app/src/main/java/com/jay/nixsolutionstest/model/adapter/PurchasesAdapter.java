package com.jay.nixsolutionstest.model.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.jay.nixsolutionstest.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PurchasesAdapter extends RecyclerView.Adapter<PurchasesAdapter.ViewHolder> {

    private List<Drawable> drawablesList;
    private List<String> descriptionsList;
    private List<String> pricesList;

    private LayoutInflater layoutInflater;

    public PurchasesAdapter(Context context, List<Drawable> drawablesList, List<String> descriptionsList,
                            List<String> pricesList) {

        this.drawablesList = drawablesList;
        this.descriptionsList = descriptionsList;
        this.pricesList = pricesList;

        layoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycler_view_purchases, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PurchasesAdapter.ViewHolder viewHolder, int position) {

        viewHolder.purchaseImageView.setImageDrawable(drawablesList.get(position));

        viewHolder.descriptionTextView.setText(descriptionsList.get(position));

        viewHolder.priceTextView.setText(pricesList.get(position));
    }


    @Override
    public int getItemCount() {
        return descriptionsList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.purchases_image) ImageView purchaseImageView;

        @BindView(R.id.purchases_description) TextView descriptionTextView;

        @BindView(R.id.purchases_price) TextView priceTextView;

        @BindView(R.id.purchases_checkbox) CheckBox selectedPurchaseBox;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
