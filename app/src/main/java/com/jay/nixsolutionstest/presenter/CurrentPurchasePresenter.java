package com.jay.nixsolutionstest.presenter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.jay.nixsolutionstest.contract.CurrentPurchasesContract;
import com.jay.nixsolutionstest.model.database.DataBaseTransaction;

import java.util.List;

public class CurrentPurchasePresenter implements CurrentPurchasesContract.Presenter,
        CurrentPurchasesContract.Model.GetCompletedFeedback {


    private CurrentPurchasesContract.View view;

    private CurrentPurchasesContract.Model model = new DataBaseTransaction();
   


    public CurrentPurchasePresenter(CurrentPurchasesContract.View view) {
        this.view = view;
    }


    @Override
    public void loadData(Context context) {

        if (view != null){

            view.showProgress();
            model.getFromDataBase(context,this);
        }
    }


    @Override
    public void updateData(Context context, String key) {

        if (view != null){

            view.showProgress();
            model.updateDataInDataBase(context, key);
        }
    }


    @Override
    public void onGetCompleted(List<Drawable> drawable, List<String> description, List<String> price,
                               List<Boolean> isCompleted) {

        if (view != null){

            view.onLoadDataCompleted(drawable, description, price, isCompleted);
            view.hideProgress();

            drawable.clear();
            description.clear();
            price.clear();
            isCompleted.clear();
        }
    }


    @Override
    public void onDestroy() {

        view = null;
        model = null;
    }
}
