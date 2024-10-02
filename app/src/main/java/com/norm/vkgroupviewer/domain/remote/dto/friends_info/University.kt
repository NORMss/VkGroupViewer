package com.norm.vkgroupviewer.domain.remote.dto.friends_info

import kotlinx.serialization.Serializable

@Serializable
data class University(
    val chair: Int,
    val chair_name: String,
    val city: Int,
    val country: Int,
    val education_form: String,
    val education_form_id: Int,
    val education_status: String,
    val education_status_id: Int,
    val faculty: Int,
    val faculty_name: String,
    val graduation: Int,
    val id: Int,
    val name: String
)