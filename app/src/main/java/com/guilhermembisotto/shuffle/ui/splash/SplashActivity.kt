package com.guilhermembisotto.shuffle.ui.splash

import android.os.Bundle
import com.guilhermembisotto.core.base.BaseActivity
import com.guilhermembisotto.core.utils.extensions.launchActivity
import com.guilhermembisotto.shuffle.R
import com.guilhermembisotto.shuffle.databinding.ActivitySplashBinding
import com.guilhermembisotto.shuffle.ui.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        launch {
            delay(1_000)

            launchActivity<MainActivity> { }
        }
    }
}