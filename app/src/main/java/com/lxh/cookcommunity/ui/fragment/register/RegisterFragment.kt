package com.lxh.cookcommunity.ui.fragment.register

import android.content.Intent
import androidx.navigation.fragment.findNavController
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.FragmentRegisterBinding
import com.lxh.cookcommunity.ui.activity.MainActivity
import com.lxh.cookcommunity.ui.base.BaseFragment

class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterViewModel>(
    RegisterViewModel::class.java, R.layout.fragment_register
) {

    override fun initView() {

        //注册并登录按钮
        binding.btnNext.setOnClickListener {
            viewModel.register()
        }

        //返回登陆按钮
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
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