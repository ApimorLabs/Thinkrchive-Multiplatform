package work.racka.thinkrchive.v2.common.features.details.repository

import io.mockk.impl.annotations.RelaxedMockK
import org.koin.test.KoinTest
import org.koin.test.inject
import work.racka.thinkrchive.v2.common.database.dao.ThinkpadDao
import kotlin.test.BeforeTest

class DetailsRepositoryTest : KoinTest {

    private val repo: DetailsRepository by inject()

    @RelaxedMockK
    lateinit var dao: ThinkpadDao

    @BeforeTest
    fun setup
}