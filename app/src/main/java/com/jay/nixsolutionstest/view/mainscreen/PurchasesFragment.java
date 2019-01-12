package com.jay.nixsolutionstest.view.mainscreen;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jay.nixsolutionstest.R;
import com.jay.nixsolutionstest.contract.PurchaseContract;
import com.jay.nixsolutionstest.model.adapter.PurchasesAdapter;
import com.jay.nixsolutionstest.presenter.PurchasePresenter;
import com.jay.nixsolutionstest.view.newpurchasescreen.NewPurchaseActivity;

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
public class PurchasesFragment extends Fragment implements PurchaseContract.View {

    private Activity activity;

    private Unbinder unbinder;

    //MainActivity's view
    @BindView(R.id.purchases)
    LinearLayout purchasesTab;

    //MainActivity's view
    @BindView(R.id.fab_add_new_purchase)
    FloatingActionButton actionBtn;

    @BindColor(R.color.colorAccent)
    int colorAccent;

    @BindColor(R.color.colorPrimary)
    int colorPrimary;

    @BindDrawable(R.drawable.ic_action_add)
    Drawable iconAdd;

    RecyclerView purchasesRecyclerView;

    private PurchasesAdapter purchasesAdapter;

    private List<Drawable> drawableList = new ArrayList<>();
    private List<String> descriptionList = new ArrayList<>();
    private List<String> priceList = new ArrayList<>();

    private PurchasePresenter presenter;

    public PurchasesFragment() {
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

        purchasesRecyclerView = view.findViewById(R.id.purchases_recycler_view);
        purchasesRecyclerView.setLayoutManager(new LinearLayoutManager(activity));

        purchasesAdapter = new PurchasesAdapter(activity, drawableList, descriptionList, priceList);
        purchasesRecyclerView.setAdapter(purchasesAdapter);

        presenter = new PurchasePresenter(this);
        presenter.loadData(activity);

        return view;
    }


    @SuppressLint("CheckResult")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        unbinder = ButterKnife.bind(this, activity);

        purchasesTab.setBackgroundColor(colorAccent);
        actionBtn.setImageDrawable(iconAdd);
    }


    @Override
    public void onPause() {
        super.onPause();

        purchasesTab.setBackgroundColor(colorPrimary);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
        presenter.onDestroy();
    }


    @OnClick(R.id.fab_add_new_purchase)
    public void onAddNewPurchaseClick(){

        startActivity(new Intent(activity, NewPurchaseActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }



    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void addNewPurchaseClick() {

    }

    @Override
    public void deleteSelectedItems() {

    }

    @Override
    public void onLoadDataCompleted(List<Drawable> drawable, List<String> description, List<String> price) {

        drawableList.addAll(drawable);
        descriptionList.addAll(description);
        priceList.addAll(price);

        purchasesAdapter.notifyDataSetChanged();
    }
}
