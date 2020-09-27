package com.cavista.cavista;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.cavista.cavista.databinding.ActivityImageDetailBinding;

public class ImageDetailActivity extends AppCompatActivity {

    ImageDetailViewModel viewModel = new ImageDetailViewModel();
    ActivityImageDetailBinding binding;
    ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


         binding = DataBindingUtil.setContentView(this,
                R.layout.activity_image_detail);

        viewModel = new ViewModelProvider(this).get(ImageDetailViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setVariable(BR.viewModel, viewModel);



       ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        getBundleData();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    void getBundleData(){

        Intent intent = getIntent();
        String imageLink = intent.getStringExtra("imageLink");
        String imageTitle = intent.getStringExtra("imageTitle");


        ab.setTitle(imageTitle);

        Glide.with(this)
                .load(imageLink)
                .into( binding.imgDetail);
    }
}