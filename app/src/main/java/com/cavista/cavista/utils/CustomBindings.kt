package com.cavista.cavista.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: Any?) {
    Glide.with(view.context)
        .load(imageUrl)
        .into(view)


}