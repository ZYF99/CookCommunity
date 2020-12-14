package com.lxh.cookcommunity.ui.fragment.camerasearch

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Matrix
import android.util.Size
import android.view.Surface
import android.view.ViewGroup
import androidx.camera.core.*
import com.example.rubbishcommunity.ui.search.cameraSearch.ThingsAnalyzer
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.FragmentCameraSearchBinding
import com.lxh.cookcommunity.ui.activity.ContentActivity
import com.lxh.cookcommunity.ui.base.BaseFragment
import com.lxh.cookcommunity.util.checkCameraPermission
import java.util.concurrent.Executors

class CameraSearchFragment : BaseFragment<FragmentCameraSearchBinding, CameraSearchViewModel>(
    CameraSearchViewModel::class.java, layoutRes = R.layout.fragment_camera_search
) {

    private val executor = Executors.newSingleThreadExecutor()

    override fun initView() {

        //获取相机权限
        checkCameraPermission().doOnNext {
            binding.viewFinder.post { startCamera() }
        }.bindLife()

        binding.viewFinder.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            updateTransform()
        }

    }

    override fun initData() {

    }

    override fun initDataObServer() {
        viewModel.thingString.observeNonNull {
            binding.tvName.text = it
        }
    }

    @SuppressLint("RestrictedApi")
    private fun startCamera() {

        // Create configuration object for the viewfinder use case
        val previewConfig = PreviewConfig.Builder().build()

        // Build the viewfinder use case
        val preview = Preview(previewConfig)

        val imageCaptureConfig = ImageCaptureConfig.Builder()
            .apply {
                // We don't set a resolution for image capture; instead, we
                // select a capture mode which will infer the appropriate
                // resolution based on aspect ration and requested mode
                setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
            }.build()

        val imageCapture = ImageCapture(imageCaptureConfig)
        // Setup image analysis pipeline that computes average pixel luminance
        val analyzerConfig = ImageAnalysisConfig.Builder().apply {
            			//setTargetResolution(Size(1280, 720))
            // 旋转
            setImageReaderMode(
                ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE
            )
        }.build()

        // Build the image analysis use case and instantiate our analyzer
        val analyzerUseCase = ImageAnalysis(analyzerConfig).apply {
            setAnalyzer(executor, ThingsAnalyzer {
                viewModel.identifyThingName(it)
            })
        }

        // Every time the viewfinder is updated, recompute layout
        preview.setOnPreviewOutputUpdateListener {
            // To update the SurfaceTexture, we have to remove it and re-add it
            val parent = binding.viewFinder.parent as ViewGroup
            parent.removeView(binding.viewFinder)
            parent.addView(binding.viewFinder, 0)
            binding.viewFinder.setSurfaceTexture(it.surfaceTexture)
            updateTransform()
        }

        CameraX.bindToLifecycle(this, preview, imageCapture, analyzerUseCase)
    }

    private fun updateTransform() {
        val matrix = Matrix()

        // Compute the center of the view finder
        val centerX = binding.viewFinder.width / 2f
        val centerY = binding.viewFinder.height / 2f

        // Correct preview output to account for display rotation
        val rotationDegrees = when (binding.viewFinder.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }
        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)

        // Finally, apply transformations to our TextureView
        binding.viewFinder.setTransform(matrix)
    }

}

fun Context.jumpToCameraSearch() {
    val intent = ContentActivity.createIntent(
        context = this,
        des = ContentActivity.Destination.CameraSearch
    )
    this.startActivity(intent)
}