package com.example.quotegenerator.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.quotegenerator.databinding.FragmentFavBinding

class FavFragment : Fragment() {

    private lateinit var binding: FragmentFavBinding
    private val viewModel by viewModels<ViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        // Inflate the layout for this fragment
        binding = FragmentFavBinding.inflate(layoutInflater)

        viewModel.getQuotes()

        val adapter = Adapter()
        binding.quoteRecycler.adapter = adapter
        adapter.submitList(viewModel.quoteList.toMutableList())

        return binding.root
    }

}