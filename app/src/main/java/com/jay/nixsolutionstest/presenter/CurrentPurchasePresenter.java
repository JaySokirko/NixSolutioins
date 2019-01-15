package com.jay.nixsolutionstest.presenter;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.jay.nixsolutionstest.contract.CurrentPurchasesContract;
import com.jay.nixsolutionstest.model.database.DataBaseTransaction;

import java.util.List;

import javax.inject.Inject;

public class CurrentPurchasePresenter implements CurrentPurchasesContract.Presenter,
        CurrentPurchasesContract.Model.GetCompletedFeedback {


    private CurrentPurchasesContract.View view;

    private CurrentPurchasesContract.Model model = new DataBaseTransaction();


    @Inject
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
    public void onGetCompleted(List<Drawable> drawableList, List<String> descriptionList,
                               List<String> priceList, List<Boolean> isCompletedList) {

        if (view != null){


            for (int i = isCompletedList.size() - 1; i >= 0; i--){

                if (isCompletedList.get(i)){

                    drawableList.remove(i);
                    descriptionList.remove(i);
                    priceList.remove(i);
                    isCompletedList.remove(i);
                }
            }

            view.onLoadDataCompleted(drawableList, descriptionList, priceList);
            view.hideProgress();

            drawableList.clear();
            descriptionList.clear();
            priceList.clear();
            isCompletedList.clear();
        }
    }


    @Override
    public void onDestroy() {

        view = null;
        model = null;
    }
}
