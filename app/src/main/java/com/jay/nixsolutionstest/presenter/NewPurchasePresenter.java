package com.jay.nixsolutionstest.presenter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.jay.nixsolutionstest.contract.NewPurchaseContract;

public class NewPurchasePresenter implements NewPurchaseContract.Presenter {

    private NewPurchaseContract.View view;
    private NewPurchaseContract.Model model;


    public NewPurchasePresenter(NewPurchaseContract.View view) {
        this.view = view;
    }


    @Override
    public void onAcceptClickListener(Drawable drawable, String description, String price) {

        if (view != null){

            if (description.isEmpty()){
                view.showDescriptionEditTextError();

            } else if (price.isEmpty()){
                view.showPriceEditTextError();

            } else {
                //todo add to database
                view.showProgress();
            }
        }
    }


    @Override
    public void onDestroy() {

        view = null;
        model = null;
    }
}
