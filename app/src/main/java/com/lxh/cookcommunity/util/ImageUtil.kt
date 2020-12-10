package com.lxh.cookcommunity.util

import android.graphics.*
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.manager.sharedpref.SharedPrefModel
import java.io.*
import java.nio.ByteBuffer

@BindingAdapter("fitCenterImageUrl")
fun loadImageFitCenter(imageView: ImageView, url: String?) {
    Glide.with(imageView.context).load(url)
        .skipMemoryCache(false)
        .fitCenter()
        .into(imageView)
}

@BindingAdapter("imageUrl")
fun loadImageCenterCrop(imageView: ImageView, url: String?) {
    Glide.with(imageView.context).load(url)
        .placeholder(R.color.colorGray)
        .skipMemoryCache(false)
        .centerCrop()
        .into(imageView)
}

/**
 * 旋转YUV格式图片Byte[]90度
 * */
fun rotateYUV420Degree90(
    data: ByteArray,
    imageWidth: Int,
    imageHeight: Int
): ByteArray? {
    val yuv = ByteArray(imageWidth * imageHeight * 3 / 2)
    var i = 0
    for (x in 0 until imageWidth) {
        for (y in imageHeight - 1 downTo 0) {
            yuv[i] = data[y * imageWidth + x]
            i++
        }
    }
    i = imageWidth * imageHeight * 3 / 2 - 1
    var x = imageWidth - 1
    while (x > 0) {
        for (y in 0 until imageHeight / 2) {
            yuv[i] = data[imageWidth * imageHeight + y * imageWidth + x]
            i--
            yuv[i] = data[imageWidth * imageHeight + y * imageWidth
                    + (x - 1)]
            i--
        }
        x -= 2
    }
    return yuv
}

/**
 * 旋转YUV格式图片Byte[]180度
 * */
fun rotateYUV420Degree180(
    data: ByteArray,
    imageWidth: Int,
    imageHeight: Int
): ByteArray? {
    val yuv = ByteArray(imageWidth * imageHeight * 3 / 2)
    var i = 0
    var count = 0
    i = imageWidth * imageHeight - 1
    while (i >= 0) {
        yuv[count] = data[i]
        count++
        i--
    }
    i = imageWidth * imageHeight * 3 / 2 - 1
    i = imageWidth * imageHeight * 3 / 2 - 1
    while (i >= imageWidth
        * imageHeight
    ) {
        yuv[count++] = data[i - 1]
        yuv[count++] = data[i]
        i -= 2
    }
    return yuv
}

/**
 * 旋转YUV格式图片Byte[]270度
 * */
fun rotateYUV420Degree270(
    data: ByteArray,
    imageWidth: Int,
    imageHeight: Int
): ByteArray? {
    val yuv = ByteArray(imageWidth * imageHeight * 3 / 2)
    var nWidth = 0
    var nHeight = 0
    var wh = 0
    var uvHeight = 0
    if (imageWidth != nWidth || imageHeight != nHeight) {
        nWidth = imageWidth
        nHeight = imageHeight
        wh = imageWidth * imageHeight
        uvHeight = imageHeight shr 1 // uvHeight = height / 2
    }
    var k = 0
    for (i in 0 until imageWidth) {
        var nPos = 0
        for (j in 0 until imageHeight) {
            yuv[k] = data[nPos + i]
            k++
            nPos += imageWidth
        }
    }
    var i = 0
    while (i < imageWidth) {
        var nPos = wh
        for (j in 0 until uvHeight) {
            yuv[k] = data[nPos + i]
            yuv[k + 1] = data[nPos + i + 1]
            k += 2
            nPos += imageWidth
        }
        i += 2
    }
    return rotateYUV420Degree180(yuv, imageWidth, imageHeight)
}

/**
 * NV21格式图片Byte[]转BitMap
 * */
fun Bytes2Bimap(
    bs: ByteArray,
    width: Int,
    height: Int
): Bitmap {
    val yuvimage = YuvImage(bs, ImageFormat.NV21, width, height, null)
    val baos = ByteArrayOutputStream()
    yuvimage.compressToJpeg(Rect(0, 0, 20, 20), 100, baos)
    val jdata = baos.toByteArray()
    return BitmapFactory.decodeByteArray(jdata, 0, jdata.size)
}

/**
 * 缓存BitMap为图片文件
 * */
fun bitmap2File(bitmap: Bitmap, filePath: String): File? {
    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val file = File(filePath)
    try {
        file.createNewFile()
        val fos = FileOutputStream(file)
        val inputStream: InputStream = ByteArrayInputStream(baos.toByteArray())
        var x = 0
        val b = ByteArray(1024 * 100)
        while (inputStream.read(b).also { x = it } != -1) {
            fos.write(b, 0, x)
        }
        fos.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return file
}

/**
 * 缓存YUV格式数据为图片文件
 * */
fun cacheYUVImageFile(
    data: ByteArray,
    width: Int,
    height: Int
): File {
    val tempFilePath = SharedPrefModel.cacheDir + "/pic_${System.currentTimeMillis()}.jpeg"
    YUV2Bitmap(rotateYUV420Degree270(data, width, height), height, width)?.let {
        bitmap2File(it, tempFilePath)
    }
    return File(tempFilePath)
}

/**
 * YUV格式转BitMap
 * */
fun YUV2Bitmap(yuv: ByteArray?, mWidth: Int, mHeight: Int): Bitmap? {
    var bmp: Bitmap? = null
    try {
        val image = YuvImage(yuv, ImageFormat.NV21, mWidth, mHeight, null)
        val stream = ByteArrayOutputStream()
        image.compressToJpeg(Rect(0, 0, mWidth, mHeight), 80, stream)
        bmp = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size())
        stream.flush()
        stream.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return bmp
}


/**
 * 提取图像中的BGR像素
 * @param image
 * @return
 */
fun getPixelsBGR(image: Bitmap): ByteArray? {
    // calculate how many bytes our image consists of
    val bytes = image.byteCount
    val buffer = ByteBuffer.allocate(bytes) // Create a new buffer
    image.copyPixelsToBuffer(buffer) // Move the byte data to the buffer
    val temp = buffer.array() // Get the underlying array containing the data.
    val pixels = ByteArray(temp.size / 4 * 3) // Allocate for BGR

    // Copy pixels into place
    for (i in 0 until temp.size / 4) {
        pixels[i * 3] = temp[i * 4 + 2] //B
        pixels[i * 3 + 1] = temp[i * 4 + 1] //G
        pixels[i * 3 + 2] = temp[i * 4] //R
    }
    return pixels
}

/**
 * 放大BitMap
 * */
fun Bitmap.big(scale: Float): Bitmap? {
    val matrix = Matrix()
    matrix.setScale(scale, scale) //长和宽放大缩小的比例
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

/**
 * 旋转BitMap
 * */
fun rotateBitmap(bitmap: Bitmap, degress: Float): Bitmap {
    val m = Matrix()
    m.postRotate(degress)
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, m, true)
}