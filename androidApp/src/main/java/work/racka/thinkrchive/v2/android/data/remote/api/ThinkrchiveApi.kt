package work.racka.thinkrchive.v2.android.data.remote.api

import work.racka.thinkrchive.v2.android.data.remote.responses.ThinkpadResponse

interface ThinkrchiveApi {

    suspend fun getThinkpads(): List<ThinkpadResponse>
}