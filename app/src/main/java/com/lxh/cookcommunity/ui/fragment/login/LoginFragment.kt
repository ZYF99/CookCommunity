package com.lxh.cookcommunity.ui.fragment.login

import android.content.Intent
import androidx.navigation.fragment.findNavController
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.FragmentLoginBinding
import com.lxh.cookcommunity.manager.sharedpref.SharedPrefModel
import com.lxh.cookcommunity.ui.activity.MainActivity
import com.lxh.cookcommunity.util.hideSoftKeyBoard
import com.lxh.cookcommunity.ui.base.BaseFragment
import com.lxh.cookcommunity.ui.base.Event
import com.lxh.cookcommunity.ui.base.isNetworkAvailable

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(
    LoginViewModel::class.java,
    layoutRes = R.layout.fragment_login
) {

    override fun initView() {

        //自动登陆
        if (SharedPrefModel.hasLogin) {
            activity?.hideSoftKeyBoard()
            if (isNetworkAvailable()) {
                viewModel.checkAndLogin()
            }
        }

        //登录按钮
        binding.btnLogin.setOnClickListener {
            viewModel.checkAndLogin()
        }

        //去注册按钮
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.register_fragment)
        }

    }

    override fun initDataObServer() {

        //登陆成功事件
        viewModel.loginEvent.observeNonNull {
            it.handleIfNot { content ->
                if (content) {
                    startActivity(Intent(context, MainActivity::class.java))
                    activity?.finish()
                }
            }
        }
    }

    override fun initData() {

    }


}