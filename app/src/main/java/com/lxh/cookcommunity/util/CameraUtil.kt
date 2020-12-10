package com.lxh.cookcommunity.util

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.ImageFormat
import android.hardware.Camera
import android.hardware.camera2.CameraManager
import android.view.Surface
import android.view.SurfaceHolder
import kotlin.math.abs

private var resultWidth = 0
private var resultHeight = 0

/**
 * 打开相机
 * */
fun openCamera(
    context: Context,
    cameraId: Int,
    width: Int = 800,
    height: Int = 600,
    doOnPreviewCallback: (ByteArray?, Camera?, Int, Int) -> Unit
): Camera {
    (context.getSystemService(Context.CAMERA_SERVICE) as CameraManager).cameraIdList
    val c = Camera.open(cameraId)
    initParameters(context, c, width, height)
    c.setPreviewCallback { data, camera ->
        doOnPreviewCallback(data, camera, resultWidth, resultHeight)
    }
    return c
}

/**
 * 设置相机参数
 * */
fun initParameters(
    context: Context,
    camera: Camera,
    width: Int,
    height: Int
) {
    //获取Parameters对象
    val parameters = camera.parameters
    val size = getOptimalSize(context, parameters.supportedPreviewSizes, width, height)
    parameters?.setPictureSize(size?.width ?: 0, size?.height ?: 0)
    parameters?.setPreviewSize(size?.width ?: 0, size?.height ?: 0)
    resultWidth = size?.width ?: 0
    resultHeight = size?.height ?: 0
    //设置预览格式getOptimalSize
    parameters?.previewFormat = ImageFormat.NV21
    //对焦
    parameters?.focusMode = Camera.Parameters.FOCUS_MODE_FIXED
    //给相机设置参数
    camera.parameters = parameters
}

/**
 * 释放相机资源
 * */
fun Camera?.releaseCamera() {
    if (this != null) {
        //停止预览
        stopPreview()
        setPreviewCallback(null)
        //释放相机资源
        release()
    }
}

/**
 * 获取相机旋转角度
 * */
fun getDisplayRotation(activity: Activity): Int {
    val rotation = activity.windowManager.defaultDisplay
        .rotation
    when (rotation) {
        Surface.ROTATION_0 -> return 270
        Surface.ROTATION_90 -> return 180
        Surface.ROTATION_180 -> return 90
        Surface.ROTATION_270 -> return 0
    }
    return 90
}

/**
 * 设置预览展示角度
 * */
fun setCameraDisplayOrientation(
    activity: Activity,
    cameraId: Int,
    camera: Camera
) {
    // See android.hardware.Camera.setCameraDisplayOrientation for
    // documentation.
    val info = Camera.CameraInfo()
    Camera.getCameraInfo(cameraId, info)
    val degrees = getDisplayRotation(activity)
    var result: Int
    if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
        result = (info.orientation + degrees) % 360
        result = (360 - result) % 360 // compensate the mirror
    } else { // back-facing
        result = (info.orientation - degrees + 360) % 360
    }
    camera.setDisplayOrientation(result)
}

/**
 * 开始相机预览
 * */
fun Camera.startPreview(surfaceHolder: SurfaceHolder) {
    //根据所传入的SurfaceHolder对象来设置实时预览
    setPreviewDisplay(surfaceHolder)
    startPreview()
}


/**
 * 选取与width、height比例最接近的、设置支持的size
 * @param context
 * @param sizes 设置支持的size序列
 * @param w 相机预览视图的width
 * @param h 相机预览视图的height
 * @return
 */
private fun getOptimalSize(
    context: Context,
    sizes: List<Camera.Size>,
    w: Int,
    h: Int
): Camera.Size? {
    val ASPECT_TOLERANCE = 0.1 //阈值，用于选取最优
    var targetRatio = -1.0
    val orientation = context.resources.configuration.orientation
    //保证targetRatio始终大于1，因为size.width/size.height始终大于1
    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
        targetRatio = h.toDouble() / w
    } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
        targetRatio = w.toDouble() / h
    }
    var optimalSize: Camera.Size? = null
    var minDiff = Double.MAX_VALUE
    val targetHeight = w.coerceAtMost(h)
    for (size in sizes) {
        val ratio = size.width.toDouble() / size.height
        //若大于了阈值，则继续筛选
        if (abs(ratio - targetRatio) > ASPECT_TOLERANCE) {
            continue
        }
        if (abs(size.height - targetHeight) < minDiff) {
            optimalSize = size
            minDiff = abs(size.height - targetHeight).toDouble()
        }
    }
    //若通过比例没有获得最优，则通过最小差值获取最优，保证至少能得到值
    if (optimalSize == null) {
        minDiff = Double.MAX_VALUE
        for (size in sizes) {
            if (abs(size.height - targetHeight) < minDiff) {
                optimalSize = size
                minDiff = abs(size.height - targetHeight).toDouble()
            }
        }
    }
    return optimalSize
}
