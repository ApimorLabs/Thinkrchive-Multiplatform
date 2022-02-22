package work.racka.thinkrchive.v2.common.database

import kotlinx.coroutines.runBlocking
import work.racka.thinkrchive.v2.common.database.di.Koin

fun main() {
    runBlocking {
        val koin = Koin.initKoin(enableNetworkLogs = true).koin
    }
}