package com.jay.nixsolutionstest.contract;

public interface NewPurchaseContract {

     interface View{

        void acceptClick();

        void showDescriptionEditTextError();

        void showPriceEditTextError();

        void showProgress();

        void hideProgress();
    }


     interface Presenter{

        void onAcceptClickListener(String description, String price);

        void onDestroy();
    }


     interface Model{

        interface LoadListener{

        }
    }
}
