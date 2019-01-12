package com.jay.nixsolutionstest.model.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Arrays;

@Entity(tableName = "purchases")
public class Purchases {


    @PrimaryKey(autoGenerate = true)
    private int key;

    @ColumnInfo(name = "image")
    private byte[] image;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "price")
    private String price;


    Purchases(byte[] image, String description, String price) {
        this.image = image;
        this.description = description;
        this.price = price;
    }


    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
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
                "image=" + Arrays.toString(image) +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
