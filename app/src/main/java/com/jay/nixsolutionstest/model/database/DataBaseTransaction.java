package com.jay.nixsolutionstest.model.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.jay.nixsolutionstest.contract.CompletedPurchasesContract;
import com.jay.nixsolutionstest.contract.CurrentPurchasesContract;
import com.jay.nixsolutionstest.contract.NewPurchaseContract;
import com.jay.nixsolutionstest.utils.DrawableConverter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DataBaseTransaction implements NewPurchaseContract.Model, CurrentPurchasesContract.Model
        , CompletedPurchasesContract.Model {

    private PurchasesDataBase dataBase;
    private Purchases purchases;

    private List<Drawable> drawableList = new ArrayList<>();
    private List<String> descriptionList = new ArrayList<>();
    private List<String> priceList = new ArrayList<>();
    private List<Boolean> isCompletedList = new ArrayList<>();

    @SuppressLint("CheckResult")
    @Override
    public void insertToDataBase(Context context, NewPurchaseContract.Model.DataBaseFeedback feedback,
                                 Drawable drawable, String description, String price, boolean isCompleted) {

        dataBase = PurchasesDataBase.getInstance(context);

        purchases = new Purchases(DrawableConverter.convertDrawableToByteArray(drawable),
                description, price, isCompleted);

        Completable.fromAction(() ->
                dataBase.purchasesDAO().insert(purchases))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {

                        feedback.onInsertCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {

                        feedback.onInsertFailure(e);
                    }
                });

    }


    @SuppressLint("CheckResult")
    @Override
    public void getFromDataBase(Context context, GetCompletedFeedback feedback) {

        dataBase = PurchasesDataBase.getInstance(context);

        dataBase.purchasesDAO().getAllPurchases()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(purchases -> {

                    for (Purchases p : purchases) {

                        drawableList.add(DrawableConverter.convertByteArrayToDrawable(context,p.getImage()));

                        descriptionList.add(p.getDescription());

                        priceList.add(p.getPrice());

                        isCompletedList.add(p.isCompleted());
                    }

                    feedback.onGetCompleted(drawableList, descriptionList, priceList, isCompletedList);
                });
    }


    @Override
    public void updateDataInDataBase(Context context, String key) {

        dataBase = PurchasesDataBase.getInstance(context);

        Completable.fromAction(() ->
                dataBase.purchasesDAO().update(key))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }


    @Override
    public void deleteFromDataBaseByKey(Context context,
                                        CompletedPurchasesContract.Model.DataBaseFeedback feedback,
                                        String key) {

        dataBase = PurchasesDataBase.getInstance(context);

        Completable.fromAction(() ->
                dataBase.purchasesDAO().deleteByKey(key))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {

                        feedback.onDeleteCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });

    }


    @Override
    public void deleteAllDataFromDataBase(Context context,
                                          CompletedPurchasesContract.Model.DataBaseFeedback feedback) {

        dataBase = PurchasesDataBase.getInstance(context);

        Completable.fromAction(() ->
                dataBase.purchasesDAO().deleteAll())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {

                        feedback.onDeleteCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }
}
