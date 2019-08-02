package demo.cryptocurrency.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import demo.cryptocurrency.model.CurrencyEntity

/**
 * @author  Qing Guo
 */
@Dao
public interface CurrencyDao {
    @Insert
    fun insertAllCurrency(list: MutableList<CurrencyEntity>)

    @Query("DELETE FROM currency_table")
    fun deleteAllCurrency()

    @Query("SELECT * FROM currency_table")
    fun getAllCurrency(): MutableList<CurrencyEntity>

    @Query("SELECT * FROM currency_table where purchase = 1")
    fun getCurrencyPurchase(): MutableList<CurrencyEntity>

    @Query("SELECT * FROM currency_table where symbol like '%' || :symbol || '%'")
    fun getCurrencyBySymbol(symbol: String): MutableList<CurrencyEntity>

    @Query("SELECT count(*) FROM currency_table")
    fun getCurrencyNumber(): Integer
}