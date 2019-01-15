package com.jay.nixsolutionstest.contract;

import android.content.Context;
import android.graphics.drawable.Drawable;

public interface NewPurchaseContract {

    interface View {

        void showDescriptionEditTextError();

        void showPriceEditTextError();

        void showSaveItemError(Throwable throwable);

        void startMainActivity();
    }


    interface Presenter {

        void onAcceptClickListener(Context context, Drawable drawable, String description,
                                   String price, boolean isCompleted);

        void onDestroy();
    }


    interface Model {

        interface DataBaseFeedback {

            void onInsertCompleted();

            void onInsertFailure(Throwable throwable);
        }

        void insertToDataBase(Context context, DataBaseFeedback feedback, Drawable drawable,
                              String description, String price, boolean isCompleted);

    }
}
