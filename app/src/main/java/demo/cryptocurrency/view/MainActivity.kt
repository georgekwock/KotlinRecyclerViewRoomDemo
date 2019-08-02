package demo.cryptocurrency.view

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import demo.cryptocurrency.R
import demo.cryptocurrency.database.CurrencyDB
import demo.cryptocurrency.database.JsonUtil
import demo.cryptocurrency.model.CurrencyEntity
import demo.cryptocurrency.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), CurrencyAdapter.OnItemClickListener, SearchView.OnQueryTextListener,
    View.OnClickListener {
    private var currencyRecyclerView: RecyclerView? = null
    private var recyclerViewAdapter: CurrencyAdapter? = null
    private var searchView: SearchView? = null
    private var viewModel: MainViewModel? = null
    private var buttonA: FloatingActionButton? = null
    private var buttonB: FloatingActionButton? = null
    private var buttonC: FloatingActionButton? = null
    private var buttonD: FloatingActionButton? = null
    private var buttonE: FloatingActionButton? = null

    private var db: CurrencyDB? = null

//    private var currencyList = mutableListOf<CurrencyEntity>()

    companion object {
        lateinit var currencyList: MutableList<CurrencyEntity>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = CurrencyDB.getDatabase(this)
        currencyList = JsonUtil(this).parseJson("ListB.json")
        loadRecyclverView()
        setListener()
    }

    private fun setListener() {
        searchView = findViewById(R.id.search) as SearchView
        buttonA = findViewById(R.id.button_add_item)
        buttonB = findViewById(R.id.button_clear_list)
        buttonC = findViewById(R.id.button_use_A)
        buttonD = findViewById(R.id.button_use_B)
        buttonE = findViewById(R.id.button_filter_purchase)
        searchView!!.setOnQueryTextListener(this)
        buttonA!!.setOnClickListener(this)
        buttonB!!.setOnClickListener(this)
        buttonC!!.setOnClickListener(this)
        buttonD!!.setOnClickListener(this)
        buttonE!!.setOnClickListener(this)
    }

    fun loadRecyclverView() {
        db!!.daoCurrency().deleteAllCurrency()
        db!!.daoCurrency().insertAllCurrency(currencyList)
        currencyRecyclerView = findViewById(R.id.recycler_view)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        recyclerViewAdapter = CurrencyAdapter(currencyList, this, viewModel!!)
        currencyRecyclerView!!.layoutManager = LinearLayoutManager(this)
        currencyRecyclerView!!.adapter = recyclerViewAdapter
//        viewModel!!.getListCurrencies().observe(this, Observer { currencies ->
//            recyclerViewAdapter!!.addCryptocurrencies(currencies!!)
//        })
    }

    override fun onItemClick(currency: CurrencyEntity) {
        Toast.makeText(this, currency.name, Toast.LENGTH_LONG).show()
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        recyclerViewAdapter!!.filterSearch(newText)
        return false
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.button_add_item -> recyclerViewAdapter!!.filterInsert()
            R.id.button_clear_list -> recyclerViewAdapter!!.filterDeleteAll()
            R.id.button_use_A -> recyclerViewAdapter!!.filterUseA()
            R.id.button_use_B -> recyclerViewAdapter!!.filterUseB()
            R.id.button_filter_purchase -> recyclerViewAdapter!!.filterPurchase()
        }
    }
}
