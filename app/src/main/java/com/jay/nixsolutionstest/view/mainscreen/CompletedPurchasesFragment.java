package com.jay.nixsolutionstest.view.mainscreen;


import android.app.Activity;
import android.content.Context;
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

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompletedPurchasesFragment extends Fragment {

    private Activity activity;

    private Unbinder unbinder;

    //MainActivity's view
    @BindView(R.id.completed_purchases)
    LinearLayout completedPurchasesTab;

    //MainActivity's view
    @BindView(R.id.main_fab)
    FloatingActionButton deleteBtn;

    @BindDrawable(R.drawable.ic_action_delete)
    Drawable iconDelete;

    @BindColor(R.color.colorAccent)
    int colorAccent;

    @BindColor(R.color.colorPrimary)
    int colorPrimary;

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
        return inflater.inflate(R.layout.fragment_completed_purchases, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        unbinder = ButterKnife.bind(this,activity);

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
}
