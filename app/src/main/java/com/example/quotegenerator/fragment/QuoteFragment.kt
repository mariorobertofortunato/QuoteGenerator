package com.example.quotegenerator.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.quotegenerator.R
import com.example.quotegenerator.databinding.FragmentQuoteBinding
import com.example.quotegenerator.model.Quote
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuoteFragment : Fragment() {

    private lateinit var binding: FragmentQuoteBinding
    private val viewModel by viewModels<ViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val providerId = arguments?.getInt("providerId")

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_quote, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        //If it's the first access show a placeholder text and hide the heart btn
        firstAccess()

        /** LISTENERS */
        //"Generate" btn listener. Generate a new quote from selected API
        binding.mainButton.setOnClickListener {
            viewModel.refreshQuote(providerId!!)
            //Show and Reset heart button
            binding.heartFav.isVisible = true
            binding.heartFav.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            animateHeartBtn()
        }

        //FavoriteQuoteList btn listener. Navigate to FavFragment
        binding.favQuotesBtn.setOnClickListener {
            //viewModel.getQuotes()
            findNavController().navigate(QuoteFragmentDirections.actionQuoteFragmentToFavFragment())
        }

        //Heart (=add to favorites) btn listener
        binding.heartFav.setOnClickListener {
            //Don't add the placeholder text of the ZenQuote API to the Fav
            // TODO what if I hide the heart btn instead?
            if (!viewModel.notPlaceHolderText()) {
                binding.heartFav.setImageResource(R.drawable.ic_baseline_favorite_24)
                //viewModel.insertQuote()
            } else {
                Toast.makeText(requireContext(),"Generate a valid quote first",Toast.LENGTH_SHORT).show()
            }
        }

        //"Powered by click" listener
        binding.poweredByText.setOnClickListener {
            //TODO make explicit intent for navigation to provider website?
        }

        //INFO btn click listener. Show Dialog Alert with info about the app
        binding.infoButton.setOnClickListener {
            displayDialog()
        }

        return binding.root
    }

    //Display dialog with info
    private fun displayDialog() {
        val builder = AlertDialog.Builder(requireContext())
            .setTitle(resources.getString(R.string.dialog_title))
            .setMessage(getString(R.string.dialog_message))
            .setPositiveButton(android.R.string.ok, null)
        val alert = builder.create()
        alert.show()
        alert.getButton(DialogInterface.BUTTON_POSITIVE).contentDescription = "OK"
    }

    private fun firstAccess() {
        if (viewModel.justStarted()) {
            binding.heartFav.isVisible = false
        }
    }

    /**Heart btn animation, basically used as a workaround to the problem of the btn showing before the quote is actually loaded.
     * This way the button surely shows up AFTER the refreshQuote coroutine (or hopefully at least at the same time).
     *
     * Quite pointless per se, but animations are cool, c'mon.*/
    private fun animateHeartBtn () {
        binding.heartFav.alpha = 0f
        binding.heartFav.translationY = 4000f
        binding.heartFav.animate().alpha(1f).translationY(-1f).duration = 750
    }

}