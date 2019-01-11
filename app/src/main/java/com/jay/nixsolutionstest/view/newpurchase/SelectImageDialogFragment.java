package com.jay.nixsolutionstest.view.newpurchase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.jay.nixsolutionstest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SelectImageDialogFragment extends DialogFragment {


    @BindView(R.id.open_camera)
    ImageView camera;

    @BindView(R.id.open_gallery)
    ImageView gallery;

    @BindView(R.id.dismiss_dialog)
    Button dismissDialogBtn;

    Activity activity;

    private ImageView imageView;

    private final int ACTION_CAMERA = 2;
    private final int ACTION_GALLERY = 3;

    public SelectImageDialogFragment() {
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        activity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dialog_select_image, null);

        ButterKnife.bind(this, view);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        imageView = activity.findViewById(R.id.load_image);
    }


    @OnClick(R.id.dismiss_dialog)
    void onCancelClick() {
        dismiss();
    }


    @OnClick(R.id.open_camera)
    void onCameraClick() {

        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, ACTION_CAMERA);
    }


    @OnClick(R.id.open_gallery)
    void onGalleryClick() {

        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, ACTION_GALLERY);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case ACTION_CAMERA:

                if (data.getExtras() != null) {
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    imageView.setImageBitmap(imageBitmap);
                }
                dismiss();
                break;

            case ACTION_GALLERY:

                if (data != null) {
                    Uri selectedImage = data.getData();
                    imageView.setImageURI(selectedImage);
                }
                dismiss();
                break;
        }
    }
}
