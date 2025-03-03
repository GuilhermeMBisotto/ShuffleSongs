package com.guilhermembisotto.shuffle.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.guilhermembisotto.core.utils.bindingadapters.helpers.BindableAdapter
import com.guilhermembisotto.shuffle.BR

abstract class BaseRecyclerViewAdapter<T>(
    private var items: List<T>
) :
    RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseViewHolder<T>>(), BindableAdapter<List<T>> {

    companion object {
        const val BUNDLE_KEY = "items"
    }

    protected abstract fun getObjForPosition(position: Int): Any
    protected abstract fun getLayoutIdForPosition(position: Int): Int
    protected abstract fun getAdapter(position: Int): BaseRecyclerViewAdapter<T>
    protected fun getList() = items

    class BaseViewHolder<T>(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(obj: Any) {
            binding.setVariable(BR.obj, obj)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> =
        BaseViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), viewType, parent, false
            )
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: BaseViewHolder<T>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val args = (payloads[0]) as Bundle
            for (key in args.keySet()) {
                if (key == BUNDLE_KEY) {
                    holder.bind(getObjForPosition(position))
                }
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(getObjForPosition(position))
    }

    override fun getItemViewType(position: Int): Int = getLayoutIdForPosition(position)

    override fun setData(data: List<T>?) {
        this.items = data ?: listOf()
        notifyDataSetChanged()
    }
}