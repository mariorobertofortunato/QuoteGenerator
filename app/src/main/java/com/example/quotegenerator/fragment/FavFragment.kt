package com.example.quotegenerator.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.example.quotegenerator.R
import com.example.quotegenerator.database.asDomainModel
import com.example.quotegenerator.databinding.FragmentFavBinding
import com.example.quotegenerator.model.Quote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



class FavFragment : Fragment() {

    private lateinit var binding: FragmentFavBinding
    private val viewModel by viewModels<ViewModel>()

    //TODO cancellare una volta che non serve piu
   var provaquote1 = Quote(0,"quote1", "author1", "")
    var provaquote2 = Quote(0,"quote2", "author2", "")
    var provaquote9 = Quote(0,"quote3", "author3", "")
    var provaqQuoteList = ArrayList<Quote>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_fav, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        val adapter = Adapter()
        binding.quoteRecycler.adapter = adapter

        provaqQuoteList.add(provaquote9)
        //adapter.submitList(provaqQuoteList)


        adapter.submitList(viewModel.quoteList)


        return binding.root
    }

}