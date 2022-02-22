package work.racka.thinkrchive.v2.android.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ThinkpadDatabaseObject::class],
    version = 2
)
abstract class ThinkpadDatabase : RoomDatabase() {
    abstract val thinkpadDao: ThinkpadDao
}