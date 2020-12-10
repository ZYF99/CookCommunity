package com.lxh.cookcommunity.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LevelListDrawable
import android.text.Html
import android.widget.TextView
import com.lxh.cookcommunity.R
import com.zgxwxy.tuputech.util.switchThread
import io.reactivex.Single
import java.net.URL

private val mDrawable = LevelListDrawable()

class HtmlImageGetter(val context: Context, val textView: TextView) :
    Html.ImageGetter {
    override fun getDrawable(source: String?): Drawable {
        Single.create<Bitmap> { emitter ->
            mDrawable.addLevel(0, 0, context.getDrawable(R.mipmap.ic_launcher))
            mDrawable.setBounds(0, 0, 200, 200)
            val bitmap = BitmapFactory.decodeStream(URL(source).openStream())
            emitter.onSuccess(bitmap)
        }.switchThread()
            .doOnSuccess {
                val drawable = BitmapDrawable(null, it)
                mDrawable.addLevel(1, 1, drawable)
                mDrawable.setBounds(0, 0, it.width, it.height)
                mDrawable.level = 1
                textView.invalidate()
            }.subscribe()
        return mDrawable
    }

}