package com.example.quotegenerator.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.example.quotegenerator.R
import com.example.quotegenerator.api.NetworkId0
import com.example.quotegenerator.api.NetworkId1
import com.example.quotegenerator.api.NetworkId2
import com.example.quotegenerator.databinding.FragmentQuoteBinding
import com.example.quotegenerator.model.Quote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject


class QuoteFragment : Fragment() {

    private lateinit var binding: FragmentQuoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set the behaviour of transition between fragments
        val transInflater = TransitionInflater.from(requireContext())
        enterTransition = transInflater.inflateTransition(R.transition.slide_right)
        exitTransition = transInflater.inflateTransition(R.transition.fade)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val providerId = arguments?.getInt("providerId")

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_quote, container, false)

        /** LISTENERS */
        //Main btn click listener
        binding.mainButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                refreshQuote(providerId!!)
            }
            binding.heartFav.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }

        //Fav btn listener
        binding.favQuotesBtn.setOnClickListener {
            findNavController().navigate(QuoteFragmentDirections.actionQuoteFragmentToFavFragment())
        }

        //Heart btn listner
        binding.heartFav.setOnClickListener {
            binding.heartFav.setImageResource(R.drawable.ic_baseline_favorite_24)
            //TODO aggiunge la quote alle fav e qui sono cazzi
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

        val quote = Quote("","","")
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
                //nested
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