package com.jay.nixsolutionstest.model.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import static com.jay.nixsolutionstest.model.database.PurchasesDataBase.DATA_BASE_VERSION;

@Database(entities = Purchases.class, version = DATA_BASE_VERSION)
public abstract class PurchasesDataBase extends RoomDatabase {

    static final int DATA_BASE_VERSION = 6;
    private static final String DATA_BASE_NAME = "purchases_db";

    public abstract PurchasesDAO purchasesDAO();


    private static PurchasesDataBase dataBaseInstance;

    static PurchasesDataBase getInstance(Context context){

        if (dataBaseInstance == null){

            dataBaseInstance = Room.databaseBuilder(context, PurchasesDataBase.class, DATA_BASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return dataBaseInstance;
    }

}
