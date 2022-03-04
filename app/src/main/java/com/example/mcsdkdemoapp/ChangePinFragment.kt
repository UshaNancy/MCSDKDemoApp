package com.example.mcsdkdemoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mcsdkdemoapp.databinding.FragmentChangePinBinding
import com.example.mcsdkdemoapp.ui.home.HomeFragment
import com.kobil.wrapper.events.ChangePinResultEvent
import com.kobil.wrapper.events.EventFrameworkEvent

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChangePinFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChangePinFragment private constructor() : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewModel: ChangePinViewModel
    private lateinit var binding: FragmentChangePinBinding

    companion object {
        fun newInstance() = ChangePinFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mcHandler = context?.let { MCHandler.getInstance(it) }

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_change_pin, container, false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(ChangePinViewModel::class.java)
        mcHandler?.triggerStartChangePinEvent()
        binding.changePin.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v?.id) {
            R.id.change_pin -> {

                changePin(binding.oldPin.text.toString(), binding.newPin.text.toString())

            }
        }
    }

    private fun changePin(currentPin: String, newPin: String) {
        mcHandler?.triggerProvideSetNewPinEvent(currentPin, newPin)

    }

    override fun onEventReceived(eventFrameworkEvent: EventFrameworkEvent) {
        super.onEventReceived(eventFrameworkEvent)

        when (eventFrameworkEvent) {
            is ChangePinResultEvent -> {

                activity?.runOnUiThread {
                    Toast.makeText(
                        context,
                        eventFrameworkEvent.retryCounter.toString() + eventFrameworkEvent.status.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.nav_host_fragment, HomeFragment.newInstance())
                        ?.commitNow()
                }


            }
        }
    }

}