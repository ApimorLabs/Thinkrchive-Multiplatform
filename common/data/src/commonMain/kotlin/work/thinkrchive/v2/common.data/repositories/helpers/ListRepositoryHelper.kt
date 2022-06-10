package work.thinkrchive.v2.common.data.repositories.helpers

import data.remote.response.ThinkpadResponse
import work.thinkrchive.v2.common.data.repositories.interfaces.ListRepository

class ListRepositoryHelper(
    val repository: ListRepository
) {

    // Should remove hardcoded sort option values
    suspend fun getThinkpadListSorted(model: String, sortOption: Int) =
        when (sortOption) {
            0 -> repository.getThinkpadsAlphaAscending(model)
            1 -> repository.getThinkpadsNewestFirst(model)
            2 -> repository.getThinkpadsOldestFirst(model)
            3 -> repository.getThinkpadsLowPriceFirst(model)
            4 -> repository.getThinkpadsHighPriceFirst(model)
            else -> repository.getAllThinkpads()
        }

    suspend fun refreshThinkpadList(thinkpads: List<ThinkpadResponse>) {
        repository.refreshThinkpadList(thinkpads)
    }
}