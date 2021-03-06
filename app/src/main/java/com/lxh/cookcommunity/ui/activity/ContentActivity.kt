package com.lxh.cookcommunity.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.databinding.ActivitySingleBinding
import com.lxh.cookcommunity.ui.base.BaseActivity
import java.io.Serializable

class ContentActivity : BaseActivity() {

    private lateinit var binding: ActivitySingleBinding
    private var hideClickCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_single)
        intent.extras.let {

            val destination = it?.getSerializable(INTENT_EXTRA_KEY_EXTRA_DES) as? Destination
            destination.let { des ->
                findNavController(R.id.nav_host_fragment).navigate(
                    des?.id ?: Destination.Splash.id,
                    it?.getBundle(INTENT_EXTRA_KEY_BUNDLE),
                    navOptions { popUpTo(R.id.bridgeFragment) { inclusive = true } }
                )
            }

            (it?.getInt(INTENT_EXTRA_KEY_DES_ID))?.let { desId ->
                if (desId == 0) return@let
                findNavController(R.id.nav_host_fragment).navigate(
                    desId,
                    null,
                    navOptions { popUpTo(R.id.bridgeFragment) { inclusive = true } })
            }
        }

    }

    companion object {
        private const val INTENT_EXTRA_KEY_EXTRA_DES = "extra_des"
        private const val INTENT_EXTRA_KEY_DES_ID = "extra_des_id"
        private const val INTENT_EXTRA_KEY_BUNDLE = "extra_args_bundle"
        private const val BUNDLE_KEY = "extra_args"

        fun createIntent(
            context: Context,
            des: Destination,
            s: Serializable? = null,
            bundle: Bundle? = null
        ): Intent {
            return Intent(context, ContentActivity::class.java)
                .putExtra(INTENT_EXTRA_KEY_EXTRA_DES, des)
                .putExtra(INTENT_EXTRA_KEY_BUNDLE, bundle ?: bundleOf(BUNDLE_KEY to s))
        }

        // desId may not contains in the navGraph
        fun createIntentUnsafe(context: Context, @IdRes desId: Int): Intent {
            return Intent(context, ContentActivity::class.java)
                .putExtra(INTENT_EXTRA_KEY_DES_ID, desId)
        }
    }

    enum class Destination(@IdRes val id: Int) {
        Splash(R.id.splash_fragment),
        CameraSearch(R.id.camera_search_fragment),
        FoodDetail(R.id.food_detail_fragment),
        SearchFood(R.id.search_food_fragment),
        MomentDetail(R.id.moment_detail_fragment),
        StudyVideo(R.id.study_video_fragment),
        CookPersonal(R.id.cook_personal_fragment),
        Classify(R.id.classify_fragment),
        Mark(R.id.mark_fragment),
        GoodsDetail(R.id.goods_detail_fragment),
        PersonPersonal(R.id.person_personal_fragment),
        EditInfo(R.id.edit_info_fragment),
        ReleaseMoment(R.id.release_moment_fragment),
    }

}
