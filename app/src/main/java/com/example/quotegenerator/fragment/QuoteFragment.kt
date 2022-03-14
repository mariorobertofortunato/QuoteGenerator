package com.example.quotegenerator.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.quotegenerator.R
import com.example.quotegenerator.api.NetworkId0
import com.example.quotegenerator.api.NetworkId1
import com.example.quotegenerator.api.NetworkId2
import com.example.quotegenerator.databinding.FragmentQuoteBinding
import com.example.quotegenerator.model.Quote
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class QuoteFragment : Fragment() {

    private lateinit var binding: FragmentQuoteBinding
    private val viewModel by viewModels<ViewModel>()
    var quote = Quote(0,"","")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val providerId = arguments?.getInt("providerId")

        // Inflate the layout for this fragment
        binding = FragmentQuoteBinding.inflate(layoutInflater)

        /** LISTENERS */
        //"Generate" btn listener
        binding.mainButton.setOnClickListener {
            lifecycleScope.launch {
                refreshQuote(providerId!!)
            }
            //Reset heart button
            binding.heartFav.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }

        //FavoriteQuoteList btn listener
        binding.favQuotesBtn.setOnClickListener {
            viewModel.getQuotes()
            findNavController().navigate(QuoteFragmentDirections.actionQuoteFragmentToFavFragment())
        }

        //Heart btn listner
        binding.heartFav.setOnClickListener {
            if (quote.q!="") {
                viewModel.insertQuote(quote)
            } else {
                Toast.makeText(requireContext(),"Generate a quote first",Toast.LENGTH_SHORT).show()
            }
            binding.heartFav.setImageResource(R.drawable.ic_baseline_favorite_24)
        }

        //Powered by click listener
        binding.poweredByText.setOnClickListener {
            //TODO fare l'intent che porta alla pagine web del provider relativo
        }

        //INFO btn click listener
        binding.infoButton.setOnClickListener {
            displayDialog()
        }

        return binding.root
    }


    private suspend fun refreshQuote(providerId: Int) {

        var providerName = ""

        when (providerId) {
            0 -> {
                //JSONString from API method
                val jsonString = NetworkId0.retrofitServiceId0.getQuote()
                //Convert JSONString in JSONArray
                val jsonArray = JSONArray(jsonString)
                //Convert JSONArray item (in pos x) in JSONObject
                val jsonObject: JSONObject = jsonArray.getJSONObject(0)
                // From the jsonObject assigns the string labelled "q" (the actual quote) or "a" (author)
                // to the same field of the Kotlin Quote object
                quote.q = jsonObject.getString("q")
                quote.a = jsonObject.getString("a")
                providerName = "ZenQuotes.io"
            }
            1 -> {
                val jsonString = NetworkId1.retrofitServiceId1.getQuoteNoValue()
                val jsonObject = JSONObject(jsonString)
                quote.q = jsonObject.getString("quote")
                quote.a = "Kanye West"
                providerName = "Kanye as a Service"
            }
            2 -> {
                val jsonString = NetworkId2.retrofitServiceId2.getQuote()
                val jsonObject = JSONObject(jsonString)
                //nested json
                val author = jsonObject.getJSONObject("author")
                quote.q = jsonObject.getString("text")
                quote.a = author.getString("name")
                providerName = "fisenkodv"
            }
            else -> {}
        }
        binding.mainTextView.text = quote.q
        binding.authorTextView.text = quote.a
        binding.poweredByText.isVisible = true
        binding.poweredByText.text = "Powered by $providerName"
    }

    //Display dialog with info
    //TODO fare intent a web o mail?
    private fun displayDialog() {
        val builder = AlertDialog.Builder(requireContext())
            .setTitle(resources.getString(R.string.dialog_title))
            .setMessage(getString(R.string.dialog_message))
            .setPositiveButton(android.R.string.ok, null)
        val alert = builder.create()
        alert.show()
        alert.getButton(DialogInterface.BUTTON_POSITIVE).contentDescription = "OK"
    }


}