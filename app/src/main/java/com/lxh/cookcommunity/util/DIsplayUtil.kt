package com.lxh.cookcommunity.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.text.InputFilter
import android.text.Spanned
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.lxh.cookcommunity.MainApplication
import com.lxh.cookcommunity.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * 显示 时：分，如 08：30
 * */
/*@BindingAdapter("timehm")
fun getTimeHourAndMinutes(textView: TextView, timeStamp: Long) {
    if (timeStamp == 0.toLong()) return
    textView.text = dateToTimeShort(Date(timeStamp))
}*/

/**
 * 显示以"-" 连接的 年-月-日
 * */
@BindingAdapter("date")
fun getTimeDate(textView: TextView, birthLong: Long) {
    textView.text = long2DateString(birthLong)
}

/**
 * 显示以中文 连接的 年月日
 * */
/*@BindingAdapter("dateInChinese")
fun getTimeChineseDate(textView: TextView, birthLong: Long) {
    textView.text = long2DateChineseString(birthLong)
}*/

/**
 * 显示周几
 * */
/*
@BindingAdapter("week")
fun getTimeWeek(textView: TextView, birthLong: Long) {
    textView.text = dateToWeek(Date(birthLong))
}
*/

/**
 * 获取最近的时间
 * */
@BindingAdapter("recentTime")
fun getRecentTime(textView: TextView, timeStamp: Long) {
    if (timeStamp == 0.toLong()) return
    //获取年月日
    val format = SimpleDateFormat("MM/dd HH:mm")
    val date = Date(timeStamp)
    textView.text = format.format(date)
}

@BindingAdapter("banSpace")
fun banEditSpace(editText: EditText, banSpace: Boolean) {
    if (banSpace) {
        editText.filters = arrayOf(object : InputFilter {
            override fun filter(
                source: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Spanned?,
                p4: Int,
                p5: Int
            ): CharSequence? {
                if (source!! == " ")
                    return ""
                return null
            }
        })
    }
}

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, url: String?) {
    Glide.with(imageView.context).load(url)
        .placeholder(R.color.colorWhite)
        .skipMemoryCache(false)
        .centerCrop()
        .into(imageView)
}

@BindingAdapter("imageUrlWithAddIcon")
fun loadImageWithAddIcon(imageView: ImageView, url: String?) {
    Glide.with(imageView.context).load(url)
        .placeholder(R.drawable.icon_add_pic)
        .centerCrop()
        .into(imageView)
}


@SuppressLint("NewApi")
@BindingAdapter("gender")
fun getGenderDrawable(imageView: ImageView, gender: String?) {
    when (gender) {
        "F" -> {
            Glide.with(imageView.context).load(R.drawable.icon_gender_female)
                .placeholder(R.mipmap.ic_launcher)
                .dontAnimate()
                .into(imageView)

            imageView.imageTintList =
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        MainApplication.instance!!,
                        R.color.colorFemale
                    )
                )
        }
        else -> {
            Glide.with(imageView.context).load(R.drawable.icon_gender_male)
                .placeholder(R.mipmap.ic_launcher)
                .dontAnimate()
                .into(imageView)

            imageView.imageTintList =
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        MainApplication.instance!!,
                        R.color.colorMale
                    )
                )
        }
    }
}

fun Context.px2dp(pxValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

fun Context.dp2px(dipValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (dipValue * scale + 0.5f).toInt()
}

fun Context.px2sp(pxValue: Float): Int {
    val fontScale = resources.displayMetrics.scaledDensity
    return (pxValue / fontScale + 0.5f).toInt()
}

fun Context.sp2px(spValue: Float): Int {
    val fontScale = resources.displayMetrics.scaledDensity
    return (spValue * fontScale + 0.5f).toInt()
}

fun Activity.hideSoftKeyBoard(view: View? = null) {
    val currentView: View = view ?: (currentFocus ?: View(this))
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager ?: return
    imm.hideSoftInputFromWindow(currentView.windowToken, 0)
}

fun Activity.openSoftKeyBoard() {
    var view = currentFocus
    if (view == null) view = View(this)
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
        ?: return
    imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
}

fun Activity.openSoftKeyBoard(view: View) {
    view.isFocusable = true
    view.isFocusableInTouchMode = true
    view.requestFocus()
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        ?: return
    imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
}



