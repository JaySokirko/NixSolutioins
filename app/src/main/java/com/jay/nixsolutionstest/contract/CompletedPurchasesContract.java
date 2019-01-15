package com.jay.nixsolutionstest.contract;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.List;

public interface CompletedPurchasesContract {

    interface View{

        void showProgress();

        void hideProgress();

        void onLoadDataCompleted(List<Drawable> drawable, List<String> description,
                                 List<String> price, List<Boolean> isCompleted);
    }


    interface Presenter{

        void loadData(Context context);

        void deleteDataByKey(Context context, String key);

        void deleteAllData(Context context);

        void onDestroy();
    }


    interface Model{

        interface DataBaseFeedback {

            void onDeleteCompleted();
        }

        void deleteFromDataBaseByKey(Context context, DataBaseFeedback feedback, String key);

        void deleteAllDataFromDataBase(Context context, DataBaseFeedback feedback);
    }
}
