package com.jay.nixsolutionstest.view.mainscreen;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.jay.nixsolutionstest.R;
import com.jay.nixsolutionstest.contract.CompletedPurchasesContract;
import com.jay.nixsolutionstest.model.adapter.PurchasesAdapter;
import com.jay.nixsolutionstest.presenter.CompletedPurchasesPresenter;

import java.util.ArrayList;
import java.util.List;

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

    @BindDrawable(R.drawable.ic_action_delete)
    Drawable iconDelete;

    @BindColor(R.color.colorAccent)
    int colorAccent;

    @BindColor(R.color.colorPrimary)
    int colorPrimary;

    private ProgressBar progressBar;

    private RecyclerView purchasesRecyclerView;

    private PurchasesAdapter purchasesAdapter;

    private List<Drawable> drawableList = new ArrayList<>();
    private List<String> descriptionList = new ArrayList<>();
    private List<String> priceList = new ArrayList<>();

    private boolean isEnyItemSelected;

    private ArrayList<Integer> selectedPositions = new ArrayList<>();

    private CompletedPurchasesPresenter presenter;
    private String TAG = "COMPLETED_TAG";

    public CompletedPurchasesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_completed_purchases, container, false);

        progressBar = view.findViewById(R.id.load_from_db_progress_bar_completed_purchases);

        purchasesRecyclerView = view.findViewById(R.id.completed_purchases_recycler_view);
        purchasesRecyclerView.setLayoutManager(new LinearLayoutManager(activity));

        purchasesAdapter = new PurchasesAdapter(activity, drawableList, descriptionList,
                priceList, this);
        purchasesRecyclerView.setAdapter(purchasesAdapter);

        presenter = new CompletedPurchasesPresenter(this);
        presenter.loadData(activity);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        unbinder = ButterKnife.bind(this, activity);

        completedPurchasesTab.setBackgroundColor(colorAccent);
        deleteBtn.setImageDrawable(iconDelete);
    }


    @Override
    public void onPause() {
        super.onPause();

        completedPurchasesTab.setBackgroundColor(colorPrimary);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
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
                                    List<String> prices, List<Boolean> isCompletedList) {

        drawableList.clear();
        descriptionList.clear();
        priceList.clear();

        boolean isPurchaseAlreadyDone;

        for (int i = 0; i < isCompletedList.size(); i++) {

            isPurchaseAlreadyDone = isCompletedList.get(i);

            if (isPurchaseAlreadyDone) {

                drawableList.add(drawables.get(i));
                descriptionList.add(descriptions.get(i));
                priceList.add(prices.get(i));
            }
        }
        purchasesAdapter.notifyDataSetChanged();
    }


    @OnClick(R.id.fab_action)
    void onDeleteClick() {

        for (int selectedPosition : selectedPositions) {

            presenter.deleteDataByKey(activity, descriptionList.get(selectedPosition));
        }
    }


    @Override
    public void onItemClick(ArrayList<Integer> position, boolean isSelectedEnyOne) {

        this.isEnyItemSelected = isSelectedEnyOne;

        this.selectedPositions.clear();
        this.selectedPositions.addAll(position);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        presenter.onDestroy();
    }
}
