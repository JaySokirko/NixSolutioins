package com.jay.nixsolutionstest.model.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface PurchasesDAO {


    @Query("SELECT * FROM purchases")
    Flowable<List<Purchases>> getAllPurchases();


    @Insert
    void insertAll(Purchases ... purchases);


    @Insert
    void insert(Purchases purchases);


    @Delete
    void delete(Purchases purchases);


    @Query("DELETE FROM purchases")
    void deleteAll();
}
