package com.guilhermembisotto.core.utils.bindingadapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.guilhermembisotto.core.utils.bindingadapters.helpers.BindableAdapter

@BindingAdapter("bind:data")
fun <T> RecyclerView.setRecyclerViewProperties(data: T?) {
    if (this.adapter is BindableAdapter<*>) {
        (this.adapter as BindableAdapter<T>).setData(data)
    }
}