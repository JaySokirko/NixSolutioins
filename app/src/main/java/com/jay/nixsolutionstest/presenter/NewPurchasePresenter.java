package com.jay.nixsolutionstest.presenter;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.jay.nixsolutionstest.contract.NewPurchaseContract;
import com.jay.nixsolutionstest.model.database.DataBaseTransaction;

public class NewPurchasePresenter implements NewPurchaseContract.Presenter,
        NewPurchaseContract.Model.InsertIntoDataBaseListener {

    private NewPurchaseContract.View view;
    private NewPurchaseContract.Model model = new DataBaseTransaction();


    public NewPurchasePresenter(NewPurchaseContract.View view) {
        this.view = view;
    }


    @Override
    public void onAcceptClickListener(Context context, Drawable drawable, String description,
                                      String price) {

        if (view != null){

            if (description.isEmpty()){
                view.showDescriptionEditTextError();

            } else if (price.isEmpty()){
                view.showPriceEditTextError();

            } else {

                view.showProgress();
                model.insertToDataBase(context, this, drawable, description, price);
            }
        }
    }


    @Override
    public void onInsertCompleted(boolean isCompleted) {

        if (view != null){

            view.hideProgress();
        }
    }


    @Override
    public void onInsertFailure(Throwable throwable) {

    }


    @Override
    public void onDestroy() {

        view = null;
        model = null;
    }
}
