package com.example.quotegenerator.fragment

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.quotegenerator.R
import com.example.quotegenerator.databinding.FragmentHomeBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<ViewModel>()
    private var providerId = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        //Load the picture of the day
        CoroutineScope(Dispatchers.Main).launch {
            getPicture()
        }

        //Start buttons anim
        animation()

        /** LISTENERS */

        binding.picture.setOnClickListener {
            Toast.makeText(requireContext(),"Ciao",Toast.LENGTH_SHORT).show()
            //TODO work in progress
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
                //askPermissions()
            } else {
                CoroutineScope(Dispatchers.Main).launch {
                    //downloadImage(viewModel.getPictureUrl())
                }
            }
        }

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
        Picasso.get()
            .load(viewModel.getPictureUrl())
            .placeholder(R.drawable.ic_baseline_error_24)
            .error(R.drawable.ic_baseline_error_24)
            .into(binding.picture)
    }


}


