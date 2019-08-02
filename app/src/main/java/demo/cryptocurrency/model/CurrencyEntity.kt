package demo.cryptocurrency.model

import androidx.room.*

/**
 * @author  Qing Guo
 */

@Entity(tableName = "currency_table")
data class CurrencyEntity(
    @PrimaryKey(autoGenerate = true)
    val sid: Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "symbol")
    var symbol: String?,
    @ColumnInfo(name = "purchase")
    val purchase: Int = 0
)
