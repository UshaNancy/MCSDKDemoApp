package com.example.mcsdkdemoapp.ui.home

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mcsdkdemoapp.*
import com.kobil.mcwmpgettingstarted.util.Utils.showAlertDialog
import com.kobil.wrapper.events.*

class HomeFragment : BaseFragment() {

    private lateinit var homeViewModel: HomeViewModel

    companion object {
        fun newInstance() = HomeFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })


        return root
    }

    override fun onEventReceived(eventFrameworkEvent: EventFrameworkEvent) {
        super.onEventReceived(eventFrameworkEvent)
        when(eventFrameworkEvent) {
            is DisplayConfirmationRequestEvent -> {
               activity?. runOnUiThread {
                       activity?.supportFragmentManager?.beginTransaction()
                       ?.replace(R.id.nav_host_fragment, ConfirmTransactionFragment.newInstance(eventFrameworkEvent.transactionInformation,eventFrameworkEvent.timerValue))
                       ?.commit()

                }
            }
            is TransactionPinRequiredRequestEvent ->{
                activity?. runOnUiThread {
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.nav_host_fragment, TransactionPinFragment.newInstance())
                        ?.commit()

                }}



        }
    }
}