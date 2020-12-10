package com.lxh.cookcommunity.model.api.commonlist

data class CommonListPageModel<T>(
    val pageNum: Int? = 0,
    var pageSize: Int? = 0,
    var siz: Int? = 0,
    var startRow: Int? = 0,
    var endRow: Int? = 0,
    var pages: Int? = 0,
    var isFirstPage: Boolean? = false,
    var isLastPage: Boolean? = false,
    var hasNextPage: Boolean? = false,
    var navigatePages: Int? = 0,
    var navigatepageNums: List<Int>? = null,
    var navigateFirstPage: Int? = 0,
    var navigateLastPage: Int? = 0,
    var total: Int? = 0,
    var dataList: List<T>? = null,
    var isHasPreviousPage: Boolean? = false
)