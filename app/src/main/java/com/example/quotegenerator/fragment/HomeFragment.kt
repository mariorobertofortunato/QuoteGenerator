package com.example.quotegenerator.fragment

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*

//Notification values
private const val CHANNEL_ID = "channelId"
private const val NOTIFICATION_ID = 0

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<ViewModel>()
    private var providerId = 0
    private lateinit var notificationManager: NotificationManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        //Load the picture of the day
        CoroutineScope(Dispatchers.Main).launch {
            getPicture()
        }

        //Init the notif channel
        createChannel(CHANNEL_ID,getString(R.string.notification_channel_name))

        //Start buttons anim
        animation()

        /** LISTENERS */

        binding.picture.setOnClickListener {
            if (isPermissionGranted()) {
                Toast.makeText(requireContext(),"Downloading picture",Toast.LENGTH_SHORT).show()
                lifecycleScope.launch {
                    saveImage(binding.picture,requireActivity())
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

    /** ANIMATION */
    private fun animation () {
        binding.container.alpha = 0f
        binding.container.translationY = 4000f
        binding.container.animate().alpha(1f).translationY(-1f).duration = 1000
    }

    /** PICTURE METHODS */

            private suspend fun getPicture() {
                Picasso.get()
                    .load(viewModel.getPictureUrl())
                    .placeholder(R.drawable.ic_baseline_error_24)
                    .error(R.drawable.ic_baseline_error_24)
                    .into(binding.picture)
            }

            private fun saveImage (itemImage: View, activity: Activity) {

                val fileName: String
                val imageFromView = getBitmapFromView(itemImage)

                ByteArrayOutputStream().apply {
                    imageFromView.compress(Bitmap.CompressFormat.JPEG,100,this)
                    fileName = UUID.nameUUIDFromBytes(this.toByteArray()).toString().replace("-","")
                }

                val imageFile = File("${activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)}/QuoteGenerator/$fileName.jpg/")

                if(!imageFile.exists()) {
                    val contentResolver = ContentValues().apply {
                        put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis())
                        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                        put(MediaStore.Images.Media.DATA, imageFile.absolutePath)
                    }

                    activity.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentResolver).apply {
                        imageFromView.compress(Bitmap.CompressFormat.JPEG,100,activity.contentResolver.openOutputStream(this!!))
                    }

                    Toast.makeText(requireContext(),"Image saved", Toast.LENGTH_SHORT).show()
                    //Notif is shown once the download is completed
                    notificationManager = ContextCompat.getSystemService(requireContext(), NotificationManager::class.java) as NotificationManager
                    notificationManager.sendNotification(requireContext().getText(R.string.notification_description).toString(), requireContext())
                }
            }

            private fun getBitmapFromView (view: View) : Bitmap {
                return Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888).apply {
                    Canvas(this).apply {
                        view.draw(this)
                    }
                }
            }


    /** PERMISSION METHODS */

            private fun isPermissionGranted() : Boolean {
                return checkSelfPermission(requireActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE) === PermissionChecker.PERMISSION_GRANTED
            }

            override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
                if (requestCode == 1) {
                    if (grantResults.size > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                        CoroutineScope(Dispatchers.Main).launch {
                            saveImage(binding.picture, requireActivity())
                        }
                    } else {
                        Snackbar.make(binding.mainLayout, "Authorization needed", Snackbar.LENGTH_SHORT
                        ).setAction("Settings") {
                            startActivity(Intent(Settings.ACTION_SETTINGS))
                        }
                            .show()

                    }
                }
            }

    /** NOTIFICATION */

    //Build and send the notif
    private fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {
        //Notif builder
        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_download_done_24)
            .setContentTitle("Quote Generator")
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        notify(NOTIFICATION_ID, builder.build())
    }

    //Build the notif channel
    @RequiresApi(Build.VERSION_CODES.O)
    fun createChannel(channelId : String, channelName : String) {

        val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
        notificationChannel.enableLights(true)
        notificationChannel.enableVibration(true)
        notificationChannel.lightColor = Color.RED
        notificationChannel.description = "channel"

        notificationManager = ContextCompat.getSystemService(requireContext(), NotificationManager::class.java) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }


}