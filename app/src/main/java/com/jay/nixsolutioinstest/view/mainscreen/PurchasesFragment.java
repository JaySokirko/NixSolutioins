package com.jay.nixsolutioinstest.view.mainscreen;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jay.nixsolutioinstest.R;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PurchasesFragment extends Fragment {

    private Activity activity;

    private Unbinder unbinder;

    @BindView(R.id.purchases)
    LinearLayout purchasesTab;

    @BindColor(R.color.colorAccent)
    int colorAccent;

    @BindColor(R.color.colorPrimary)
    int colorPrimary;

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
    }


    @Override
    public void onPause() {
        super.onPause();

        purchasesTab.setBackgroundColor(colorPrimary);
    }
}
