package com.lxh.cookcommunity.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

//个位时间补0
fun fixTimeWith0(time: Int): String {
    return if (time < 10) "0$time"
    else "$time"
}

///判断是否是同一天
fun isTheSameDay(oneTimeStamp: Long, secondTimeStamp: Long): Boolean {
    val c1 = Calendar.getInstance()
    val c2 = Calendar.getInstance()
    c1.time = Date(oneTimeStamp)
    c2.time = Date(secondTimeStamp)
    return c1[Calendar.YEAR] == c2[Calendar.YEAR]
            && c1[Calendar.MONTH] == c2[Calendar.MONTH]
            && c1[Calendar.DAY_OF_MONTH] == c2[Calendar.DAY_OF_MONTH]
}

//获取年月日
fun long2DateStringChineseSplit(timeStamp: Long): String {
    val format = SimpleDateFormat("yyyy年MM月dd日")
    val date = Date(timeStamp)
    return format.format(date)
}

//判断是否在时分(String：HH:mm)范围内
fun judgeTimeInRange(
    startTimeShortString: String?,
    endTimeShortString: String?
): Boolean {
    val format = "HH:mm"
    val nowTime: Date = SimpleDateFormat(format).parse(dateToTimeShort(Date()))
    val startTime: Date = SimpleDateFormat(format).parse(startTimeShortString)
    val endTime: Date = SimpleDateFormat(format).parse(endTimeShortString)
    return isEffectiveDate(nowTime, startTime, endTime)
}

//判断是否在时分范围内
fun isEffectiveDate(nowTime: Date, startTime: Date, endTime: Date): Boolean {
    if (nowTime.time === startTime.time
        || nowTime.time === endTime.time
    ) {
        return true
    }
    val date = Calendar.getInstance()
    date.time = nowTime
    val begin = Calendar.getInstance()
    begin.time = startTime
    val end = Calendar.getInstance()
    end.time = endTime
    return date.after(begin) && date.before(end)
}


/**
 * 针对str格式的时间做转换 格式为"xx:xx"
 * @param time  传入的时间
 * @param hour  true：返回小时；false：返回分钟
 * @return  当前小时或分钟
 */
private fun getHourOrMinute(time: String, hour: Boolean): Int {
    return if (hour) {
        Integer.parseInt(time.substring(0, time.indexOf(":")))
    } else {
        Integer.parseInt(time.substring(time.indexOf(":") + 1))
    }
}


//获取 年-月-日
fun long2DateString(timeStamp: Long?): String {
    if (timeStamp == 0.toLong() || timeStamp == null) return ""
    val format = SimpleDateFormat("yyyy-MM-dd")
    if (timeStamp != null) {
        val date = Date(timeStamp)
        return format.format(date)
    } else {
        return ""
    }
}

//获取中文间隙的年月日
fun long2DateChineseString(timeStamp: Long): String {
    val format = SimpleDateFormat("yyyy年MM月dd日")
    val date = Date(timeStamp)
    return format.format(date)
}

//获取时间 小时:分 HH:mm
fun dateToTimeShort(date: Date): String {
    val formatter = SimpleDateFormat("HH:mm")
    return formatter.format(date)
}

//获取周
fun getWeek(date: Date): String {
    val weekMap = hashMapOf(
        Pair("星期日", "周天"),
        Pair("星期一", "周一"),
        Pair("星期二", "周二"),
        Pair("星期三", "周三"),
        Pair("星期四", "周四"),
        Pair("星期五", "周五"),
        Pair("星期六", "周六")
    )
    return weekMap[dateToWeek(date)] ?: "周天"
}

//获取星期
fun dateToWeek(dateTime: Date): String {
    val weekDays =
        arrayOf("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")
    val cal = Calendar.getInstance()
    try {
        cal.time = dateTime
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    //一周的第几天
    var w = cal[Calendar.DAY_OF_WEEK] - 1
    if (w < 0) w = 0
    return weekDays[w]
}

//获取今天是一周的第几天
fun getTodayNumOfWeek(): Int {
    val cal = Calendar.getInstance()
    var w = cal[Calendar.DAY_OF_WEEK] - 1
    if (w < 0) w = 0
    return w
}