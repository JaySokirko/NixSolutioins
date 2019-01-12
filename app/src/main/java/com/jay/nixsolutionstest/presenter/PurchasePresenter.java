package com.jay.nixsolutionstest.presenter;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.jay.nixsolutionstest.contract.PurchaseContract;
import com.jay.nixsolutionstest.model.database.DataBaseTransaction;

import java.util.List;

public class PurchasePresenter implements PurchaseContract.Presenter,
        PurchaseContract.Model.GetFromDataBaseListener {


    private PurchaseContract.View view;

    private PurchaseContract.Model model = new DataBaseTransaction();


    public PurchasePresenter(PurchaseContract.View view) {
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
    public void onGetCompleted(List<Drawable> drawable, List<String> description, List<String> price) {

        if (view != null){

            view.hideProgress();
            view.onLoadDataCompleted(drawable, description, price);
        }
    }


    @Override
    public void onGetFailure(Throwable throwable) {

    }


    @Override
    public void onDestroy() {

        view = null;
        model = null;
    }

}
