package com.lxh.cookcommunity.model.api.commonlist

data class CommonListPageModel<T>(
    val pageNum: Int? = 0,
    var pageSize: Int? = 0,
    var size: Int? = 0,
    var startRow: Int? = 0,
    var endRow: Int? = 0,
    var pages: Int? = 0,
    var hasPreviousPage: Boolean? = false,
    var hasNextPage: Boolean? = false,
    var total: Int? = 0,
    var dataList: MutableList<T>? = null,
    var firstPage: Boolean? = false,
    var lastPage: Boolean? = false
)