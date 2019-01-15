package com.jay.nixsolutionstest.presenter;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.jay.nixsolutionstest.contract.NewPurchaseContract;
import com.jay.nixsolutionstest.model.database.DataBaseTransaction;

import javax.inject.Inject;

public class NewPurchasePresenter implements NewPurchaseContract.Presenter,
        NewPurchaseContract.Model.DataBaseFeedback {

    private NewPurchaseContract.View view;

    private NewPurchaseContract.Model model = new DataBaseTransaction();


    @Inject
    public NewPurchasePresenter(NewPurchaseContract.View view) {
        this.view = view;
    }


    @Override
    public void onAcceptClickListener(Context context, Drawable drawable, String description,
                                      String price, boolean isCompleted) {

        if (view != null){

            if (description.isEmpty()){
                view.showDescriptionEditTextError();

            } else if (price.isEmpty()){
                view.showPriceEditTextError();

            } else {
                model.insertToDataBase(context, this, drawable, description, price, isCompleted);
            }
        }
    }


    @Override
    public void onInsertCompleted() {

        if (view != null){
            view.startMainActivity();
        }
    }


    @Override
    public void onInsertFailure(Throwable throwable) {

        if (view != null){
            view.showSaveItemError(throwable);
        }
    }


    @Override
    public void onDestroy() {

        view = null;
        model = null;
    }
}
