package com.lxh.cookcommunity.ui.fragment.releasemoment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.entity.LocalMedia
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.FragmentReleaseMomentBinding
import com.lxh.cookcommunity.ui.activity.ContentActivity
import com.lxh.cookcommunity.ui.adapter.ITEM_SWIPE_FREE
import com.lxh.cookcommunity.ui.adapter.attachItemSwipe
import com.lxh.cookcommunity.ui.base.BaseFragment
import com.lxh.cookcommunity.util.showAlbum
import com.lxh.cookcommunity.util.showGallery

var hasReleaseMoment = false

class ReleaseMomentFragment : BaseFragment<FragmentReleaseMomentBinding, ReleaseMomentViewModel>(
    ReleaseMomentViewModel::class.java, layoutRes = R.layout.fragment_release_moment
) {

    //添加图片按钮点击事件
    private val onAddPicClick: () -> Unit = {

        // 打开选图界面
        showAlbum(9, viewModel.selectedList.value!!)

    }

    //单项图片点击
    private val onGridItemClick: (Int, View) -> Unit = { position, _ ->
        //单项点击
        showGallery(
            context = requireContext(),
            imgList = viewModel.selectedList.value!!.map { it.path },
            currentPosition = position
        )
    }

    //单项图片删除
    private val onGridItemDelClick: (Int) -> Unit = { position ->
        viewModel.selectedList.value?.removeAt(position)
    }

    override fun initView() {
        //已选图片列表
        binding.rvPhoto.run {
            layoutManager = GridLayoutManager(context, 3)
            attachItemSwipe(ITEM_SWIPE_FREE, {}, {})
            adapter = ReleaseDynamicGridImageAdapter(
                context,
                viewModel.selectedList.value ?: mutableListOf(),
                onAddPicClick,
                onGridItemClick,
                onGridItemDelClick
            )
        }

        //发布按钮
        binding.btnRelease.setOnClickListener {
            //真实发布
            viewModel.release()
        }

        //监听选中列表的变化
        viewModel.selectedList.observeNonNull {
            (binding.rvPhoto.adapter as ReleaseDynamicGridImageAdapter).replaceData(it)
        }

        //发布成功事件
        viewModel.releaseSuccessEvent.observeNonNull {
            it.handleIfNot {con->
                if(con) {
                    hasReleaseMoment = true
                    activity?.finish()
                }
            }
        }

    }

    override fun initData() {

    }


    //选图后的回调
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val images: List<LocalMedia>
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {// 图片选择结果回调
                // 例如 LocalMedia 里面返回三种path
                // 1.media.getPath(); 为原图path
                // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                images = PictureSelector.obtainMultipleResult(data)
                viewModel.selectedList.postValue(images)
            }
        }
    }

}

fun Context.jumpToReleaseMoment() {
    val intent = ContentActivity.createIntent(
        context = this,
        des = ContentActivity.Destination.ReleaseMoment
    )
    this.startActivity(intent)
}