package com.jay.nixsolutionstest.contract;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public interface NewPurchaseContract {

    interface View {

        void showDescriptionEditTextError();

        void showPriceEditTextError();

        void showProgress();

        void hideProgress();
    }


    interface Presenter {

        void onAcceptClickListener(Drawable drawable, String description, String price);

        void onDestroy();
    }


    interface Model {

        interface LoadListener {

            void onOperationcomplete();

            void onOperationFailure();
        }

        void saveToDataBase();

        void loadFromDataBase();
    }
}
