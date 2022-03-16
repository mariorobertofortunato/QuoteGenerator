package com.example.quotegenerator.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.quotegenerator.R
import com.example.quotegenerator.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
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
            if (isPermissionGranted()) {
                Toast.makeText(requireContext(),"Downloading picture",Toast.LENGTH_SHORT).show()
                lifecycleScope.launch {
                    downloadImage(viewModel.getPictureUrl())
                }
            } else {
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
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
        binding.container.translationY = 4000f
        binding.container.animate().alpha(1f).translationY(-1f).duration = 1000
    }

    /**PICTURE METHODS*/

            private suspend fun getPicture() {
                Picasso.get()
                    .load(viewModel.getPictureUrl())
                    .placeholder(R.drawable.ic_baseline_error_24)
                    .error(R.drawable.ic_baseline_error_24)
                    .into(binding.picture)
            }

            private fun downloadImage (url: String) {
                //TODO salvare immagine in galleria
            }

    /**PERMISSION METHODS*/

            private fun isPermissionGranted() : Boolean {
                return checkSelfPermission(requireActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE) === PermissionChecker.PERMISSION_GRANTED
            }

            override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
                if (requestCode == 1) {
                    if (grantResults.size > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                        //downloadImage(viewModel.getPictureUrl())
                        Toast.makeText(requireContext(),"aaaaaaa",Toast.LENGTH_SHORT).show()
                    } else {
                        Snackbar.make(binding.mainLayout, "Authorization needed", Snackbar.LENGTH_LONG
                        ).setAction("Settings") {
                            startActivity(Intent(Settings.ACTION_SETTINGS))
                        }
                            .show()

                    }
                }
            }


}


