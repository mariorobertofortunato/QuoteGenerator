package com.example.quotegenerator.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quotegenerator.database.asDBModel
import com.example.quotegenerator.database.asDomainModel
import com.example.quotegenerator.model.Quote
import com.example.quotegenerator.repository.DBRepository
import com.example.quotegenerator.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(private val networkRepository: NetworkRepository,
                                    private val databaseRepository: DBRepository) : ViewModel() {

    //Initiate the networkQuote with a starting placeholder text
    private val networkQuote = Quote (0,"Click GENERATE button below to generate a quote","","")

    private val placeHolderText = Quote (0, "Too many requests. Obtain an auth key for unlimited access.","zenquotes.io","Provided by ZenQuotes.io")

    var status = Status.NOTLOADED

    private var _quote = MutableLiveData<Quote>()
    val quote: LiveData<Quote> get() = _quote

    private var _quoteList = MutableLiveData<ArrayList<Quote>>()        //backing field (private)
    val quoteList: LiveData<ArrayList<Quote>> get() = _quoteList        //backing property (public, read only)

    /** Public methods */

/** DB */
            fun getQuotes() {
                viewModelScope.launch {
                    getQuotesFromDB()
                }
            }

            fun insertQuote() {
                viewModelScope.launch {
                    insertQuoteInDB()
                }
            }

            fun deleteQuote(quote: Quote) {
                viewModelScope.launch {
                    deleteQuoteFromDB(quote)
                }
            }

            /** Quote from network*/
            fun refreshQuote(providerId: Int) {
                status = Status.LOADED
                viewModelScope.launch {
                    refreshQuoteFromNetwork(providerId)
                }
            }

            /** Picture */
            suspend fun getPictureUrl () : String {
                val jsonString = networkRepository.getPicture()
                val jsonObject = JSONObject(jsonString)
                return jsonObject.getString("file")
            }

            /** UTILS */

                    //Show a placeholder text the first time the user enter the Quote frag
                    fun justStarted() : Boolean {
                        if (status == Status.NOTLOADED) {
                            _quote.value = networkQuote
                            return true
                        }
                        return false
                    }

                    //Check if the loaded quote is actually a placeholder text due to too many requests
                    fun notPlaceHolderText(): Boolean {
                        return _quote.value == placeHolderText
                    }


    /** Private methods */

            /** DB */
            private suspend fun getQuotesFromDB() {
                val quotesFromDB = databaseRepository.getAll().asDomainModel()
                _quoteList.value = ArrayList(quotesFromDB)
            }

            private suspend fun insertQuoteInDB() {
                databaseRepository.insertQuote(networkQuote.asDBModel())
            }

            private suspend fun deleteQuoteFromDB(quote: Quote) {
                databaseRepository.deleteQuote(quote.asDBModel())
            }

            /** Quote from network*/
            private suspend fun refreshQuoteFromNetwork(providerId: Int) {

                when (providerId) {
                    0 -> {
                        //JSONString from API method
                        val jsonString = networkRepository.getQuoteValue1()
                        //Convert JSONString in JSONArray
                        val jsonArray = JSONArray(jsonString)
                        //Convert JSONArray item (in pos x) in JSONObject
                        val jsonObject: JSONObject = jsonArray.getJSONObject(0)
                        // From the jsonObject assigns the string labelled "q" (the actual quote) or "a" (author)
                        // to the same field of the Kotlin Quote object
                        networkQuote.q = jsonObject.getString("q")
                        networkQuote.a = jsonObject.getString("a")
                        networkQuote.provider = "Provided by ZenQuotes.io"
                        _quote.value = networkQuote

                    }
                    1 -> {
                        val jsonString = networkRepository.getQuoteNoValue()
                        val jsonObject = JSONObject(jsonString)
                        networkQuote.q = jsonObject.getString("quote")
                        networkQuote.a = "Kanye West"
                        networkQuote.provider = "Provided by Kanye as a Service"
                        _quote.value = networkQuote

                    }
                    else -> {
                        val jsonString = networkRepository.getQuoteValue2()
                        val jsonObject = JSONObject(jsonString)
                        //nested json
                        val author = jsonObject.getJSONObject("author")
                        networkQuote.q = jsonObject.getString("text")
                        networkQuote.a = author.getString("name")
                        networkQuote.provider = "Provided by fisenkodv"
                        _quote.value = networkQuote

                    }
                }
            }
}

enum class Status {
    LOADED, NOTLOADED
}