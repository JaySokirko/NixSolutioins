package com.jay.nixsolutionstest.view.mainscreen;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jay.nixsolutionstest.R;
import com.jay.nixsolutionstest.view.newpurchase.NewPurchaseActivity;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PurchasesFragment extends Fragment {

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

    public PurchasesFragment() {
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
        return inflater.inflate(R.layout.fragment_purshases, container, false);
    }


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
    }


    @OnClick(R.id.fab_add_new_purchase)
    public void onAddNewPurchaseClick(){

        startActivity(new Intent(activity, NewPurchaseActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
}
