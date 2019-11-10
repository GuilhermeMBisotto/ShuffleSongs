package com.guilhermembisotto.core.base

import android.app.Dialog
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.guilhermembisotto.core.NetworkChangeReceiver
import com.guilhermembisotto.core.utils.extensions.activityBinding
import com.guilhermembisotto.core.utils.extensions.animateTransitionOnRebind

abstract class BaseActivity<T : ViewDataBinding>(
    @LayoutRes val resId: Int
) : AppCompatActivity(), NetworkChangeReceiver.ConnectivityReceiverListener {

    var dialog: Dialog? = null
    private var networkChangeReceiver = NetworkChangeReceiver()

    val binding by activityBinding<T>(resId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        binding.animateTransitionOnRebind<ViewDataBinding>()

        subscribeUi()
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(
            networkChangeReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        NetworkChangeReceiver.connectivityReceiverListener = this
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(networkChangeReceiver)
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        onConnectionChanged(isConnected)
    }

    /**
     * Override this method to observe livedata objects (optional)
     */
    open fun subscribeUi() {}

    /**
     * Override this method to check connectivity (optional)
     */
    open fun onConnectionChanged(isConnected: Boolean) {}
}