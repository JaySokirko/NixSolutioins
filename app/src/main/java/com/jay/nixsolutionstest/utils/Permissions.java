package com.jay.nixsolutionstest.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;

import com.jay.nixsolutionstest.R;

import javax.inject.Inject;

public class Permissions  {


    private static final int PERMISSION_REQUEST_CODE = 123;


    @Inject
    public Permissions() {
    }

    public boolean hasPermissions(Context context){
        int res;
        //string array of permissions,
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        for (String perms : permissions){
            res = context.checkCallingOrSelfPermission(perms);
            if (!(res == PackageManager.PERMISSION_GRANTED)){
                return false;
            }
        }
        return true;
    }


    public void requestPermissionWithRationale(Activity activity, View layout) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {

            Snackbar.make(layout, activity.getResources().getString(R.string.storage_permission),
                    Snackbar.LENGTH_LONG)
                    .setAction(activity.getResources().getString(R.string.grant),
                            v -> requestPerms(activity))
                    .show();
        } else {
            requestPerms(activity);
        }
    }


    public void showNoStoragePermissionSnackBar(Context context, View parentLayout) {
        Snackbar.make(parentLayout, context.getResources()
                .getString(R.string.storage_permission_isnt_granted) , Snackbar.LENGTH_LONG)
                .setAction(context.getResources().getString(R.string.settings), v -> {
                    openApplicationSettings(context);

                    Toast.makeText(context.getApplicationContext(),
                            context.getResources().getString(R.string.open_permissions),
                            Toast.LENGTH_SHORT)
                            .show();
                })
                .show();
    }


    private void openApplicationSettings(Context context) {
        Intent appSettingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + context.getPackageName()));
        context.startActivity(appSettingsIntent);
    }


    private void requestPerms(Activity activity) {
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.requestPermissions(permissions, PERMISSION_REQUEST_CODE);
        }
    }
}
