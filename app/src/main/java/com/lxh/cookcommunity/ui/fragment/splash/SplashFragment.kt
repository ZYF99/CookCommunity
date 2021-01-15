package com.lxh.cookcommunity.ui.fragment.splash

import android.content.Intent
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.FragmentSplashBinding
import com.lxh.cookcommunity.ui.activity.LoginActivity
import com.lxh.cookcommunity.ui.base.BaseFragment
import com.lxh.cookcommunity.util.fullScreen
import io.reactivex.Completable
import java.util.concurrent.TimeUnit

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>(
    SplashViewModel::class.java,
    layoutRes = R.layout.fragment_splash
) {

    override fun initDataObServer() {

    }

    override fun initView() {
        Completable.timer(1, TimeUnit.SECONDS)
            .doOnComplete {
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }.bindLife()
    }

    override fun initData() {

    }

    override fun onResume() {
        super.onResume()
        activity?.let { fullScreen(it) }
    }


}