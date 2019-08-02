package demo.cryptocurrency.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import demo.cryptocurrency.dao.CurrencyDao
import demo.cryptocurrency.model.CurrencyEntity

/**
 * @author  Qing Guo
 */
@Database(entities = [(CurrencyEntity::class)], version = 1, exportSchema = false)
abstract class CurrencyDB : RoomDatabase() {
    companion object {
        private var instance: CurrencyDB? = null
        fun getDatabase(context: Context): CurrencyDB {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext, CurrencyDB::class.java, "currency-db")
                    .allowMainThreadQueries().build()
            }
            return instance as CurrencyDB
        }
    }

    abstract fun daoCurrency(): CurrencyDao
}