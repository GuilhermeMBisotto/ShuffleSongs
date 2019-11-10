package com.guilhermembisotto.shuffle.ui.main

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.guilhermembisotto.core.base.BaseActivity
import com.guilhermembisotto.core.hasInternet
import com.guilhermembisotto.core.utils.extensions.createSimpleDialog
import com.guilhermembisotto.shuffle.R
import com.guilhermembisotto.shuffle.databinding.ActivityMainBinding
import com.guilhermembisotto.shuffle.ui.main.adapter.SongsAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModel()
    private val songsAdapter = SongsAdapter()
    private lateinit var snack: Snackbar

    private companion object {
        private const val VERTICALLY_SCROLL = -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        snack =
            Snackbar.make(
                binding.root,
                getString(R.string.no_internet_connection),
                Snackbar.LENGTH_INDEFINITE
            )

        binding.viewModel = viewModel
        binding.mainRecyclerViewSongs.apply {
            adapter = songsAdapter

            viewTreeObserver.addOnScrollChangedListener {
                binding.frameMainAppbar.isSelected = this.canScrollVertically(VERTICALLY_SCROLL)
            }
        }

        if (hasInternet(this)) {
            lifecycleScope.launchWhenResumed {
                getSongs()
            }
        }

        binding.btnMainShuffle.setOnClickListener {
            callShuffle()
        }
    }

    private suspend fun getSongs() {
        if (viewModel.songs.value.isNullOrEmpty()) {
            binding.mainProgressContentLoader.show()
            viewModel.getSongsFromApi()
        }
    }

    private fun callShuffle() = lifecycleScope.launch {
        binding.mainProgressContentLoader.show()
        viewModel.shuffleSongs()
    }

    override fun subscribeUi() {
        super.subscribeUi()

        viewModel.loading.observe(this, Observer {
            if (it) {
                binding.mainProgressContentLoader.hide()
            }
        })

        viewModel.errorMessage.observe(this, Observer {
            binding.mainProgressContentLoader.hide()
            showErrorDialog(it)
        })

        viewModel.internetHandlerLiveData.observe(this, Observer {
            toggleSnackBarNoInternet(it)
            if (it) tryAgain()
        })
    }

    private fun tryAgain() = lifecycleScope.launch {
        getSongs()
    }

    private fun showErrorDialog(message: String) {
        createSimpleDialog(
            title = getString(R.string.error_dialog_title),
            message = message,
            neutralButtonText = getString(R.string.try_again),
            neutralButtonAction = {
                tryAgain()
            },
            positiveButtonText = getString(R.string.ok),
            positiveButtonAction = {
            }
        ).show()
    }

    private fun toggleSnackBarNoInternet(isConnected: Boolean) = lifecycleScope.launch {
        snack.apply {
            if (isConnected) dismiss() else show()
        }
    }

    override fun onConnectionChanged(isConnected: Boolean) {
        super.onConnectionChanged(isConnected)
        viewModel.setInternetConnectionStatus(isConnected)
    }
}
