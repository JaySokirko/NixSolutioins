package com.jay.nixsolutionstest.contract;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.List;

public interface CurrentPurchasesContract {


    interface View {

        void showProgress();

        void hideProgress();

        void onLoadDataCompleted(List<Drawable> drawable, List<String> description, List<String> price);
    }


    interface Presenter {

        void loadData(Context context);

        void updateData(Context context, String key);

        void onDestroy();
    }


    interface Model {

        interface GetCompletedFeedback {

            void onGetCompleted(List<Drawable> drawable, List<String> description,
                                List<String> price, List<Boolean> isCompleted);
        }


        void getFromDataBase(Context context, GetCompletedFeedback feedback);

        void updateDataInDataBase(Context context, String key);
    }
}
