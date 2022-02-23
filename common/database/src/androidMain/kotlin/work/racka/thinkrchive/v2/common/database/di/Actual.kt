package work.racka.thinkrchive.v2.common.database.di

import com.squareup.sqldelight.android.AndroidSqliteDriver
import io.ktor.client.engine.android.*
import org.koin.dsl.module
import work.racka.thinkrchive.v2.common.database.db.ThinkpadDatabase
import work.racka.thinkrchive.v2.common.database.util.Constants


actual fun platformModule() = module {
    single { Android.create() }

    single<ThinkrchiveDatabaseWrapper> {
        val driver = AndroidSqliteDriver(
            schema = ThinkpadDatabase.Schema,
            context = get(),
            name = Constants.THINKPAD_DATABASE
        )
        ThinkrchiveDatabaseWrapper(ThinkpadDatabase(driver))
    }
}