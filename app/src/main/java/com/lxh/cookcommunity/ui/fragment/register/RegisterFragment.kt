package com.lxh.cookcommunity.ui.fragment.register

import androidx.navigation.fragment.findNavController
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.FragmentRegisterBinding
import com.lxh.cookcommunity.ui.base.BaseFragment

class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterViewModel>(
    RegisterViewModel::class.java, R.layout.fragment_register
) {

    override fun initView() {

        //登录按钮
        binding.btnNext.setOnClickListener {
            viewModel.register()
        }

        //返回登陆按钮
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initDataObServer() {

    }

    override fun initData() {

    }


}