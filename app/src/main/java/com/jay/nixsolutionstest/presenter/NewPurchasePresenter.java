package com.jay.nixsolutionstest.presenter;

import com.jay.nixsolutionstest.contract.NewPurchaseContract;

public class NewPurchasePresenter implements NewPurchaseContract.Presenter {

    private NewPurchaseContract.View view;
    private NewPurchaseContract.Model model;


    public NewPurchasePresenter(NewPurchaseContract.View view) {
        this.view = view;
    }


    @Override
    public void onAcceptClickListener(String description, String price) {

        if (view != null){

            if (description == null){
                view.showDescriptionEditTextError();

            } else if (price == null){
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
