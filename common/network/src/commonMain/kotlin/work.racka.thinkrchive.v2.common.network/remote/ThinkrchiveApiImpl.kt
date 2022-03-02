package work.racka.thinkrchive.v2.common.network.remote

import data.remote.response.ThinkpadResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import work.racka.thinkrchive.v2.common.network.util.Constants


internal class ThinkrchiveApiImpl(
    private val client: HttpClient
) : ThinkrchiveApi {

    override suspend fun getThinkpads(): List<ThinkpadResponse> =
        client.get {
            url(Constants.ALL_LAPTOPS)
            contentType(ContentType.Application.Json)
        }

}