package com.example.quotegenerator.fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.quotegenerator.database.asDBModel
import com.example.quotegenerator.database.asDomainModel
import com.example.quotegenerator.database.getDB
import com.example.quotegenerator.model.Quote
import kotlinx.coroutines.launch

class ViewModel(application: Application) : AndroidViewModel(application) {

   private val database = getDB(application)
    private var _quoteList = ArrayList<Quote>()
    val quoteList: ArrayList<Quote> get() = _quoteList

    init {
        viewModelScope.launch {
            getQuotesFromDB()
        }
    }

    suspend fun insertQuote(quote: Quote) {
        database.quoteDao.insertQuote(quote.asDBModel())
    }

    suspend fun deleteQuote(quote: Quote) {
        database.quoteDao.deleteQuote(quote.asDBModel())
    }

    suspend fun getQuotesFromDB() {
            val collection = database.quoteDao.getAll().asDomainModel()
            _quoteList = ArrayList(collection)
    }




}