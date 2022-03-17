package work.racka.thinkrchive.v2.common.features.details.repository

import domain.Thinkpad
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {
    suspend fun getThinkpad(thinkpadModel: String): Flow<Thinkpad>?
}