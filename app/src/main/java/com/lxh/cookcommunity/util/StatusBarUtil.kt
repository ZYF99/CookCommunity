package com.lxh.cookcommunity.util

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import com.lxh.cookcommunity.R
import java.io.IOException


/**
 * 将acitivity中的activity中的状态栏设置为一个纯色
 * @param activity 需要设置的activity
 * @param color 设置的颜色（一般是titlebar的颜色）
 */
fun setStatusBarColor(activity: Activity, color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        //5.0及以上，不设置透明状态栏，设置会有半透明阴影
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        //设置statusBar的背景色
        activity.window.statusBarColor = color
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        // 生成一个状态栏大小的矩形
        val statusView = createStatusBarView(activity, color)
        // 添加 statusView 到布局中
        val decorView = activity.window.decorView as ViewGroup
        decorView.addView(statusView)
        //让我们的activity_main。xml中的布局适应屏幕
        setRootView(activity)
    }
    //状态栏字体黑色
    setStatusTextColor(true, activity)

}

/**
 * 当顶部是图片时，是图片显示到状态栏上
 * @param activity
 */
fun setImage(activity: Activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        //5.0及以上，不设置透明状态栏，设置会有半透明阴影
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        //是activity_main。xml中的图片可以沉浸到状态栏上
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        //设置状态栏颜色透明。
        activity.window.statusBarColor = Color.TRANSPARENT
    } else {
        //。。。。
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }

}

/**
 * 设置根布局参数，让跟布局参数适应透明状态栏
 *
 */
private fun setRootView(activity: Activity) {
    //获取到activity_main.xml文件
    val rootView =
        (activity.findViewById<View>(R.id.content) as ViewGroup).getChildAt(
            0
        ) as ViewGroup

    //如果不是设置参数，会使内容显示到状态栏上
    rootView.fitsSystemWindows = true
}

/**
 * 获取状态栏的高度
 * @param acitivity
 * @return
 */
private fun getStatusBarHeight(acitivity: Activity): Int {
    val resourceId =
        acitivity.resources.getIdentifier("status_bar_height", "dimen", "android")
    return acitivity.resources.getDimensionPixelOffset(resourceId)
}

/**
 * 生成一个和状态栏大小相同的矩形条
 *
 * @param activity 需要设置的activity
 * @param color    状态栏颜色值
 * @return 状态栏矩形条
 */
private fun createStatusBarView(activity: Activity, color: Int): View {
    // 绘制一个和状态栏一样高的矩形
    val statusBarView = View(activity)
    val params = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        getStatusBarHeight(activity)
    )
    statusBarView.layoutParams = params
    statusBarView.setBackgroundColor(color)
    return statusBarView
}

var screenWidth = 0
var screenHeight = 0
var navigationHeight = 0

private var mMetrics: DisplayMetrics? = null
const val HOME_CURRENT_TAB_POSITION = "HOME_CURRENT_TAB_POSITION"

/**
 * 通过反射的方式获取状态栏高度
 *
 * @return
 */
fun getStatusBarHeight(context: Context): Int {
    try {
        val c = Class.forName("com.android.internal.R\$dimen")
        val obj = c.newInstance()
        val field = c.getField("status_bar_height")
        val x = field[obj].toString().toInt()
        return context.resources.getDimensionPixelSize(x)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return 0
}

/**
 * 获取底部导航栏高度
 *
 * @return
 */
fun getNavigationBarHeight(context: Context): Int {
    val resources = context.resources
    val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    //获取NavigationBar的高度
    navigationHeight = resources.getDimensionPixelSize(resourceId)
    return navigationHeight
}

//获取是否存在NavigationBar
fun checkDeviceHasNavigationBar(context: Context): Boolean {
    var hasNavigationBar = false
    val rs = context.resources
    val id = rs.getIdentifier("config_showNavigationBar", "bool", "android")
    if (id > 0) {
        hasNavigationBar = rs.getBoolean(id)
    }
    try {
        val systemPropertiesClass =
            Class.forName("android.os.SystemProperties")
        val m =
            systemPropertiesClass.getMethod("get", String::class.java)
        val navBarOverride =
            m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
        if ("1" == navBarOverride) {
            hasNavigationBar = false
        } else if ("0" == navBarOverride) {
            hasNavigationBar = true
        }
    } catch (e: Exception) {
    }
    return hasNavigationBar
}

/**
 * @param activity
 * @param useThemestatusBarColor   是否要状态栏的颜色，不设置则为透明色
 * @param withoutUseStatusBarColor 是否不需要使用状态栏为暗色调
 */
fun setStatusBar(
    activity: Activity,
    useThemestatusBarColor: Boolean,
    withoutUseStatusBarColor: Boolean
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //5.0及以上
        val decorView = activity.window.decorView
        val option = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        decorView.systemUiVisibility = option
        if (useThemestatusBarColor) {
            activity.window.statusBarColor = activity.resources.getColor(R.color.colorWhite)
        } else {
            activity.window.statusBarColor = Color.TRANSPARENT
        }
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //4.4到5.0
        val localLayoutParams = activity.window.attributes
        localLayoutParams.flags =
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or localLayoutParams.flags
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !withoutUseStatusBarColor) {
        activity.window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}

fun reMeasure(activity: Activity) {
    val display = activity.windowManager.defaultDisplay
    mMetrics = DisplayMetrics()
    if (Build.VERSION.SDK_INT >= 17) {
        display.getRealMetrics(mMetrics)
    } else {
        display.getMetrics(mMetrics)
    }
    screenWidth = mMetrics!!.widthPixels
    screenHeight = mMetrics!!.heightPixels
}

/**
 * 改变魅族的状态栏字体为黑色，要求FlyMe4以上
 */
fun processFlyMe(isLightStatusBar: Boolean, activity: Activity) {
    val lp = activity.window.attributes
    try {
        val instance =
            Class.forName("android.view.WindowManager\$LayoutParams")
        val value = instance.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON").getInt(lp)
        val field = instance.getDeclaredField("meizuFlags")
        field.isAccessible = true
        val origin = field.getInt(lp)
        if (isLightStatusBar) {
            field[lp] = origin or value
        } else {
            field[lp] = value.inv() and origin
        }
    } catch (ignored: Exception) {
        ignored.printStackTrace()
    }
}

/**
 * 改变小米的状态栏字体颜色为黑色, 要求MIUI6以上  lightStatusBar为真时表示黑色字体
 */
fun processMIUI(lightStatusBar: Boolean, activity: Activity) {
    val clazz: Class<out Window?> = activity.window.javaClass
    try {
        val darkModeFlag: Int
        val layoutParams =
            Class.forName("android.view.MiuiWindowManager\$LayoutParams")
        val field =
            layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
        darkModeFlag = field.getInt(layoutParams)
        val extraFlagField = clazz.getMethod(
            "setExtraFlags",
            Int::class.javaPrimitiveType,
            Int::class.javaPrimitiveType
        )
        extraFlagField.invoke(
            activity.window,
            if (lightStatusBar) darkModeFlag else 0,
            darkModeFlag
        )
    } catch (ignored: Exception) {
        ignored.printStackTrace()
    }
}

private const val KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code"
private const val KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name"
private const val KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage"

/**
 * 判断手机是否是小米
 * @return
 */
fun isMIUI(): Boolean {
    return try {
        val prop: BuildProperties = BuildProperties.newInstance()
        prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null || prop.getProperty(
            KEY_MIUI_VERSION_NAME,
            null
        ) != null || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null
    } catch (e: IOException) {
        false
    }
}

/**
 * 判断手机是否是魅族
 * @return
 */
fun isFlyme(): Boolean {
    return try {
        // Invoke Build.hasSmartBar()
        val method =
            Build::class.java.getMethod("hasSmartBar")
        method != null
    } catch (e: Exception) {
        false
    }
}

/**
 * 设置状态栏文字色值为深色调
 * @param useDart 是否使用深色调
 * @param activity
 */
fun setStatusTextColor(useDart: Boolean, activity: Activity) {
    when {
        isFlyme() -> {
            processFlyMe(useDart, activity)
        }
        isMIUI() -> {
            processMIUI(useDart, activity)
        }
        else -> {
            if (useDart) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    activity.window.decorView.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
            } else {
                activity.window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            }
            activity.window.decorView.findViewById<View>(android.R.id.content)
                .setPadding(0, 0, 0, navigationHeight)
        }
    }
}

/**
 * 通过设置全屏，设置状态栏透明:沉浸式
 *
 * @param activity
 */
fun fullScreen(activity: Activity) {
    //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
    val window: Window = activity.window
    val decorView: View = window.decorView
    //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
    val option = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
    decorView.systemUiVisibility = option
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = Color.TRANSPARENT
    //导航栏颜色也可以正常设置
//                window.setNavigationBarColor(Color.TRANSPARENT);
}