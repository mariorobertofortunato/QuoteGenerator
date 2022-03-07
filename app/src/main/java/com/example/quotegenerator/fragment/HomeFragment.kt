package com.example.quotegenerator.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.quotegenerator.R
import com.example.quotegenerator.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var providerId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)

        binding.firstOptButton.setOnClickListener {
            providerId = 0
            val action = HomeFragmentDirections.actionHomeFragmentToQuoteFragment(providerId)
            findNavController().navigate(action)
        }

        binding.secondOptButton.setOnClickListener {
            providerId = 1
            val action = HomeFragmentDirections.actionHomeFragmentToQuoteFragment(providerId)
            findNavController().navigate(action)
        }

        binding.thirdOptButton.setOnClickListener {
            providerId = 2
            val action = HomeFragmentDirections.actionHomeFragmentToQuoteFragment(providerId)
            findNavController().navigate(action)
        }

        return binding.root
    }
}


