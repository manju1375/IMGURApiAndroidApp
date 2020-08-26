package com.dms.imagesearch.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dms.imagesearch.R;
import com.dms.imagesearch.core.storage.entity.ImageDbItem;
import com.dms.imagesearch.ui.base.DaggerActivity;
import com.dms.imagesearch.ui.viewmodel.ImageDetailViewModel;

public class ImageDetailsActivity extends DaggerActivity {
    private ImageDetailViewModel mImageDetailViewModel;
    private ImageView mImageView;
    RequestOptions options;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);

        mImageView = findViewById(R.id.ivImg);
        mImageDetailViewModel = new ViewModelProvider(this).get(ImageDetailViewModel.class);
        mImageDetailViewModel.getSelectedImageItem(getSharedPreferences("imagesdb", Context.MODE_PRIVATE).getString("selectedImg",""))
        .observe(this, new Observer<ImageDbItem>() {
            @Override
            public void onChanged(ImageDbItem imageDbItem) {
                Glide.with(ImageDetailsActivity.this).load(imageDbItem.getLink()).apply(options).into(mImageView);
            }
        });


    }
}
