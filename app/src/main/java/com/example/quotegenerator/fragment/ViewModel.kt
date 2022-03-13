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

    /**Public methods*/
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

    /**Private methods*/
    private suspend fun getQuotesFromDB() {
        val quotesFromDB = database.quoteDao.getAll().asDomainModel()
        _quoteList = ArrayList(quotesFromDB)
    }

    private suspend fun insertQuoteInDB(quote: Quote) {
        database.quoteDao.insertQuote(quote.asDBModel())
    }

    private suspend fun deleteQuoteFromDB(quote: Quote) {
        database.quoteDao.deleteQuote(quote.asDBModel())
    }






}