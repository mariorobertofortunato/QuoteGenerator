package com.example.quotegenerator.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.quotegenerator.R
import com.example.quotegenerator.api.NetworkId1
import com.example.quotegenerator.api.PictureNetwork
import com.example.quotegenerator.databinding.FragmentHomeBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<ViewModel>()
    private var providerId = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        CoroutineScope(Dispatchers.Main).launch {
            getPicture()
        }

        //Start buttons anim
        animation()

        /** LISTENERS */
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

    private fun animation () {
        binding.container.alpha = 0f
        binding.container.translationY = 50f
        binding.container.animate().alpha(1f).translationY(-100f).duration = 1000
    }

    private suspend fun getPicture() {

        val jsonString = PictureNetwork.retrofitServicePicture.getPicture()
        val jsonObject = JSONObject(jsonString)
        val url = jsonObject.getString("file")
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.ic_baseline_error_24)
            .error(R.drawable.ic_baseline_error_24)
            .into(binding.picture)
    }
}


