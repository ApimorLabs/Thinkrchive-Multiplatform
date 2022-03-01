package work.racka.thinkrchive.v2.common.network.remote

import data.remote.response.ThinkpadResponse

interface ThinkrchiveApi {
    suspend fun getThinkpads(): List<ThinkpadResponse>
}