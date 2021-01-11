package com.lxh.cookcommunity.model.api.editinfo

data class EditInfoRequestModel(
    val name: String? = null,
    val gender: String? = null,
    val avatar: String? = null,
    val aboutMe: String? = null
)