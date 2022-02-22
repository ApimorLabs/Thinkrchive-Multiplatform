package work.racka.thinkrchive.v2.common.database.remote

import work.racka.thinkrchive.v2.common.database.model.Thinkpad

interface ThinkrchiveApi {
    suspend fun getThinkpads(): List<Thinkpad>
}