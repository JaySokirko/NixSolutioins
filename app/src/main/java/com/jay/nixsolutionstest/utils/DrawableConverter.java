package com.jay.nixsolutionstest.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;

public class DrawableConverter {


    public static byte[] convertDrawableToByteArray(Drawable drawable){

        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        return stream.toByteArray();
    }


    public static Drawable converByteArrayToDrawable(byte[] bytes){

        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        return new BitmapDrawable(bitmap);
    }
}
