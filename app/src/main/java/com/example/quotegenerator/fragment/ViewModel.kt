package com.example.quotegenerator.fragment

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.view.isVisible
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quotegenerator.api.NetworkId0
import com.example.quotegenerator.api.NetworkId1
import com.example.quotegenerator.api.NetworkId2
import com.example.quotegenerator.api.PictureNetwork
import com.example.quotegenerator.database.asDBModel
import com.example.quotegenerator.database.asDomainModel
import com.example.quotegenerator.database.getDB
import com.example.quotegenerator.model.Quote
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class ViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDB(application)

    private var _quote = MutableLiveData<Quote>()
    val quote: LiveData<Quote> get() = _quote

    private var _quoteList = MutableLiveData<ArrayList<Quote>>()        //private var for internal use
    val quoteList: LiveData<ArrayList<Quote>> get() = _quoteList        //public var, accessible from other frags

    /** Public methods */

            /** DB */
            fun getQuotes() {
                viewModelScope.launch {
                    getQuotesFromDB()
                }
            }

            fun insertQuote(quote: Quote) {
                viewModelScope.launch {
                    insertQuoteInDB(quote)
                }
            }

            fun deleteQuote(quote: Quote) {
                viewModelScope.launch {
                    deleteQuoteFromDB(quote)
                }
            }

            /** Quotes from network*/
            fun refreshQuote(providerId: Int) {
                viewModelScope.launch {
                    refreshQuoteFromNetwork(providerId)
                }
            }

            /** Picture */
            suspend fun getPictureUrl () : String {
                val jsonString = PictureNetwork.retrofitServicePicture.getPicture()
                val jsonObject = JSONObject(jsonString)
                return jsonObject.getString("file")
            }


    /** Private methods */

            /** DB */
            private suspend fun getQuotesFromDB() {
                val quotesFromDB = database.quoteDao.getAll().asDomainModel()
                _quoteList.value = ArrayList(quotesFromDB)
            }

            private suspend fun insertQuoteInDB(quote: Quote) {
                database.quoteDao.insertQuote(quote.asDBModel())
            }

            private suspend fun deleteQuoteFromDB(quote: Quote) {
                database.quoteDao.deleteQuote(quote.asDBModel())
            }

            /** Quotes from network*/
            private suspend fun refreshQuoteFromNetwork(providerId: Int) {

                val networkQuote = Quote (0,"","","")

                when (providerId) {
                    0 -> {
                        //JSONString from API method
                        val jsonString = NetworkId0.retrofitServiceId0.getQuote()
                        //Convert JSONString in JSONArray
                        val jsonArray = JSONArray(jsonString)
                        //Convert JSONArray item (in pos x) in JSONObject
                        val jsonObject: JSONObject = jsonArray.getJSONObject(0)
                        // From the jsonObject assigns the string labelled "q" (the actual quote) or "a" (author)
                        // to the same field of the Kotlin Quote object
                        networkQuote.q = jsonObject.getString("q")
                        networkQuote.a = jsonObject.getString("a")
                        networkQuote.provider = "ZenQuotes.io"
                        _quote.value = networkQuote

                    }
                    1 -> {
                        val jsonString = NetworkId1.retrofitServiceId1.getQuoteNoValue()
                        val jsonObject = JSONObject(jsonString)
                        networkQuote.q = jsonObject.getString("quote")
                        networkQuote.a = "Kanye West"
                        networkQuote.provider = "Kanye as a Service"
                        _quote.value = networkQuote

                    }
                    else -> {
                        val jsonString = NetworkId2.retrofitServiceId2.getQuote()
                        val jsonObject = JSONObject(jsonString)
                        //nested json
                        val author = jsonObject.getJSONObject("author")
                        networkQuote.q = jsonObject.getString("text")
                        networkQuote.a = author.getString("name")
                        networkQuote.provider = "fisenkodv"
                        _quote.value = networkQuote

                    }
                }
            }
}