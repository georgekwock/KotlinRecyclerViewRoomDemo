package demo.cryptocurrency.database

import android.content.Context
import demo.cryptocurrency.model.CurrencyEntity
import org.json.JSONArray
import java.io.InputStream

/**
 * @author Qing Guo
 */
class JsonUtil(context: Context) {
    var context = context

    //read local json file to String
    open fun readTextFromAsset(file: String): String? {
        var json: String?
        try {
            val inputStream: InputStream = context.assets.open(file)
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    //parse json file to Entity
    open fun parseJson(file: String): MutableList<CurrencyEntity> {
        val jsonResponse = readTextFromAsset(file)
        val jsonArray = JSONArray(jsonResponse)
        val listBean: MutableList<CurrencyEntity> = mutableListOf()
        for (i in 0..jsonArray!!.length() - 1) {
            var jsonObj = jsonArray.optJSONObject(i)
            val name = jsonObj.optString("name")
            val symbol = jsonObj.optString("symbol")
            val featureArray = jsonObj.optJSONArray("features")
            var purchase = 0
            for (i in 0..featureArray!!.length() - 1) {
                val featureObj = featureArray.get(i)
                if (featureObj.toString().equals("purchase"))
                    purchase = 1
            }
            var entity = CurrencyEntity(1 + i, name, symbol, purchase)
            if (entity != null) {
                listBean.add(entity)
            }
        }
        return listBean
    }
}
