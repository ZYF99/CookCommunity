package com.lxh.cookcommunity.util

import android.Manifest
import com.lxh.cookcommunity.ui.base.BaseFragment
import com.tbruyelle.rxpermissions2.Permission
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import kotlin.collections.HashMap

val permissionStrMap = HashMap<String, String>().apply {
	this[Manifest.permission.CAMERA] = "相机"
	this[Manifest.permission.READ_EXTERNAL_STORAGE] = "读取内存"
	this[Manifest.permission.WRITE_EXTERNAL_STORAGE] = "写入内存"
	this[Manifest.permission.READ_PHONE_STATE] = "读取手机识别码"
	this[Manifest.permission.ACCESS_COARSE_LOCATION] = "定位"
}

//检查权限,
//有就执行onNext,没有就去获取
//获取失败执行传入的操作
fun BaseFragment<*, *>.rxCheckPermission(
	vararg permissions: String,
	onGetPermissionFailed: () -> Unit
): Observable<Permission> {
	
	return RxPermissions(activity!!).requestEach(*permissions)
		.doOnNext {
			when {
				it.shouldShowRequestPermissionRationale -> {
					/*context!!.showLeadToSettingDialog(
						permissionStrMap[it.name] ?: "～～～"
					)*/
				}
				it.granted -> return@doOnNext
				else -> onGetPermissionFailed()
			}
		}
}

//IMEI权限检查获取
fun BaseFragment<*, *>.checkIMEIPermission(): Observable<Permission> =
	rxCheckPermission(Manifest.permission.READ_PHONE_STATE) {}

fun BaseFragment<*, *>.checkIMEIPermission(onGetPermissionFailed: () -> Unit): Observable<Permission> =
	rxCheckPermission(Manifest.permission.READ_PHONE_STATE) { onGetPermissionFailed() }


//定位权限检查获取
fun BaseFragment<*, *>.checkLocationPermission(): Observable<Permission> =
	rxCheckPermission(
		Manifest.permission.ACCESS_COARSE_LOCATION,
		Manifest.permission.ACCESS_BACKGROUND_LOCATION
	) {}


fun BaseFragment<*, *>.checkLocationPermission(onGetPermissionFailed: () -> Unit): Observable<Permission> =
	rxCheckPermission(
		Manifest.permission.READ_PHONE_STATE,
		Manifest.permission.ACCESS_COARSE_LOCATION
	) { onGetPermissionFailed() }


//相机权限检查获取
fun BaseFragment<*, *>.checkCameraPermission(): Observable<Permission> =
	rxCheckPermission(Manifest.permission.CAMERA) {}

fun BaseFragment<*, *>.checkCameraPermission(onGetPermissionFailed: () -> Unit): Observable<Permission> =
	rxCheckPermission(Manifest.permission.CAMERA) { onGetPermissionFailed() }

//内存权限检查获取
fun BaseFragment<*, *>.checkStoragePermission(): Observable<Permission> =
	rxCheckPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) {}

fun BaseFragment<*, *>.checkStoragePermission(onGetPermissionFailed: () -> Unit): Observable<Permission> =
	rxCheckPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) { onGetPermissionFailed() }




