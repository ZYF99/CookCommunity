package com.lxh.cookcommunity.util

import android.widget.Toast
import com.lxh.cookcommunity.MainApplication
import io.reactivex.android.schedulers.AndroidSchedulers

fun showToast(message: String) {
    AndroidSchedulers.mainThread().scheduleDirect {
        Toast.makeText(MainApplication.instance, message, Toast.LENGTH_SHORT).show()
    }

}