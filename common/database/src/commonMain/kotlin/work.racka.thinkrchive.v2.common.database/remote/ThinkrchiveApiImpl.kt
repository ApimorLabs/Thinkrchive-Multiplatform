package work.racka.thinkrchive.v2.common.database.remote

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import work.racka.thinkrchive.v2.common.database.model.Thinkpad
import work.racka.thinkrchive.v2.common.database.util.Constants


class ThinkrchiveApiImpl(
    private val client: HttpClient
) : ThinkrchiveApi {

    override suspend fun getThinkpads(): List<Thinkpad> =
        client.get {
            url(Constants.ALL_LAPTOPS)
            contentType(ContentType.Application.Json)
        }

}