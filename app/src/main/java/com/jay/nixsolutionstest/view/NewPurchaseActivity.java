package com.jay.nixsolutionstest.view;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jay.nixsolutionstest.R;
import com.jay.nixsolutionstest.contract.NewPurchaseContract;
import com.jay.nixsolutionstest.presenter.NewPurchasePresenter;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class NewPurchaseActivity extends AppCompatActivity implements NewPurchaseContract.View {

    private AnimationDrawable backgroundAnimation;

    @BindView(R.id.parent_layout) RelativeLayout parentLayout;

    @BindView(R.id.image) CircleImageView imageView;

    @BindView(R.id.description) EditText descriptionEditText;

    @BindView(R.id.price) EditText priceEditText;

    @BindView(R.id.cancel) Button cancelBtn;

    @BindView(R.id.accept) Button acceptBtn;

    @BindView(R.id.description_edit_error) TextView descriptionErrorTextView;

    @BindView(R.id.price_edit_error) TextView priceErrorTexView;

    @BindDrawable(R.drawable.shape_bottom_left_corner_90_error) Drawable errorBackground;

    @BindDrawable(R.drawable.shape_bottom_left_corner_0) Drawable normalBackground;

    private NewPurchasePresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_purchase);

        ButterKnife.bind(this);

        backgroundAnimation = (AnimationDrawable) parentLayout.getBackground();
        backgroundAnimation.setExitFadeDuration(2800);

        presenter = new NewPurchasePresenter(this);

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


    @Override
    public void acceptClick() {

        String description = descriptionEditText.getText().toString();
        String price = priceEditText.getText().toString();

        presenter.onAcceptClickListener(description, price);
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
    public void showProgress() {

    }


    @Override
    public void hideProgress() {

    }
}
