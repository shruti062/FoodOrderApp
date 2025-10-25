package com.example.foodorderapp

import android.widget.ImageView
import com.bumptech.glide.Glide

object ImageLoader {
    fun loadImage(imageView: ImageView, url: String) {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
            .into(imageView)
    }
}
