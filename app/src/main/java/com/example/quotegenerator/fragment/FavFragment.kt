package com.example.quotegenerator.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.snapshots.Snapshot.Companion.observe
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.transition.TransitionInflater
import com.example.quotegenerator.R
import com.example.quotegenerator.databinding.FragmentFavBinding
import com.example.quotegenerator.model.Quote


class FavFragment : Fragment() {

    private lateinit var binding: FragmentFavBinding
    private val viewModel by viewModels<ViewModel>()

    var provaquote = Quote("quote", "author", "")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_fav, container, false)

        binding.lifecycleOwner = this                                                               //lifecyleOwner
        binding.viewModel = viewModel

        //TODO recyclerView e tutte cose
        val adapter = Adapter()
        binding.quoteRecycler.adapter = adapter

        /**ASTEROID LIST*/
            //adapter.data.clear()
            //adapter.data.add(0,provaquote)
            //adapter.notifyDataSetChanged()


        return binding.root
    }

}