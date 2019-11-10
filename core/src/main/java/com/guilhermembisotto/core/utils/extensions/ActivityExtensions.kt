package com.guilhermembisotto.core.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.transition.TransitionManager
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.databinding.OnRebindCallback
import androidx.databinding.ViewDataBinding
import com.guilhermembisotto.core.R
import com.guilhermembisotto.core.utils.bindingproperties.ActivityBindingProperty

fun <T : ViewDataBinding> activityBinding(@LayoutRes resId: Int) =
    ActivityBindingProperty<T>(resId)

inline fun <reified T : ViewDataBinding> T.animateTransitionOnRebind() {
    addOnRebindCallback(object : OnRebindCallback<T>() {
        override fun onPreBind(binding: T?): Boolean {
            TransitionManager.beginDelayedTransition(binding?.root as ViewGroup)
            return super.onPreBind(binding)
        }
    })
}

inline fun <reified T : Any> newIntent(
    context: Context,
    noinline init: Intent.() -> Unit = {}
): Intent {
    val intent = Intent(context, T::class.java)
    intent.init()
    return intent
}

inline fun <reified T : Any> Activity.launchActivity(
    requestCode: Int = -1,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    startActivityForResult(newIntent<T>(this, init), requestCode, options)
}

fun Activity.createSimpleDialog(
    title: String = "",
    message: String = "",
    cancelable: Boolean = true,
    positiveButtonText: String = "",
    positiveButtonAction: (() -> Unit)? = null,
    neutralButtonText: String = "",
    neutralButtonAction: (() -> Unit)? = null,
    negativeButtonText: String = "",
    negativeButtonAction: (() -> Unit)? = null

): AlertDialog {
    val dialog =
        AlertDialog.Builder(this, R.style.AppTheme_Dialog)
            .setTitle(title)
            .setMessage(message)

    positiveButtonAction?.run {
        dialog.setPositiveButton(positiveButtonText) { _, _ ->
            positiveButtonAction()
        }
    }
    neutralButtonAction?.run {
        dialog.setNeutralButton(neutralButtonText) { _, _ ->
            neutralButtonAction()
        }
    }
    negativeButtonAction?.run {
        dialog.setNegativeButton(negativeButtonText) { _, _ ->
            negativeButtonAction()
        }
    }
    dialog.setCancelable(cancelable)
    return dialog.create()
}