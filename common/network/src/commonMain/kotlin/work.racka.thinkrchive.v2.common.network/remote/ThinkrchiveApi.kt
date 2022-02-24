package work.racka.thinkrchive.v2.common.network.remote

import domain.Thinkpad

interface ThinkrchiveApi {
    suspend fun getThinkpads(): List<Thinkpad>
}