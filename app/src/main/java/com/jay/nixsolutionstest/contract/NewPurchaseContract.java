package com.jay.nixsolutionstest.contract;

import android.content.Context;
import android.graphics.drawable.Drawable;

public interface NewPurchaseContract {

    interface View {

        void showDescriptionEditTextError();

        void showPriceEditTextError();

        void showProgress();

        void hideProgress();
    }


    interface Presenter {

        void onAcceptClickListener(Context context, Drawable drawable, String description, String price);

        void onDestroy();
    }


    interface Model {

        interface InsertIntoDataBaseListener {

            void onInsertCompleted(boolean isCompleted);

            void onInsertFailure(Throwable throwable);
        }

        void insertToDataBase(Context context, InsertIntoDataBaseListener listener, Drawable drawable,
                              String description, String price);

    }
}
