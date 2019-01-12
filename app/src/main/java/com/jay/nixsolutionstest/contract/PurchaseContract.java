package com.jay.nixsolutionstest.contract;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.List;

public interface PurchaseContract {


    interface View {

        void showProgress();

        void hideProgress();

        void addNewPurchaseClick();

        void deleteSelectedItems();

        void onLoadDataCompleted(List<Drawable> drawable, List<String> description,
                                 List<String> price);
    }


    interface Presenter {

        void loadData(Context context);

        void onDestroy();
    }


    interface Model {

        interface GetFromDataBaseListener {

            void onGetCompleted(List<Drawable> drawable, List<String> description,
                                List<String> price);

            void onGetFailure(Throwable throwable);
        }

        void getFromDataBase(Context context, GetFromDataBaseListener listener);
    }
}
