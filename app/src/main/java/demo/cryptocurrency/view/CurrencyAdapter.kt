package demo.cryptocurrency.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import demo.cryptocurrency.R
import demo.cryptocurrency.model.CurrencyEntity
import demo.cryptocurrency.view.MainActivity.Companion.currencyList
import demo.cryptocurrency.viewmodel.MainViewModel
import java.util.*

/**
 * @author  Qing Guo
 */
class CurrencyAdapter(
    currencyList: MutableList<CurrencyEntity>,
    listener: OnItemClickListener,
    viewModel: MainViewModel
) :
    RecyclerView.Adapter<CurrencyAdapter.RecyclerViewHolder>() {
    private var list: MutableList<CurrencyEntity>
    private var listenerCurrency: OnItemClickListener = listener
    private val viewModel = viewModel

    init {
        list = mutableListOf()
        this.list.addAll(currencyList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.currency_item, parent, false))
    }

    override fun getItemCount(): Int {
        return currencyList.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        var bean: CurrencyEntity = currencyList[position]
        var nameCurrency = bean.name
        var symbolCurrency = bean.symbol
        var capitalCurrency = nameCurrency.substring(0, 1).toUpperCase()
        holder!!.mName.text = nameCurrency
        holder!!.mSymbol.text = symbolCurrency
        holder!!.mCapital.text = capitalCurrency
        holder.bind(bean, listenerCurrency)
    }

    interface OnItemClickListener {
        fun onItemClick(currency: CurrencyEntity)
    }

    fun addCryptocurrencies(cryptoCurrencies: MutableList<CurrencyEntity>) {
        currencyList = cryptoCurrencies
        notifyDataSetChanged()
    }

    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mName = itemView.findViewById<TextView>(R.id.text_view_name)!!
        var mSymbol = itemView.findViewById<TextView>(R.id.text_view_symbol)!!
        var mCapital = itemView.findViewById<TextView>(R.id.text_view_capital)!!

        fun bind(currency: CurrencyEntity, listener: OnItemClickListener) {
            itemView.setOnClickListener {
                listener.onItemClick(currency)
            }
        }

    }

    // Filter search Class
    fun filterSearch(charText: String) {
        var charText = charText
        charText = charText.toLowerCase(Locale.getDefault())
        MainActivity.currencyList.clear()
        if (charText.length == 0) {
            MainActivity.currencyList.addAll(list)
        } else {
            for (cy in list) {
                if (cy.name.toLowerCase(Locale.getDefault()).contains(charText)) {
                    MainActivity.currencyList.add(cy)
                }
            }
        }
        notifyDataSetChanged()
    }

    // Filter Currency can be purchased
    fun filterPurchase() {
        MainActivity.currencyList.clear()
        viewModel.filterPurchaseList()?.let { MainActivity.currencyList.addAll(it) }
        notifyDataSetChanged()
    }

    // Filter insert data
    fun filterInsert() {
        MainActivity.currencyList.clear()
        viewModel.insertAll()?.let { MainActivity.currencyList.addAll(it) }
        notifyDataSetChanged()
    }

    // Filter delete data
    fun filterDeleteAll() {
        MainActivity.currencyList.clear()
        viewModel.clearList()?.let { MainActivity.currencyList.addAll(it) }
        notifyDataSetChanged()
    }

    // Filter use A file
    fun filterUseA() {
        MainActivity.currencyList.clear()
        viewModel.useAList()?.let { MainActivity.currencyList.addAll(it) }
        notifyDataSetChanged()
    }

    // Filter useB file
    fun filterUseB() {
        MainActivity.currencyList.clear()
        viewModel.useBList()?.let { MainActivity.currencyList.addAll(it) }
        notifyDataSetChanged()
    }
}