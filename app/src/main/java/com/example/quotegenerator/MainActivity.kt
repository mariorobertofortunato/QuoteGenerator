package com.example.quotegenerator

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.quotegenerator.api.Network
import com.example.quotegenerator.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        //Main btn click listener
        binding.mainButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                refreshQuote()
            }
        }

        //INFO btn click listener
        binding.infoButton.setOnClickListener {
            displayDialog()
        }

    }

    private suspend fun refreshQuote() {
        val quote = Network.retrofitService.getQuote()
        binding.mainTextView.text = quote
    }


    //Display dialog with info
    private fun displayDialog() {
        val builder = AlertDialog.Builder(this)
            .setTitle(resources.getString(R.string.dialog_title))
            .setMessage(getString(R.string.dialog_message))
            .setPositiveButton(android.R.string.ok, null)
        val alert = builder.create()
        alert.show()
        alert.getButton(DialogInterface.BUTTON_POSITIVE).contentDescription = "OK"
    }


}