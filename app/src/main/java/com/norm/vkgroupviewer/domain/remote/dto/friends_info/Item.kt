package com.norm.vkgroupviewer.domain.remote.dto.friends_info

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val bdate: String? = null,
    val can_access_closed: Boolean,
    val can_post: Int? = null,
    val can_see_all_posts: Int? = null,
    val can_write_private_message: Int? = null,
    val city: City? = null,
    val domain: String? = null,
    val education_form: String? = null,
    val education_status: String? = null,
    val faculty: Int? = null,
    val faculty_name: String? = null,
    val first_name: String,
    val graduation: Int? = null,
    val has_mobile: Int? = null,
    val home_phone: String? = null,
    val id: Int,
    val is_closed: Boolean,
    val last_name: String,
    val last_seen: LastSeen? = null,
    val lists: List<Int>? = null,
    val mobile_phone: String? = null,
    val nickname: String? = null,
    val online: Int? = null,
    val online_app: Int? = null,
    val online_mobile: Int? = null,
    val photo_100: String? = null,
    val photo_200_orig: String? = null,
    val photo_50: String? = null,
    val relation: Int? = null,
    val relation_partner: RelationPartner? = null,
    val sex: Int? = null,
    val status: String? = null,
    val track_code: String? = null,
    val universities: List<University>? = null,
    val university: Int? = null,
    val university_name: String? = null
)
