package com.jay.nixsolutionstest.view.newpurchasescreen;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jay.nixsolutionstest.R;
import com.jay.nixsolutionstest.contract.NewPurchaseContract;
import com.jay.nixsolutionstest.di.DaggerAppComponent;
import com.jay.nixsolutionstest.di.PresenterModule;
import com.jay.nixsolutionstest.presenter.NewPurchasePresenter;
import com.jay.nixsolutionstest.utils.Permissions;
import com.jay.nixsolutionstest.view.mainscreen.MainActivity;

import javax.inject.Inject;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewPurchaseActivity extends AppCompatActivity implements NewPurchaseContract.View {


    @BindView(R.id.parent_layout)
    RelativeLayout parentLayout;

    @BindView(R.id.load_image)
    ImageView imageView;

    @BindView(R.id.description)
    EditText descriptionEditText;

    @BindView(R.id.price)
    EditText priceEditText;

    @BindView(R.id.cancel)
    Button cancelBtn;

    @BindView(R.id.accept)
    Button acceptBtn;

    @BindView(R.id.description_edit_error)
    TextView descriptionErrorTextView;

    @BindView(R.id.price_edit_error)
    TextView priceErrorTexView;

    @BindDrawable(R.drawable.shape_bottom_left_corner_90_error)
    Drawable errorBackground;

    @BindDrawable(R.drawable.shape_bottom_left_corner_0)
    Drawable normalBackground;

    @Inject
    public NewPurchasePresenter presenter;

    @Inject
    public Permissions permissions;

    private AnimationDrawable backgroundAnimation;

    private static final int PERMISSION_REQUEST_CODE = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_purchase);

        ButterKnife.bind(this);

        DaggerAppComponent.builder().presenterModule(new PresenterModule(this))
                .build().inject(this);

        backgroundAnimation = (AnimationDrawable) parentLayout.getBackground();
        backgroundAnimation.setExitFadeDuration(2800);

        presenter = new NewPurchasePresenter(this);

        onDescriptionEditTextTouchListener();
        onPriceEditTextTouchListener();
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (backgroundAnimation != null && !backgroundAnimation.isRunning())
            backgroundAnimation.start();
    }


    @Override
    protected void onPause() {
        super.onPause();

        if (backgroundAnimation != null && backgroundAnimation.isRunning())
            backgroundAnimation.stop();
    }


    @OnClick(R.id.accept)
    public void acceptClick() {
        String description = descriptionEditText.getText().toString();
        String price = priceEditText.getText().toString();

        Drawable drawable = imageView.getDrawable();

        boolean isDrawableCorrect = drawable instanceof BitmapDrawable;

        if (isDrawableCorrect) {

            presenter.onAcceptClickListener(this, drawable, description, price, false);

        } else {
            drawable = getResources().getDrawable(R.drawable.ic_purchase_standart);
            presenter.onAcceptClickListener(this, drawable, description, price, false);
        }
    }


    @OnClick(R.id.cancel)
    public void cancelClick() {

        startActivity(new Intent(this, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }


    @OnClick(R.id.load_image)
    public void addImageClick() {

        if (permissions.hasPermissions(this)) {
            //The application has permissions.
            new SelectImageDialogFragment().show(getSupportFragmentManager(), "dialogFragment");
        } else {
            //The application does not have permission.
            //Request permissions from a user.
           permissions.requestPermissionWithRationale(this, parentLayout);
        }
    }


    @Override
    public void showDescriptionEditTextError() {

        descriptionErrorTextView.setVisibility(View.VISIBLE);
        descriptionEditText.setBackground(errorBackground);
    }


    @Override
    public void showPriceEditTextError() {

        priceErrorTexView.setVisibility(View.VISIBLE);
        priceEditText.setBackground(errorBackground);
    }


    @Override
    public void showSaveItemError(Throwable throwable) {

        Snackbar.make(parentLayout, throwable.getMessage(), Snackbar.LENGTH_LONG).show();
    }


    @Override
    public void startMainActivity() {

        startActivity(new Intent(this, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }


    /**
     * Hide the error when the user starts typing a description.
     */
    @SuppressLint("ClickableViewAccessibility")
    private void onDescriptionEditTextTouchListener() {

        descriptionEditText.setOnTouchListener((view, motionEvent) -> {

            descriptionEditText.setBackground(normalBackground);
            descriptionErrorTextView.setVisibility(View.INVISIBLE);
            return false;
        });
    }


    /**
     * Hide the error when the user starts typing a price.
     */
    @SuppressLint("ClickableViewAccessibility")
    private void onPriceEditTextTouchListener() {

        priceEditText.setOnTouchListener((view, motionEvent) -> {

            priceEditText.setBackground(normalBackground);
            priceErrorTexView.setVisibility(View.INVISIBLE);
            return false;
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[]
            permissionsArray, @NonNull int[] grantResults) {

        boolean allowed = true;

        switch (requestCode){

            case PERMISSION_REQUEST_CODE:

                for (int res : grantResults){
                    //If the user has granted permissions.
                    allowed = allowed && (res == PackageManager.PERMISSION_GRANTED);
                }
                break;
            default:
                //If the user has not granted permissions.
                allowed = false;
                break;
        }
        if (allowed){
            //The user has granted all permissions.
            new SelectImageDialogFragment().show(getSupportFragmentManager(), "dialogFragment");
        }
        else {
            //Will give a warning to the user that he has not granted permission.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    Toast.makeText(this,
                            getResources().getString(R.string.storage_permissions_denied), Toast.LENGTH_SHORT).show();
                } else {
                    permissions.showNoStoragePermissionSnackBar(this, parentLayout);
                }
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.onDestroy();
    }

}
