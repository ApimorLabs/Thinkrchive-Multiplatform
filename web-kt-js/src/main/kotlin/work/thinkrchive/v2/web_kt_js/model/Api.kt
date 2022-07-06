package work.thinkrchive.v2.web_kt_js.model

import data.remote.response.ThinkpadResponse
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

private const val URL = "https://thinkrchive-server.herokuapp.com/v1/all-laptops"
suspend fun fetchThinkpads(): List<ThinkpadResponse> = coroutineScope {
    val response = window.fetch(URL)
        .await()
        .text()
        .await()
    Json.decodeFromString(response)
}