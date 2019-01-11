package com.jay.nixsolutionstest.model.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.drawable.Drawable;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "purchases")
public class Purchases {

    @ColumnInfo(name = "image")
    private Drawable image;

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "price")
    private String price;


    public Purchases() {
    }


    @Ignore
    public Purchases(Drawable image, String description, String price) {
        this.image = image;
        this.description = description;
        this.price = price;
    }


    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "Purchases{" +
                "image=" + image +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
