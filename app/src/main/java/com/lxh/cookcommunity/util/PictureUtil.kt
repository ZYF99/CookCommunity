package com.lxh.cookcommunity.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Environment
import androidx.exifinterface.media.ExifInterface
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


fun bytesToImageFile(bytes: ByteArray): File? {
    return try {
        val file =
            File(Environment.getExternalStorageDirectory().absolutePath + "/${System.currentTimeMillis()}.jpeg");
        val fos = FileOutputStream(file)
        fos.write(bytes, 0, bytes.size)
        fos.flush()
        fos.close()
        file
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

/**
 * 图片压缩-质量压缩
 *
 * @param filePath 源图片路径
 * @return 压缩后的路径
 */
fun compressImage(oldFile: File?): File? {

    //压缩文件路径 照片路径/
    val targetPath = oldFile?.path
    val quality = 50 //压缩比例0-100
    var bm: Bitmap? = getSmallBitmap(oldFile?.path) //获取一定尺寸的图片
    val degree = getRotateAngle(oldFile?.path) //获取相片拍摄角度
    if (degree != 0) { //旋转照片角度，防止头像横着显示
        bm = setRotateAngle(degree, bm)
    }
    val outputFile = File(targetPath)
    try {
        if (!outputFile.exists()) {
            outputFile.parentFile.mkdirs()
            //outputFile.createNewFile();
        } else {
            outputFile.delete()
        }
        val out = FileOutputStream(outputFile)
        bm!!.compress(Bitmap.CompressFormat.JPEG, quality, out)
        out.close()
    } catch (e: Exception) {
        e.printStackTrace()
        return oldFile
    }
    return outputFile
}

/**
 * 获取图片的旋转角度
 *
 * @param filePath
 * @return
 */
fun getRotateAngle(filePath: String?): Int {
    var rotate_angle = 0
    try {
        val exifInterface =
            ExifInterface(filePath!!)
        val orientation = exifInterface.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotate_angle =
                90
            ExifInterface.ORIENTATION_ROTATE_180 -> rotate_angle =
                180
            ExifInterface.ORIENTATION_ROTATE_270 -> rotate_angle =
                270
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return rotate_angle
}

/**
 * 旋转图片角度
 *
 * @param angle
 * @param bitmap
 * @return
 */
fun setRotateAngle(angle: Int, bitmap: Bitmap?): Bitmap? {
    var bitmap = bitmap
    if (bitmap != null) {
        val m = Matrix()
        m.postRotate(angle.toFloat())
        bitmap = Bitmap.createBitmap(
            bitmap, 0, 0, bitmap.width,
            bitmap.height, m, true
        )
        return bitmap
    }
    return bitmap
}

/**
 * 根据路径获得图片信息并按比例压缩，返回bitmap
 */
fun getSmallBitmap(filePath: String?): Bitmap {
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true //只解析图片边沿，获取宽高
    BitmapFactory.decodeFile(filePath, options)
    // 计算缩放比
    options.inSampleSize = calculateInSampleSize(options, 480, 800)
    // 完整解析图片返回bitmap
    options.inJustDecodeBounds = false
    return BitmapFactory.decodeFile(filePath, options)
}

fun calculateInSampleSize(
    options: BitmapFactory.Options,
    reqWidth: Int, reqHeight: Int
): Int {
    val height = options.outHeight
    val width = options.outWidth
    var inSampleSize = 1
    if (height > reqHeight || width > reqWidth) {
        val heightRatio =
            Math.round(height.toFloat() / reqHeight.toFloat())
        val widthRatio =
            Math.round(width.toFloat() / reqWidth.toFloat())
        inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
    }
    return inSampleSize
}