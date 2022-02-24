package work.racka.thinkrchive.v2.common.network.remote

import domain.Thinkpad
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import work.racka.thinkrchive.v2.common.network.util.Constants


class ThinkrchiveApiImpl(
    private val client: HttpClient
) : ThinkrchiveApi {

    override suspend fun getThinkpads(): List<Thinkpad> =
        client.get {
            url(Constants.ALL_LAPTOPS)
            contentType(ContentType.Application.Json)
        }

}