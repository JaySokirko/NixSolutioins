package com.jay.nixsolutionstest.view.mainscreen;


import android.app.Activity;
import android.content.Context;
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
import com.jay.nixsolutionstest.contract.CompletedPurchasesContract;
import com.jay.nixsolutionstest.di.DaggerAppComponent;
import com.jay.nixsolutionstest.di.PresenterModule;
import com.jay.nixsolutionstest.model.adapter.PurchasesAdapter;
import com.jay.nixsolutionstest.presenter.CompletedPurchasesPresenter;

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
public class CompletedPurchasesFragment extends Fragment implements CompletedPurchasesContract.View,
        PurchasesAdapter.OnItemClickListener {

    private Activity activity;

    private Unbinder unbinder;

    //MainActivity's view
    @BindView(R.id.completed_purchases)
    LinearLayout completedPurchasesTab;

    //MainActivity's view
    @BindView(R.id.fab_action)
    FloatingActionButton deleteBtn;

    //MainActivity's view
    @BindView(R.id.select_all_layout)
    LinearLayout selectAllLayout;

    //MainActivity's view
    @BindView(R.id.select_all_check_box)
    CheckBox selectAllCheckBox;

    @BindDrawable(R.drawable.ic_action_delete)
    Drawable iconDelete;

    @BindColor(R.color.colorAccent)
    int colorAccent;

    @BindColor(R.color.colorPrimary)
    int colorPrimary;

    @Inject
    public CompletedPurchasesPresenter presenter;

    private ProgressBar progressBar;

    private PurchasesAdapter purchasesAdapter;

    private List<Drawable> drawableList = new ArrayList<>();
    private List<String> descriptionList = new ArrayList<>();
    private List<String> priceList = new ArrayList<>();

    private boolean isAllItemsSelected;

    private ArrayList<Integer> selectedPositions = new ArrayList<>();


    public CompletedPurchasesFragment() {
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
        View view = inflater.inflate(R.layout.fragment_completed_purchases, container, false);

        DaggerAppComponent.builder().presenterModule(new PresenterModule(this))
                .build().inject(this);

        progressBar = view.findViewById(R.id.load_from_db_progress_bar_completed_purchases);

        RecyclerView purchasesRecyclerView = view.findViewById(R.id.completed_purchases_recycler_view);
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

        completedPurchasesTab.setBackgroundColor(colorAccent);
        deleteBtn.setImageDrawable(iconDelete);

        selectAllCheckBox.setChecked(false);
    }


    @Override
    public void onPause() {
        super.onPause();

        completedPurchasesTab.setBackgroundColor(colorPrimary);
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
    public void onLoadDataCompleted(List<Drawable> drawables, List<String> descriptions,
                                    List<String> prices) {

        drawableList.clear();
        descriptionList.clear();
        priceList.clear();


        drawableList.addAll(drawables);
        descriptionList.addAll(descriptions);
        priceList.addAll(prices);

        purchasesAdapter.notifyDataSetChanged();
    }


    @OnClick(R.id.fab_action)
    void onDeleteClick() {

        if (isAllItemsSelected) {

            presenter.deleteAllData(activity);

            hideSelectAllCheckBox();

        } else {

            //delete only selected purchases
            for (int selectedPosition : selectedPositions) {

                presenter.deleteDataByKey(activity, descriptionList.get(selectedPosition));

                purchasesAdapter.disableItemCheckedAtPosition(selectedPosition);

                hideSelectAllCheckBox();
            }
        }
    }


    @OnClick(R.id.select_all_check_box)
    void onSelectAllClick() {

        purchasesAdapter.setAllChecked();

        isAllItemsSelected = !isAllItemsSelected;
    }


    @Override
    public void onItemClick(ArrayList<Integer> positions, boolean isSelectedEnyOne) {

        this.selectedPositions.clear();
        this.selectedPositions.addAll(positions);

        isAllItemsSelected = false;

        if (isSelectedEnyOne) {

            showSelectAllCheckBox();

        } else {
            hideSelectAllCheckBox();
        }
    }


    private void showSelectAllCheckBox() {

        selectAllLayout.setVisibility(View.VISIBLE);
        selectAllLayout.animate().translationX(0).setDuration(500).start();
    }


    private void hideSelectAllCheckBox() {

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
