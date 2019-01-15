package com.jay.nixsolutionstest.presenter;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.jay.nixsolutionstest.contract.CompletedPurchasesContract;
import com.jay.nixsolutionstest.contract.CurrentPurchasesContract;
import com.jay.nixsolutionstest.model.database.DataBaseTransaction;

import java.util.List;

import javax.inject.Inject;

public class CompletedPurchasesPresenter implements CompletedPurchasesContract.Presenter,
        CompletedPurchasesContract.Model.DataBaseFeedback, CurrentPurchasesContract.Model.GetCompletedFeedback {

    private CompletedPurchasesContract.View view;

    private CompletedPurchasesContract.Model completedPurchaseModel = new DataBaseTransaction();

    private CurrentPurchasesContract.Model currentPurchaseModel = new DataBaseTransaction();

    @Inject
    public CompletedPurchasesPresenter(CompletedPurchasesContract.View view) {
        this.view = view;
    }


    @Override
    public void loadData(Context context) {

        if (view != null){
            currentPurchaseModel.getFromDataBase(context, this);
        }
    }


    @Override
    public void deleteDataByKey(Context context,String key) {

        if (view != null){

            completedPurchaseModel.deleteFromDataBaseByKey(context,this, key);
        }
    }


    @Override
    public void deleteAllData(Context context) {

        if (view != null){

            view.showProgress();
            completedPurchaseModel.deleteAllDataFromDataBase(context,this);
        }
    }


    @Override
    public void onDestroy() {

        view = null;
        completedPurchaseModel = null;
    }


    @Override
    public void onDeleteCompleted() {

        if (view != null){

            view.hideProgress();
        }
    }


    @Override
    public void onGetCompleted(List<Drawable> drawable, List<String> description, List<String> price,
                               List<Boolean> isCompleted) {

        if (view != null){

            view.hideProgress();
            view.onLoadDataCompleted(drawable, description, price);

            drawable.clear();
            description.clear();
            price.clear();
            isCompleted.clear();
        }
    }
}
