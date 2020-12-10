package com.lxh.cookcommunity.util

import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Build
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*

/*机器工具类*/

/*机器获取以太网mac地址*/
fun getMac(context: Context): String {

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        getMacMoreThanM(context) ?:""
    }else{
        val wifi = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val info = wifi.connectionInfo
        info.macAddress
    }
}

//android 6.0+获取wifi的mac地址
fun getMacMoreThanM(paramContext: Context?): String? {
    try {
        //获取本机器所有的网络接口
        val enumeration: Enumeration<*> =
            NetworkInterface.getNetworkInterfaces()
        while (enumeration.hasMoreElements()) {
            val networkInterface =
                enumeration.nextElement() as NetworkInterface
            //获取硬件地址，一般是MAC
            val arrayOfByte = networkInterface.hardwareAddress
            if (arrayOfByte == null || arrayOfByte.isEmpty()) {
                continue
            }
            val stringBuilder = StringBuilder()
            for (b in arrayOfByte) {
                //格式化为：两位十六进制加冒号的格式，若是不足两位，补0
                stringBuilder.append(
                    String.format(
                        "%02X:",
                        java.lang.Byte.valueOf(b)
                    )
                )
            }
            if (stringBuilder.isNotEmpty()) {
                //删除后面多余的冒号
                stringBuilder.deleteCharAt(stringBuilder.length - 1)
            }
            val str = stringBuilder.toString()
            // wlan0:无线网卡 eth0：以太网卡
            return str

        }
    } catch (socketException: SocketException) {
        return null
    }
    return null
}

const val SET_POWER_ON_OFF = "android.intent.action.setpoweronoff"

/*设置自动开关机时间*/
fun setPowerOnAndOff(
    context: Context,
    powerOnTimeString: String,
    powerOffString: String
) {
    //携带的数据格式为：
    val timeOnArray = powerOnTimeString.split("-").map { it.toInt() }.toIntArray()//下次开机具体日期时间
    val timeOffArray = powerOffString.split("-").map { it.toInt() }.toIntArray()//下次关机的时间
    val intent = Intent(SET_POWER_ON_OFF)
    intent.putExtra("timeon", timeOnArray)
    intent.putExtra("timeoff", timeOffArray)
    intent.putExtra("enable", true) //使能开关机，true为打开，false为关闭
    context.sendBroadcast(intent) //注意:定时开关机的时间精度是1分钟，为避免时间失效，建议设置的关机时间离当前时间2分钟及以上值，开机时间离关机设置时间在3分钟及以上间隔的值。
}

/*设置自动开关机时间--特殊版本（至善机器）*/
fun setPowerOnAndOffInSpecial(
    powerOnTimeString: String,
    powerOffString: String
) {
     //ByteflyerApiManager().XHSetPowerOffOnTime(powerOffString,powerOnTimeString,true)
}
