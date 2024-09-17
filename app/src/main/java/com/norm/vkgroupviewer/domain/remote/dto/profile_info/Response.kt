package com.norm.vkgroupviewer.domain.remote.dto.profile_info

import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val bdate: String,
    val bdate_visibility: Int,
    val first_name: String,
    val home_town: String,
    val id: Int,
    val is_service_account: Boolean,
    val last_name: String,
    val phone: String,
    val photo_200: String,
    val promo_verifications: List<String>, //val promo_verifications: List<Any>
    val relation: Int,
    val screen_name: String,
    val sex: Int,
    val status: String,
    val verification_status: String
)