package com.norm.vkgroupviewer.domain.remote

import io.ktor.client.HttpClient

interface HttpClientProvider {
    val client: HttpClient
}