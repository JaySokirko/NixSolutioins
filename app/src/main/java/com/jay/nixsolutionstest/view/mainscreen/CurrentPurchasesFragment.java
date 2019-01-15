package com.jay.nixsolutionstest.view.mainscreen;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.jay.nixsolutionstest.R;
import com.jay.nixsolutionstest.contract.CurrentPurchasesContract;
import com.jay.nixsolutionstest.di.DaggerAppComponent;
import com.jay.nixsolutionstest.di.PresenterModule;
import com.jay.nixsolutionstest.model.adapter.PurchasesAdapter;
import com.jay.nixsolutionstest.presenter.CurrentPurchasePresenter;
import com.jay.nixsolutionstest.view.newpurchasescreen.NewPurchaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentPurchasesFragment extends Fragment implements CurrentPurchasesContract.View,
        PurchasesAdapter.OnItemClickListener {

    private Activity activity;

    private Unbinder unbinder;

    //MainActivity's view
    @BindView(R.id.purchases)
    LinearLayout purchasesTab;

    //MainActivity's view
    @BindView(R.id.fab_action)
    FloatingActionButton actionBtn;

    //MainActivity's view
    @BindView(R.id.select_all_layout)
    LinearLayout selectAllLayout;

    //MainActivity's view
    @BindView(R.id.select_all_check_box)
    CheckBox selectAllCheckBox;

    private ProgressBar progressBar;

    @BindColor(R.color.colorAccent)
    int colorAccent;

    @BindColor(R.color.colorPrimary)
    int colorPrimary;

    @BindDrawable(R.drawable.ic_action_add)
    Drawable iconAdd;

    @BindDrawable(R.drawable.ic_action_purchased)
    Drawable iconPurchased;

    @Inject
    public CurrentPurchasePresenter presenter;

    private PurchasesAdapter purchasesAdapter;

    private List<Drawable> drawableList = new ArrayList<>();
    private List<String> descriptionList = new ArrayList<>();
    private List<String> priceList = new ArrayList<>();

    private boolean isEnyItemSelected;
    private boolean isAllItemsSelected;

    private ArrayList<Integer> selectedPositions = new ArrayList<>();

    public CurrentPurchasesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_purshases, container, false);

        DaggerAppComponent.builder().presenterModule(new PresenterModule(this))
                .build().inject(this);

        progressBar = view.findViewById(R.id.load_from_db_progress_bar);

        RecyclerView purchasesRecyclerView = view.findViewById(R.id.purchases_recycler_view);
        purchasesRecyclerView.setLayoutManager(new LinearLayoutManager(activity));

        purchasesAdapter = new PurchasesAdapter(activity, drawableList, descriptionList,
                priceList, this);
        purchasesRecyclerView.setAdapter(purchasesAdapter);

        presenter.loadData(activity);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        unbinder = ButterKnife.bind(this, activity);

        purchasesTab.setBackgroundColor(colorAccent);
        actionBtn.setImageDrawable(iconAdd);

        hideSelectAllCheckBox();

        selectAllCheckBox.setChecked(false);
    }


    @Override
    public void onPause() {
        super.onPause();

        purchasesTab.setBackgroundColor(colorPrimary);
    }


    @OnClick(R.id.fab_action)
    public void onAddNewPurchaseClick() {

        if (!isEnyItemSelected) {

            startActivity(new Intent(activity, NewPurchaseActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        } else {

            if (isAllItemsSelected){

                //update all purchases
                for (int i = 0; i < descriptionList.size(); i++) {

                    presenter.updateData(activity, descriptionList.get(i));
                    isEnyItemSelected = false;
                    actionBtn.setImageDrawable(iconAdd);
                }
            } else {

                //update only selected purchases
                for (int selectedPosition : selectedPositions) {

                    presenter.updateData(activity, descriptionList.get(selectedPosition));

                    isEnyItemSelected = false;
                    actionBtn.setImageDrawable(iconAdd);
                    purchasesAdapter.disableItemCheckedAtPosition(selectedPosition);
                }
            }
           hideSelectAllCheckBox();
        }
    }


    @OnClick(R.id.select_all_check_box)
    void onSelectAllClick(){

        purchasesAdapter.setAllChecked();

        isAllItemsSelected = !isAllItemsSelected;
    }


    @Override
    public void showProgress() {

        progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void hideProgress() {

        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void onLoadDataCompleted(List<Drawable> drawables, List<String> descriptions, List<String> prices) {

        drawableList.clear();
        descriptionList.clear();
        priceList.clear();

        drawableList.addAll(drawables);
        descriptionList.addAll(descriptions);
        priceList.addAll(prices);

        purchasesAdapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(ArrayList<Integer> selectedPositions, boolean isEnyItemSelected) {

        this.isEnyItemSelected = isEnyItemSelected;

        this.selectedPositions.clear();
        this.selectedPositions.addAll(selectedPositions);

        if (isEnyItemSelected) {

            actionBtn.setImageDrawable(iconPurchased);

            showSelectAllCheckBox();
        } else {

            actionBtn.setImageDrawable(iconAdd);

            hideSelectAllCheckBox();
        }
    }


    private void showSelectAllCheckBox(){

        selectAllLayout.setVisibility(View.VISIBLE);
        selectAllLayout.animate().translationX(0).setDuration(500).start();
    }


    private void hideSelectAllCheckBox(){

        selectAllLayout.animate().translationX(1000).setDuration(500).start();
        new Handler().postDelayed(() -> selectAllLayout.setVisibility(View.INVISIBLE), 500);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
        presenter.onDestroy();
    }
}
