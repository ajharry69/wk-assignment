package com.xently.persona.ui.list

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.xently.persona.R

@BindingAdapter("imageFromUrl")
fun setImageFromUrl(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.ic_avator)
            .into(view)
    }
}