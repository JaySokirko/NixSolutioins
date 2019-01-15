package com.jay.nixsolutionstest.view.mainscreen;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.jay.nixsolutionstest.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private CurrentPurchasesFragment currentPurchasesFragment;
    private CompletedPurchasesFragment completedPurchasesFragment;

    @BindView(R.id.purchases)
    LinearLayout purchases;

    @BindView(R.id.completed_purchases)
    LinearLayout completed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        purchases.setOnClickListener(this);
        completed.setOnClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();

        setupFragments();
    }


    private void setupFragments(){

        fragmentManager = getSupportFragmentManager();
        currentPurchasesFragment = new CurrentPurchasesFragment();
        completedPurchasesFragment = new CompletedPurchasesFragment();

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, currentPurchasesFragment);
        transaction.commit();
    }


    @Override
    public void onClick(View v) {

        transaction = fragmentManager.beginTransaction();

        switch (v.getId()){

            case R.id.purchases:
                transaction.replace(R.id.main_container, currentPurchasesFragment);
                break;

            case R.id.completed_purchases:
                transaction.replace(R.id.main_container, completedPurchasesFragment);
                break;
        }
        transaction.commit();
    }
}
