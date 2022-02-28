package work.racka.thinkrchive.v2.common.integration.containers.list

import data.remote.response.ThinkpadResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import work.racka.thinkrchive.v2.common.integration.repository.ThinkrchiveRepository

class ThinkpadListHelper(
    val repository: ThinkrchiveRepository
) {

    suspend fun getThinkpadListSorted(model: String, sortOption: Int) =
        when (sortOption) {
            0 -> repository.getThinkpadsAlphaAscending(model)
            1 -> repository.getThinkpadsNewestFirst(model)
            2 -> repository.getThinkpadsOldestFirst(model)
            3 -> repository.getThinkpadsLowPriceFirst(model)
            4 -> repository.getThinkpadsHighPriceFirst(model)
            else -> repository.getAllThinkpads()
        }

    suspend fun refreshThinkpadList(
        thinkpads: List<ThinkpadResponse>,
        dispatcher: CoroutineDispatcher
    ) {
        withContext(dispatcher) {
            repository.refreshThinkpadList(thinkpads)
        }
    }
}