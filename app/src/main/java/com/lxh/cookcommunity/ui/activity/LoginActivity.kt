package com.lxh.cookcommunity.ui.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.ActivityLoginBinding
import com.lxh.cookcommunity.ui.base.BaseActivity

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var hideClickCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.flFinish.setOnClickListener {
            hideClickCount++
            if (hideClickCount == 5) {
                finish()
                hideClickCount = 0
            }
        }

    }

}
