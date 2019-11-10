package com.guilhermembisotto.core.utils.bindingadapters

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.guilhermembisotto.core.utils.bindingadapters.helpers.loadImage

@BindingAdapter(
    "bind:imageSet"
)
fun AppCompatImageView.imageFromUrl(
    imageUrl: String?
) {
    loadImage(this, imageUrl)
}