package com.example.quotegenerator.fragment

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quotegenerator.api.PictureNetwork
import com.example.quotegenerator.database.asDBModel
import com.example.quotegenerator.database.asDomainModel
import com.example.quotegenerator.database.getDB
import com.example.quotegenerator.model.Quote
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URL

class ViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDB(application)
    private var _quoteList = MutableLiveData<ArrayList<Quote>>()
    val quoteList: LiveData<ArrayList<Quote>> get() = _quoteList

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
}