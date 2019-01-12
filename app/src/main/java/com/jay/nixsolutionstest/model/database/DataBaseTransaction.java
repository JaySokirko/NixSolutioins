package com.jay.nixsolutionstest.model.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.jay.nixsolutionstest.contract.NewPurchaseContract;
import com.jay.nixsolutionstest.contract.PurchaseContract;
import com.jay.nixsolutionstest.utils.DrawableConverter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DataBaseTransaction implements NewPurchaseContract.Model, PurchaseContract.Model {

    private PurchasesDataBase dataBase;
    private Purchases purchases;

    private List<Drawable> drawableList = new ArrayList<>();
    private List<String> descriptionList = new ArrayList<>();
    private List<String> priceList = new ArrayList<>();

    @Override
    public void insertToDataBase(Context context, InsertIntoDataBaseListener listener,
                                 Drawable drawable, String description, String price) {

        dataBase = PurchasesDataBase.getInstance(context);

        purchases = new Purchases(DrawableConverter.convertDrawableToByteArray(drawable),
                description, price);

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
                        listener.onInsertCompleted(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onInsertFailure(e);
                    }
                });
    }


    @SuppressLint("CheckResult")
    @Override
    public void getFromDataBase(Context context, GetFromDataBaseListener listener) {

        dataBase = PurchasesDataBase.getInstance(context);

        dataBase.purchasesDAO().getAllPurchases()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(purchases -> {


                    for (Purchases p : purchases) {

                        drawableList.add(DrawableConverter.converByteArrayToDrawable(p.getImage()));

                        descriptionList.add(p.getDescription());

                        priceList.add(p.getPrice());
                    }

                    listener.onGetCompleted(drawableList, descriptionList, priceList);
                });
    }
}
