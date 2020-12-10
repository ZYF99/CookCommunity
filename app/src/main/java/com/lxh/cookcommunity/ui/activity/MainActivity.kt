package com.lxh.cookcommunity.ui.activity

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.lxh.cookcommunity.R
import com.lxh.cookcommunity.ui.base.BaseActivity
import com.lxh.cookcommunity.ui.base.setupNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpBottomNavigation()
    }

    //设置底部导航栏
    private fun setUpBottomNavigation() {
        val controller = bottom_navigation_view_home.setupNavController(
            navGraphIds = listOf(
                R.navigation.top_home,
                R.navigation.top_moment,
                R.navigation.top_personal
            ),
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_fragment_container_main,
            intent = intent
        )
        currentNavController = controller
    }
}