package work.racka.thinkrchive.v2.common.features.thinkpad_list.container

import data.remote.response.ThinkpadResponse
import work.racka.thinkrchive.v2.common.features.thinkpad_list.repository.ListRepository

class ThinkpadListHelper(
    val repository: ListRepository
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

    suspend fun refreshThinkpadList(thinkpads: List<ThinkpadResponse>) {
        repository.refreshThinkpadList(thinkpads)
    }
}