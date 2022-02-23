package work.racka.thinkrchive.v2.common.database

import kotlinx.coroutines.runBlocking
import work.racka.thinkrchive.v2.common.database.di.Koin
import work.racka.thinkrchive.v2.common.database.remote.ThinkrchiveApi

fun main() {
    runBlocking {
        val koin = Koin.initKoin(enableNetworkLogs = true).koin
        val api = koin.get<ThinkrchiveApi>()
        println(api.getThinkpads())
    }
}