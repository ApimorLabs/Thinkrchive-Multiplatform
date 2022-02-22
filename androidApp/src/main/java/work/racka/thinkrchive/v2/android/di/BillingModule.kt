package work.racka.thinkrchive.v2.android.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import work.racka.thinkrchive.v2.android.billing.BillingManager
import work.racka.thinkrchive.v2.android.repository.BillingRepository

@Module
@InstallIn(ViewModelComponent::class)
object BillingModule {

    @ViewModelScoped
    @Provides
    fun providesBillingRepository(
        billingManager: BillingManager
    ) = BillingRepository(billingManager)


    @ViewModelScoped
    @Provides
    fun providesBillingManager(
        @ApplicationContext context: Context
    ): BillingManager = BillingManager(context)
}