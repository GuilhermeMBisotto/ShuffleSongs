package com.guilhermembisotto.core.utils.bindingadapters.helpers

import androidx.appcompat.widget.AppCompatImageView
import coil.Coil
import coil.api.load
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.guilhermembisotto.core.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun loadImage(view: AppCompatImageView, url: String?) {
    CoroutineScope(Dispatchers.Main).launch {
        view.load(url, Coil.loader()) {
            crossfade(true)
            error(R.mipmap.logo)
            placeholder(R.mipmap.logo)
            scale(Scale.FIT)
            transformations(CircleCropTransformation())
        }
    }
}