package work.racka.thinkrchive.v2.common.network

import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import work.racka.thinkrchive.v2.common.network.di.Network
import work.racka.thinkrchive.v2.common.network.remote.ThinkrchiveApi

fun main() {
    runBlocking {
        val koin = startKoin {
            with(Network) {
                networkModules(true)
            }
        }.koin
        val api: ThinkrchiveApi = koin.get()
        println(api.getThinkpads())
    }
}