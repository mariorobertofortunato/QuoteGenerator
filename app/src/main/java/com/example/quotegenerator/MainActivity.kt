package com.example.quotegenerator

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.quotegenerator.api.Network
import com.example.quotegenerator.databinding.ActivityMainBinding
import com.example.quotegenerator.model.Quote
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var quote = Quote ("xxx","yyy","zzz")

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
        //JSONString from API method
        val jsonString = Network.retrofitService.getQuote()
        //Convert JSONString in JSONArray
        val jsonArray = JSONArray(jsonString)
        //Convert JSONArray (in pos x) in JSONObject
        val jsonObject: JSONObject = jsonArray.getJSONObject(0)
        // From the jObject assigns the string labelled "q" (the actual quote) or "a" (author)
        // to the same field of the Kotlin Quote object
        // and bind the string to the view
        binding.mainTextView.text = jsonObject.getString("q")
        binding.authorTextView.text = jsonObject.getString("a")
    }

    //Display dialog with info
    //TODO fare intent a web o mail?
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