package demo.cryptocurrency.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import demo.cryptocurrency.database.CurrencyDB
import demo.cryptocurrency.database.JsonUtil
import demo.cryptocurrency.model.CurrencyEntity

/**
 * @author  Qing Guo
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var currencyDB: CurrencyDB
    private var listCurrency: MutableList<CurrencyEntity>
    private var context = application

    init {
        currencyDB = CurrencyDB.getDatabase(this.getApplication())
        listCurrency = currencyDB.daoCurrency().getAllCurrency()
    }
//
//    fun getListCurrencies(): MutableList<CurrencyEntity> {
//        return listCurrency
//    }

    //insert All Data to list
    fun insertAll() :MutableList<CurrencyEntity>{
        var size: Integer = currencyDB.daoCurrency().getCurrencyNumber()
        if (size > 0) {
            Toast.makeText(context, "do not repeatedly insert data", Toast.LENGTH_SHORT).show()
        } else {
            currencyDB.daoCurrency().insertAllCurrency(JsonUtil(context).parseJson("ListA.json"))
        }
        return currencyDB.daoCurrency().getAllCurrency()
    }

    //clear all data
    fun clearList() : MutableList<CurrencyEntity>? {
        var size: Integer = currencyDB.daoCurrency().getCurrencyNumber()
        if (size > 0) {
            currencyDB.daoCurrency().deleteAllCurrency()
        } else {
            Toast.makeText(context, "no data to clear", Toast.LENGTH_SHORT).show()
        }
        return  null
    }

    //change list using A file
    fun useAList() : MutableList<CurrencyEntity>{
        currencyDB.daoCurrency().deleteAllCurrency()
        currencyDB.daoCurrency().insertAllCurrency(JsonUtil(context).parseJson("ListA.json"))
        return currencyDB.daoCurrency().getAllCurrency()
    }

    //changing List using B file
    fun useBList(): MutableList<CurrencyEntity> {
        currencyDB.daoCurrency().deleteAllCurrency()
        currencyDB.daoCurrency().insertAllCurrency(JsonUtil(context).parseJson("ListB.json"))
        return currencyDB.daoCurrency().getAllCurrency()
    }

    //filter cuurency which can be purchased
    fun filterPurchaseList(): MutableList<CurrencyEntity>? {
        var size: Integer = currencyDB.daoCurrency().getCurrencyNumber()
        if (size < 0) {
            Toast.makeText(context, "no data to search", Toast.LENGTH_SHORT).show()
        } else {
            return currencyDB.daoCurrency().getCurrencyPurchase()
        }
        return null
    }
}