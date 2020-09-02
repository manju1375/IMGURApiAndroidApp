package com.dms.imagesearch.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dms.imagesearch.R;
import com.dms.imagesearch.core.storage.entity.ImageDbItem;
import com.dms.imagesearch.ui.base.DaggerActivity;
import com.dms.imagesearch.ui.viewmodel.ImageDetailViewModel;
import com.google.android.material.snackbar.Snackbar;


public class ImageDetailsActivity extends DaggerActivity {
    private ImageDetailViewModel mImageDetailViewModel;
    private ImageView mImageView;
    private Button mSubmitButton;
    RequestOptions options;
    private EditText mEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);

        mImageView = findViewById(R.id.ivImg);
        mSubmitButton = findViewById(R.id.btnSubmit);
        mEditText = findViewById(R.id.etComments);

        mImageDetailViewModel = new ViewModelProvider(this).get(ImageDetailViewModel.class);
        LiveData<ImageDbItem> imageDbItemLiveData = mImageDetailViewModel.getSelectedImageItem(getSharedPreferences("imagesdb", Context.MODE_PRIVATE).getString("selectedImg", ""));
        imageDbItemLiveData.observe(this, new Observer<ImageDbItem>() {
            @Override
            public void onChanged(ImageDbItem imageDbItem) {
                Glide.with(ImageDetailsActivity.this).load(imageDbItem.getLink()).apply(options).into(mImageView);
                if (imageDbItemLiveData.getValue().getComment() != "") {
                    mEditText.setText(imageDbItemLiveData.getValue().getComment());
                }
            }
        });

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkConnected()) {
                    if (imageDbItemLiveData.getValue() != null && !TextUtils.isEmpty(mEditText.getText().toString())) {
                        imageDbItemLiveData.getValue().setComment(mEditText.getText().toString());


                        try {
                            Toast.makeText(ImageDetailsActivity.this, "Updating comments", Toast.LENGTH_LONG).show();
                            mImageDetailViewModel.updateImageWithComments(imageDbItemLiveData.getValue().getComment(), imageDbItemLiveData.getValue().getId());
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                else
                    Snackbar.make(findViewById(R.id.root_view), "Please check network connection...", 3000).show();
            }

        });
    }

    @Override
    public void showMessage(boolean isConnected) {
        super.showMessage(isConnected);
        if (isNetworkStatustoShow()) {
            if (isConnected) {
                Snackbar.make(findViewById(R.id.root_view), "You are Online...", 3000).show();
                setNetworkStatustoShow(false);
            } else
                Snackbar.make(findViewById(R.id.root_view), "You are Offline...", 3000).show();
        }
    }
}
