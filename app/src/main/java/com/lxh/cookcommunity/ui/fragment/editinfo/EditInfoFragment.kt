package com.lxh.cookcommunity.ui.fragment.editinfo

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.entity.LocalMedia
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.FragmentEditInfoBinding
import com.lxh.cookcommunity.manager.sharedpref.SharedPrefModel
import com.lxh.cookcommunity.ui.activity.ContentActivity
import com.lxh.cookcommunity.ui.base.BaseFragment
import com.lxh.cookcommunity.ui.fragment.login.jumpToLogin
import com.lxh.cookcommunity.util.showAvatarAlbum

class EditInfoFragment : BaseFragment<FragmentEditInfoBinding, EditInfoViewModel>(
    EditInfoViewModel::class.java, layoutRes = R.layout.fragment_edit_info
) {


    override fun initView() {

        //修改头像按钮
        binding.btnAvatar.setOnClickListener {
            showAvatarAlbum()
        }

        //修改昵称按钮
        binding.btnName.setOnClickListener {
            showEditDialog(
                "昵称",
                viewModel.name.value!!,
                12
            ) {
                viewModel.name.value = it
                viewModel.editName()
            }
        }

        //修改性别按钮
        binding.btnGender.setOnClickListener {
            EditGenderDialog(context, viewModel.gender.value!!) {
                viewModel.gender.value = it
                viewModel.editGender()
            }.show()
        }

        //修改签名按钮
        binding.btnSignature.setOnClickListener {
            showEditDialog(
                "个人签名",
                viewModel.signature.value!!,
                40
            ) {
                viewModel.signature.value = it
                viewModel.editSignature()
            }
        }

        //注销按钮
        binding.btnLogout.setOnClickListener { logout() }
    }


    override fun initData() {
        viewModel.fetchUserProfile()
    }

    //弹出修改文字按钮
    private fun showEditDialog(
        title: String,
        oldText: String,
        maxLength: Int,
        onFinish: (String) -> Unit
    ) {
        EditTextDialog(
            requireContext(),
            title,
            oldText,
            maxLength
        ) {
            onFinish(it)
        }.show()
    }

    private fun logout() {
        activity?.finish()
        SharedPrefModel.hasLogin = false
        context?.jumpToLogin()
    }

    //选图后的回调
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val images: List<LocalMedia>
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {// 图片选择结果回调
                images = PictureSelector.obtainMultipleResult(data)
                viewModel.avatar.value = images[0].cutPath
                viewModel.editAvatar()
            }
        }
    }
}

fun Context.jumpToEditInfo() {
    val intent = ContentActivity.createIntent(
        context = this,
        des = ContentActivity.Destination.EditInfo
    )
    this.startActivity(intent)
}