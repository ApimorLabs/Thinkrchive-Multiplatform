package work.racka.thinkrchive.v2.common.database.util

import com.squareup.sqldelight.ColumnAdapter

internal object TypeAdapters {
    private val longToIntAdapter = object : ColumnAdapter<Int, Long> {
        override fun decode(databaseValue: Long): Int = databaseValue.toInt()
        override fun encode(value: Int): Long = value.toLong()
    }

    // To be used when upgrading to 2.0.0 release
    /*fun thinkpadListTableAdapter() = ThinkpadListTable(
        marketPriceEndAdapter = longToIntAdapter,
        marketPriceStartAdapter = longToIntAdapter
    )*/
}