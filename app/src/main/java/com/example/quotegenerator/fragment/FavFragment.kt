package com.example.quotegenerator.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.quotegenerator.R
import com.example.quotegenerator.databinding.FragmentFavBinding
import com.example.quotegenerator.model.Quote


class FavFragment : Fragment() {

    private lateinit var binding: FragmentFavBinding
    private val viewModel by viewModels<ViewModel>()

    //TODO cancellare una volta che non serve piu
    var provaquote1 = Quote("quote1", "author1", "")
    var provaquote2 = Quote("quote2", "author2", "")
    var provaquote3 = Quote("quote3", "author3", "")
    var quoteList = ArrayList<Quote>()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_fav, container, false)

        binding.lifecycleOwner = this                                                               //lifecyleOwner
        binding.viewModel = viewModel

    //TODO cancellare auendo inutlie
        quoteList.add(provaquote1)
        quoteList.add(provaquote2)
        quoteList.add(provaquote3)

        //TODO recyclerView e tutte cose
        //binding.quoteRecycler.adapter = Adapter()

        //questo è l'esempio
        val adapter = Adapter()
        binding.quoteRecycler.adapter = adapter
        adapter.submitList(quoteList.toMutableList())


        /**ASTEROID LIST*/
            //adapter.data.clear()
            //adapter.data.add(0,provaquote)
            //adapter.notifyDataSetChanged()


        return binding.root
    }

}