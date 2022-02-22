package work.racka.thinkrchive.v2.android.data.remote.api

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import work.racka.thinkrchive.v2.android.data.remote.responses.ThinkpadResponse
import work.racka.thinkrchive.v2.android.utils.Constants

class ThinkrchiveApiImpl(
    private val client: HttpClient
) : ThinkrchiveApi {

    override suspend fun getThinkpads(): List<ThinkpadResponse> =
        client.get {
            url(Constants.ALL_LAPTOPS)
            contentType(ContentType.Application.Json)
        }

}