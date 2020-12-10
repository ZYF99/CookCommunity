package com.lxh.cookcommunity.util

import android.content.Context
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.PopGalleryBinding
import com.lxh.cookcommunity.model.api.moments.ImagePagerAdapter
import com.lxh.cookcommunity.ui.widget.FullScreenDialog
import kotlinx.android.synthetic.main.pop_gallery.*


fun showGallery(context: Context, imgList: List<String>?, currentPosition: Int) {
    val a = DataBindingUtil.inflate<PopGalleryBinding>(
        LayoutInflater.from(context),
        R.layout.pop_gallery,
        null,
        false
    )
    val gallery = FullScreenDialog(context, a)
    gallery.show()
    gallery.viewpager.adapter = ImagePagerAdapter(context, imgList?: emptyList()) { gallery.dismiss() }
    gallery.viewpager.currentItem = currentPosition
}